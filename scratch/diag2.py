import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

# ping 走文件，避免读超时干扰
sh("ping -c 2 -W 2 192.168.1.11 > /tmp/_p.txt 2>&1 &")
time.sleep(6)
print("ping 结果:", sh("cat /tmp/_p.txt 2>/dev/null | tail -4"))
print()

print("=== 实验：临时加一条【按源 IP】的 REDIRECT（放在 MAC 规则之上）===")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -s 192.168.1.11 -p udp --dport 53 -j REDIRECT --to-ports 5353 && echo '已插入'"))
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '1,5p'"))
print()
print("等 90 秒，看两条规则谁吃到包 ...")
time.sleep(90)
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3,5p'"))
print()
print("ixc-go 有没有收到:", sh("tail -4 /tmp/ixc-go.log"))
print()
print("=== 结论后清理临时规则 ===")
print(sh(f"{IPT} -t nat -D PREROUTING -i br0 -s 192.168.1.11 -p udp --dport 53 -j REDIRECT --to-ports 5353 && echo '已删'"))
cli.close()
