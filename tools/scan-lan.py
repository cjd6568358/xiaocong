#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
scan-lan.py —— 主动 ARP 全网段扫描（不依赖 Windows ARP 缓存）

为什么需要：
    Windows 的 `arp -a` 只列出"最近和本机交换过流量"的邻居。
    一台刚上线的插座如果还没跟本机通信，就**不会**出现在 arp -a 里。
    所以轮询 arp -a 找插座是不可靠的 —— 必须主动发 ARP 请求问遍全网段。

实现要点（踩坑记录）：
    在 Npcap 上 scapy 的 `srp()`（发+收）经常返回 0 个应答，即使网络完全正常；
    而 `sendp()` 单独发包是可靠的。
    所以这里用 **sendp 广播 ARP 请求 → 等 1.5s → 读 Windows ARP 表** 的组合，
    比 srp 稳得多。srp 结果若拿到也一并合并。

用法：
    python tools/scan-lan.py                 # 打印全网段 + 高亮插座
    python tools/scan-lan.py --json out.json # 落盘
退出码：0=找到插座, 1=没找到
"""
import argparse
import importlib.util
import json
import os
import sys
import time

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)

from scapy.all import ARP, Ether, sendp  # noqa: E402


def sweep(iface_name, our_ip, timeout=2.0):
    prefix = ".".join(our_ip.split(".")[:3])
    pkt = Ether(dst="ff:ff:ff:ff:ff:ff") / ARP(pdst=f"{prefix}.1-254")
    sendp(pkt, iface=iface_name, verbose=0)
    time.sleep(1.5)
    hosts = lm.arp_table()
    hosts = {ip: mac for ip, mac in hosts.items() if ip.startswith(prefix + ".")}
    # srp 兜底（拿到就用，拿不到无所谓）
    try:
        srp_hosts = lm.arp_scan(iface_name, our_ip, timeout=timeout)
        for ip, mac in srp_hosts.items():
            hosts.setdefault(ip, mac)
    except Exception:
        pass
    return hosts


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--json", default=None)
    ap.add_argument("--timeout", type=float, default=2.0)
    a = ap.parse_args()

    name, iface = lm.pick_iface(a.our_ip)
    if not name:
        print("❌ 没找到 192.168.* 网卡", flush=True)
        sys.exit(2)
    desc = getattr(iface, "description", "") or ""
    print(f"网卡: {name}  ({desc})  本机IP={getattr(iface,'ip','?')}", flush=True)

    hosts = sweep(name, a.our_ip, a.timeout)
    print(f"主动 ARP 扫描到 {len(hosts)} 台设备：", flush=True)
    plug = None
    for ip in sorted(hosts, key=lambda x: int(x.split(".")[-1])):
        mac = hosts[ip].lower()
        tag = ""
        if mac.startswith("b4:e6:2d"):
            tag = "   ★★★ 插座(Espressif) ★★★"
            plug = (ip, mac)
        print(f"  {ip:<16} {mac}{tag}", flush=True)

    if a.json:
        with open(a.json, "w", encoding="utf-8") as f:
            json.dump({"iface": name, "hosts": hosts, "plug": plug}, f, ensure_ascii=False, indent=2)

    if plug:
        print(f"\n✅ 找到插座: {plug[0]}  {plug[1]}", flush=True)
        sys.exit(0)
    print("\n❌ 全网段没有 B4:E6:2D（插座不在局域网）", flush=True)
    sys.exit(1)


if __name__ == "__main__":
    main()
