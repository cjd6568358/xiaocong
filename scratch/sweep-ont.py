# -*- coding: utf-8 -*-
"""清点光猫上属于本项目的产物，并算出校验和，与本机对比。"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()

print("=== 1. /f4610u/app 与 /f4610u/etc ===")
print(sh("ls -la /f4610u/app/ /f4610u/etc/ 2>&1"))
print()
print("=== 2. 证书校验和（与本机 server/certs 比对）===")
print(sh("md5sum /f4610u/etc/server.crt /f4610u/etc/server.key 2>&1"))
print()
print("=== 3. /tmp 下本项目相关文件 ===")
print(sh("ls -la /tmp/ 2>&1 | grep -iE 'ixc|busybox|iptables|prov|fakecloud' "))
print()
print("=== 4. ixc 日志与样本大小 ===")
print(sh("ls -la /tmp/ixc-go.log /tmp/ixc.jsonl /tmp/ixc-ont.sh 2>&1"))
print(sh("wc -l < /tmp/ixc-go.log 2>&1"))
print()
print("=== 5. iptables 备份 ===")
print(sh("ls -la /tmp/iptables-*-backup-*.txt 2>&1"))
print()
print("=== 6. user_init.sh 里的 ixc-go 段（要保留的原始内容）===")
print(sh("sed -n '148,246p' /f4610u/etc/user_init.sh 2>&1 | head -40"))
print()
print("=== 7. 当前生效规则（快照）===")
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"
print(sh(f"{IPT} -t nat -S PREROUTING 2>&1 | head -5"))
print(sh(f"{IPT} -t nat -S OUTPUT 2>&1"))
print()
print("=== 8. 插座最新状态 ===")
print(sh("tail -3 /tmp/ixc-go.log 2>&1"))
cli.close()
