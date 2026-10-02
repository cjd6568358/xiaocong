import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
def conn():
    c = paramiko.SSHClient(); c.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    c.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
              timeout=15, look_for_keys=False, allow_agent=False)
    return c
cli = conn()
def sh(c, t=60): return sshutil.run(cli, c, timeout=t).strip()
print("插座 ARP 状态（0x2=可达 / 0x0=失联）:")
for i in range(4):
    print(f"  第{i+1}次:", sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp || echo 'ARP 里没有'"))
    if i < 3: time.sleep(15)
print("\nixc-go 最近 15 行:")
print(sh("tail -15 /tmp/ixc-go.log"))
print("\nconntrack(插座):")
print(sh("grep '192.168.1.23' /proc/net/nf_conntrack 2>/dev/null | head -5 || echo '(无条目)'"))
print("\n规则命中计数:")
print(sh("LD_LIBRARY_PATH=/f4610u/lib /f4610u/bin/iptables_upx -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null | sed -n '3,4p'"))
cli.close()
