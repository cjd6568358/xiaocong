import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=60): return sshutil.run(cli, c, timeout=t).strip()
print("进程:", sh("ps w | grep -v grep | grep ixc-go | head -1"))
print()
print("nat PREROUTING 前两条：")
print(sh("LD_LIBRARY_PATH=/f4610u/lib /f4610u/bin/iptables_upx -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null | sed -n '1,4p'"))
print()
print("br0:0 别名:", sh("ifconfig br0:0 2>/dev/null | grep 'inet addr' || echo '(无)'"))
print()
print("插座连接:", sh("grep '192.168.1.23' /proc/net/nf_conntrack 2>/dev/null | grep 8188 | head -1 || echo '(无)'"))
print()
print("最近 6 条日志:")
print(sh("tail -6 /tmp/ixc-go.log"))
cli.close()
