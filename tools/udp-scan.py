#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
udp-scan.py —— 可靠的 UDP 端口扫描（靠 ICMP 端口不可达判定）

背景：
    普通 UDP 扫描"没响应"无法区分「端口关闭」和「端口开着但不理你」。
    但实测这台插座（ESP8266/lwIP）**会回 ICMP type 3 code 3（端口不可达）**。
    于是判据变得非常干净：
        - 回了 ICMP 端口不可达  ⇒ 端口【关闭】
        - 一直不回 ICMP         ⇒ 端口【开放】或【被静默丢弃】

    用 scapy 直接在网卡上嗅探 ICMP（Windows 默认会把 ICMP 吞掉，普通 socket 看不见）。

用法：
    python tools/udp-scan.py                          # 扫常用端口表
    python tools/udp-scan.py --range 1-2000           # 扫一段
    python tools/udp-scan.py --ports 5683,5684,13078  # 只扫指定端口
    python tools/udp-scan.py --range 1-65535 --rate 120
"""
import argparse
import importlib.util
import os
import random
import socket
import sys
import threading
import time
from datetime import datetime

from scapy.all import Ether, ICMP, IP, UDP, conf, get_if_hwaddr, sendp, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

# CoAP 标准端口 5683/5684 最值得试；其余是本项目/常见 IoT 端口
COMMON = [
    53, 67, 68, 69, 123, 137, 138, 161, 162, 500, 514, 520, 623, 1900,
    5353, 5683, 5684, 5658, 6666, 6667, 6668, 8000, 8080, 8888, 9999,
    13000, 13078, 13079, 13080, 13081, 13082, 13083, 13084, 13085,
    49152, 49153, 49154,
]

CLOSED = set()
SEEN = set()
LOG = None
STOP = {"v": False}
STATS = {"icmp_total": 0, "non_icmp": 0}


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def parse_ports(a):
    if a.ports:
        return sorted({int(x) for x in a.ports.split(",") if x.strip()})
    if a.range:
        lo, hi = a.range.split("-")
        return list(range(int(lo), int(hi) + 1))
    return COMMON


def on_pkt(pkt):
    if not pkt.haslayer(IP) or pkt[IP].src != PLUG:
        return
    if not pkt.haslayer(ICMP):
        STATS["non_icmp"] += 1
        if STATS["non_icmp"] <= 10:
            log(f"  （调试）插座非 ICMP 包：{pkt.summary()}")
        return
    STATS["icmp_total"] += 1
    icmp = pkt[ICMP]
    if int(icmp.type) != 3 or int(icmp.code) != 3:
        return
    # 从 ICMP 载荷里挖出被拒的原始 UDP 目的端口
    try:
        emb = pkt[ICMP].payload
        while emb and not emb.haslayer(IP):
            emb = emb.payload
        if emb and emb.haslayer(UDP):
            dport = int(emb[UDP].dport)
            if dport not in CLOSED:
                CLOSED.add(dport)
                log(f"  ✗ {dport:5d} 关闭（收到 ICMP 端口不可达）")
    except Exception:
        pass


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--ports", default=None)
    ap.add_argument("--range", default=None)
    ap.add_argument("--rate", type=float, default=40.0, help="每秒发包数（ESP8266 别打太快）")
    ap.add_argument("--send-method", choices=["socket", "sendp"], default="socket",
                    help="socket=走操作系统协议栈；sendp=L2 原始注入")
    ap.add_argument("--payload", choices=["zero", "coap"], default="zero",
                    help="探测载荷：zero=8 个零字节 / coap=真实 CoAP Scan 请求")
    ap.add_argument("--rounds", type=int, default=2, help="对每个端口重复几轮（防丢包）")
    ap.add_argument("--settle", type=float, default=2.5, help="每轮结束后等待秒数")
    ap.add_argument("--log", default="udp-scan.log")
    a = ap.parse_args()

    global LOG, PLUG
    PLUG = a.plug
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== udp-scan  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ports = parse_ports(a)
    ifname, iface = lm.pick_iface(a.our_ip)
    our_mac = get_if_hwaddr(ifname)
    plug_mac = lm.find_mac(iface, a.our_ip, a.plug)
    if not plug_mac:
        log(f"❌ 拿不到插座 {a.plug} 的 MAC（先用 scan-lan.py）")
        return 1
    log(f"网卡={ifname}  本机={a.our_ip}  插座={a.plug} ({plug_mac})")
    log(f"待扫 {len(ports)} 个端口，每端口 {a.rounds} 轮，速率 {a.rate}/s")
    log(f"端口范围：{ports[0]} … {ports[-1]}\n")

    # ★ 过滤器写法：`icmp and ip src X` 在本机 Npcap 上实测收不到包，
    #   只用 `ip src X` 正常 —— 所以过滤得宽一点，类型判断交给 on_pkt。
    def sniffer():
        try:
            log(f"嗅探线程已启动（iface={ifname}, filter='ip src {a.plug}'）")
            sniff(iface=iface, prn=on_pkt, store=False,
                  filter=f"ip src {a.plug}",
                  stop_filter=lambda p: STOP["v"],
                  timeout=len(ports) / max(a.rate, 1) * a.rounds + a.settle * a.rounds + 60)
            log("嗅探线程已退出")
        except Exception as e:
            log(f"❌ 嗅探线程异常：{type(e).__name__} {e}")

    # ★ 实测：只有走 OS 协议栈（socket）发包，插座才会回 ICMP 端口不可达。
    #   用 sendp 在 L2 直接注入时，插座**不回** ICMP。
    #   ★ 另一个坑：socket 必须在**启动嗅探线程之前**建好并 bind，
    #   否则插座不产生 ICMP 回包（与 coap-radar.py 的差异就在这里）。
    sock = None
    if a.send_method == "socket":
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
        sock.bind((a.our_ip, 0))
        sock.settimeout(0.02)

    th = threading.Thread(target=sniffer, daemon=True)
    th.start()

    delay = 1.0 / a.rate
    src_port = 40000
    t0 = time.time()
    for rnd in range(a.rounds):
        for p in ports:
            src_port += 1
            if src_port > 65000:
                src_port = 40000
            probe = b"\x00" * 8
            if a.payload == "coap":
                tok = bytes(random.getrandbits(8) for _ in range(2))
                probe = (bytes([0x44, 0x01, 0x00, 0x01]) + tok +
                         bytes([0xB4]) + b"Scan" + b"\xff" + b'{"scanType":"0"}')
            if sock:
                try:
                    sock.sendto(probe, (a.plug, p))
                except BlockingIOError:
                    pass
                except Exception:
                    pass                      # ICMP 回灌会让 sendto 报错，忽略
                # ★ 关键：把 socket 上挂着的错误"排空"。Windows 收到 ICMP 端口不可达后
                #   会把错误挂到 socket 上，若不读掉，**后续 sendto 会静默失败**（包根本没发出去）。
                #   这是本项目踩到的一个真坑：不加这一步，扫描结果会全是"未回 ICMP"。
                try:
                    while True:
                        sock.recvfrom(2048)
                except socket.timeout:
                    pass
                except Exception:
                    pass
            else:
                sendp(Ether(src=our_mac, dst=plug_mac) /
                      IP(src=a.our_ip, dst=a.plug) /
                      UDP(sport=src_port, dport=p) / probe,
                      iface=iface, verbose=0)
            time.sleep(delay)
        log(f"第 {rnd+1}/{a.rounds} 轮发完，已判明关闭 {len(CLOSED)} 个，等 ICMP 回流…")
        time.sleep(a.settle)

    if sock:
        try:
            sock.close()
        except Exception:
            pass

    STOP["v"] = True
    time.sleep(0.8)

    open_like = [p for p in ports if p not in CLOSED]
    log("")
    log("=" * 56)
    log(f"耗时 {time.time()-t0:.0f}s   共探测 {len(ports)} 个端口 × {a.rounds} 轮")
    log(f"确认关闭（收到 ICMP 端口不可达）：{len(CLOSED)}")
    log(f"未回 ICMP（开放 或 静默丢弃）：{len(open_like)}")
    log("=" * 56)
    if open_like:
        log("⚠️ 以下端口**没有**回 ICMP，值得进一步验证：", important=True)
        for p in open_like:
            log(f"     {p}")
        log("")
        log("  提示：ESP8266 在 UDP 端口关闭时通常会回 ICMP，但不回也可能是", important=True)
        log("  它太忙/丢包/该端口有监听但静默。用 coap-radar.py 对这些端口发真实", important=True)
        log("  CoAP 请求做二次确认。", important=True)
    else:
        log("✅ 全部端口都回了 ICMP ⇒ 插座上**没有任何开放的 UDP 端口**。", important=True)

    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
