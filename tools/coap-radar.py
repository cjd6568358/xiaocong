#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
coap-radar.py —— CoAP 局域网控制的**决定性判定**

为什么需要它：
    之前用普通 UDP socket 发 CoAP 拿不到任何响应，但这**不能**区分两种情况：
      (a) 插座根本没监听 13078
      (b) 插座在监听，只是不理会我们的请求（格式/状态不对）
    普通 socket 看不见 ICMP —— 而且 Windows 默认会把 ICMP 端口不可达吞掉。

    ★ 本脚本用 scapy 直接在网卡上嗅探，能看到 OS 看不到的 ICMP：
        - 收到 ICMP type 3 code 3（端口不可达）  ⇒ **13078 是关的，固件没有 CoAP 服务**（定论）
        - 完全没有 ICMP，也没有回包             ⇒ 端口可能开着但被丢弃（或防火墙静默）

报文格式严格照抄 libxcsdk.so 的 `generate_broadcast` / `generate_getlocalkey`：
    Scan   : MID=1, token 2B, Uri-Path="Scan"(opt 11), payload {"scanType":"0"}
    Getkey : MID=1, token 2B, Uri-Path="Getkey"(opt 11), Uri-Query="Getkey"(opt 15), payload {"Query":"1"}

用法：
    python tools/coap-radar.py                       # 默认扫 192.168.1.23，20 秒
    python tools/coap-radar.py --seconds 600         # 常驻（配合插座断电重启，抓开机窗口）
    python tools/coap-radar.py --plug 192.168.1.23 --our-ip 192.168.1.20
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

from scapy.all import ICMP, IP, UDP, conf, get_if_hwaddr, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

LOG = None
PLUG = None
OUR_IP = None
COAP_PORT = 13078

STATS = {"sent": 0, "coap_reply": 0, "icmp_port_unreach": 0, "icmp_other": 0,
         "udp_from_plug": 0, "other_from_plug": 0}
FIRST_SEEN = {"v": None}
SEND_FAIL_LOGGED = set()
CLOSED_PORTS = set()


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
def _opt(delta, value: bytes):
    """单个 CoAP 选项（delta < 13，length < 13）"""
    return bytes([(delta << 4) | len(value)]) + value


def build_scan(scan_type="0", product_id=None, mid=1):
    token = bytes(random.getrandbits(8) for _ in range(2))
    head = bytes([0x44, 0x01]) + struct.pack(">H", mid) + token
    opts = _opt(11, b"Scan")
    if product_id:
        payload = ('{"scanType":"%s","productId":"%s"}' % (scan_type, product_id)).encode()
    else:
        payload = ('{"scanType":"%s"}' % scan_type).encode()
    return head + opts + b"\xff" + payload


def build_getkey(mid=1):
    token = bytes(random.getrandbits(8) for _ in range(2))
    head = bytes([0x44, 0x01]) + struct.pack(">H", mid) + token
    opts = _opt(11, b"Getkey") + _opt(4, b"Getkey")     # 11 -> 15，delta = 4
    return head + opts + b"\xff" + b'{"Query":"1"}\x00'


# ------------------------------------------------------------------ 嗅探
def dport_of_icmp(pkt):
    """
    从 ICMP 载荷里挖出被拒的那个原始 UDP 目的端口。
    scapy 不一定会自动解析 ICMP 错误里内嵌的 IP 头，所以手工按字节解析：
        载荷 = [原始 IP 头 (IHL*4 字节)] [原始 UDP 头前 8 字节]
        UDP 头：sport(2) dport(2) len(2) chksum(2)
    """
    try:
        raw = bytes(pkt[ICMP].payload)
        if len(raw) >= 4 and (raw[0] >> 4) == 4:
            ihl = (raw[0] & 0x0F) * 4
            if len(raw) >= ihl + 4:
                return struct.unpack(">H", raw[ihl + 2:ihl + 4])[0]
    except Exception:
        pass
    return -1


