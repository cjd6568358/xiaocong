import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")

cli = sshutil.connect()
cmds = [
  ("ARP 表里有没有插座", "grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp; cat /proc/net/arp | head -20"),
  ("插座是否还 ping 得通", "ping -c 2 -W 2 192.168.1.23 2>&1 | tail -3"),
  ("无线关联表(如果有)", "for f in /proc/net/iw_station /tmp/iw_station; do [ -f $f ] && echo \"-- $f\" && cat $f; done; iw dev 2>/dev/null | head -20"),
  ("ixc-go 进程", "ps w | grep -v grep | grep ixc-go"),
  ("ixc-go 日志尾部", "tail -25 /tmp/ixc-go.log 2>/dev/null"),
  ("DNS 劫持规则命中计数", "LD_LIBRARY_PATH=/f4610u/lib /f4610u/bin/iptables_upx -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null | head -12"),
]
for title, c in cmds:
    print("\n" + "="*60)
    print("## " + title)
    print("="*60)
    try:
        print(sshutil.run(cli, c, timeout=40).rstrip())
    except Exception as e:
        print("[ERR]", e)
cli.close()
