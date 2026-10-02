# -*- coding: utf-8 -*-
"""用 SSH stdin 管道上传（不拼超长命令行）。"""
import os, sys, base64, socket, time
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

def put_stdin(b64text, remote, mode="755"):
    """分块 printf 追加。块必须小：60000 字符的单行命令会把 dropbear 撑断
    （实测 EOFError）。base64 字符集不含单引号，可安全放进单引号里。"""
    sh("rm -f %s %s.b64" % (remote, remote))
    n = 0
    for i in range(0, len(b64text), 4000):
        chunk = b64text[i:i+4000]
        sh("printf '%%s' '%s' >> %s.b64" % (chunk, remote))
        n += 1
    print("   （分 %d 块写入）" % n)
    sh("base64 -d %s.b64 > %s && rm -f %s.b64 && chmod %s %s"
       % (remote, remote, remote, mode, remote))

put_stdin(b64, "/tmp/ixc-ont.sh")
print("=== 上传结果 ===")
print(sh("ls -la /tmp/ixc-ont.sh; head -1 /tmp/ixc-ont.sh; wc -l < /tmp/ixc-ont.sh"))
print()
print("=== 语法检查 sh -n ===")
r = sh("sh -n /tmp/ixc-ont.sh 2>&1; echo RC=$?")
print(r)
print()
print("=== 跑 status（只读，验证脚本能在光猫上跑起来）===")
print(sh("sh /tmp/ixc-ont.sh status 2>&1"))
cli.close()