def on_pkt(pkt):
    if not pkt.haslayer(IP):
        return
    ip = pkt[IP]
    if ip.src != PLUG:
        return

    if pkt.haslayer(ICMP):
        icmp = pkt[ICMP]
        code = int(icmp.code)
        if int(icmp.type) == 3 and code == 3:
            STATS["icmp_port_unreach"] += 1
            inner = ""
            try:
                # ICMP 的载荷里嵌着"被拒绝的那个原始 IP 包"
                embedded = pkt[ICMP].payload
                while embedded and not embedded.haslayer(IP):
                    embedded = embedded.payload
                if embedded and embedded.haslayer(IP):
                    eip = embedded[IP]
                    dport = eip.payload.dport if eip.haslayer(UDP) else "?"
                    inner = f"  内嵌被拒包: → {eip.dst}:{dport}"
            except Exception:
                pass
            CLOSED_PORTS.add(dport_of_icmp(pkt))
            log(f"★★★ 收到 ICMP 端口不可达（type 3 code 3）← 来自插座 {PLUG}！{inner}", important=True)
            log(f"     ⇒ 端口 {dport_of_icmp(pkt)} 在插座上是【关闭】的（固件无监听）", important=True)
        else:
            STATS["icmp_other"] += 1
            log(f"  ICMP type={int(icmp.type)} code={code} ← {PLUG}")
        return

    if pkt.haslayer(UDP):
        u = pkt[UDP]
        if int(u.sport) == COAP_PORT or int(u.dport) == COAP_PORT:
            STATS["coap_reply"] += 1
            body = bytes(u.payload)
            log(f"★★★ 【CoAP 回包！】{PLUG}:{u.sport} → {ip.dst}:{u.dport}  {len(body)} 字节", important=True)
            log(f"  hex : {body.hex()}")
            log(f"  text: {body.decode('utf-8', 'replace')!r}")
        else:
            STATS["udp_from_plug"] += 1
            log(f"  UDP {PLUG}:{u.sport} → {ip.dst}:{u.dport}  {len(bytes(u.payload))} 字节")
        return

    STATS["other_from_plug"] += 1
    if STATS["other_from_plug"] <= 20:
        log(f"  插座其他流量：{pkt.summary()}")


