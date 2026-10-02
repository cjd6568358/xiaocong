'use strict';
/**
 * 局域网 CoAP 设备发现（复刻 libxcsdk.so 的 Scan）
 * 协议依据：docs/03-配网与native.md §7
 *
 *   CoAP v1, Type=CON, Code=0.01(GET), Option 11(Uri-Path)="Scan"
 *   payload {"scanType":"0"}
 *   → 广播 255.255.255.255:13078
 *
 * 用法：
 *   node tools/coap-scan.js                      # 默认 5 轮，每 2s
 *   COAP_ROUNDS=3 COAP_INTERVAL=1500 node tools/coap-scan.js
 *   COAP_BCAST=192.168.1.255 node tools/coap-scan.js     # 指定子网广播
 *
 * 退出码：0 = 有设备响应；3 = 无响应
 */
const dgram = require('dgram');
const crypto = require('crypto');

const PORT = +(process.env.COAP_PORT || 13078);
const BCAST = process.env.COAP_BCAST || '255.255.255.255';
const ROUNDS = +(process.env.COAP_ROUNDS || 5);
const INTERVAL = +(process.env.COAP_INTERVAL || 2000);
// 可选：改为对指定 IP 单播（逗号分隔），用于排除"只忽略广播"的情况
const TARGETS = (process.env.COAP_TARGETS || '').split(',').map((s) => s.trim()).filter(Boolean);

/** 构造 CoAP Scan 请求 */
function buildScan(obj) {
  const token = crypto.randomBytes(4);
  const mid = crypto.randomBytes(2);
  const header = Buffer.from([0x44, 0x01, mid[0], mid[1]]); // Ver1|CON|TKL4, GET
  const opt = Buffer.concat([Buffer.from([0xB4]), Buffer.from('Scan', 'ascii')]); // (11<<4)|4
  const payload = Buffer.from(JSON.stringify(obj), 'utf8');
  return Buffer.concat([header, token, opt, Buffer.from([0xFF]), payload]);
}

/** 解析 CoAP 报文 */
function parse(msg) {
  const tkl = msg[0] & 0x0F;
  const code = msg[1];
  let off = 4 + tkl;
  const opts = [];
  let payload = '';
  let prev = 0;
  while (off < msg.length) {
    if (msg[off] === 0xFF) { payload = msg.slice(off + 1).toString('utf8'); break; }
    let delta = (msg[off] >> 4) & 0x0F;
    let len = msg[off] & 0x0F;
    off++;
    if (delta === 13) { delta = 13 + msg[off++]; }
    else if (delta === 14) { delta = 269 + msg.readUInt16BE(off); off += 2; }
    if (len === 13) { len = 13 + msg[off++]; }
    else if (len === 14) { len = 269 + msg.readUInt16BE(off); off += 2; }
    const num = prev + delta;
    const val = msg.slice(off, off + len);
    off += len;
    opts.push({ num, val: val.toString('utf8') });
    prev = num;
  }
  return { code, opts, payload };
}

const seen = new Map();
const sock = dgram.createSocket({ type: 'udp4', reuseAddr: true });
sock.on('error', (e) => { console.error('[coap] socket error:', e.message); process.exit(1); });

sock.on('message', (msg, rinfo) => {
  const p = parse(msg);
  if (seen.has(rinfo.address)) return;
  seen.set(rinfo.address, p);
  console.log(`\n★ 有设备响应：${rinfo.address}:${rinfo.port}`);
  console.log(`  code = ${p.code}`);
  console.log(`  options = ${p.opts.map((o) => `${o.num}:"${o.val}"`).join(', ') || '(无)'}`);
  console.log(`  payload = ${p.payload || '(空)'}`);
});

sock.bind(() => {
  sock.setBroadcast(true);
  const targets = TARGETS.length ? TARGETS : [BCAST];
  console.log(`CoAP Scan → ${targets.join(', ')}:${PORT}  共 ${ROUNDS} 轮，每 ${INTERVAL}ms`);
  let n = 0;
  const fire = () => {
    n++;
    for (const t of targets) {
      sock.send(buildScan({ scanType: '0' }), PORT, t, (e) => { if (e) console.error('send error:', e.message); });
    }
    console.log(`  [${n}/${ROUNDS}] ${TARGETS.length ? '单播' : '广播'} Scan → ${targets.length} 个目标`);
    if (n >= ROUNDS) {
      clearInterval(timer);
      setTimeout(() => {
        console.log(`\n共发现 ${seen.size} 台设备`);
        sock.close();
        process.exit(seen.size ? 0 : 3);
      }, 3000);
    }
  };
  fire();
  const timer = setInterval(fire, INTERVAL);
});
