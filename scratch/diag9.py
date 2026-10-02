# -*- coding: utf-8 -*-
"""换入口：查光猫自己的 DNS 解析器，打算直接改它的解析结果（绕过 conntrack 锁死）。"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()

print("=== 1. DNS 进程 ===")
print(sh("ps | grep -iE 'dns|dnsmasq|resolved' | grep -v grep"))
print()
print("=== 2. dnsmasq 二进制在哪 ===")
print(sh("ls -la /usr/sbin/dnsmasq /sbin/dnsmasq /f4610u/bin/dnsmasq /usr/bin/dnsmasq 2>&1; "
         "which dnsmasq 2>&1"))
print()
print("=== 3. 配置文件 ===")
print(sh("ls -la /etc/dnsmasq.conf /etc/dnsmasq.d/ /var/etc/dnsmasq.conf /tmp/dnsmasq* 2>&1 | head -20"))
print()
print("=== 4. 谁在听 53 ===")
print(sh("netstat -lnup 2>/dev/null | grep ':53' ; echo '--- ss ---'; "
         "cat /proc/net/udp | awk '{print $2}' | head -1; "
         "cat /proc/net/udp | grep -i ':0035'"))
print()
print("=== 5. /etc/hosts ===")
print(sh("cat /etc/hosts"))
print()
print("=== 6. 实测光猫自己解析 ixiaocong ===")
print(sh("(nslookup iot.ixiaocong.com 127.0.0.1 2>&1 || getent hosts iot.ixiaocong.com 2>&1) | head -8"))
print()
print("=== 7. 找 conntrack 工具 ===")
print(sh("ls /f4610u/bin/ | tr '\\n' ' '"))
cli.close()
