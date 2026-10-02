# -*- coding: utf-8 -*-
"""1) 反查 53 端口(inode 7064)属于哪个进程
   2) 用 filter 表（逐包遍历，不像 nat 只在建连时走）验证插座的流是否真的有包过 CPU
"""
import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()

print("=== 1. 谁占着 53（inode 7064）===")
print(sh("cat /proc/net/udp | awk '$2 ~ /:0035$/ {print $2, $10}'"))
print(sh("for p in /proc/[0-9]*; do "
         "for f in $p/fd/*; do "
         "t=$(readlink $f 2>/dev/null); "
         "case \"$t\" in socket:\\[7064\\]) echo \"PID=$(basename $p) CMD=$(tr '\\0' ' ' < $p/cmdline)\";; esac; "
         "done; done 2>/dev/null | head"))
print()
print("=== 2. 进程全名里带 dns/proxy 的 ===")
print(sh("ps | grep -viE 'grep' | grep -iE 'dns|proxy|relay|dhcp'"))
print()

IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"
print("=== 3. filter 表逐包计数实验（60 秒）===")
for tbl, chain, tag in [("filter", "INPUT", "IN"), ("filter", "FORWARD", "FW")]:
    r = sh(f"{IPT} -t {tbl} -I {chain} 1 -s 192.168.1.11 -p udp --dport 53 "
           f"-j LOG --log-prefix 'P{tag}:' 2>&1")
    print(f"  {tbl}/{chain}: {r if r.strip() else 'OK'}")
print("  等 60 秒 ...")
time.sleep(60)
for tbl, chain in [("filter", "INPUT"), ("filter", "FORWARD")]:
    print(f"--- {tbl}/{chain} ---")
    print(sh(f"{IPT} -t {tbl} -L {chain} -n -v --line-numbers 2>&1 | head -4"))
print()
print("=== dmesg 里的插座包 ===")
print(sh("dmesg | grep -E 'P(IN|FW):' | tail -8"))
print()
print("=== 清理 ===")
for tbl, chain, tag in [("filter", "INPUT", "IN"), ("filter", "FORWARD", "FW")]:
    print(sh(f"{IPT} -t {tbl} -D {chain} -s 192.168.1.11 -p udp --dport 53 "
             f"-j LOG --log-prefix 'P{tag}:' 2>&1") or "OK")
cli.close()
