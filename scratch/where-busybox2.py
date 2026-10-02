import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")

def sh(host, port, cmds):
    import paramiko
    cli = paramiko.SSHClient()
    cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    cli.connect(host, port, username="root", password=os.environ["SSH_PW"],
                timeout=12, look_for_keys=False, allow_agent=False)
    for title, c in cmds:
        print("\n" + "="*64)
        print(f"## [{host}] {title}")
        print("="*64)
        try:
            print(sshutil.run(cli, c, timeout=60).rstrip())
        except Exception as e:
            print("[ERR]", e)
    cli.close()

print("\n\n##################### 编译服务器 10.0.0.4 #####################")
sh("10.0.0.4", 22, [
  ("编译脚本目录 /media/disk/backup/script",
   "ls -la /media/disk/backup/script/ 2>/dev/null"),
  ("busybox 相关脚本内容确认",
   "ls -la /media/disk/backup/script/*busybox* 2>/dev/null; head -30 /media/disk/backup/script/build_busybox_static.sh 2>/dev/null"),
  ("服务器上有没有 busybox 产物",
   "find /media/disk /tmp /root -maxdepth 3 -iname '*busybox*' 2>/dev/null | head -20"),
  ("工具链还在不在",
   "ls -d ~/.cache/musl-cross/aarch64-linux-musl-cross 2>/dev/null; ~/.cache/musl-cross/aarch64-linux-musl-cross/bin/aarch64-linux-musl-gcc --version 2>/dev/null | head -1; /usr/bin/upx --version 2>/dev/null | head -1"),
])

print("\n\n##################### 光猫 192.168.1.1 #####################")
sh("192.168.1.1", 10022, [
  ("user_init.sh 里 blinker / server 相关段落",
   "grep -n 'blinker\\|server-go\\|commonServer' /f4610u/user_init.sh 2>/dev/null | head -20"),
  ("/tmp/busybox-full 的版本与 applet 数",
   "/tmp/busybox-full 2>&1 | head -1; /tmp/busybox-full --list 2>/dev/null | wc -l"),
])
