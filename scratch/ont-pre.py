import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
cli = sshutil.connect()
cmds = [
  ("ixc-go 进程完整命令行（看 -hijackip / -domains）",
   "ps w | grep -v grep | grep ixc-go"),
  ("当前 WAN IP",
   "ifconfig ppp0 2>/dev/null | grep 'inet addr' ; ip addr show ppp0 2>/dev/null | grep inet"),
  ("ixc-go 日志尾部 30 行",
   "tail -30 /tmp/ixc-go.log 2>/dev/null"),
  ("conntrack 里 8188 的现有条目（基准）",
   "grep 8188 /proc/net/nf_conntrack 2>/dev/null | head -10; echo '(空=当前无连接)'"),
  ("插座还在线吗",
   "grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp; ping -c 1 -W 2 192.168.1.23 2>&1 | tail -2"),
]
for t, c in cmds:
    print("\n" + "="*62); print("## " + t); print("="*62)
    try: print(sshutil.run(cli, c, timeout=45).rstrip())
    except Exception as e: print("[ERR]", e)
cli.close()
