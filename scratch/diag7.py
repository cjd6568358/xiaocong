# -*- coding: utf-8 -*-
"""用 iptables -S（权威表示）看清楚规则真相，再做纯计数对照实验。

四条 ACCEPT 规则（非终止劫持，只计数，不影响任何人上网）：
  A: -i br0 -s 192.168.1.11 -p udp --dport 53
  B:          -s 192.168.1.11 -p udp --dport 53
  C: -i br0              -p udp --dport 53
  D:                     -p udp --dport 53
等 90 秒读计数。A/B 为 0 而 C/D > 0 → 插座的包匹配不上 -s 或 -i br0。
"""
import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

print("=== 0. 权威规则列表 (iptables -S) ===")
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1"))
print()
print("=== 1. 清理所有残留的 5353/5354/5355/PC 规则 ===")
out = sh(f"{IPT} -t nat -S PREROUTING 2>&1")
for line in out.splitlines():
    if ("5353" in line or "5354" in line or "5355" in line or "192.168.1.19" in line) \
       and "--dport 53" in line:
        d = line.replace("-A ", "-D ", 1)
        r = sh(f"{IPT} -t nat {d} 2>&1")
        print("  删:", d[:100], "→", r if r else "OK")
print()
print("=== 清理后 ===")
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1"))
print()

PLUG = "192.168.1.11"
rules = [
    ("A", f"-i br0 -s {PLUG} -p udp --dport 53"),
    ("B", f"-s {PLUG} -p udp --dport 53"),
    ("C", f"-i br0 -p udp --dport 53"),
    ("D", f"-p udp --dport 53"),
]
print("=== 2. 插入 4 条 ACCEPT 计数规则 ===")
for tag, body in rules:
    r = sh(f"{IPT} -t nat -I PREROUTING 1 {body} -j ACCEPT 2>&1")
    print(f"  {tag}: -I PREROUTING 1 {body} -j ACCEPT → {r if r else 'OK'}")
print()
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1 | head -8"))
print()
print("等 90 秒 ...")
time.sleep(90)
print()
print("=== 3. 计数 ===")
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers 2>&1 | head -10"))
print()
print("=== 4. 清理 ===")
for tag, body in rules:
    sh(f"{IPT} -t nat -D PREROUTING {body} -j ACCEPT 2>&1")
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1 | head -8"))
cli.close()
