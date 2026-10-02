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
BB = "/tmp/busybox-full_upx"

print("=== 桥成员 ===")
print(sh("brctl show 2>/dev/null || cat /sys/class/net/br0/brif/* 2>/dev/null | head; echo '--- brif ---'; ls /sys/class/net/br0/brif/ 2>/dev/null"))
print()
print("=== 各接口收发包计数（看插座流量从哪进）===")
print(sh("cat /proc/net/dev | awk 'NR>2{print $1, \"RX=\" $3, \"TX=\" $11}' | head -20"))
print()
print("=== 实验：插入【不带 -i】的规则（只按源 IP + dport 53）===")
print(sh(f"{IPT} -t nat -I PREROUTING 1 -s 192.168.1.11 -p udp --dport 53 -j REDIRECT --to-ports 5353 && echo '已插入'"))
print(sh(f"{IPT} -t nat -I PREROUTING 1 -p udp --dport 53 -j REDIRECT --to-ports 5354 && echo '已插入全量(5354,只计数不真用)'"))
print()
print("等 75 秒 ...")
time.sleep(75)
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3,7p'"))
print()
print("=== 清理 ===")
print(sh(f"{IPT} -t nat -D PREROUTING -s 192.168.1.11 -p udp --dport 53 -j REDIRECT --to-ports 5353 && echo '删1'"))
print(sh(f"{IPT} -t nat -D PREROUTING -p udp --dport 53 -j REDIRECT --to-ports 5354 && echo '删2'"))
print()
print("清理后:", sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers | sed -n '3,5p'"))
cli.close()
