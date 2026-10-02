import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
cli = sshutil.connect()
cmds = [
  ("当前 nat PREROUTING（带命中计数）",
   "LD_LIBRARY_PATH=/f4610u/lib /f4610u/bin/iptables_upx -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null"),
  ("br0 / br0:0 地址（确认 203.0.113.9 落在本机）",
   "ip addr show br0 2>/dev/null | grep -E 'inet '; echo '--- ppp0 ---'; ip addr show ppp0 2>/dev/null | grep inet"),
  ("203.0.113.9 本机可达吗",
   "ping -c 1 -W 2 203.0.113.9 2>&1 | tail -2"),
]
for t, c in cmds:
    print("\n" + "="*62); print("## " + t); print("="*62)
    try: print(sshutil.run(cli, c, timeout=45).rstrip())
    except Exception as e: print("[ERR]", e)
cli.close()
