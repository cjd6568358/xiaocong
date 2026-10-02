#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""只读：用 LD_LIBRARY_PATH 让 iptables 跑起来，导出光猫现有 nat/filter/mangle 规则。"""
import os
import paramiko

PW = os.environ["SSH_PW"]
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)

PRE = "export LD_LIBRARY_PATH=/f4610u/lib; "
IPT = "/f4610u/bin/iptables_upx"


def sh(c, t=30):
    _, o, e = cli.exec_command(PRE + c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


print("## 计数器/版本确认")
print(sh(f"{IPT} --version 2>&1 | head -2; {IPT} -t nat -L -n 2>&1 | head -3"))

print("=" * 68)
print("## nat 表")
print(sh(f"{IPT} -t nat -L -n -v --line-numbers 2>&1 | head -100"))

print("=" * 68)
print("## filter 表")
print(sh(f"{IPT} -t filter -L -n -v --line-numbers 2>&1 | head -100"))

print("=" * 68)
print("## mangle 表")
print(sh(f"{IPT} -t mangle -L -n -v --line-numbers 2>&1 | head -60"))

print("=" * 68)
print("## 规则总数统计")
print(sh(f"for t in nat filter mangle; do printf '%s: ' $t; "
         f"{IPT} -t $t -L -n 2>/dev/null | wc -l; done"))

cli.close()
