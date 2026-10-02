# -*- coding: utf-8 -*-
"""只读确认：proxy 的上游 DNS 是谁、ixc-go 的上游参数叫什么。"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()

print("=== /etc/resolv.conf（光猫自己用谁做 DNS）===")
print(sh("cat /etc/resolv.conf"))
print()
print("=== ppp 下发的 DNS ===")
print(sh("cat /etc/ppp/resolv.conf /tmp/resolv.conf.auto /var/resolv.conf 2>/dev/null | head -10"))
print()
print("=== /sbin/proxy 打开的文件 / 配置 ===")
print(sh("ls -l /proc/105/fd 2>/dev/null | head -20"))
print(sh("ls /etc/ | grep -iE 'proxy|dns' ; ls /f4610u/etc/ 2>/dev/null | head -20"))
print()
print("=== ixc-go 启动参数（user_init.sh 里的实际命令行）===")
print(sh("grep -A3 -B1 'ixc-go-arm64_upx' /f4610u/etc/user_init.sh 2>/dev/null | head -20 || "
         "grep -rn 'ixc-go-arm64_upx' /f4610u/ 2>/dev/null | head -5"))
print()
print("=== ixc-go 完整命令行 ===")
print(sh("tr '\\0' ' ' < /proc/224/cmdline"))
print()
print("=== 上游参数名（源码）===")
print(sh("grep -nE 'upstream|flag\\.String' /f4610u/app/../../workspace 2>/dev/null; echo skip"))
cli.close()
