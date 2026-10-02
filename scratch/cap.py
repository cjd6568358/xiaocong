# -*- coding: utf-8 -*-
"""摸清 ZTE iptables 的 match / target 能力，为"按域名劫持"做准备。
   关键问题：有没有 string / u32 match（能按 DNS 报文内容匹配），有没有 raw 表。"""
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

print("=== 版本 ===")
print(sh(f"{IPT} --version"))
print()
for m in ["string", "u32", "conntrack", "state", "comment", "multiport", "limit", "physdev"]:
    out = sh(f"{IPT} -m {m} --help 2>&1 | head -4")
    ok = "✅" if ("no such" not in out.lower() and "unknown" not in out.lower()
                 and "error" not in out.lower() and out.strip()) else "❌"
    print(f"[{ok}] -m {m}: {out.strip()[:150]!r}")
print()
print("=== 各表是否存在 ===")
for t in ["raw", "mangle", "nat", "filter", "security"]:
    out = sh(f"{IPT} -t {t} -L -n 2>&1 | head -2")
    ok = "✅" if "Error" not in out and "error" not in out and out.strip() else "❌"
    print(f"[{ok}] -t {t}: {out.strip()[:120]!r}")
print()
print("=== raw 表 PREROUTING 现有内容 ===")
print(sh(f"{IPT} -t raw -L PREROUTING -n -v 2>&1 | head -10"))
print()
print("=== string match 详细用法 ===")
print(sh(f"{IPT} -m string --help 2>&1 | head -20"))
print()
print("=== u32 match 详细用法 ===")
print(sh(f"{IPT} -m u32 --help 2>&1 | head -20"))
cli.close()
