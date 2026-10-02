# -*- coding: utf-8 -*-
"""用 heredoc 直接写文件（不走 base64 —— 这台 busybox 的 base64 -d 没生效）。
   用 << 'EOF' 加引号，防止脚本里的 $ 变量被远端 shell 展开。"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

SRC = r"C:\workspace\xiaocong\firmware\ont-go\ixc-ont.sh"
content = open(SRC, "r", encoding="utf-8").read()
MARK = "IXCEOF_9f3a"
assert MARK not in content

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()

cmd = "cat > /tmp/ixc-ont.sh << '%s'\n%s\n%s\nchmod 755 /tmp/ixc-ont.sh" % (MARK, content, MARK)
sh(cmd)

print("=== 上传结果 ===")
print(sh("ls -la /tmp/ixc-ont.sh; wc -l < /tmp/ixc-ont.sh; head -1 /tmp/ixc-ont.sh"))
print()
print("=== 首尾校验（看是否完整）===")
print(sh("head -3 /tmp/ixc-ont.sh; echo '...'; tail -3 /tmp/ixc-ont.sh"))
print()
print("=== 语法检查 sh -n ===")
print(sh("sh -n /tmp/ixc-ont.sh 2>&1; echo RC=$?"))
print()
print("=== 跑 status（只读）===")
print(sh("sh /tmp/ixc-ont.sh status 2>&1"))
cli.close()
