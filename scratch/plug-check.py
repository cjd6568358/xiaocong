import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=60): return sshutil.run(cli, c, timeout=t).strip()
print("ARP:", sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp | awk '{print $1,$3}'"))
print("conntrack:", sh("grep '192.168.1.23' /proc/net/nf_conntrack 2>/dev/null | grep 8188 | head -2 || echo '(无)'"))
print("日志尾:")
print(sh("tail -8 /tmp/ixc-go.log"))
cli.close()
