import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=90): return sshutil.run(cli, c, timeout=t).strip()
print("进程:", sh("ps w | grep -v grep | grep -E 'ixc-go|easytier|dropbear' | awk '{print $1, $5, $6}'"))
print()
print("插座 ARP:", sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp | awk '{print $1,$3}' || echo '不在'"))
print("插座连接:", sh("grep '192.168.1.23' /proc/net/nf_conntrack 2>/dev/null | grep 8188 | head -1 || echo '(无)'"))
print()
print("最近日志:")
print(sh("tail -5 /tmp/ixc-go.log"))
print()
print("easytier 网络:")
print(sh("ip addr show | grep -E '^[0-9]+:|inet ' | grep -v '127.0.0.1' | head -12"))
print()
print("WAN:", sh("ifconfig ppp0 2>/dev/null | grep 'inet addr' | awk '{print $2}'"))
cli.close()
