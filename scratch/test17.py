#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""验证 REDIRECT 机制本身：临时把 DNS 劫持规则改成匹配【本机 MAC】，
然后用普通 socket 向真 223.5.5.5:53 查询，看是否被拉回本地劫持。测完删除临时规则。
"""
import os
import socket
import struct

import paramiko

PW = os.environ["SSH_PW"]
PRE = "export LD_LIBRARY_PATH=/f4610u/lib; "
IPT = "/f4610u/bin/iptables_upx"
PC_MAC = "A0:B3:39:80:DA:8B"
HIJACK = "203.0.113.9"

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=30):
    _, o, e = cli.exec_command(PRE + c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


TEMP = ["-i", "br0", "-m", "mac", "--mac-source", PC_MAC,
        "-p", "udp", "--dport", "53", "-j", "REDIRECT", "--to-ports", "5353"]


def dns_query(name, server):
    q = struct.pack(">HHHHHH", 0x2222, 0x0100, 1, 0, 0, 0)
    for lab in name.split("."):
        q += bytes([len(lab)]) + lab.encode()
    q += b"\x00" + struct.pack(">HH", 1, 1)
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.settimeout(4)
    s.sendto(q, (server, 53))
    try:
        data, _ = s.recvfrom(2048)
    except Exception as e:
        s.close()
        return None, None, f"超时({type(e).__name__})"
    src = "?"
    rcode = data[3] & 0x0F
    ancount = struct.unpack(">H", data[6:8])[0]
    ips, i = [], 12
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
        typ, _, _ttl, rdlen = struct.unpack(">HHIH", data[i:i + 10])
        i += 10
        if typ == 1 and rdlen == 4:
            ips.append(".".join(str(b) for b in data[i:i + 4]))
        i += rdlen
    s.close()
    return rcode, ips, None


print("## 本机联网方式")
print(sh("echo '(下面用 Windows 侧判断)'"))
print("## 加临时规则（匹配本机 MAC）")
print(sh(f"{IPT} -t nat -I PREROUTING 1 " + " ".join(TEMP) + " 2>&1; echo rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | sed -n '1,4p'"))

print("\n## 用普通 socket 向【真 223.5.5.5:53】查询 iot.ixiaocong.com")
rc, ips, err = dns_query("iot.ixiaocong.com", "223.5.5.5")
print(f"    rcode={rc}  answers={ips}  err={err}")
verdict = "✅ REDIRECT 生效，被拉到本地劫持" if ips and HIJACK in ips else "❌ 没被劫持"
print("    →", verdict)

print("\n## 同一路径查询普通域名（确认劫持后转发链路也通）")
rc, ips, err = dns_query("www.qq.com", "223.5.5.5")
print(f"    rcode={rc}  answers={ips}  err={err}")

print("\n## 删临时规则")
print(sh(f"{IPT} -t nat -D PREROUTING " + " ".join(TEMP) + " 2>&1; echo 已删 rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | sed -n '1,4p'"))

print("\n## 服务端日志（看有没有这两条劫持记录）")
print(sh("tail -8 /tmp/ixc.log 2>/dev/null"))

cli.close()
