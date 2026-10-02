#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
lan-port-watch.py —— 轻量哨兵：长期盯着插座 UDP/13078 和 5683 的开合状态

目的：
    已验证稳态下 13078 关闭、5683 绑定但静默。
    文档说 CoAP 监听只在**配网模式**跑 —— 那开机/配网窗口里它会不会短暂打开？
    本脚本用极低速率（默认每 15s 各探一次）长期蹲守，一旦端口状态翻转就高亮记录。

判据同 udp-port-verify：收到 ICMP 端口不可达 = 关闭；连续 N 次无 ICMP = 可能开放。
为了不打扰插座，速率压到最低，并且只在**状态变化**时才刷屏。

用法：
    python tools/lan-port-watch.py --seconds 7200     # 蹲 2 小时
"""
import argparse
import importlib.util
import os
import socket
import struct
import sys
import threading
import time
from datetime import datetime

from scapy.all import ICMP, IP, conf, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

PLUG = None
LOG = None
STOP = {"v": False}
ICMP_HITS = {}
LAST = {}          # port -> "open"/"closed"
SILENT_STREAK = {}


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def dport_of_icmp(pkt):
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
    if not pkt.haslayer(IP) or pkt[IP].src != PLUG or not pkt.haslayer(ICMP):
        return
    ic = pkt[ICMP]
    if int(ic.type) == 3 and int(ic.code) == 3:
        ICMP_HITS[dport_of_icmp(pkt)] = ICMP_HITS.get(dport_of_icmp(pkt), 0) + 1


def probe(port, payload=b"\x00" * 8):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.settimeout(0.3)
        s.sendto(payload, (PLUG, port))
        try:
            s.recvfrom(2048)
        except Exception:
            pass
    except Exception:
        pass
    finally:
        s.close()


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--ports", default="13078,5683")
    ap.add_argument("--interval", type=float, default=15.0)
    ap.add_argument("--silent-open", type=int, default=4,
                    help="连续多少次无 ICMP 就判为'可能开放'")
    ap.add_argument("--seconds", type=int, default=7200)
    ap.add_argument("--log", default="lan-port-watch.log")
    a = ap.parse_args()

    global PLUG, LOG
    PLUG = a.plug
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== lan-port-watch  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ifname, iface = lm.pick_iface(a.our_ip)
    if not ifname:
        log("❌ 找不到网卡")
        return 1
    ports = [int(x) for x in a.ports.split(",") if x.strip()]
    log(f"网卡={ifname}  插座={a.plug}  盯防端口={ports}  间隔={a.interval}s  "
        f"持续={a.seconds}s")

    th = threading.Thread(target=lambda: sniff(iface=iface, prn=on_pkt, store=False,
                                               filter=f"ip src {a.plug}",
                                               stop_filter=lambda p: STOP["v"]),
                          daemon=True)
    th.start()

    t0 = time.time()
    rnd = 0
    while not STOP["v"] and time.time() - t0 < a.seconds:
        rnd += 1
        for p in ports:
            before = ICMP_HITS.get(p, 0)
            probe(p)
            time.sleep(0.4)
            after = ICMP_HITS.get(p, 0)
            got = after > before
            if got:
                SILENT_STREAK[p] = 0
                state = "closed"
            else:
                SILENT_STREAK[p] = SILENT_STREAK.get(p, 0) + 1
                state = "open" if SILENT_STREAK[p] >= a.silent_open else "unknown"

            prev = LAST.get(p)
            if state != prev and state != "unknown":
                LAST[p] = state
                icon = "❌关闭" if state == "closed" else "✅可能开放"
                log(f"★ 状态变化：UDP/{p} → {icon}"
                    f"（连续静默 {SILENT_STREAK[p]} 次）", important=True)
            elif rnd % 20 == 0:
                log(f"  …第 {rnd} 轮：{p} = {state}"
                    f"（静默连击 {SILENT_STREAK[p]}）")
        time.sleep(a.interval)

    STOP["v"] = True
    log("")
    log("===== 哨兵结束 =====")
    for p in ports:
        log(f"  UDP/{p}  最终状态 = {LAST.get(p, 'unknown')}  "
            f"累计 ICMP = {ICMP_HITS.get(p, 0)}")
    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
