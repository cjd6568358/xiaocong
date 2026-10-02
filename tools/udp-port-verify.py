#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
udp-port-verify.py —— 严格判定插座上某个 UDP 端口到底开没开

为什么不能用 coap-radar 的结论：
    "没收到 ICMP 端口不可达 ⇒ 端口开放" 这条判据有**丢包假阳性** ——
    只要那一次探测包或 ICMP 回包在网络里丢了，就会把关闭的端口误判成开放。
    （之前 1-1024 全扫时 18~32 号端口集体"开放"就是明证，物理上不可能。）

本脚本的严格做法：
    1. 每个端口重复 N 次（默认 12），间隔 ≥0.5s（避免 lwIP ICMP 限速）；
    2. 混入**阳性对照端口**（几乎不可能开放的高位端口）——
       如果对照端口一次 ICMP 都没有，说明方法/网络有问题，结论不可信；
    3. 判定规则：
         - 至少 1 次 ICMP ⇒ 【关闭】（一次不可达就足以证明没有 PCB 绑定）
         - N 次全无 ICMP ⇒ 【静默】（可能开放，也可能被静默丢弃）

用法：
    python tools/udp-port-verify.py --ports 5683,13078,5684,5353,45678 --trials 12
    python tools/udp-port-verify.py --ports 5683 --trials 20 --gap 0.6
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

from scapy.all import ICMP, IP, conf, get_if_hwaddr, sniff

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location("lanmitm", os.path.join(HERE, "lan-mitm.py"))
lm = importlib.util.module_from_spec(spec)
spec.loader.exec_module(lm)
conf.verb = 0

PLUG = None
LOG = None
ICMP_HITS = {}          # dport -> count
ICMP_SEEN = []          # (dport, time)
STOP = {"v": False}


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
    """从 ICMP 载荷里挖出被拒的原始 UDP 目的端口（scapy 不保证自动解析内嵌 IP 头）"""
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
    if not pkt.haslayer(IP) or pkt[IP].src != PLUG:
        return
    if not pkt.haslayer(ICMP):
        return
    ic = pkt[ICMP]
    if int(ic.type) == 3 and int(ic.code) == 3:
        dp = dport_of_icmp(pkt)
        ICMP_HITS[dp] = ICMP_HITS.get(dp, 0) + 1
        ICMP_SEEN.append((dp, time.time()))
        log(f"  ← ICMP 端口不可达：插座 {PLUG} 拒绝 UDP/{dp}")
    else:
        log(f"  ← ICMP type={int(ic.type)} code={int(ic.code)}（非端口不可达）")


def send_probe(dst, port, payload=b"\x00"):
    """
    用**全新的 OS socket** 发一个 UDP 包。
    每次新建是为了绕开 Windows 的坑：收到 ICMP 后 socket 会被置错误态，
    不 drain 的话后续 sendto 会静默失败。
    """
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.settimeout(0.25)
        s.sendto(payload, (dst, port))
        try:
            s.recvfrom(2048)
            return "reply"          # 竟然有回包
        except socket.timeout:
            return "sent"
        except ConnectionResetError:
            return "icmp"           # OS 也看到了不可达
    except Exception as e:
        return f"err:{e}"
    finally:
        s.close()


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--plug", default="192.168.1.23")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--ports", default="5683,13078,5684,5353,45678",
                    help="要验证的端口（逗号分隔）。建议混入一个高位对照端口")
    ap.add_argument("--trials", type=int, default=12, help="每个端口重复次数")
    ap.add_argument("--gap", type=float, default=0.5, help="两次发送间隔（秒）")
    ap.add_argument("--payload", default="zero", choices=["zero", "coap"],
                    help="zero=全零字节；coap=标准 CoAP GET /Scan 请求")
    ap.add_argument("--log", default="udp-verify.log")
    a = ap.parse_args()

    global PLUG, LOG
    PLUG = a.plug
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== udp-port-verify  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ifname, iface = lm.pick_iface(a.our_ip)
    if not ifname:
        log("❌ 找不到网卡")
        return 1
    plug_mac = lm.find_mac(iface, a.our_ip, a.plug)
    log(f"网卡={ifname}  本机={a.our_ip}  插座={a.plug}  MAC={plug_mac or '(ARP 无)'}")

    ports = [int(x) for x in a.ports.split(",") if x.strip()]
    if 45678 not in ports:
        log("提示：建议在 --ports 里保留 45678 作阳性对照")

    if a.payload == "coap":
        import importlib.util as _iu
        cs = _iu.spec_from_file_location("cr", os.path.join(HERE, "coap-radar.py"))
        cr = _iu.module_from_spec(cs)
        cs.loader.exec_module(cr)
        probe = cr.build_scan("0")
        log(f"载荷 = 标准 CoAP GET /Scan（{len(probe)} 字节）")
    else:
        probe = b"\x00" * 8
        log("载荷 = 8 字节 0x00")

    # 注意：本机 Npcap 上 `icmp and ip src X` 这种组合过滤器抓不到任何包，
    # 必须只用 `ip src X`，ICMP 判断放到回调里做。
    th = threading.Thread(target=lambda: sniff(iface=iface, prn=on_pkt, store=False,
                                               filter=f"ip src {a.plug}",
                                               stop_filter=lambda p: STOP["v"]),
                          daemon=True)
    th.start()

    log(f"\n开始：{len(ports)} 个端口 × {a.trials} 轮，间隔 {a.gap}s "
        f"（总时长约 {len(ports)*a.trials*a.gap:.0f}s）\n")

    for r in range(a.trials):
        for p in ports:
            r0 = len(ICMP_SEEN)
            res = send_probe(a.plug, p, probe)
            time.sleep(a.gap)
            got = len(ICMP_SEEN) - r0
            mark = "❌关闭" if got else "  ·  "
            log(f"  [轮{r+1:2d}] UDP/{p:<6} sendto={res:<6} 本轮ICMP={got}  {mark}")
        log(f"  —— 第 {r+1} 轮结束：累计 ICMP {sum(ICMP_HITS.values())} 个，"
            f"涉及端口 {sorted(ICMP_HITS)}")

    STOP["v"] = True
    time.sleep(0.8)

    log("")
    log("=" * 60)
    log(f"探测 {len(ports)} 个端口，每个 {a.trials} 次")
    log("=" * 60)
    closed, silent = [], []
    for p in ports:
        n = ICMP_HITS.get(p, 0)
        if n:
            closed.append((p, n))
            log(f"  UDP/{p:<6} 【关闭】  收到 {n}/{a.trials} 次 ICMP 端口不可达")
        else:
            silent.append(p)
            log(f"  UDP/{p:<6} 【静默】  0/{a.trials} 次 ICMP（可能开放，或被静默丢弃）")

    log("")
    ctrl = 45678
    if ctrl in ports:
        if ICMP_HITS.get(ctrl):
            log(f"✅ 阳性对照 UDP/{ctrl} 正常收到 ICMP ⇒ 本方法有效，结论可信。", important=True)
        else:
            log(f"⚠️ 阳性对照 UDP/{ctrl} 也没回 ICMP ⇒ 方法/网络有问题，"
                f"本次'静默'结论**不可信**！", important=True)
    log("")
    if silent:
        log(f"⇒ 需要进一步确认的'静默'端口：{silent}", important=True)
    else:
        log("⇒ 所有端口都确认关闭。", important=True)

    if LOG:
        LOG.close()
    return 0


if __name__ == "__main__":
    sys.exit(main())
