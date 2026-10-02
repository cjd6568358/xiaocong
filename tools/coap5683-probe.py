#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
coap5683-probe.py —— 专攻插座的 UDP/5683（CoAP 标准端口）

【为什么会有这个脚本】
    项目早期结论是"CoAP 13078 零响应 ⇒ 局域网直控已排除"。
    但用 ICMP 端口不可达法重测发现：
        5681 关闭 / 5682 关闭 / **5683 没有 ICMP** / 5684 关闭 / 5685 关闭
        / 13078 关闭
    ⇒ **13078 那个端口根本是错的，固件监听的是 CoAP 的标准端口 5683。**

    而 5683 虽然开着，却对我们的 `Scan` 请求不理不睬 —— 这本身也说明问题：
    在 lwIP 里，**只要该端口有 PCB 存在，就不会回 ICMP 端口不可达**，
    哪怕应用层不回应。所以 5683 上确实"有人"，只是没听懂我们的话。

    本脚本就负责把它"问出来"：穷举 CoAP 方法/类型/Uri-Path/payload，
    并记录插座的**任何** UDP 回包。

用法：
    python tools/coap5683-probe.py                       # 默认穷举一遍
    python tools/coap5683-probe.py --seconds 120         # 拉长，配合插座重启
    python tools/coap5683-probe.py --src-port 5683       # 用 5683 作为源端口再试一遍
