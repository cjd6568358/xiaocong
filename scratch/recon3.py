#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""第三轮只读侦察：ifconfig 能力 / 接口名 / 持久化钩子 / OpenList 部署方式"""
import os
import sys

import paramiko

PW = os.environ.get("SSH_PW", "")
if not PW:
    sys.exit("缺少 SSH_PW")

CMDS = [
    ("busybox 全量 applet", "busybox --list 2>&1 | tr '\\n' ' '"),
    ("ifconfig 能力", "busybox ifconfig -a 2>&1 | head -60"),
    ("route 命令", "route -n 2>&1 | head -20; echo '--- /proc/net/route ---'; cat /proc/net/route"),
    ("/etc/rc.local", "ls -la /etc/rc.local 2>&1; echo '--- 内容 ---'; cat /etc/rc.local 2>&1"),
    ("完整进程表", "ps w 2>&1 | tail -n +1 | head -80"),
    ("找 OpenList/用户程序", "for d in /opt /opt/cu /opt/cu/apps /tmp /mnt /root /home /userdata /data /var; do echo \"## $d\"; ls -la $d 2>/dev/null | head -20; done"),
    ("/opt/cu/apps 递归", "ls -laR /opt/cu/apps 2>/dev/null | head -60"),
    ("sbin/usr-sbin 清单", "ls /sbin 2>/dev/null | tr '\\n' ' '; echo; echo '--- /usr/sbin ---'; ls /usr/sbin 2>/dev/null | tr '\\n' ' '"),
    ("监听端口全表", "cat /proc/net/tcp 2>/dev/null | head -25; echo '--- udp ---'; cat /proc/net/udp 2>/dev/null | head -25"),
    ("接口名", "ls /sys/class/net 2>&1; echo '--- 每口地址(用 busybox) ---'; busybox ifconfig 2>&1 | head -40"),
    ("有没有 ip 的替代", "ls /bin /sbin /usr/bin /usr/sbin 2>/dev/null | grep -iE '^(ip|ifconfig|brctl|vconfig|iptables|nft)$'"),
]

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect(hostname="192.168.1.1", port=10022, username="root", password=PW,
            timeout=12, banner_timeout=20, auth_timeout=20,
            look_for_keys=False, allow_agent=False)
print("✅ 光猫登录成功")
for title, cmd in CMDS:
    print(f"\n{'='*70}\n### {title}\n{'='*70}")
    try:
        _, out, err = cli.exec_command(cmd, timeout=40)
        o = out.read().decode("utf-8", "replace")
        e = err.read().decode("utf-8", "replace")
        if o.strip():
            print(o.rstrip()[:5000])
        if e.strip():
            print("[stderr]", e.rstrip()[:1500])
    except Exception as ex:  # noqa: BLE001
        print("[失败]", type(ex).__name__, ex)
cli.close()
