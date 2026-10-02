# -*- coding: utf-8 -*-
"""安全写文件到光猫：分块 heredoc 追加。

踩过的坑（这台 ZTE F4610U 的 dropbear）：
  - 单次 exec_command 的内容超过约 8KB → EOFError，连接直接断
  - 60000 字符单行 → 同样断
  - base64 -d 在这台 busybox 上没生效（写出来 0 字节）
  ⇒ 只能分块 heredoc，每块控制在 3000 字符以内，且按行切不切断行。
"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

MARK = "IXCEOF_9f3a"

def connect():
    cli = paramiko.SSHClient()
    cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    cli.connect("192.168.1.1", 10022, username="root",
                password=os.environ["SSH_PW"], timeout=15,
                look_for_keys=False, allow_agent=False)
    return cli

def put_file(cli, sh, content, remote, mode="755", chunk=3000):
    assert MARK not in content, "内容里出现了 heredoc 标记，换个标记"
    sh("rm -f %s" % remote)
    buf, n = "", 0
    lines = content.split("\n")
    for i, line in enumerate(lines):
        if len(buf) + len(line) + 1 > chunk:
            sh("cat >> %s << '%s'\n%s\n%s" % (remote, MARK, buf, MARK))
            n += 1
            buf = ""
        buf += line + ("\n" if i < len(lines) - 1 else "")
    if buf:
        sh("cat >> %s << '%s'\n%s\n%s" % (remote, MARK, buf, MARK))
        n += 1
    sh("chmod %s %s" % (mode, remote))
    return n

if __name__ == "__main__":
    SRC = r"C:\workspace\xiaocong\firmware\ont-go\ixc-ont.sh"
    content = open(SRC, "r", encoding="utf-8").read()
    cli = connect()
    def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()

    n = put_file(cli, sh, content, "/tmp/ixc-ont.sh")
    print("分 %d 块写入" % n)
    print("远端: ", sh("ls -la /tmp/ixc-ont.sh; wc -l < /tmp/ixc-ont.sh"))
    print("本地:  %d 字节" % os.path.getsize(SRC))
    print()
    print("=== 语法检查 sh -n ===")
    print(sh("sh -n /tmp/ixc-ont.sh 2>&1; echo RC=$?"))
    print()
    print("=== 跑 status（只读）===")
    print(sh("sh /tmp/ixc-ont.sh status 2>&1"))
    cli.close()
