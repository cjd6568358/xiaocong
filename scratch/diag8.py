# -*- coding: utf-8 -*-
"""一次测出 DNS 包真正的入接口。

对每个候选接口插一条 -j LOG（非终止，零风险，且自带 pkts 计数），
等 90 秒后读每条的计数：谁的计数非 0，谁就是真入接口。
再交叉验证 dmesg 里的 IN= / MAC= / SRC=。
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
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

IFS = ["br0", "eth0", "eth1", "eth2", "eth3", "wlan0", "wlan1", "wlan2", "wlan3",
       "wlan4", "wlan5", "wlan6", "wlan7", "nbif5", "sw"]

print("=== 插入 %d 条 LOG 计数规则 ===" % len(IFS))
bad = []
for i in IFS:
    r = sh(f"{IPT} -t nat -I PREROUTING 1 -i {i} -p udp --dport 53 "
           f"-j LOG --log-prefix 'IF{i}:' 2>&1")
    if r.strip():
        bad.append((i, r.strip()))
        print(f"  {i}: {r.strip()}")
print("失败:", bad if bad else "无")
print()
print("等 90 秒 ...")
time.sleep(90)
print()
print("=== 计数结果 ===")
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers 2>&1 | head -%d" % (len(IFS) + 4)))
print()
print("=== dmesg 交叉验证（插座的 MAC / IP）===")
print(sh("dmesg 2>/dev/null | grep -E 'IF.*(B4:E6:2D|192.168.1.11)' | tail -10"))
print()
print("=== dmesg 里各 IN= 出现次数 ===")
print(sh("dmesg 2>/dev/null | grep -oE 'IF[a-z0-9]+' | sort | uniq -c | sort -rn | head -20"))
print()
print("=== 清理 ===")
for i in IFS:
    sh(f"{IPT} -t nat -D PREROUTING -i {i} -p udp --dport 53 -j LOG --log-prefix 'IF{i}:' 2>&1")
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1"))
cli.close()
