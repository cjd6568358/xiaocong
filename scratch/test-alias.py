#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""光猫可行性实测（可逆）：
   没有 iptables / ip，但有 busybox ifconfig。
   测：给 br0 加一个 /32 别名地址，能不能让"公网 IP"落到光猫本地端口。
   测完立即撤销。
"""
import os
import sys
import time

import paramiko

PW = os.environ.get("SSH_PW", "")
if not PW:
    sys.exit("缺少 SSH_PW")

FAKE_IP = "203.0.113.9"
PORT = "8188"
IFACE = "br0"


def sh(cli, cmd, timeout=25):
    _, out, err = cli.exec_command(cmd, timeout=timeout)
    o = out.read().decode("utf-8", "replace").rstrip()
    e = err.read().decode("utf-8", "replace").rstrip()
    return o, e


cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW,
            timeout=12, look_for_keys=False, allow_agent=False)
print("✅ 光猫登录成功")

print(f"\n[0] 加别名前 br0:")
o, _ = sh(cli, f"busybox ifconfig {IFACE} | head -4")
print(o)

print(f"\n[1] 加别名 {FAKE_IP}/32 到 {IFACE} …")
o, e = sh(cli, f"ifconfig {IFACE}:0 {FAKE_IP} netmask 255.255.255.255 up 2>&1; echo rc=$?")
print(o, e)
time.sleep(1)
o, _ = sh(cli, f"busybox ifconfig {IFACE} | head -8")
print(o)

print(f"\n[2] 在 {FAKE_IP}:{PORT} 起一个监听（busybox nc，后台）…")
sh(cli, f"rm -f /tmp/nctest.log; nohup busybox nc -l -p {PORT} > /tmp/nctest.log 2>&1 & sleep 2; true")
o, _ = sh(cli, f"netstat -ltn 2>/dev/null | grep {PORT} || echo '(没看到监听)'")
print(o)

print(f"\n[3] 光猫自连 {FAKE_IP}:{PORT} 测试 …")
o, e = sh(cli, f"echo HELLO-FROM-ONT | busybox nc -w 3 {FAKE_IP} {PORT} 2>&1; echo rc=$?")
print(o, e)
time.sleep(1)
o, _ = sh(cli, "cat /tmp/nctest.log 2>&1")
print("[监听端收到的内容]", repr(o))

print(f"\n[4] 路由表有没有多出 {FAKE_IP} …")
o, _ = sh(cli, f"route -n | head -20")
print(o)

print("\n[5] 撤销（清理）…")
o, _ = sh(cli, f"killall nc 2>/dev/null; ifconfig {IFACE}:0 down 2>&1; echo rc=$?")
print(o)
o, _ = sh(cli, f"busybox ifconfig {IFACE} | head -6")
print(o)
o, _ = sh(cli, "route -n | head -20")
print(o)

cli.close()
print("\n✅ 测试结束，已恢复原状")
