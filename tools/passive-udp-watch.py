#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
passive-udp-watch.py —— 被动监听插座的全部 UDP 流量，判断 5683 到底是谁在用

背景：
    udp-port-verify 已确认 5683 被"绑定"（0 ICMP），但对 48 种 CoAP 请求全静默。
    最可能的解释：那不是**服务端** socket，而是固件里某个 **客户端** socket
    绑了本地端口 5683（只等特定对端回包，收到别的就丢弃）。

判据：
    - 如果插座**从 sport=5683 主动发包** ⇒ 确认是客户端 socket（服务端假设推翻）
    - 如果插座从不使用 5683 收发   ⇒ 可能是残留 PCB / 特殊防火墙规则
    - 顺带把插座所有非 MQTT 的 UDP 收全，看有没有隐藏的发现/上报通道

用法：
    python tools/passive-udp-watch.py --seconds 90
"""
import argparse
import importlib.util
import os
import sys
import threading
import time
from collections import Counter, defaultdict
from datetime import datetime

from scapy.all import ICMP, IP, TCP, UDP, conf, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

PLUG = None
LOG = None
STOP = {"v": False}

UDP_EVENTS = []                                   # (sport, dport, len)
SPORT_CNT = Counter()
DPORT_CNT = Counter()
PAIR_CNT = Counter()
OTHER = Counter()
SAMPLE = defaultdict(list)


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def on_pkt(pkt):
    if not pkt.haslayer(IP) or pkt[IP].src != PLUG:
        return
    ip = pkt[IP]
    if pkt.haslayer(UDP):
        u = pkt[UDP]
        sp, dp = int(u.sport), int(u.dport)
        body = bytes(u.payload)
        UDP_EVENTS.append((sp, dp, len(body)))
        SPORT_CNT[sp] += 1
        DPORT_CNT[dp] += 1
        PAIR_CNT[(sp, dp)] += 1
        if len(SAMPLE[(sp, dp)]) < 3:
            SAMPLE[(sp, dp)].append(body[:120].hex())
        flag = ""
        if sp == 5683 or dp == 5683:
            flag = "  ★★★ 涉及 5683！"
        log(f"  UDP  {PLUG}:{sp:<6} → {ip.dst}:{dp:<6}  {len(body):>5} 字节{flag}",
            important=bool(flag))
        return
    if pkt.haslayer(ICMP):
        ic = pkt[ICMP]
        OTHER[f"icmp/{int(ic.type)}/{int(ic.code)}"] += 1
        return
    if pkt.haslayer(TCP):
        t = pkt[TCP]
        OTHER[f"tcp/{int(t.dport)}"] += 1
        return
    OTHER[f"other/{ip.proto}"] += 1


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--seconds", type=int, default=90)
    ap.add_argument("--log", default="passive-udp.log")
    a = ap.parse_args()

    global PLUG, LOG
    PLUG = a.plug
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== passive-udp-watch  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ifname, iface = lm.pick_iface(a.our_ip)
    if not ifname:
        log("❌ 找不到网卡")
        return 1
    log(f"网卡={ifname}  本机={a.our_ip}  被动监听插座 {a.plug} 的流量 {a.seconds}s")
    log("（只嗅探，不发送任何包）\n")

    th = threading.Thread(target=lambda: sniff(iface=iface, prn=on_pkt, store=False,
                                               filter=f"ip src {a.plug}",
                                               stop_filter=lambda p: STOP["v"]),
                          daemon=True)
    th.start()

    t0 = time.time()
    while time.time() - t0 < a.seconds:
        time.sleep(5)
        el = int(time.time() - t0)
        log(f"  …{el}s  已收 UDP {len(UDP_EVENTS)} 个，其他 {sum(OTHER.values())} 个")

    STOP["v"] = True
    time.sleep(0.8)

    log("")
    log("=" * 62)
    log(f"监听 {a.seconds}s，插座共发出 UDP {len(UDP_EVENTS)} 个")
    log("=" * 62)

    log("\n【按源端口统计】")
    for sp, n in SPORT_CNT.most_common():
        tag = "  ★ 这是 5683！" if sp == 5683 else ""
        log(f"  sport={sp:<6} {n:>4} 次{tag}")

    log("\n【按目的端口统计】")
    for dp, n in DPORT_CNT.most_common():
        tag = "  ★ 这是 5683！" if dp == 5683 else ""
        log(f"  dport={dp:<6} {n:>4} 次{tag}")

    log("\n【源→目的 组合】")
    for (sp, dp), n in PAIR_CNT.most_common(15):
        log(f"  {sp:<6} → {dp:<6} {n:>4} 次   样本={SAMPLE[(sp,dp)][:1]}")

    if OTHER:
        log("\n【其他协议】")
        for k, n in OTHER.most_common():
            log(f"  {k:<16} {n} 次")

    log("")
    if SPORT_CNT.get(5683):
        log(f"✅ 插座**从 sport=5683 主动发过 {SPORT_CNT[5683]} 个包** ⇒ "
            f"5683 是固件里的**客户端** socket，不是服务端。", important=True)
        log("   ⇒ 局域网 CoAP 服务端假设被推翻；5683 不提供控制入口。", important=True)
    elif DPORT_CNT.get(5683):
        log("⚠️ 插座往 5683 发过包（作客户端访问别人）——同样说明它是客户端。", important=True)
    else:
        log("ℹ️ 监听期间插座完全没用过 5683 ⇒ 无法从流量侧判定；"
            "更可能是残留 PCB 或特殊丢弃规则。", important=True)

    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
