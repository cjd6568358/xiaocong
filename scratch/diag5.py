# -*- coding: utf-8 -*-
"""验证假设：已建立的 conntrack 流会被中兴快转引擎旁路，iptables 再也看不到。

实验设计（用 PC 当受控流量源，服务器固定为 114.114.114.114 保证可达）：
  A) 先【不插规则】连查 3 次 → 把这条流建起来并刷成 ASSURED
  B) 再插规则 → 连查 3 次（同一条流）
  C) 换一个新服务器插同样的规则 → 查 1 次（全新流）
如果 B 不命中而 C 命中 → 快转假设成立。
"""
import os, sys, time, socket, struct, random
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

def dnsq(name, server, timeout=4):
    tid = random.randint(0, 65535)
    qn = b"".join(bytes([len(p)]) + p.encode() for p in name.split(".")) + b"\x00"
    msg = struct.pack(">HHHHHH", tid, 0x0100, 1, 0, 0, 0) + qn + struct.pack(">HH", 1, 1)
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM); s.settimeout(timeout)
    try:
        s.sendto(msg, (server, 53)); data, _ = s.recvfrom(2048)
    except Exception as e:
        return None, "%s" % e
    finally:
        s.close()
    ancount = struct.unpack(">H", data[6:8])[0]
    i = 12
    while data[i] != 0: i += data[i] + 1
    i += 5
    ips = []
    for _ in range(ancount):
        if i >= len(data): break
        if data[i] & 0xC0 == 0xC0: i += 2
        else:
            while i < len(data) and data[i] != 0: i += data[i] + 1
            i += 1
        if i + 10 > len(data): break
        rtype, rcls, ttl, rdlen = struct.unpack(">HHIH", data[i:i+10]); i += 10
        rdata = data[i:i+rdlen]; i += rdlen
        if rtype == 1 and rdlen == 4: ips.append(".".join(str(b) for b in rdata))
    return (ips[0] if ips else None), ips

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

print("=== 0. 找 conntrack 工具 / 快转开关 ===")
print(sh("ls /f4610u/bin/ 2>/dev/null | grep -iE 'conntrack|fc|hwnat|fast|ppe|flow' ; echo '-- proc --'; "
         "ls /proc/sys/net/netfilter/ 2>/dev/null | tr '\\n' ' '; echo; "
         "ls /proc/ 2>/dev/null | grep -iE 'fc|hwnat|fast|pon|ponmac' | tr '\\n' ' '"))
print()

PC = "192.168.1.19"
OLD = "114.114.114.114"
NEW = "223.6.6.6"

print("=== A. 不插规则，连查 3 次建流 ===")
for k in range(3):
    ip, _ = dnsq("iot.ixiaocong.com", OLD)
    print("   #%d → %s" % (k + 1, ip))
print("  conntrack:", sh(f"cat /proc/net/nf_conntrack | grep '{PC}.*dport=53' | head -3"))
print()

print("=== B. 插规则后，同一条流再查 3 次 ===")
sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -s {PC} -p udp --dport 53 -j REDIRECT --to-ports 5353")
time.sleep(0.8)
for k in range(3):
    ip, _ = dnsq("iot.ixiaocong.com", OLD)
    print("   #%d → %s" % (k + 1, ip))
cnt = sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3p'")
print("  B 阶段规则计数:", cnt.split()[0] if cnt else "?")
print()

print("=== C. 同一条规则，换全新服务器查 1 次 ===")
ip, _ = dnsq("iot.ixiaocong.com", NEW)
cnt = sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3p'")
print("   → %s   规则计数:%s" % (ip, cnt.split()[0] if cnt else "?"))
print()

sh(f"{IPT} -t nat -D PREROUTING -i br0 -s {PC} -p udp --dport 53 -j REDIRECT --to-ports 5353")
print("已清理。最终:", sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers | sed -n '3,5p'"))
cli.close()
