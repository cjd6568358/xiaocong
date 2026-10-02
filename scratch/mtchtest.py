# -*- coding: utf-8 -*-
"""真插一条再删，测 match 的【内核侧】可用性（-C 探测法不可靠）。
   全部用 -j LOG（非终止），插完立刻删。"""
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
CAND = [
    ("u32",        '-m u32 --u32 "0>>22&0x3C@0>>16&0xFFFF=0x0035"'),
    ("conntrack",  '-m conntrack --ctstate NEW'),
    ("addrtype",   '-m addrtype --dst-type LOCAL'),
    ("owner",      '-m owner --uid-owner 0'),
    ("physdev",    '-m physdev --physdev-in wlan0'),
    ("length",     '-m length --length 40:600'),
    ("mac",        '-m mac --mac-source B4:E6:2D:3A:6E:7C'),
    ("multiport",  '-m multiport --dports 53,5353'),
]
print("=== 内核侧可用性（插→看→删）===")
for name, m in CAND:
    ins = sh(f"{IPT} -t nat -I PREROUTING 1 {BASE} {m} -j LOG --log-prefix 'MT:' 2>&1")
    ok = not ins.strip()
    print(f"  [{'✅ 可用' if ok else '❌ 不可用'}] -m {name}")
    if not ok:
        print(f"         {ins.strip()[:110]}")
    else:
        print(f"         落库: {sh(f'{IPT} -t nat -S PREROUTING | sed -n 2p')}")
        sh(f"{IPT} -t nat -D PREROUTING {BASE} {m} -j LOG --log-prefix 'MT:' 2>&1")
print()
print("=== REDIRECT 在 nat OUTPUT 可用吗 ===")
ins = sh(f"{IPT} -t nat -I OUTPUT 1 -p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1")
print("  插入:", "OK" if not ins.strip() else ins)
if not ins.strip():
    print("  落库:", sh(f"{IPT} -t nat -S OUTPUT | sed -n 2p"))
    sh(f"{IPT} -t nat -D OUTPUT -p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1")
    print("  已删")
print()
print("=== 最终确认干净 ===")
print(sh(f"{IPT} -t nat -S PREROUTING | head -3"))
print(sh(f"{IPT} -t nat -S OUTPUT"))
cli.close()