# ------------------------------------------------------------------ 主逻辑
def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--seconds", type=int, default=20)
    ap.add_argument("--interval", type=float, default=2.0)
    ap.add_argument("--targets", default="all",
                    choices=["all", "unicast", "broadcast"],
                    help="发给谁：all=广播+子网广播+单播（默认）/ unicast=只发插座 / broadcast=只发广播")
    ap.add_argument("--ports", default="13078",
                    help="目标端口列表（逗号分隔，逐轮轮换）。用它可以顺带扫 CoAP 的标准端口 5683/5684")
    ap.add_argument("--log", default="coap-radar.log")
    a = ap.parse_args()

    global LOG, PLUG, OUR_IP
    PLUG, OUR_IP = a.plug, a.our_ip
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== coap-radar  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ifname, iface = lm.pick_iface(a.our_ip)
    if not ifname:
        log("❌ 找不到网卡")
        return 1
    our_mac = get_if_hwaddr(ifname)
    plug_mac = lm.find_mac(iface, a.our_ip, a.plug)
    log(f"网卡={ifname}  本机={a.our_ip}({our_mac})")
    log(f"目标插座={a.plug}  MAC={plug_mac or '(ARP 表里没有)'}")

    # 发送用普通 UDP socket（避免 OS 回 ICMP 干扰）；接收靠 scapy 嗅探
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
    sock.bind((a.our_ip, 0))
    sock.settimeout(0.2)
    src_port = sock.getsockname()[1]
    log(f"发送 socket 源端口 = {src_port}")

    stop = {"v": False}
    th = threading.Thread(target=lambda: sniff(iface=iface, prn=on_pkt, store=False,
                                              filter=f"ip src {a.plug}",
                                              stop_filter=lambda p: stop["v"],
                                              timeout=a.seconds), daemon=True)
    th.start()

    if a.targets == "unicast":
        targets = [a.plug]
    elif a.targets == "broadcast":
        targets = ["255.255.255.255", "192.168.1.255"]
    else:
        targets = ["255.255.255.255", "192.168.1.255", a.plug]
    variants = [
        ("Scan type=0", build_scan("0")),
        ("Scan type=1 +productId", build_scan("1", "381785")),
        ("Getkey", build_getkey()),
    ]

    log(f"目标：{', '.join(targets)}   端口 {COAP_PORT}   持续 {a.seconds}s")
    log("（CoAP 请求字节与 libxcsdk.so 的 generate_broadcast 完全一致）\n")

    port_list = [int(x) for x in a.ports.split(",") if x.strip()]
    if len(port_list) > 1:
        log(f"多端口模式：{port_list}（逐轮轮换，收到 ICMP 即判定该端口关闭）\n")

    t0 = time.time()
    idx = 0
    while not stop["v"] and time.time() - t0 < a.seconds:
        name, pkt = variants[idx % len(variants)]
        port = port_list[idx % len(port_list)]
        for t in targets:
            try:
                sock.sendto(pkt, (t, port))
                STATS["sent"] += 1
            except Exception as e:
                # Windows 收到 ICMP 端口不可达后，下一次 sendto 可能报 WSAECONNRESET，
                # 这本身就是"端口关闭"的旁证，不必每次都刷屏
                key = (t, port)
                if key not in SEND_FAIL_LOGGED:
                    SEND_FAIL_LOGGED.add(key)
                    log(f"  ⚠️ 发送到 {t}:{port} 报错（多为 ICMP 不可达回灌）：{e}")
        # 顺手读一下有没有普通 UDP 回包（双保险）
        try:
            while True:
                data, addr = sock.recvfrom(4096)
                STATS["coap_reply"] += 1
                log(f"★★★ 【UDP 回包】{addr}  {len(data)} 字节  hex={data.hex()}", important=True)
        except socket.timeout:
            pass
        if idx % 5 == 0:
            log(f"[{idx+1}] 已发 {name} × {len(targets)}   "
                f"(CoAP回包={STATS['coap_reply']} ICMP不可达={STATS['icmp_port_unreach']})")
        idx += 1
        time.sleep(a.interval)

    stop["v"] = True
    time.sleep(0.6)

    log("")
    log("=" * 56)
    log(f"发送 CoAP 请求      : {STATS['sent']}")
    log(f"收到 CoAP/UDP 回包  : {STATS['coap_reply']}")
    log(f"收到 ICMP 端口不可达: {STATS['icmp_port_unreach']}   ← 关键指标")
    log(f"收到其他 ICMP       : {STATS['icmp_other']}")
    log(f"插座其他 UDP        : {STATS['udp_from_plug']}")
    log("=" * 56)
    if len(port_list) > 1:
        tried = set(port_list)
        open_like = sorted(p for p in tried if p not in CLOSED_PORTS)
        log(f"多端口扫描结果：探测 {len(tried)} 个端口，"
            f"确认关闭 {len(CLOSED_PORTS & tried)} 个")
        if open_like:
            log(f"★ 未回 ICMP（即【可能开放】）的端口共 {len(open_like)} 个：", important=True)
            for i in range(0, len(open_like), 16):
                log("     " + ", ".join(str(x) for x in open_like[i:i + 16]))
        else:
            log("所有探测端口都回了 ICMP ⇒ 全部关闭。")

    if STATS["coap_reply"]:
        log("✅ 结论：插座**响应了 CoAP** —— 局域网控制可行！", important=True)
    elif STATS["icmp_port_unreach"]:
        log("❌ 结论：插座回了 ICMP 端口不可达 ⇒ 13078 **是关闭的**，", important=True)
        log("   固件根本没有 CoAP 服务（不是格式问题，也不是状态问题）。", important=True)
    else:
        log("⚠️ 结论：既无回包也无 ICMP ⇒ 端口状态不明（可能开放但静默丢弃，", important=True)
        log("   也可能是设备侧未运行 lwIP 的 ICMP 应答）。建议换个时间/状态重测。", important=True)

    try:
        sock.close()
    except Exception:
        pass
    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
