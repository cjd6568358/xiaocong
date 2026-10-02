# -*- coding: utf-8 -*-
"""只读探测 match 是否存在 —— 用 -C（check）命令，它不写任何规则。
   match 不存在 → "Couldn't load match"
   match 存在但规则不存在 → "Bad rule (does a matching rule exist)" / "Illegal"
   两种情况可区分，且全程只读。
"""
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

CAND = {
    "string":   '-m string --string "ixiaocong" --algo bm',
    "u32":      '-m u32 --u32 "0>>22&0x3C@12>>26&0x3C@0&0x0=0x1"',
    "owner":    '-m owner --uid-owner 0',
    "conntrack": '-m conntrack --ctstate NEW',
    "length":   '-m length --length 40',
    "statistic": '-m statistic --mode nth --every 2',
    "bpf":      '-m bpf --bytecode "1,0 0 0 0,"',
    "addrtype": '-m addrtype --dst-type LOCAL',
    "physdev":  '-m physdev --physdev-in wlan0',
}
print("=== match 可用性（只读 -C 探测）===")
for name, args in CAND.items():
    out = sh(f"{IPT} -t nat -C PREROUTING {args} -j ACCEPT 2>&1")
    low = out.lower()
    if "couldn't load match" in low or "no such file" in low:
        print(f"[❌ 不支持] -m {name}")
    else:
        print(f"[✅ 支持]   -m {name}   (返回: {out.strip()[:70]})")
print()
print("=== 当前 nat PREROUTING（现状快照）===")
print(sh(f"{IPT} -t nat -S PREROUTING"))
print()
print("=== 当前 mangle PREROUTING ===")
print(sh(f"{IPT} -t mangle -S PREROUTING"))
print()
print("=== 插座 conntrack 现状 ===")
print(sh("cat /proc/net/nf_conntrack | grep -E 'src=192\\.168\\.1\\.11 '"))
print()
print("=== 所有疑似小葱设备（按 DHCP 租约看 hostname）===")
print(sh("ls /var/dhcp* /tmp/dhcp* /etc/dhcp* 2>/dev/null; "
         "cat /var/dhcp.leases /tmp/dhcp.leases 2>/dev/null | head -40"))
print()
print("=== /sbin/proxy 的命令行与配置线索 ===")
print(sh("tr '\\0' ' ' < /proc/105/cmdline; echo; ls -la /sbin/proxy"))
cli.close()
