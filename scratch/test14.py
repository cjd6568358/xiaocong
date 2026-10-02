#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""只读+可逆探测：iptables 可用 match 模块、光猫自启机制。所有临时规则都会删除。"""
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


print("## iptables 可用 match 模块")
print(sh("cat /proc/net/ip_tables_matches 2>/dev/null | tr '\\n' ' '"))

print("\n## 试 -m mac --mac-source（插座 MAC）")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -m mac --mac-source B4:E6:2D:3A:6E:7C "
         f"-p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1; echo rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | head -4"))
print(sh(f"{IPT} -t nat -D PREROUTING -i br0 -m mac --mac-source B4:E6:2D:3A:6E:7C "
         f"-p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1; echo 已删 rc=$?"))

print("\n## 试 -m addrtype --dst-type LOCAL")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -p tcp --dport 8188 -m addrtype "
         f"--dst-type LOCAL -j REDIRECT --to-ports 8188 2>&1; echo rc=$?"))
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | head -4"))
print(sh(f"{IPT} -t nat -D PREROUTING -i br0 -p tcp --dport 8188 -m addrtype "
         f"--dst-type LOCAL -j REDIRECT --to-ports 8188 2>&1; echo 已删 rc=$?"))

print("\n## 确认 PREROUTING 已恢复原样")
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | head -8"))

print("\n## /f4610u 目录（你的自留地）")
print(sh("ls -la /f4610u/ /f4610u/etc/ 2>&1 | head -40"))
print("## 有没有自启脚本痕迹")
print(sh("find /f4610u -maxdepth 3 -name '*.sh' -o -maxdepth 3 -name 'rc*' 2>/dev/null | head -20"))

print("\n## crontab")
print(sh("cat /etc/crontabs/root 2>/dev/null; echo '--- var/spool ---'; "
         "cat /var/spool/cron/crontabs/root 2>/dev/null; echo '--- crond 进程 ---'; "
         "ps 2>/dev/null | grep -i cron | grep -v grep"))

print("\n## init.d 列表")
print(sh("ls /etc/init.d/ 2>/dev/null | head -50"))

print("\n## rc.local / 启动钩子")
print(sh("ls -la /etc/rc.local /etc/rc.d /etc/init.d/rcS 2>&1 | head -10; "
         "echo '--- rc.local 内容 ---'; cat /etc/rc.local 2>/dev/null | head -30"))

print("\n## 当前 easytier 是怎么起来的（参考它的自启方式）")
print(sh("ps 2>/dev/null | grep -iE 'easytier|openlist|f4610u' | grep -v grep | head -10"))

cli.close()
