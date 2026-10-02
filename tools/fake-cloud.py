#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
fake-cloud.py —— 冒充官方云端（用户态 TCP + TLS + MQTT），**可主动下发控制**

【为什么要它】
    厂商云端 iot.ixiaocong.com 已注销（NXDOMAIN），插座拿不到 IP 就永远不上线。
    实测证明：**固件不校验证书** ⇒ 自签证书即可完成 TLS 握手 ⇒ 可以完全冒充官方云端。

【两个必须知道的坑】
    1. 固件**拒绝私有网段 IP**（防 DNS 重绑定）：伪造应答给 192.168.1.x 时，
       插座解析成功、也发了 ARP，但**死活不发 TCP**。必须给**公网 IP**（如 203.0.113.9）。
    2. 端口是 **8188**，不是 443。
    因为公网 IP 不在本子网，插座会把 SYN 交给"网关"——网关被我们 ARP 欺骗，
    包就落到我们网卡。但操作系统不认这个 IP、不会接管连接，
    所以必须**在用户态自己实现 TCP**（SYN → SYN-ACK → ACK → 数据 → ACK）。

【TLS】
    插座 ClientHello 是 **TLS 1.1 + 仅 RSA 密钥交换 + 零扩展（连 SNI 都没有）**。
    新版 OpenSSL 默认禁用 TLS1.0/1.1 且要求 SECLEVEL≥1 → 必须：
      minimum_version=TLSv1 / maximum_version=TLSv1_1
      set_ciphers("ALL:@SECLEVEL=0")
      **RSA 证书**（EC 证书不行，插座不支持 ECDHE）
    用 ssl.MemoryBIO 把插座字节喂进内存 BIO，再把 SSL 对象吐出的字节用 scapy 发回去。

【控制（本轮新增）】
    ★ 实测发现：插座**从不发 SUBSCRIBE**，只有 CONNECT + PUBLISH。
      说明这套"MQTT"只借用了 MQTT 的报文框架做**点对点**传输：
      路由靠 payload 的 receiveId，服务端直接往这条 TCP 连接里写 PUBLISH 即可。
    所以 --control 就是往插座那条 TLS 连接里塞一个 PUBLISH（topic=control），
    再靠它下一帧 snapshot 的 switch 是否翻转来验证是否真的受控。

用法：
    # 只观察（默认）
    python tools/fake-cloud.py --target 192.168.1.23 --seconds 600
    # 观察 + 上线 8 秒开灯、20 秒关灯
    python tools/fake-cloud.py --target 192.168.1.23 \\
        --control "8:switch=1" --control "20:switch=0"
    # 常驻（0 = 不限时）
    python tools/fake-cloud.py --seconds 0

输出：
    fake-cloud.log     可读日志
    fake-cloud.jsonl   结构化样本，**逐条实时落盘**（进程被杀也不丢数据）
