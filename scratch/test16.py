#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""ixc-ont 上机验证：部署 → 起服务 → 自动铺规则 → 三条判别测试。

  T1 直接问 192.168.1.1:5353 → 应劫持到 203.0.113.9
  T2 直接问 192.168.1.1:5353 普通域名 → 应正常转发（DNS 不残废）
  T3 伪造【插座 MAC】发 DNS 到 223.5.5.5:53 → 应被 REDIRECT 抓走并劫持（真链路）
  T4 对照：用【本机 MAC】发同样查询 → 应走真 223.5.5.5，拿到 NXDOMAIN（不影响其他设备）
  T5 TLS 探 203.0.113.9:8188 → 握手应成功
"""
import os
import socket
import ssl
import struct
import time

import paramiko
from scapy.all import Ether, IP, UDP, DNS, DNSQR, get_if_list, getmacbyip, srp1

PW = os.environ["SSH_PW"]
ONT = ("192.168.1.1", 10022)
ROOT = r"C:\workspace\xiaocong"
PLUG_MAC = "b4:e6:2d:3a:6e:7c"
HIJACK = "203.0.113.9"
GW = "192.168.1.1"
PC_IP = "192.168.1.20"

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect(*ONT, username="root", password=PW, timeout=12, look_for_keys=False, allow_agent=False)


def sh(c, t=40):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


def put(local, remote):
    cli.exec_command(f"rm -f {remote}")
    time.sleep(0.2)
    stdin, stdout, _ = cli.exec_command(f"cat > {remote}", timeout=300)
    n = 0
    with open(local, "rb") as f:
        while True:
            c = f.read(65536)
            if not c:
                break
            stdin.write(c)
            n += len(c)
    stdin.flush()
    stdin.channel.shutdown_write()
    stdout.channel.recv_exit_status()
    return n


print("=" * 70)
print("1) 传二进制 + 证书到 /tmp")
for local, remote in [
    (os.path.join(ROOT, ".scratch", "ixc-ont-arm64"), "/tmp/ixc-ont"),
    (os.path.join(ROOT, "server", "certs", "server.crt"), "/tmp/server.crt"),
    (os.path.join(ROOT, "server", "certs", "server.key"), "/tmp/server.key"),
]:
    print(f"    {os.path.basename(remote):14s} {put(local, remote):>9,d} bytes")

print("\n2) 起 ixc-ont（-manage-rules 自动铺规则）")
sh("killall fakecloud 2>/dev/null; killall ixc-ont 2>/dev/null; sleep 1")
cmd = ("nohup /tmp/ixc-ont -cert /tmp/server.crt -key /tmp/server.key "
       "-listen :8188 -dns :5353 -hijack-ip " + HIJACK + " "
       "-manage-rules -lan-if br0 -wan-if ppp0 -alias-if br0:0 "
       "-plug-mac " + PLUG_MAC.upper() + " "
       "-http 192.168.1.1:8080 -jsonl /tmp/ixc.jsonl "
       "</dev/null >>/tmp/ixc.log 2>&1 & echo started")
print("   ", sh("chmod +x /tmp/ixc-ont; " + cmd)[0])
time.sleep(4)

print("\n3) 启动日志")
print(sh("cat /tmp/ixc.log 2>/dev/null | head -30"))

print("\n4) 别名与规则落位情况")
print("    br0:0 ->", sh("ifconfig br0:0 2>/dev/null | grep -E 'inet addr' || echo '(无)'"))
print(sh("export LD_LIBRARY_PATH=/f4610u/lib; /f4610u/bin/iptables_upx -t nat -L PREROUTING "
         "-n --line-numbers 2>/dev/null | head -6"))


def dns_query_direct(name, port=5353):
    """直接向 192.168.1.1:<port> 发一条 A 查询，返回 (rcode, 解析结果列表)"""
    txid = 0x1234
    q = struct.pack(">HHHHHH", txid, 0x0100, 1, 0, 0, 0)
    for lab in name.split("."):
        q += bytes([len(lab)]) + lab.encode()
    q += b"\x00" + struct.pack(">HH", 1, 1)
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.settimeout(4)
    s.sendto(q, (GW, port))
    try:
        data, _ = s.recvfrom(2048)
    except Exception as e:
        s.close()
        return None, [f"超时({type(e).__name__})"]
    s.close()
    rcode = data[3] & 0x0F
    ancount = struct.unpack(">H", data[6:8])[0]
    ips = []
    i = 12
    while data[i] != 0:
        i += data[i] + 1
    i += 5
    for _ in range(ancount):
        if data[i] & 0xC0 == 0xC0:
            i += 2
        else:
            while data[i] != 0:
                i += data[i] + 1
            i += 1
        typ, _, ttl, rdlen = struct.unpack(">HHIH", data[i:i + 10])
        i += 10
        if typ == 1 and rdlen == 4:
            ips.append(".".join(str(b) for b in data[i:i + 4]))
        i += rdlen
    return rcode, ips


print("\n" + "=" * 70)
print("T1 直接问 192.168.1.1:5353  → iot.ixiaocong.com")
rc, ips = dns_query_direct("iot.ixiaocong.com")
print(f"    rcode={rc}  answers={ips}   {'✅ 劫持成功' if HIJACK in ips else '❌'}")

print("\nT2 直接问 192.168.1.1:5353  → www.baidu.com（转发是否正常）")
rc, ips = dns_query_direct("www.baidu.com")
print(f"    rcode={rc}  answers={ips}   {'✅ 转发正常' if ips and '超时' not in ips[0] else '❌ 转发坏了'}")

# ---------------- scapy 判别测试 ----------------
iface = None
for i in get_if_list():
    if "3BD620B1" in i:
        iface = i
print(f"\n    网卡 = {iface}")
gwm = getmacbyip(GW)
print(f"    网关 MAC = {gwm}")


def spoof_dns(src_mac, name, label):
    pkt = (Ether(src=src_mac, dst=gwm) /
           IP(src=PC_IP, dst="223.5.5.5") /
           UDP(sport=40123, dport=53) /
           DNS(id=0x4321, rd=1, qd=DNSQR(qname=name)))
    r = srp1(pkt, iface=iface, timeout=5, verbose=0)
    if r is None or not r.haslayer(DNS):
        print(f"    [{label}] 没有收到应答")
        return None
    d = r[DNS]
    ips = []
    try:
        for i in range(d.ancount):
            rr = d.an[i]
            if rr.type == 1:
                ips.append(rr.rdata)
    except Exception:
        pass
    src = r[IP].src if r.haslayer(IP) else "?"
    print(f"    [{label}] 应答来自 {src}  rcode={d.rcode}  answers={ips}")
    return ips


print("\n" + "=" * 70)
print("T3 伪造【插座 MAC】发 DNS 到真 223.5.5.5:53（走 REDIRECT 规则）")
ips3 = spoof_dns(PLUG_MAC, "iot.ixiaocong.com", "T3/plug-mac")
print(f"    {'✅ 被劫持到本地，命中 ' + HIJACK if ips3 and HIJACK in ips3 else '❌ 未被劫持'}")

print("\nT4 对照：用【本机 MAC】发同样查询（应不受影响）")
ips4 = spoof_dns("a0:b3:39:80:da:8b", "iot.ixiaocong.com", "T4/pc-mac")
print(f"    {'✅ 未被劫持（其他设备不受影响）' if not ips4 or HIJACK not in ips4 else '⚠️ 被误劫持'}")

print("\n" + "=" * 70)
print("T5 TLS 探 " + HIJACK + ":8188")
ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
ctx.minimum_version = ssl.TLSVersion.TLSv1
ctx.maximum_version = ssl.TLSVersion.TLSv1_1
ctx.set_ciphers("AES256-SHA:@SECLEVEL=0")
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE
try:
    raw = socket.create_connection((HIJACK, 8188), timeout=5)
    t = ctx.wrap_socket(raw)
    print(f"    ★ TLS 握手成功 {t.version()} {t.cipher()}   ✅")
    t.close()
except Exception as e:
    print(f"    ❌ {type(e).__name__} {e}")

print("\n6) 服务端日志尾部")
print(sh("tail -25 /tmp/ixc.log 2>/dev/null"))
print("\n7) HTTP 控制接口自检")
print(sh("wget -qO- http://192.168.1.1:8080/status 2>&1 | head -3"))

cli.close()
