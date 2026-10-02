'use strict';
/**
 * 零依赖 MQTT 3.1.1 broker（够小葱 App / 插座用）
 *
 * 支持：CONNECT / CONNACK / PUBLISH(QoS0,1) / PUBACK / SUBSCRIBE / SUBACK /
 *       UNSUBSCRIBE / UNSUBACK / PINGREQ / PINGRESP / DISCONNECT
 * 同时监听 TCP 与 TLS（App 用 ssl://）。
 *
 * 设计取舍：这是给"复活一个已死厂商的智能插座"用的最小实现，不是通用 broker。
 * 不实现 QoS2 / 持久会话 / 保留消息（App 用的是 QoS0，不需要）。
 */
const net = require('net');
const tls = require('tls');
const { EventEmitter } = require('events');

// ---------- varint (remaining length) ----------
function encodeLen(n) {
  const out = [];
  do {
    let b = n % 128;
    n = Math.floor(n / 128);
    if (n > 0) b |= 0x80;
    out.push(b);
  } while (n > 0);
  return Buffer.from(out);
}
function decodeLen(buf, off) {
  let mult = 1, val = 0, i = off, b;
  do {
    if (i >= buf.length) return null; // 不完整
    b = buf[i++];
    val += (b & 0x7f) * mult;
    mult *= 128;
  } while (b & 0x80);
  return { value: val, bytes: i - off };
}

// ---------- 读长度前缀字符串 ----------
function readStr(buf, off) {
  if (off + 2 > buf.length) return null;
  const len = buf.readUInt16BE(off);
  if (off + 2 + len > buf.length) return null;
  return { value: buf.slice(off + 2, off + 2 + len), next: off + 2 + len };
}
function writeStr(buf) {
  const b = Buffer.isBuffer(buf) ? buf : Buffer.from(buf);
  const h = Buffer.alloc(2);
  h.writeUInt16BE(b.length, 0);
  return Buffer.concat([h, b]);
}
function writeLen(n) {
  const b = Buffer.alloc(2);
  b.writeUInt16BE(n, 0);
  return b;
}

// ---------- topic 匹配（支持 + 和 #）----------
function topicMatches(filter, topic) {
  if (filter === topic) return true;
  const f = filter.split('/'), t = topic.split('/');
  for (let i = 0; i < f.length; i++) {
    if (f[i] === '#') return true;
    if (i >= t.length) return false;
    if (f[i] === '+') continue;
    if (f[i] !== t[i]) return false;
  }
  return f.length === t.length;
}

class Broker extends EventEmitter {
  /**
   * @param {object} opts
   * @param {number} [opts.tcpPort]    明文端口，0=不启用
   * @param {number} [opts.tlsPort]    TLS 端口（App 用这个）
   * @param {object} [opts.tlsOptions] {key, cert}
   */
  constructor(opts = {}) {
    super();
    this.opts = opts;
    this.clients = new Map();      // connId -> client
    this.connSeq = 0;
    this.servers = [];
  }

  listen(cb) {
    const { tcpPort, tlsPort, tlsOptions } = this.opts;
    const started = [];
    let pending = 0;
    const done = () => { if (--pending === 0) { this.addresses = started; if (cb) cb(started); } };

    if (tcpPort) {
      pending++;
      const s = net.createServer((sock) => this._attach(sock, 'tcp'));
      s.listen(tcpPort, () => { started.push(`tcp://0.0.0.0:${tcpPort}`); done(); });
      this.servers.push(s);
    }
    if (tlsPort && tlsOptions && tlsOptions.key && tlsOptions.cert) {
      pending++;
      const s = tls.createServer(tlsOptions, (sock) => this._attach(sock, 'tls'));
      s.listen(tlsPort, () => { started.push(`ssl://0.0.0.0:${tlsPort}`); done(); });
      this.servers.push(s);
    }
    this.addresses = started;
    if (pending === 0 && cb) cb();
  }

  close() { this.servers.forEach((s) => s.close()); }

  // ---------- 连接处理 ----------
  _attach(sock, kind) {
    const id = ++this.connSeq;
    const c = {
      id, sock, kind, clientId: null, username: null, password: null,
      subs: new Set(), buf: Buffer.alloc(0), alive: true,
    };
    this.clients.set(id, c);
    sock.on('data', (d) => { c.buf = Buffer.concat([c.buf, d]); this._pump(c); });
    sock.on('error', () => this._drop(c));
    sock.on('close', () => this._drop(c));
    sock.setNoDelay && sock.setNoDelay(true);
  }

  _drop(c) {
    if (!c.alive) return;
    c.alive = false;
    this.clients.delete(c.id);
    if (c.clientId) this.emit('disconnect', { clientId: c.clientId });
  }

  _send(c, buf) { try { c.sock.write(buf); } catch (e) { this._drop(c); } }

  /** 解析缓冲区里所有完整报文 */
  _pump(c) {
    for (;;) {
      if (c.buf.length < 2) return;
      const dl = decodeLen(c.buf, 1);
      if (!dl) return;
      const total = 1 + dl.bytes + dl.value;
      if (c.buf.length < total) return;
      const header = c.buf[0];
      const body = c.buf.slice(1 + dl.bytes, total);
      c.buf = c.buf.slice(total);
      try { this._handle(c, header, body); } catch (e) { this.emit('error', e); }
    }
  }

