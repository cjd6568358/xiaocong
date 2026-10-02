#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""取新构建的完整 applet 清单，与上一次构建（t18.txt 里记录的 417 个版本）做 diff。"""
import os, re, sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import paramiko

PW = os.environ["SSH_PW"]
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=15,
            look_for_keys=False, allow_agent=False)


def sh(c, t=40):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace")

new = set(sh("/tmp/busybox-full --list 2>/dev/null").split())
sysset = set(sh("busybox --list 2>/dev/null").split())
print("新版 applet: %d   系统: %d" % (len(new), len(sysset)))

# 从 t18.txt 的「新增的 applet」区块解析上一次构建的清单
txt = open(r"C:\workspace\xiaocong\.scratch\t18.txt", encoding="utf-8", errors="replace").read()
m = re.search(r"4\) 新增的 applet.*?\n(.*?)\n\n", txt, re.S)
old_gain = set(m.group(1).split()) if m else set()
old_all = old_gain | (sysset - {"lock", "netmsg", "ash"}) | {"ash"}
print("t18 记录的新增: %d   推算旧版总数: %d" % (len(old_gain), len(old_all)))

only_old = sorted(old_all - new)
only_new = sorted(new - old_all)
print("\n旧版有、新版没有 (%d):" % len(only_old))
for i in range(0, len(only_old), 8):
    print("   " + "  ".join("%-16s" % a for a in only_old[i:i+8]))
print("\n新版有、旧版没有 (%d):" % len(only_new))
for i in range(0, len(only_new), 8):
    print("   " + "  ".join("%-16s" % a for a in only_new[i:i+8]))

print("\n新版新增（相对系统）: %d" % len(new - sysset))
print("新版丢失（相对系统）:", sorted(sysset - new))
cli.close()