"""
import argparse
import importlib.util
import json
import os
import random
import signal
import socket
import ssl
import struct
import sys
import threading
import time
from datetime import datetime

from scapy.all import Ether, IP, TCP, UDP, DNS, DNSQR, DNSRR, conf, get_if_hwaddr, sendp, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

DOMAIN = "iot.ixiaocong.com"
DEVICE_ID = "6587529711423210"                        # 插座自己的 ID（= 它的 MQTT clientId = snapshot.senderId）
APP_CLIENT_ID = "f9ee07acc3412b0f538d778ab05e5e80"    # 配网时下发给插座的 App clientId

LOG = None
JSONL = None
LOCK = threading.RLock()
SESSIONS = {}          # peer_port -> Session
LATEST = {"sess": None}
LAST_SNAPSHOT = {}
G = {}                 # 网络上下文（iface / mac / ip / port）
REC_COUNT = 0


# ------------------------------------------------------------------ 日志
def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    with LOCK:
        if LOG:
            LOG.write(line + "\n")
            LOG.flush()


def rec(kind, **kw):
    """写一条结构化样本 —— 立刻落盘，进程被杀也不丢"""
    global REC_COUNT
    r = {"wall": datetime.now().isoformat(timespec="seconds"), "kind": kind}
    r.update(kw)
    REC_COUNT += 1
    with LOCK:
        if JSONL:
            JSONL.write(json.dumps(r, ensure_ascii=False) + "\n")
            JSONL.flush()
    return r


# ------------------------------------------------------------------ MQTT 编解码
MQTT_NAMES = {1: "CONNECT", 2: "CONNACK", 3: "PUBLISH", 4: "PUBACK", 5: "PUBREC", 6: "PUBREL",
              7: "PUBCOMP", 8: "SUBSCRIBE", 9: "SUBACK", 10: "UNSUBSCRIBE", 11: "UNSUBACK",
              12: "PINGREQ", 13: "PINGRESP", 14: "DISCONNECT"}


def _rl_enc(n):
    out = b""
    while True:
        d = n % 128
        n //= 128
        if n > 0:
            d |= 0x80
        out += bytes([d])
        if n == 0:
            return out


def _rl_dec(b, i):
    mult, val, j = 1, 0, i
    while j < len(b) and j - i < 4:
        d = b[j]
        val += (d & 0x7F) * mult
        mult *= 128
        j += 1
        if not (d & 0x80):
            return val, j
    return None, i


def _u16(b, o):
    return struct.unpack(">H", b[o:o + 2])[0]


def mqtt_pack_publish(topic, payload: bytes, qos=0, pid=1):
    t = topic.encode()
    body = struct.pack(">H", len(t)) + t
    if qos > 0:
        body += struct.pack(">H", pid)
    body += payload
    return bytes([0x30 | (qos << 1)]) + _rl_enc(len(body)) + body


def mqtt_parse_stream(buf):
    """从字节流里解出尽可能多的 MQTT 报文，返回 (packets, 剩余残包)"""
    out, i, n = [], 0, len(buf)
    while i + 1 < n:
        b0 = buf[i]
        rl, j = _rl_dec(buf, i + 1)
        if rl is None:
            break
        total = (j - i) + rl
        if n - i < total:
            break
        out.append((b0, buf[j:i + total]))
        i += total
    return out, buf[i:]


def mqtt_describe(b0, body):
    """把一条报文解成人类可读的 dict"""
    ptype = b0 >> 4
    d = {"type": MQTT_NAMES.get(ptype, str(ptype))}
    try:
        if ptype == 1:                              # CONNECT
            o = 0
            pl = _u16(body, o); o += 2
            d["协议"] = body[o:o + pl].decode(errors="replace"); o += pl
            d["级别"] = body[o]; o += 1
            cf = body[o]; o += 1
            d["keepalive"] = _u16(body, o); o += 2
            cl = _u16(body, o); o += 2
            d["clientId"] = body[o:o + cl].decode(errors="replace"); o += cl
            if cf & 0x04:
                wl = _u16(body, o); o += 2
                d["willTopic"] = body[o:o + wl].decode(errors="replace"); o += wl
                wp = _u16(body, o); o += 2
                d["willPayload"] = body[o:o + wp].hex(); o += wp
            if cf & 0x80:
                ul = _u16(body, o); o += 2
                d["username"] = body[o:o + ul].decode(errors="replace"); o += ul
            if cf & 0x40:
                pw = _u16(body, o); o += 2
                raw = body[o:o + pw]; o += pw
                txt = raw.decode(errors="replace")
                d["password"] = txt
                try:                                # 96 字节 ASCII hex = 48 字节真实签名
                    real = bytes.fromhex(txt)
                    d["password_解码"] = f"{len(real)} 字节签名"
                except Exception:
                    pass
        elif ptype == 3:                            # PUBLISH
            tl = _u16(body, 0)
            d["topic"] = body[2:2 + tl].decode(errors="replace")
            o = 2 + tl
            qos = (b0 >> 1) & 3
            d["qos"] = qos
            if qos > 0:
                d["packetId"] = _u16(body, o); o += 2
            payload = body[o:]
            try:
                d["payload"] = json.loads(payload.decode())
            except Exception:
                d["payload_raw"] = payload.decode(errors="replace")
        elif ptype == 8:                            # SUBSCRIBE
            d["packetId"] = _u16(body, 0)
            o, subs = 2, []
            while o < len(body):
                tl = _u16(body, o); o += 2
                t = body[o:o + tl].decode(errors="replace"); o += tl
                subs.append({"topic": t, "qos": body[o]}); o += 1
            d["订阅列表"] = subs
    except Exception as e:
        d["解析错误"] = f"{type(e).__name__} {e}"
    return d


# ------------------------------------------------------------------ TLS
def make_ssl_ctx():
    """
    ★ 关键：插座 ClientHello = TLS 1.1 + 仅 RSA + 零扩展。
      新版 OpenSSL 默认禁 TLS1.0/1.1 且要求 SECLEVEL≥1 → 会报 UNSUPPORTED_PROTOCOL。
    """
    import warnings
    warnings.filterwarnings("ignore", category=DeprecationWarning)
    for crt, key in [("server.crt", "server.key"), ("ec-server.crt", "ec-server.key")]:
        c, k = os.path.join(ROOT, "server", "certs", crt), os.path.join(ROOT, "server", "certs", key)
        if not (os.path.exists(c) and os.path.exists(k)):
            continue
        try:
            ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
            try:
                ctx.minimum_version = ssl.TLSVersion.TLSv1
                ctx.maximum_version = ssl.TLSVersion.TLSv1_1
            except Exception as e:
                log(f"设置 TLS 版本失败：{e}")
            try:
                ctx.set_ciphers("ALL:@SECLEVEL=0")
            except Exception as e:
                log(f"set_ciphers 失败：{e}")
            ctx.load_cert_chain(c, k)
            log(f"TLS 使用证书：{crt}（已放开 TLS1.0/1.1 + SECLEVEL0，兼容插座老栈）")
            return ctx
        except Exception as e:
            log(f"加载 {crt} 失败：{e}")
    log("⚠️ 无可用证书，只抓 ClientHello")
    return None


def tls_new(ctx):
    inc, out = ssl.MemoryBIO(), ssl.MemoryBIO()
    try:
        obj = ctx.wrap_bio(inc, out, server_side=True)
    except Exception as e:
        log(f"wrap_bio 失败：{e}")
        return None
    return {"inc": inc, "out": out, "obj": obj, "done": False}


def tls_drain(sess):
    out = b""
    try:
        while sess.tls and sess.tls["out"].pending:
            out += sess.tls["out"].read()
    except Exception:
        pass
    return out


def tls_write(sess, data: bytes):
    """把数据写进 TLS，返回要发到网线上的字节"""
    if not sess.tls or not sess.tls["done"]:
        return b""
    try:
        sess.tls["obj"].write(data)
    except Exception as e:
        log(f"  TLS 写入失败：{type(e).__name__} {e}")
        return b""
    return tls_drain(sess)


# ------------------------------------------------------------------ 连接
class Session:
    def __init__(self, peer_port, my_isn):
        self.peer_port = peer_port
        self.my_seq = (my_isn + 1) & 0xFFFFFFFF
        self.peer_seq = None
        self.tls = None
        self.tls_done = False
        self.tls_failed = None
        self.mqtt_buf = b""
        self.started = time.time()
        self.est_at = None
        self.last_seen = time.time()
        self.pending = []          # [(seq, data, sent_at, tries)]
        self.closed = False
        self.ctrl_done = set()
        self.my_port = None        # 本会话我们用的源端口（默认 G["port"]）
        self.is_http = False       # True = 这不是 MQTT，是插座来下载固件的 HTTP 连接


def tcp_send(sess, payload=b"", flags="A", seq=None, ack=None):
    """构造并发送一个 TCP 段（自动更新 my_seq）"""
    if seq is None:
        seq = sess.my_seq
    if ack is None:
        ack = sess.peer_seq or 0
    p = (Ether(src=G["our_mac"], dst=G["target_mac"]) /
         IP(src=G["fake_ip"], dst=G["target_ip"]) /
         TCP(sport=(sess.my_port or G["port"]), dport=sess.peer_port,
             flags=flags, seq=seq, ack=ack))
    if payload:
        p = p / payload
    with LOCK:
        sendp(p, iface=G["iface"], verbose=0)


def tcp_send_data(sess, payload):
    """可靠发送（带简单重传）"""
    if not payload:
        return
    seq = sess.my_seq
    with LOCK:
        tcp_send(sess, payload, "PA", seq=seq, ack=sess.peer_seq or 0)
        sess.my_seq = (sess.my_seq + len(payload)) & 0xFFFFFFFF
        sess.pending.append([seq, payload, time.time(), 0])


def on_ack(sess, ack):
    """累计 ACK：ack 覆盖到的已发段可以删掉（忽略序号回绕，本场景不会发生）"""
    with LOCK:
        sess.pending = [x for x in sess.pending if (x[0] + len(x[1])) > ack]


def retransmit(sess):
    now = time.time()
    with LOCK:
        keep = []
        for seq, data, t, tries in sess.pending:
            if now - t > 2.0:
                if tries >= 6:
                    log(f"  ⚠️ 重传 {tries} 次仍无 ACK，放弃该段（{len(data)} 字节）")
                    continue
                tcp_send(sess, data, "PA", seq=seq, ack=sess.peer_seq or 0)
                keep.append([seq, data, now, tries + 1])
            else:
                keep.append([seq, data, t, tries])
        sess.pending = keep


# ------------------------------------------------------------------ 控制
def build_control_payload(field, value):
    return json.dumps({
        "messageId": random.randint(100000, 9999999),
        "protocolVersion": "1.0.0",
        "receiveId": DEVICE_ID,          # 目标设备
        "senderId": APP_CLIENT_ID,       # 冒充 App
        "command": {field: value},
    }, separators=(",", ":")).encode()


def send_control(sess, spec_str):
    """
    spec_str 支持四种形式：
        switch=1                    构造标准 control 报文
        toggle                      按当前状态取反
        raw:{json...}               把 JSON 原样发到 cmd_topic
        raw:topic@@{json...}        把 JSON 原样发到指定 topic（用于试探 OTA 等未知主题）
    """
    topic = G["cmd_topic"]
    if spec_str.startswith("raw:"):
        rest = spec_str[4:]
        if "@@" in rest:
            topic, payload_s = rest.split("@@", 1)
        else:
            payload_s = rest
        payload = payload_s.encode()
        log(f"  ▶▶▶ 【原样下发】topic={topic}  {payload_s}", important=True)
        rec("raw_sent", topic=topic, payload=payload_s)
    else:
        if "=" in spec_str:
            field, raw = spec_str.split("=", 1)
            value = int(raw) if raw.lstrip("-").isdigit() else raw
        else:
            field = "switch" if spec_str == "toggle" else spec_str
            value = 0 if LAST_SNAPSHOT.get(field) else 1     # 无 = 时按当前状态取反
        payload = build_control_payload(field, value)
        log(f"  ▶▶▶ 【下发控制】topic={topic}  {payload.decode()}", important=True)
        rec("control_sent", topic=topic, payload=payload.decode(), field=field, value=value)

    pkt = mqtt_pack_publish(topic, payload, qos=0)
    data = tls_write(sess, pkt)
    if data:
        tcp_send_data(sess, data)
        log(f"  → 已发出（TLS {len(data)} 字节）")
    else:
        log("  ⚠️ TLS 未就绪，控制命令没发出去")


# ------------------------------------------------------------------ 收包处理
def handle_mqtt(sess, app):
    sess.mqtt_buf += app
    pkts, sess.mqtt_buf = mqtt_parse_stream(sess.mqtt_buf)
    for b0, body in pkts:
        info = mqtt_describe(b0, body)
        ptype = b0 >> 4
        log(f"  ▸ MQTT {info['type']}：{json.dumps(info, ensure_ascii=False)}", important=(ptype in (1, 3, 8)))
        rec("mqtt", ptype=ptype, **{k: v for k, v in info.items() if k != "type"})
        if ptype == 1:                                    # CONNECT → CONNACK
            sess.tls["obj"].write(b"\x20\x02\x00\x00")
            log("  → 已回 CONNACK（接受连接）", important=True)
        elif ptype == 12:                                 # PINGREQ → PINGRESP
            sess.tls["obj"].write(b"\xd0\x00")
            log("  → 已回 PINGRESP")
        elif ptype == 8:                                  # SUBSCRIBE → SUBACK
            pid = _u16(body, 0)
            o, granted = 2, []
            while o < len(body):
                tl = _u16(body, o); o += 2 + tl
                granted.append(body[o]); o += 1
            sess.tls["obj"].write(b"\x90" + _rl_enc(len(granted) + 2) +
                                  struct.pack(">H", pid) + bytes(granted))
            log(f"  → 已回 SUBACK（授予 QoS {granted}）", important=True)
        elif ptype == 3:                                  # PUBLISH（设备上报）
            p = info.get("payload")
            if isinstance(p, dict) and isinstance(p.get("snapshot"), dict):
                LAST_SNAPSHOT.clear()
                LAST_SNAPSHOT.update(p["snapshot"])
                log(f"  ★ 设备状态快照：{p['snapshot']}  (rssi={p.get('rssi')})", important=True)
            if info.get("qos", 0) > 0:
                try:
                    tl = _u16(body, 0)
                    pid = body[2 + tl:4 + tl]
                    sess.tls["obj"].write(b"\x40\x02" + pid)
                except Exception:
                    pass


def tls_feed(sess, data):
    try:
        sess.tls["inc"].write(data)
    except Exception:
        return b"", "inconclusive"
    status = "handshaking"
    for _ in range(20):
        try:
            if not sess.tls["done"]:
                sess.tls["obj"].do_handshake()
                sess.tls["done"] = True
                sess.tls_done = True
                sess.est_at = time.time()
                status = "done"
                ver = sess.tls["obj"].version()
                cip = sess.tls["obj"].cipher()
                log(f"  ✅ TLS 握手**成功**（{ver} / {cip[0] if cip else '?'}）"
                    f" → 插座不校验证书！", important=True)
                rec("tls_handshake", result="accepted", version=ver, cipher=cip[0] if cip else None)
            else:
                try:
                    app = sess.tls["obj"].read(65535)
                except ssl.SSLWantReadError:
                    app = b""
                if app:
                    log(f"  ← 明文 {len(app)} 字节：{app[:160]!r}")
                    rec("app_data", length=len(app), hex=app[:4096].hex())
                    handle_mqtt(sess, app)
                break
        except ssl.SSLWantReadError:
            break
        except ssl.SSLError as e:
            msg = str(e)
            if "alert" in msg.lower() or "certificate" in msg.lower():
                log(f"  ❌ TLS 被**拒绝**（收到告警）：{msg}", important=True)
                log("     → 插座校验了证书！必须走 path A（往插座塞自定义 CA/crt）", important=True)
                sess.tls_failed = "rejected"
                status = "rejected"
                rec("tls_handshake", result="rejected", error=msg)
            else:
                log(f"  ⚠️ TLS 未完成：{msg}")
                sess.tls_failed = "inconclusive"
                status = "inconclusive"
                rec("tls_handshake", result="inconclusive", error=msg)
            break
        except Exception as e:
            log(f"  ⚠️ TLS 异常：{type(e).__name__} {e}")
            status = "inconclusive"
            break
    return tls_drain(sess), status


# ------------------------------------------------------------------ 主逻辑
def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--iface", default=None)
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--gateway", default="192.168.1.1")
    ap.add_argument("--target", default=None, help="插座 IP（不给则自动扫描找 B4:E6:2D）")
    ap.add_argument("--fake-ip", default="203.0.113.9", help="★ 必须是公网 IP（固件拒绝私有 IP）")
    ap.add_argument("--port", type=int, default=8188)
    ap.add_argument("--seconds", type=int, default=600, help="0 = 常驻")
    ap.add_argument("--no-tls", action="store_true")
    ap.add_argument("--control", action="append", default=[],
                    metavar="SEC:CMD", help='形如 "8:switch=1"、"20:toggle"，可重复')
    ap.add_argument("--cmd-topic", default="control", help="下发用的主题（默认 control）")
    ap.add_argument("--cmd-file", default=None,
                    help="运行时命令文件：每往里追加一行就立刻下发（如 echo 'switch=1' >> control.cmd）")
    ap.add_argument("--log", default="fake-cloud.log")
    ap.add_argument("--json", default="fake-cloud.jsonl")
    a = ap.parse_args()

    global LOG, JSONL
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== fake-cloud  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")
    JSONL = open(a.json, "w", encoding="utf-8")          # 逐条 flush，不再等退出

    ifname, iface = (a.iface, None) if a.iface else lm.pick_iface(a.our_ip)
    if a.iface:
        iface = conf.ifaces.dev_from_name(a.iface)
    if ifname is None:
        log("❌ 找不到网卡")
        return 1
    our_mac = get_if_hwaddr(ifname) if not a.iface else getattr(iface, "mac", None)
    gw_mac = lm.find_mac(iface, a.our_ip, a.gateway)
    log(f"网卡={ifname}  本机MAC={our_mac}  网关MAC={gw_mac}")
    log(f"冒充云端：{a.fake_ip}:{a.port}   伪造域名：{DOMAIN}")

    target_ip = a.target
    if not target_ip:
        plug = {ip: m for ip, m in lm.arp_table().items() if m.lower().startswith(lm.PLUG_OUI)}
        if plug:
            target_ip = list(plug.items())[0][0]
    if not target_ip:
        hosts = lm.arp_scan(ifname, a.our_ip, 2.5)
        plug = {ip: m for ip, m in hosts.items() if m.lower().startswith(lm.PLUG_OUI)}
        if plug:
            target_ip = list(plug.items())[0][0]
    if not target_ip:
        log("❌ 没找到插座（B4:E6:2D）")
        return 2
    target_mac = lm.find_mac(iface, a.our_ip, target_ip) or \
        next((m for ip, m in lm.arp_table().items() if ip == target_ip), None)
    log(f"插座：{target_ip}  {target_mac}", important=True)

    G.update(iface=iface, our_mac=our_mac, target_mac=target_mac, target_ip=target_ip,
             fake_ip=a.fake_ip, port=a.port, cmd_topic=a.cmd_topic)

    ctx = None if a.no_tls else make_ssl_ctx()
    stop = {"v": False}
    signal.signal(signal.SIGINT, lambda *_: stop.update(v=True))

    # 解析 --control "SEC:CMD"
    controls = []
    for c in a.control:
        if ":" in c:
            s, cmd = c.split(":", 1)
            controls.append((float(s), cmd))
        else:
            controls.append((5.0, c))
    controls.sort()
    if controls:
        log(f"已排定 {len(controls)} 条控制命令：" +
            ", ".join(f"t+{s:g}s {c}" for s, c in controls))

    def handle(pkt):
        try:
            if not pkt.haslayer(Ether) or not pkt.haslayer(IP):
                return
            if pkt[Ether].src.lower() != (target_mac or "").lower():
                return
            ip = pkt[IP]

            # ---------- DNS 伪造 ----------
            if pkt.haslayer(DNS) and pkt[DNS].qr == 0:
                name, qtype = lm.parse_dns_query(pkt)
                dst = ip.dst
                log(f"★ DNS 查询 → {dst}:53  {name}", important=True)
                rec("dns_query", name=name, server=dst)
                q = pkt[DNS]
                src_ip = a.gateway if dst == a.gateway else dst
                resp = (Ether(src=our_mac, dst=target_mac) /
                        IP(src=src_ip, dst=target_ip) /
                        UDP(sport=53, dport=pkt[UDP].sport) /
                        DNS(id=q.id, qr=1, aa=1, rd=q.rd, qd=q.qd,
                            an=DNSRR(rrname=q.qd.qname, type="A", ttl=60, rdata=a.fake_ip)))
                sendp(resp, iface=iface, verbose=0)
                log(f"  → 伪造应答 {name} = {a.fake_ip}（公网 IP，固件才肯连）")
                rec("dns_forged", name=name, answer=a.fake_ip)
                return

            if not pkt.haslayer(TCP):
                return
            tcp = pkt[TCP]
            if ip.dst != a.fake_ip:
                return
            flags = int(tcp.flags)
            payload = bytes(tcp.payload) if tcp.payload else b""

            # ---------- 新连接 ----------
            if flags & 0x02 and not flags & 0x10:      # SYN
                dport = int(tcp.dport)
                my_isn = random.randint(100000, 2 ** 31 - 1)
                sess = Session(tcp.sport, my_isn)
                SESSIONS[tcp.sport] = sess
                if dport != a.port:
                    # ★★★ 插座连到了**别的端口** —— 这是"某条命令被接受了"的最强信号！
                    #     例如 OTA 命令生效后，它去 HTTP 下载固件镜像。
                    #     照常回 SYN-ACK，但标记成 HTTP 会话：只为**看清它请求的 URL**，
                    #     拿到请求就 RST —— 绝不真发固件（避免刷进垃圾）。
                    sess.my_port = dport
                    sess.is_http = True
                    tcp_send(sess, flags="SA", seq=my_isn, ack=tcp.seq + 1)
                    log(f"★★★ 【插座发起新连接！】{target_ip}:{tcp.sport} → "
                        f"{a.fake_ip}:{dport}   ←←← 不是 MQTT 端口，可能有命令生效了！",
                        important=True)
                    rec("new_port_syn", sport=tcp.sport, dport=dport)
                    return
                if ctx:
                    sess.tls = tls_new(ctx)
                LATEST["sess"] = sess
                tcp_send(sess, flags="SA", seq=my_isn, ack=tcp.seq + 1)
                log(f"★★★ 插座连上来了！SYN {target_ip}:{tcp.sport} → {a.fake_ip}:{a.port}"
                    f"（已回 SYN-ACK）", important=True)
                rec("tcp_syn", sport=tcp.sport, dport=dport)
                return

            sess = SESSIONS.get(tcp.sport)
            if not sess:
                # ★ 插座连到了别的端口 —— 这往往是"某条命令被接受了"的最强信号
                #   （例如 OTA 命令生效后，它去下载固件镜像）。一定要显眼地记下来。
                if flags & 0x02 and not flags & 0x10 and int(tcp.dport) != G["port"]:
                    log(f"★★★ 【插座发起新连接】{target_ip}:{tcp.sport} → "
                        f"{a.fake_ip}:{tcp.dport}   ← 可能有命令生效了！", important=True)
                    rec("new_port_syn", sport=tcp.sport, dport=int(tcp.dport))
                # 没有会话却来了数据（可能是我们启动前就连上了）→ 回 RST 让它快速重连
                if not flags & 0x04:
                    log(f"  ⚠️ 收到无会话的数据包（{target_ip}:{tcp.sport} → :{tcp.dport}），回 RST")
                    tcp_send(Session(tcp.sport, 0), flags="RA",
                             seq=tcp.ack if tcp.flags & 0x10 else 0, ack=tcp.seq + len(payload))
                return
            sess.last_seen = time.time()

            # ★ HTTP 会话（插座来下载固件）—— 只记录它请求了什么，然后 RST
            if sess.is_http:
                if payload:
                    txt = payload.decode("utf-8", "replace")
                    log(f"★★★ 【插座 HTTP 请求！】{len(payload)} 字节：\n{txt}", important=True)
                    rec("http_request", length=len(payload),
                        text=txt[:600], hex=payload[:600].hex())
                    tcp_send(sess, flags="RA", seq=sess.my_seq,
                             ack=(tcp.seq + len(payload)) & 0xFFFFFFFF)
                    sess.closed = True
                return

            if flags & 0x01:                            # FIN
                tcp_send(sess, flags="FA", seq=sess.my_seq, ack=tcp.seq + 1 + len(payload))
                sess.closed = True
                log("  插座关闭连接（FIN）")
                return

            if flags & 0x10 and not payload:            # 纯 ACK
                on_ack(sess, tcp.ack)
                return

            out = b""
            if payload:
                if sess.peer_seq is not None and tcp.seq != sess.peer_seq:
                    log(f"  （乱序/重传 seq={tcp.seq} 期望={sess.peer_seq}，只回 ACK）")
                else:
                    sess.peer_seq = (tcp.seq + len(payload)) & 0xFFFFFFFF
                    log(f"  ← 插座数据 {len(payload)} 字节：{payload[:120]!r}")
                    rec("plug_data", length=len(payload), hex=payload[:512].hex())
                    if sess.tls and not sess.tls_failed:
                        out, _ = tls_feed(sess, payload)
                    else:
                        sni = lm.parse_tls_sni(payload)
                        if sni:
                            log(f"  → TLS ClientHello SNI = {sni}", important=True)
                            rec("tls_clienthello", sni=sni)
                if flags & 0x10:
                    on_ack(sess, tcp.ack)

            if out:
                tcp_send_data(sess, out)
                log(f"  → 发回 TLS {len(out)} 字节")
            else:
                tcp_send(sess, flags="A", seq=sess.my_seq, ack=sess.peer_seq or 0)
        except Exception as e:
            log(f"处理包出错：{type(e).__name__} {e}")

    th = threading.Thread(target=lambda: sniff(iface=iface, prn=handle, store=False,
                                              stop_filter=lambda p: stop["v"],
                                              timeout=a.seconds if a.seconds else None),
                          daemon=True)
    th.start()

    cmd_off = {"v": 0}
    if a.cmd_file:
        # 清空旧内容（只保留我们本次写入的）
        with open(a.cmd_file, "w", encoding="utf-8"):
            pass
        log(f"运行时命令文件：{a.cmd_file}  （追加一行即立刻下发，如 switch=1）", important=True)

    def poll_cmd_file():
        try:
            with open(a.cmd_file, "r", encoding="utf-8") as f:
                f.seek(cmd_off["v"])
                lines = [l.strip() for l in f if l.strip()]
                cmd_off["v"] = f.tell()
        except FileNotFoundError:
            return
        except Exception as e:
            log(f"读命令文件失败：{e}")
            return
        for cmd in lines:
            if cmd.startswith("#"):
                continue
            sess = LATEST["sess"]
            if sess and sess.tls_done:
                log(f"\n★ 命令文件要求：{cmd}")
                send_control(sess, cmd)
            else:
                log(f"⚠️ 命令文件里的 '{cmd}' 暂时无法下发：插座还没建立 TLS 会话")

    t0 = time.time()
    log(f"开始 ARP 欺骗（网关 + {a.fake_ip}）" +
        (f"，持续 {a.seconds}s …" if a.seconds else "，常驻 …"))
    try:
        while not stop["v"] and (a.seconds == 0 or time.time() - t0 < a.seconds):
            lm.arp_spoof(target_ip, target_mac, a.gateway, our_mac, iface)
            lm.arp_spoof(target_ip, target_mac, a.fake_ip, our_mac, iface)

            # 会话维护：重传 + 定时控制
            for sess in list(SESSIONS.values()):
                if sess.closed:
                    continue
                retransmit(sess)
                if sess.tls_done and sess.est_at:
                    dt = time.time() - sess.est_at
                    for at, cmd in controls:
                        key = (id(sess), at, cmd)
                        if dt >= at and key not in sess.ctrl_done:
                            sess.ctrl_done.add(key)
                            log(f"\n★ 到达 t+{at:g}s，执行控制：{cmd}")
                            send_control(sess, cmd)

            if a.cmd_file:
                poll_cmd_file()

            time.sleep(1.0)
    except KeyboardInterrupt:
        stop["v"] = True

    log("恢复插座的真实网关 ARP …")
    if gw_mac:
        lm.arp_restore(target_ip, target_mac, a.gateway, gw_mac, iface)

    log("")
    log(f"===== 结束：共 {REC_COUNT} 条记录 =====")
    with LOCK:
        if LOG:
            LOG.close()
        if JSONL:
            JSONL.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
