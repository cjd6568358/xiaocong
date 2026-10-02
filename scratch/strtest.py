# -*- coding: utf-8 -*-
"""试出 -m string 在这台光猫上的正确写法。每条插完立刻删，不留残留。"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

BASE = "-i br0 -p udp --dport 53"
VARIANTS = [
    ("V1 algo在前+无引号", '-m string --algo bm --string ixiaocong'),
    ("V2 algo在前+有引号", '-m string --algo bm --string "ixiaocong"'),
    ("V3 string在前+无引号", '-m string --string ixiaocong --algo bm'),
    ("V4 kmp算法", '-m string --algo kmp --string ixiaocong'),
    ("V5 只给algo", '-m string --algo bm'),
    ("V6 --hex-string", '-m string --algo bm --hex-string "|69786961|"'),
    ("V7 from/to", '-m string --algo bm --from 40 --to 200 --string ixiaocong'),
]

print("=== 逐个试（插→看→删）===")
for name, m in VARIANTS:
    ins = sh(f"{IPT} -t nat -I PREROUTING 1 {BASE} {m} -j LOG --log-prefix 'STRT:' 2>&1")
    ok = not ins.strip()
    print(f"  [{ 'OK ' if ok else 'FAIL'}] {name}: {m}")
    if not ok:
        print(f"         → {ins.strip()[:120]}")
    if ok:
        listed = sh(f"{IPT} -t nat -S PREROUTING | sed -n '2p'")
        print(f"         落库为: {listed}")
        sh(f"{IPT} -t nat -D PREROUTING {BASE} {m} -j LOG --log-prefix 'STRT:' 2>&1")
print()
print("=== 无 string 的对照（确认别的地方没错）===")
ins = sh(f"{IPT} -t nat -I PREROUTING 1 {BASE} -j LOG --log-prefix 'STRT:' 2>&1")
print("  裸规则:", "OK" if not ins.strip() else ins)
print("  落库:", sh(f"{IPT} -t nat -S PREROUTING | sed -n '2p'"))
sh(f"{IPT} -t nat -D PREROUTING {BASE} -j LOG --log-prefix 'STRT:' 2>&1")
print()
print("=== 最终 PREROUTING（确认干净）===")
print(sh(f"{IPT} -t nat -S PREROUTING | head -3"))
cli.close()
