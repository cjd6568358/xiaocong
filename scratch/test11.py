#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""让光猫上的 iptables 跑起来：找库、设 LD_LIBRARY_PATH、试两个候选。只读。"""
import os
import paramiko

PW = os.environ["SSH_PW"]
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=30):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


print("## /f4610u 目录结构")
print(sh("ls -la /f4610u/ /f4610u/bin/ /f4610u/lib/ 2>&1 | head -60"))
print("\n## 找 libcommfun.so / libxtables / libip4tc")
print(sh("find / -name 'libcommfun*' -o -name 'libxtables*' -o -name 'libip4tc*' -o -name 'libip6tc*' "
         "2>/dev/null | head -20"))
print("\n## wospeeder 的 iptables 真身")
print(sh("ls -la /opt/apps/wospeeder/files/lib/iptables/ 2>&1 | head -30"))
print(sh("ls -la /opt/apps/wospeeder/files/lib/ 2>&1 | head -30"))

print("\n## 试法 1：LD_LIBRARY_PATH=/f4610u/lib")
print(sh("LD_LIBRARY_PATH=/f4610u/lib:/f4610u/bin /f4610u/bin/iptables_upx --version 2>&1 | head -3"))
print("\n## 试法 2：wospeeder xtables-legacy-multi")
print(sh("/opt/apps/wospeeder/files/lib/iptables/iptables --version 2>&1 | head -3; echo rc=$?"))
print("\n## 试法 3：直接 xtables-legacy-multi")
print(sh("ls /opt/apps/wospeeder/files/lib/iptables/; "
         "/opt/apps/wospeeder/files/lib/iptables/xtables-legacy-multi iptables --version 2>&1 | head -3"))
print("\n## 试法 4：从 f4610u 里 find 全部 iptables 相关")
print(sh("ls -la /f4610u/bin/ 2>&1 | head -40"))

cli.close()