"""
import argparse
import importlib.util
import os
import random
import socket
import struct
import sys
import threading
import time
from datetime import datetime

from scapy.all import IP, UDP, conf, get_if_hwaddr, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

LOG = None
PLUG = None
HITS = []


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


# ------------------------------------------------------------------ CoAP 构造
def opt(delta, value: bytes):
    return bytes([(delta << 4) | len(value)]) + value


def coap(msg_type, code, path=None, query=None, payload=b"", mid=None, tkl=2):
    """
    msg_type: 0=CON 1=NON 2=ACK 3=RST
    code:     1=GET 2=POST 3=PUT 4=DELETE
    path:     字符串或字符串列表（多个 Uri-Path 选项）
    """
    mid = random.randint(0, 0xFFFF) if mid is None else mid
    token = bytes(random.getrandbits(8) for _ in range(tkl))
    head = bytes([0x40 | (msg_type << 4) | tkl, code]) + struct.pack(">H", mid) + token

    opts = b""
    prev = 0
    paths = [path] if isinstance(path, str) else (path or [])
    for p in paths:
        b = p.encode()
        opts += opt(11 - prev, b)
        prev = 11
    if query:
        for q in ([query] if isinstance(query, str) else query):
            b = q.encode()
            opts += opt(15 - prev, b)
            prev = 15

    out = head + opts
    if payload:
        out += b"\xff" + payload
    return out


def variants():
    """穷举候选请求：(说明, 字节)"""
    v = []
    # —— 照抄 libxcsdk.so 的原生格式 ——
    v.append(("原生 Scan {\"scanType\":\"0\"}", coap(0, 1, "Scan", payload=b'{"scanType":"0"}')))
    v.append(("原生 Scan type=1 +productId",
              coap(0, 1, "Scan", payload=b'{"scanType":"1","productId":"381785"}')))
    v.append(("原生 Getkey", coap(0, 1, "Getkey", query="Getkey", payload=b'{"Query":"1"}')))

    # —— 变体：类型 / 方法 ——
    v.append(("NON Scan", coap(1, 1, "Scan", payload=b'{"scanType":"0"}')))
    v.append(("POST Scan", coap(0, 2, "Scan", payload=b'{"scanType":"0"}')))
    v.append(("GET Scan 无 payload", coap(0, 1, "Scan")))
    v.append(("GET 无 Uri-Path", coap(0, 1)))
    v.append(("GET 无 token", coap(0, 1, "Scan", tkl=0)))
    v.append(("GET 8 字节 token", coap(0, 1, "Scan", tkl=8)))

    # —— 猜测 Uri-Path ——
    for p in ["scan", "SCAN", "snapshot", "control", "getkey", "GetKey", "localKey",
              "localkey", "key", "info", "device", "status", "ping", "time",
              "config", "version", "ota", "upgrade", "cmd", "api", "v1", "smart",
              "discover", "get", "set", "switch", "state"]:
        v.append((f"GET Uri-Path={p}", coap(0, 1, p, payload=b'{"scanType":"0"}')))

    # —— 猜 payload 字段 ——
    for body in [b"", b"{}", b'{"Query":"1"}', b'{"query":"1"}', b'{"key":"1"}',
                 b'{"deviceId":"6587529711423210"}',
                 b'{"productId":"381785"}',
                 b'{"mac":"B4E62D3A6E7C"}',
                 b'{"sn":"B4E62D3A6E7C"}']:
        v.append((f"Scan payload={body!r}", coap(0, 1, "Scan", payload=body)))

    # —— 完全不是 CoAP 的裸探测（万一 5683 上是别的东西）——
    v.append(("裸 8 字节 0x00", b"\x00" * 8))
    v.append(("裸文本 ping", b"ping"))
    v.append(("裸文本 Xiaocong", b"Xiaocong"))
    return v


# ------------------------------------------------------------------ 收包
def on_pkt(pkt):
    if not pkt.haslayer(IP) or pkt[IP].src != PLUG:
        return
    if not pkt.haslayer(UDP):
        return
    u = pkt[UDP]
    body = bytes(u.payload)
    HITS.append((int(u.sport), body))
    log(f"★★★ 【插座 UDP 回包】{PLUG}:{u.sport} → {pkt[IP].dst}:{u.dport}  {len(body)} 字节", important=True)
    log(f"  hex : {body.hex()}")
    log(f"  text: {body.decode('utf-8', 'replace')!r}")


# ------------------------------------------------------------------ 主逻辑
def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--port", type=int, default=5683)
    ap.add_argument("--src-port", type=int, default=0, help="0=用临时端口；填 5683 则以 5683 为源端口")
    ap.add_argument("--repeat", type=int, default=2, help="每个变体发几次")
    ap.add_argument("--interval", type=float, default=0.5)
    ap.add_argument("--seconds", type=int, default=0, help=">0 则循环重跑，直到超时")
    ap.add_argument("--targets", default="unicast",
                    choices=["unicast", "broadcast", "all"],
                    help="发给谁：unicast=插座IP / broadcast=255.255.255.255+子网广播 / all=都要")
    ap.add_argument("--log", default="coap5683.log")
    a = ap.parse_args()

    if a.targets == "broadcast":
        a.dst_list = ["255.255.255.255", "192.168.1.255"]
    elif a.targets == "all":
        a.dst_list = [a.plug, "255.255.255.255", "192.168.1.255"]
    else:
        a.dst_list = [a.plug]

    global LOG, PLUG
    PLUG = a.plug
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== coap5683-probe  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ifname, iface = lm.pick_iface(a.our_ip)
    our_mac = get_if_hwaddr(ifname)
    log(f"网卡={ifname}  本机={a.our_ip}  插座={a.plug}  目标端口={a.port}")

    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
    try:
        sock.bind((a.our_ip, a.src_port))
    except OSError as e:
        log(f"❌ bind {a.our_ip}:{a.src_port} 失败：{e}")
        return 1
    sock.settimeout(0.05)
    log(f"源端口 = {sock.getsockname()[1]}")

    stop = {"v": False}
    th = threading.Thread(target=lambda: sniff(iface=iface, prn=on_pkt, store=False,
                                              filter=f"ip src {a.plug}",
                                              stop_filter=lambda p: stop["v"],
                                              timeout=a.seconds if a.seconds else 900),
                          daemon=True)
    th.start()

    vs = variants()
    log(f"共 {len(vs)} 个候选请求 × {a.repeat} 次 → {a.dst_list}:{a.port}\n")

    t0 = time.time()
    round_no = 0
    while not stop["v"]:
        round_no += 1
        if a.seconds and time.time() - t0 > a.seconds:
            break
        for name, pkt in vs:
            for _ in range(a.repeat):
                for dst in a.dst_list:
                    try:
                        sock.sendto(pkt, (dst, a.port))
                    except Exception:
                        pass
                try:
                    while True:
                        d, addr = sock.recvfrom(4096)
                        log(f"★★★ 【socket 收到回包】{addr}  {len(d)} 字节  hex={d.hex()}", important=True)
                except socket.timeout:
                    pass
                except Exception:
                    pass
                time.sleep(a.interval)
        log(f"第 {round_no} 轮发完（{len(vs)*a.repeat} 个包），累计回包 {len(HITS)} 个")
        if not a.seconds:
            break
        time.sleep(2)

    stop["v"] = True
    time.sleep(0.5)

    log("")
    log("=" * 56)
    log(f"共发出 {len(vs)*a.repeat*max(round_no,1)} 个包，收到插座 UDP 回包 {len(HITS)} 个")
    log("=" * 56)
    if HITS:
        log("✅ 5683 上有服务在回应！看上面的 hex 分析协议。", important=True)
    else:
        log("⚠️ 5683 端口**没有 ICMP 拒绝**（说明有监听），但对以上", important=True)
        log("   %d 种请求全部沉默。" % len(vs), important=True)
        log("   下一步思路：", important=True)
        log("     1) 该 socket 可能是 lwIP 的 connected UDP（绑定了特定对端），只认特定源；", important=True)
        log("     2) 它可能只在**配网模式**下才响应（需在 AP 模式重测）；", important=True)
        log("     3) 它可能不是 CoAP，而是私有 UDP 协议。", important=True)

    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
