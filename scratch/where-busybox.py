import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")

cli = sshutil.connect()
cmds = [
  ("① 光猫上所有名字带 busybox 的文件（除 /proc /sys）",
   "find / -xdev \\( -path /proc -o -path /sys \\) -prune -o -iname '*busybox*' -print 2>/dev/null"),
  ("② /tmp 下的产物",
   "ls -la /tmp/*busybox* /tmp/bb* 2>/dev/null; echo '--- /tmp 全览 ---'; ls -la /tmp | head -40"),
  ("③ 光猫自带 busybox 是什么",
   "ls -la /bin/busybox; /bin/busybox 2>&1 | head -2; /bin/busybox --list 2>/dev/null | wc -l"),
  ("④ /f4610u/app 与 /f4610u/bin",
   "echo '-- app --'; ls -la /f4610u/app 2>/dev/null; echo '-- bin --'; ls -la /f4610u/bin 2>/dev/null"),
  ("⑤ overlay 空间",
   "df -h / /tmp /f4610u 2>/dev/null"),
]
for title, c in cmds:
    print("\n" + "="*64)
    print("## " + title)
    print("="*64)
    try:
        print(sshutil.run(cli, c, timeout=60).rstrip())
    except Exception as e:
        print("[ERR]", e)
cli.close()
