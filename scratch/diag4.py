# -*- coding: utf-8 -*-
"""决定性诊断：插座到底挂在 br0 的哪个物理口 / -i 该写谁。

不靠猜，直接读内核的三张表：
  1) /proc/net/arp         → Device 列：这个 MAC 是从哪个 netdev 学到的
  2) brctl showmacs br0    → port no：这个 MAC 在桥的几号口（对应 eth0/eth1/.../wlan0/wlan4）
  3) nat PREROUTING 计数    → 现状
"""
import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
BB = "/tmp/busybox-full_upx"
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

print("=== 1. ARP 表（看 Device 列）===")
print(sh("cat /proc/net/arp"))
print()

print("=== 2. br0 的桥成员（带序号）===")
print(sh("ls -1 /sys/class/net/br0/brif/ 2>/dev/null | nl"))
print()

print("=== 3. brctl showmacs br0（MAC → 端口号）===")
print(sh(f"{BB} brctl showmacs br0 2>&1 | head -40", t=60))
print()

print("=== 4. 当前 nat PREROUTING（带计数）===")
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers 2>&1 | head -25", t=60))
print()

print("=== 5. bridge-nf 开关 ===")
print(sh("for f in /proc/sys/net/bridge/bridge-nf-call-*; do echo -n \"$f=\"; cat $f; done 2>&1"))
print()

print("=== 6. 插座是否还在线 ===")
print(sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp; echo '--- conntrack ---'; "
         "cat /proc/net/nf_conntrack 2>/dev/null | grep -i '192.168.1.11' | head -5"))
print()

print("=== 7. ixc-go 进程 ===")
print(sh("ps | grep -E '[i]xc-go' ; echo '--- 日志尾 ---'; tail -5 /tmp/ixc-go.log 2>&1"))
cli.close()
