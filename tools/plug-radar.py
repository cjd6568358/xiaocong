#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
plug-radar.py —— 被动雷达：插座一上电就报警（比主动 ARP 扫描更灵敏）

为什么需要：
    主动 ARP 扫描只能发现"已经拿到 IP 并愿意应答"的设备。
    但插座开机时会先发 **DHCP Discover/Request（广播）** 和 **ARP 请求（广播）**，
    这些在拿到 IP 之前就发出去了，我们**能直接看到**（Wi-Fi 下广播帧可见）。

    所以被动嗅探是发现插座最早、最灵敏的手段：
      - 看到 b4:e6:2d 发的任何帧  → 插座已经连上本网段（哪怕还没 IP）
      - 看到 DHCP 请求           → 插座正在要 IP
      - 看到新的 ARP 请求源 MAC   → 有新设备加入

用法：
    python tools/plug-radar.py --seconds 300
输出：plug-radar.log（可读）  plug-radar.jsonl（结构化）
"""
import argparse
import importlib.util
import json
import os
import sys
from datetime import datetime

from scapy.all import ARP, BOOTP, DHCP, Ether, IP, TCP, UDP, conf, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

PLUG_OUI = "b4:e6:2d"
LOG = None
RECORDS = []
KNOWN = set()
T0 = None


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def rec(kind, **kw):
    r = {"wall": datetime.now().isoformat(timespec="seconds"), "kind": kind}
    r.update(kw)
    RECORDS.append(r)
    return r


def handle(pkt):
    try:
        if not pkt.haslayer(Ether):
            return
        src = (pkt[Ether].src or "").lower()
        dst = (pkt[Ether].dst or "").lower()
        is_plug = src.startswith(PLUG_OUI) or dst.startswith(PLUG_OUI)

        # 插座发出的任何帧 —— 最高优先级
        if is_plug:
            what = "?"
            if pkt.haslayer(BOOTP):
                what = f"DHCP(xid=0x{pkt[BOOTP].xid:08x})"
            elif pkt.haslayer(ARP):
                a = pkt[ARP]
                what = f"ARP op={a.op} {a.psrc}→{a.pdst}"
            elif pkt.haslayer(UDP):
                what = f"UDP {pkt[UDP].sport}→{pkt[UDP].dport}"
                if pkt.haslayer(DHCP):
                    opts = []
                    for o in pkt[DHCP].options:
                        if isinstance(o, tuple) and o[0] in ("hostname", "requested_addr", "server_id"):
                            opts.append(f"{o[0]}={o[1]}")
                    what += f" DHCP[{', '.join(opts)}]"
            elif pkt.haslayer(TCP):
                what = f"TCP {pkt[TCP].sport}→{pkt[TCP].dport} flags=0x{int(pkt[TCP].flags):02x}"
            elif pkt.haslayer(IP):
                what = f"IP {pkt[IP].src}→{pkt[IP].dst}"
            log(f"★★★ 插座帧：src={src} dst={dst}  {what}", important=True)
            rec("plug_frame", src=src, dst=dst, what=what,
                hex=bytes(pkt)[:128].hex())
            return

        # DHCP（任何设备）—— 看谁在要 IP
        if pkt.haslayer(DHCP) and pkt.haslayer(BOOTP):
            b = pkt[BOOTP]
            mac = b.chaddr.hex()
            mac_s = ":".join(mac[i:i + 2] for i in range(0, 12, 2))
            for o in pkt[DHCP].options:
                if isinstance(o, tuple) and o[0] == "message-type":
                    log(f"DHCP {o[1]}  来自 {mac_s}")
                    rec("dhcp", mac=mac_s, msg_type=o[1])

        # ARP 请求：发现新 MAC
        if pkt.haslayer(ARP) and pkt[ARP].op == 1:
            m = src
            if m not in KNOWN and not m.startswith(("ff:", "01:00:5e", "33:33", "00:00:00")):
                KNOWN.add(m)
                log(f"  新设备 ARP 请求：{m} ({pkt[ARP].psrc}) 找 {pkt[ARP].pdst}")
                rec("new_host", mac=m, ip=pkt[ARP].psrc)
    except Exception as e:
        log(f"处理包出错：{type(e).__name__} {e}")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--seconds", type=int, default=300)
    ap.add_argument("--log", default="plug-radar.log")
    ap.add_argument("--json", default="plug-radar.jsonl")
    a = ap.parse_args()

    global LOG
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== plug-radar  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    name, iface = lm.pick_iface("192.168.1.20")
    if not name:
        log("❌ 找不到 192.168.* 网卡")
        return 1
    log(f"网卡: {name}")
    log(f"被动雷达启动：监听 b4:e6:2d 的任何帧 + 所有 DHCP + 新设备 ARP，持续 {a.seconds}s")
    log("（插座一上电就会发 DHCP/ARP 广播，这里应该第一时间看到）")

    sniff(iface=iface, prn=handle, store=False, timeout=a.seconds)

    with open(a.json, "w", encoding="utf-8") as f:
        for r in RECORDS:
            f.write(json.dumps(r, ensure_ascii=False) + "\n")

    log("")
    log(f"===== 结束：共 {len(RECORDS)} 条记录，见过 {len(KNOWN)} 个设备 =====")
    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
