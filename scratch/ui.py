import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=60): return sshutil.run(cli, c, timeout=t).strip()
print("=== user_init.sh 全部分段标题 ===")
print(sh("grep -n '^# ----' /f4610u/user_init.sh"))
print()
print("=== 文件头（设置/来源说明）===")
print(sh("sed -n '1,24p' /f4610u/user_init.sh"))
print()
print("=== 运行中的 ixc-go 完整命令行 ===")
print(sh("tr '\\0' ' ' < /proc/12730/cmdline 2>/dev/null || ps w | grep -v grep | grep ixc-go"))
cli.close()