  _handle(c, header, body) {
    const type = header >> 4;
    switch (type) {
      case 1: return this._onConnect(c, body);
      case 3: return this._onPublish(c, header, body);
      case 6: return this._onPubRel(c, body);
      case 8: return this._onSubscribe(c, body);
      case 10: return this._onUnsubscribe(c, body);
      case 12: return this._send(c, Buffer.from([0xd0, 0x00])); // PINGREQ -> PINGRESP
      case 14: return this._drop(c);                             // DISCONNECT
      default: return;
    }
  }

  _onConnect(c, body) {
    let o = 0;
    const pn = readStr(body, o); if (!pn) return this._drop(c); o = pn.next;
    const proto = pn.value.toString();
    o += 1; // protocol level
    const flags = body[o++];
    o += 2; // keepalive
    const cid = readStr(body, o); if (!cid) return this._drop(c); o = cid.next;
    c.clientId = cid.value.toString() || ('anon-' + c.id);
    if (flags & 0x04) { const w = readStr(body, o); if (w) o = w.next; }        // will topic
    if (flags & 0x04) { const w = readStr(body, o); if (w) o = w.next; }        // will payload
    if (flags & 0x80) { const u = readStr(body, o); if (u) { c.username = u.value.toString(); o = u.next; } }
    if (flags & 0x40) { const p = readStr(body, o); if (p) { c.password = p.value.toString(); o = p.next; } }

    // 接受一切（已死厂商，无鉴权基准）。记录身份供上层判断手机/设备。
    const rc = 0;
    this._send(c, Buffer.concat([Buffer.from([0x20, 0x02, 0x00, rc])]));
    this.emit('connect', { clientId: c.clientId, username: c.username, password: c.password, kind: c.kind });
  }

  _onPublish(c, header, body) {
    const qos = (header >> 1) & 0x03;
    const t = readStr(body, 0); if (!t) return this._drop(c);
    let o = t.next, packetId = null;
    if (qos > 0) { packetId = body.readUInt16BE(o); o += 2; }
    const payload = body.slice(o);
    const topic = t.value.toString();

    const msg = { topic, payload, qos, clientId: c.clientId };
    this.emit('message', msg);

    if (qos === 1 && packetId != null) {
      this._send(c, Buffer.from([0x40, 0x02, packetId >> 8, packetId & 0xff])); // PUBACK
    }
    // 转发给订阅者
    this._route(topic, payload, qos);
  }

  _onPubRel(c, body) {
    const pid = body.readUInt16BE(0);
    this._send(c, Buffer.from([0x70, 0x02, pid >> 8, pid & 0xff]));
  }

  _onSubscribe(c, body) {
    const pid = body.readUInt16BE(0);
    let o = 2;
    const granted = [];
    for (;;) {
      const t = readStr(body, o); if (!t) break;
      o = t.next;
      const qos = body[o++];
      c.subs.add(t.value.toString());
      granted.push(qos & 0x03);
      this.emit('subscribe', { clientId: c.clientId, topic: t.value.toString(), qos });
    }
    const head = Buffer.from([0x90, 2 + granted.length]);
    this._send(c, Buffer.concat([head, writeLen(pid), Buffer.from(granted)]));
  }

  _onUnsubscribe(c, body) {
    const pid = body.readUInt16BE(0);
    let o = 2;
    for (;;) {
      const t = readStr(body, o); if (!t) break;
      o = t.next;
      c.subs.delete(t.value.toString());
    }
    this._send(c, Buffer.concat([Buffer.from([0xb0, 0x02]), writeLen(pid)]));
  }

  /** 把消息投递给所有匹配的订阅者 */
  _route(topic, payload, qos) {
    const pkt = this._buildPublish(topic, payload, Math.min(qos || 0, 1));
    for (const c of this.clients.values()) {
      if (!c.alive || !c.clientId) continue;
      for (const f of c.subs) {
        if (topicMatches(f, topic)) { this._send(c, pkt); break; }
      }
    }
  }

  _buildPublish(topic, payload, qos) {
    const tb = writeStr(topic);
    const pb = Buffer.isBuffer(payload) ? payload : Buffer.from(payload);
    let vb = tb;
    if (qos > 0) vb = Buffer.concat([tb, writeLen(0)]); // 简化：packetId 固定 0
    const body = Buffer.concat([vb, pb]);
    return Buffer.concat([Buffer.from([0x30 | (qos << 1)]), encodeLen(body.length), body]);
  }

  /** 供服务端主动下发（设备控制） */
  publish(topic, payload) { this._route(topic, payload, 0); }

  /** 内部订阅：服务端把自己当成一个客户端来收设备消息 */
  subscribeInternal(topic, fn) { this.on('message', (m) => { if (topicMatches(topic, m.topic)) fn(m); }); }
}

module.exports = { Broker, topicMatches };
