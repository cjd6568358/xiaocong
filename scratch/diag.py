import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=90): return sshutil.run(cli, c, timeout=t).strip()
print("ping 前 ARP:", sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp || echo '(不在 ARP)'"))
print("ping .11 :", sh("ping -c 3 -W 3 192.168.1.11 2>&1 | grep -E 'bytes from|transmitted' || echo 'ping 无输出'"))
print("ping 后 ARP:", sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp || echo '(不在 ARP)'"))
print()
print("conntrack 里 .11 :", sh("grep '192.168.1.11' /proc/net/nf_conntrack | head -5 || echo '(无)'"))
print()
print("DHCP 租约里的插座:", sh("grep -i 'b4:e6:2d:3a:6e:7c\\|B4:E6:2D:3A:6E:7C' /tmp/dhcp* /var/dhcp* /f4610u/*dhcp* 2>/dev/null | head -5 || echo '(没找到租约文件)'"))
print()
print("无线关联(插座 MAC):", sh("for d in /proc/net/rtl* /proc/net/wlan* /tmp/sta*; do [ -f $d ] && echo \"-- $d\" && grep -i '3a:6e:7c' $d; done 2>/dev/null | head -8; echo '(以上为空则无相关文件)'"))
print()
print("插座是否在 8188 上试图连接(近 1 分钟抓):", sh("grep -c '8188' /proc/net/nf_conntrack 2>/dev/null || echo 0"))
cli.close()
