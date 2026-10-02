#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""判别测试：为什么局域网主机连不通光猫的 WAN IP。只读 + 从 PC 探端口。"""
import os
import socket
import paramiko

PW = os.environ["SSH_PW"]
WAN_IP = "139.227.20.142"
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=25):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


print("=" * 68)
print("## 完整接口列表")
print(sh("ifconfig -a 2>/dev/null | grep -E '^[a-zA-Z]|inet addr'"))
print("## 完整路由表")
print(sh("route -n 2>/dev/null"))
print("## 策略路由表(local/main 等)")
print(sh("cat /proc/net/ip_tables_targets 2>/dev/null | tr '\\n' ' '; echo"))
print("## busybox 是否带 iptables applet")
print(sh("busybox --list 2>/dev/null | grep -iE 'iptables|ip6tables|nft' || echo '(无 iptables applet)'"))
print("## 系统里有没有静态 iptables 可借")
print(sh("find / -name 'iptables*' -o -name 'libiptc*' 2>/dev/null | head -10; echo '--'; "
         "ls /lib/*.so* 2>/dev/null | grep -iE 'iptc|xtables' | head"))
print("## ip_tables_targets")
print(sh("cat /proc/net/ip_tables_targets 2>/dev/null | tr '\\n' ' '"))

print("=" * 68)
print("## 从 PC 探测光猫 WAN IP 上若干个【确定在监听】的端口")
for port, what in [(80, "web ui"), (10022, "ssh"), (11010, "easytier"),
                   (15888, "?"), (18999, "?"), (42919, "?"), (8188, "fakecloud")]:
    try:
        s = socket.create_connection((WAN_IP, port), timeout=4)
        print(f"   {WAN_IP}:{port:<6} ({what})  ✅ 通")
        s.close()
    except Exception as e:
        print(f"   {WAN_IP}:{port:<6} ({what})  ❌ {type(e).__name__}")

print("\n## 对照：同样的端口，走 LAN IP 192.168.1.1")
for port in [80, 10022, 11010]:
    try:
        s = socket.create_connection(("192.168.1.1", port), timeout=4)
        print(f"   192.168.1.1:{port:<6}  ✅ 通")
        s.close()
    except Exception as e:
        print(f"   192.168.1.1:{port:<6}  ❌ {type(e).__name__}")

cli.close()
