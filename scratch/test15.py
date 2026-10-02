#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""重看：iptables match 模块可用性 + user_init.sh 内容。"""
import os
import paramiko

PW = os.environ["SSH_PW"]
PRE = "export LD_LIBRARY_PATH=/f4610u/lib; "
IPT = "/f4610u/bin/iptables_upx"
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=30):
    _, o, e = cli.exec_command(PRE + c, timeout=t)
    return (o.read().decode("utf-8", "replace").strip() +
            ("\n[stderr] " + e.read().decode("utf-8", "replace").strip() if e else ""))


print("### A) match 模块清单")
print(sh("cat /proc/net/ip_tables_matches 2>/dev/null | tr '\\n' ' '"))

print("\n### B) -m mac --mac-source 测试")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -m mac --mac-source B4:E6:2D:3A:6E:7C "
         f"-p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1; echo rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | sed -n '1,3p'"))
print(sh(f"{IPT} -t nat -D PREROUTING -i br0 -m mac --mac-source B4:E6:2D:3A:6E:7C "
         f"-p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1; echo 已删 rc=$?"))

print("\n### C) -m addrtype --dst-type LOCAL 测试")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -p tcp --dport 8188 -m addrtype "
         f"--dst-type LOCAL -j REDIRECT --to-ports 8188 2>&1; echo rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | sed -n '1,3p'"))
print(sh(f"{IPT} -t nat -D PREROUTING -i br0 -p tcp --dport 8188 -m addrtype "
         f"--dst-type LOCAL -j REDIRECT --to-ports 8188 2>&1; echo 已删 rc=$?"))

print("\n### D) 确认恢复原样")
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | sed -n '1,8p'"))

print("\n### E) /f4610u/user_init.sh 内容")
print(sh("cat /f4610u/user_init.sh 2>&1"))

print("\n### F) /f4610u 完整结构")
print(sh("ls -la /f4610u/ 2>&1"))

cli.close()
