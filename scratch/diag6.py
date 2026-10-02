# -*- coding: utf-8 -*-
"""插座现在到底连着谁？把它的 conntrack 条目全列出来（不截断）。
再间隔采样两次，判断 DNS 流是在老化（说明它已不再查 DNS）还是在刷新。"""
import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()

print("=== 插座 192.168.1.11 的全部 conntrack 条目 ===")
print(sh("cat /proc/net/nf_conntrack | grep -E 'src=192\\.168\\.1\\.11 |dst=192\\.168\\.1\\.11 '"))
print()
print("=== 与插座 MAC 相关的桥接/ARP 老化 ===")
print(sh("cat /proc/net/arp | grep -i 'b4:e6:2d:3a:6e:7c'"))
print()
print("=== 采样 1 ===")
print(sh("date '+%H:%M:%S'; cat /proc/net/nf_conntrack | grep -E 'src=192\\.168\\.1\\.11 .*dport=53' | awk '{print $3, $1, $5, $7, $8}'"))
print("等 60 秒 ...")
time.sleep(60)
print("=== 采样 2 ===")
print(sh("date '+%H:%M:%S'; cat /proc/net/nf_conntrack | grep -E 'src=192\\.168\\.1\\.11 .*dport=53' | awk '{print $3, $1, $5, $7, $8}'"))
print()
print("=== 全 LAN 谁在查 53（看插座是否真的不发）===")
print(sh("cat /proc/net/nf_conntrack | grep 'dport=53' | awk '{print $5, $7}' | sort | uniq -c | sort -rn | head -20"))
print()
print("=== 光猫自己的 DNS 解析 iot.ixiaocong.com ===")
print(sh("nslookup iot.ixiaocong.com 2>&1 | head -10"))
cli.close()
