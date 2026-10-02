# -*- coding: utf-8 -*-
"""执行批准的方案 A+B：按域名劫持（不写死 MAC/IP）。
   A: nat PREROUTING -i br0 -p udp --dport 53 -m string "ixiaocong" --algo bm -> REDIRECT 5353
   B: nat OUTPUT     -p udp --dport 53 -m string "ixiaocong" --algo bm -> REDIRECT 5353
   执行前先备份三张表到 /tmp，可一键回滚。
"""
import os, sys, time, socket, struct, random
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

# ---------- 原生 DNS 查询（不用 nslookup，避免中文编码坑） ----------
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

TS = time.strftime("%Y%m%d-%H%M%S")
print("=== 1. 备份（/tmp，重启即消失，仅作本次回滚用）===")
for t in ["nat", "filter", "mangle"]:
    p = f"/tmp/iptables-{t}-backup-{TS}.txt"
    sh(f"{IPT} -t {t} -S > {p} 2>&1")
    print("  ", sh(f"wc -l < {p}"), "行 →", p)
print()

print("=== 2. 改前：nat OUTPUT 现状 ===")
print(sh(f"{IPT} -t nat -S OUTPUT"))
print()

print("=== 3. 改前基线：从 PC 向光猫 DNS 查 iot.ixiaocong.com ===")
print("   →", dnsq("iot.ixiaocong.com", "192.168.1.1")[0])
print("   (对照) www.baidu.com →", dnsq("www.baidu.com", "192.168.1.1")[0])
print()

print("=== 4. 插入规则 A / B ===")
A = f'{IPT} -t nat -I PREROUTING 1 -i br0 -p udp --dport 53 -m string --string "ixiaocong" --algo bm -j REDIRECT --to-ports 5353'
B = f'{IPT} -t nat -I OUTPUT 1 -p udp --dport 53 -m string --string "ixiaocong" --algo bm -j REDIRECT --to-ports 5353'
for tag, r in [("A", A), ("B", B)]:
    out = sh(r + " 2>&1")
    print(f"  {tag}: {'OK' if not out.strip() else out}")
print()

print("=== 5. 改后规则快照 ===")
print(sh(f"{IPT} -t nat -S PREROUTING | head -4"))
print(sh(f"{IPT} -t nat -S OUTPUT | head -4"))
print()

print("=== 6. 验证（等 3 秒让规则生效）===")
time.sleep(3)
for dom in ["iot.ixiaocong.com", "www.baidu.com", "ixiaocong.com"]:
    ip, _ = dnsq(dom, "192.168.1.1")
    mark = "✅ 已劫持" if ip == "203.0.113.9" else "（正常解析）"
    print(f"  {dom:<22} → {ip}   {mark}")
print()

print("=== 7. 计数 ===")
print(sh(f"{IPT} -t nat -L OUTPUT -n -v --line-numbers | head -4"))
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | head -4"))
print()
print("=== 8. ixc-go 日志尾 ===")
print(sh("tail -6 /tmp/ixc-go.log"))
cli.close()
