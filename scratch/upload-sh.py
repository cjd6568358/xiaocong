# -*- coding: utf-8 -*-
"""上传 ixc-ont.sh 到光猫 /tmp（调试位），做语法检查 + 跑 status（只读）。
   光猫 dropbear 无 sftp-server，paramiko SFTP 不可用 → base64 走 SSH stdin。"""
import os, sys, base64
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

SRC = r"C:\workspace\xiaocong\firmware\ont-go\ixc-ont.sh"
data = open(SRC, "rb").read()
b64 = base64.b64encode(data).decode()

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()

def put(local_bytes, remote):
    b = base64.b64encode(local_bytes).decode()
    # 分段写，避免单行过长
    sh(f"rm -f {remote}")
    for i in range(0, len(b), 60000):
        chunk = b[i:i+60000]
        sh("echo -n '%s' >> %s.b64" % (chunk, remote))
    sh(f"base64 -d {remote}.b64 > {remote} && rm -f {remote}.b64 && chmod 755 {remote}")

put(data, "/tmp/ixc-ont.sh")
print("=== 上传结果 ===")
print(sh("ls -la /tmp/ixc-ont.sh; head -1 /tmp/ixc-ont.sh"))
print()
print("=== 语法检查 sh -n ===")
print(sh("sh -n /tmp/ixc-ont.sh 2>&1 && echo '✅ 语法 OK'"))
print()
print("=== 跑 status（只读）===")
print(sh("sh /tmp/ixc-ont.sh status 2>&1"))
cli.close()
