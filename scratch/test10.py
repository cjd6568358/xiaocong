#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""只读：确认光猫上的 iptables 二进制可用，并导出现有规则。不做任何修改。"""
import os
import paramiko

PW = os.environ["SSH_PW"]
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=30):
    _, o, e = cli.exec_command(c, timeout=t)
    return (o.read().decode("utf-8", "replace").strip() +
            ("\n[stderr] " + e.read().decode("utf-8", "replace").strip() if e.read else ""))


IPT = "/f4610u/bin/iptables_upx"

print("=" * 68)
print("## iptables 二进制探测")
print(sh(f"ls -la /f4610u/bin/iptables_upx /opt/apps/wospeeder/files/lib/iptables/iptables 2>&1; echo '---'; "
         f"{IPT} --version 2>&1 | head -3; echo rc=$?"))

print("=" * 68)
print("## nat 表")
print(sh(f"{IPT} -t nat -L -n -v --line-numbers 2>&1 | head -80"))

print("=" * 68)
print("## filter 表")
print(sh(f"{IPT} -t filter -L -n -v --line-numbers 2>&1 | head -80"))

print("=" * 68)
print("## mangle 表")
print(sh(f"{IPT} -t mangle -L -n -v --line-numbers 2>&1 | head -60"))

print("=" * 68)
print("## 现有规则里与 8188 / 139.227.20.142 / 203.0.113.9 / 53 相关的")
print(sh(f"{IPT}-save 2>/dev/null | grep -nE '8188|139\\.227\\.20\\.142|203\\.0\\.113\\.9|dport 53' | head -30 "
         f"|| {IPT} -t nat -L -n 2>/dev/null | grep -nE '8188|139\\.227|203\\.0\\.113|:53' | head -20"))

cli.close()
