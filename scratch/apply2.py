# -*- coding: utf-8 -*-
"""最小验证：加 2 条 nat OUTPUT 规则，劫持光猫自己的 DNS 上游。
   不改任何进程、不改 user_init.sh。备份已有（/tmp/iptables-nat-backup-*.txt）。
"""
import os, sys, time, socket, struct, random
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

def dnsq(name, server, timeout=5):
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
        rt, rc, ttl, rl = struct.unpack(">HHIH", data[i:i+10]); i += 10
        rd = data[i:i+rl]; i += rl
        if rt == 1 and rl == 4: ips.append(".".join(str(b) for b in rd))
    return (ips[0] if ips else None), ips

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

print("=== 0. user_init.sh 真实位置 ===")
print(sh("ls -la /f4610u/user_init.sh /f4610u/etc/user_init.sh /etc/user_init.sh 2>&1 | head"))
print()

print("=== 1. 插入 2 条 OUTPUT 规则 ===")
for ip in ["210.22.70.225", "210.22.70.3"]:
    out = sh(f"{IPT} -t nat -I OUTPUT 1 -p udp -d {ip} --dport 53 "
             f"-j REDIRECT --to-ports 5353 2>&1")
    print(f"  -d {ip}: {'OK' if not out.strip() else out}")
print()
print(sh(f"{IPT} -t nat -S OUTPUT"))
print()

print("=== 2. 立即验证（PC → 光猫 DNS）===")
time.sleep(2)
for dom in ["iot.ixiaocong.com", "www.baidu.com", "www.qq.com"]:
    ip, _ = dnsq(dom, "192.168.1.1")
    tag = "✅ 劫持成功" if ip == "203.0.113.9" else "（正常解析）"
    print(f"  {dom:<20} → {ip}   {tag}")
print()

print("=== 3. nat OUTPUT 计数 ===")
print(sh(f"{IPT} -t nat -L OUTPUT -n -v --line-numbers | head -5"))
print()

print("=== 4. ixc-go 日志尾（改前最后 5 行作对照）===")
print(sh("tail -5 /tmp/ixc-go.log"))
print()
print("=== 5. 等 90 秒看插座是否连上 ===")
time.sleep(90)
print(sh("tail -12 /tmp/ixc-go.log"))
print()
print("=== 6. 规则计数 & 插座 conntrack ===")
print(sh(f"{IPT} -t nat -L OUTPUT -n -v --line-numbers | head -5"))
print(sh("cat /proc/net/nf_conntrack | grep -E 'src=192\\.168\\.1\\.11 '"))
print()
print("=== 7. 再确认全家 DNS 正常 ===")
for dom in ["www.baidu.com", "www.taobao.com"]:
    print(f"  {dom:<18} → {dnsq(dom, '192.168.1.1')[0]}")
cli.close()
