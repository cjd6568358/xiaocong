# -*- coding: utf-8 -*-
"""接口名对照实验（v2）：自己构造 DNS 报文，不碰 nslookup 的中文输出。

每组实验都用【不同的 DNS 服务器 IP】，保证每次都是一条全新的 conntrack 流，
从而绕开「已有流被快转引擎旁路」的干扰。

判定：解析结果 == 203.0.113.9  → 该 -i 接口名有效
"""
import os, sys, time, socket, struct, random
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

# ---------- 原生 DNS 查询 ----------
SERVERS = ["8.8.8.8", "1.1.1.1", "9.9.9.9", "208.67.222.222", "114.114.114.114",
           "119.29.29.29", "223.6.6.6", "180.76.76.76"]

def dnsq(name, server, timeout=4):
    tid = random.randint(0, 65535)
    qn = b"".join(bytes([len(p)]) + p.encode() for p in name.split(".")) + b"\x00"
    msg = struct.pack(">HHHHHH", tid, 0x0100, 1, 0, 0, 0) + qn + struct.pack(">HH", 1, 1)
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.settimeout(timeout)
    try:
        s.sendto(msg, (server, 53))
        data, _ = s.recvfrom(2048)
    except Exception as e:
        return None, "%s" % e
    finally:
        s.close()
    ancount = struct.unpack(">H", data[6:8])[0]
    # 跳过 question
    i = 12
    while data[i] != 0:
        i += data[i] + 1
    i += 5
    ips = []
    for _ in range(ancount):
        if i >= len(data):
            break
        if data[i] & 0xC0 == 0xC0:
            i += 2
        else:
            while i < len(data) and data[i] != 0:
                i += data[i] + 1
            i += 1
        if i + 10 > len(data):
            break
        rtype, rcls, ttl, rdlen = struct.unpack(">HHIH", data[i:i+10])
        i += 10
        rdata = data[i:i+rdlen]
        i += rdlen
        if rtype == 1 and rdlen == 4:
            ips.append(".".join(str(b) for b in rdata))
    return (ips[0] if ips else None), ips

# ---------- 连光猫 ----------
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

print("=== 接口清单 ===")
print(sh("cat /proc/net/dev | awk 'NR>2{print $1}' | tr -d ':' | tr '\\n' ' '"))
print()

PC = "192.168.1.19"
n = 0
def fresh():
    global n
    s = SERVERS[n % len(SERVERS)]
    n += 1
    return s

print("=== 基线（无临时规则）===")
for _ in range(2):
    sv = fresh()
    ip, raw = dnsq("iot.ixiaocong.com", sv)
    print("  查 %-16s → %s" % (sv, ip))
print()

for ifname in ["br0", "wlan0", "wlan4", "eth0"]:
    ins = sh(f"{IPT} -t nat -I PREROUTING 1 -i {ifname} -s {PC} -p udp --dport 53 "
             f"-j REDIRECT --to-ports 5353 2>&1")
    if ins.strip():
        print(f"[{ifname}] 插入返回: {ins}")
    time.sleep(0.8)
    sv = fresh()
    ip, raw = dnsq("iot.ixiaocong.com", sv)
    cnt = sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3p'")
    pkts = cnt.split()[0] if cnt else "?"
    ok = "✅ 命中" if ip == "203.0.113.9" else "❌ 未命中"
    print(f"[{ifname}] 查 {sv:<16} → {ip:<16} 规则计数={pkts:<6} {ok}")
    sh(f"{IPT} -t nat -D PREROUTING -i {ifname} -s {PC} -p udp --dport 53 "
       f"-j REDIRECT --to-ports 5353 2>&1")
    time.sleep(0.4)

print()
print("=== 清理后 ===")
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers | sed -n '3,5p'"))
cli.close()
