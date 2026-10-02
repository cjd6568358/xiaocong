#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""把自编的完整版 busybox 推到光猫真机，做兼容性与能力实测。"""
import os
import time

import paramiko

PW = os.environ["SSH_PW"]
LOCAL = r"C:\workspace\xiaocong\.scratch\busybox-full-aarch64"
REMOTE = "/tmp/busybox-full"

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=15,
            look_for_keys=False, allow_agent=False)


def sh(c, t=40):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


print("=" * 70)
print("1) 上传到光猫 /tmp")
cli.exec_command(f"rm -f {REMOTE}")
time.sleep(0.3)
stdin, stdout, _ = cli.exec_command(f"cat > {REMOTE}", timeout=300)
with open(LOCAL, "rb") as f:
    while True:
        c = f.read(65536)
        if not c:
            break
        stdin.write(c)
stdin.flush()
stdin.channel.shutdown_write()
stdout.channel.recv_exit_status()
print("   ", sh(f"chmod +x {REMOTE}; ls -la {REMOTE}"))

print("\n2) 能不能跑起来（这才是关键）")
print("   ", sh(f"{REMOTE} 2>&1 | head -3"))
print("   退出码:", sh(f"{REMOTE} >/dev/null 2>&1; echo $?"))

print("\n3) applet 数量对比")
old = set(sh("busybox --list 2>/dev/null").split())
new = set(sh(f"{REMOTE} --list 2>/dev/null").split())
print(f"    系统自带 busybox : {len(old)} 个")
print(f"    自编完整版       : {len(new)} 个")
print(f"    新增             : {len(new - old)} 个")
print(f"    丢失             : {len(old - new)} 个")

gain = sorted(new - old)
print("\n4) 新增的 applet（按字母序，每行 8 个）")
for i in range(0, len(gain), 8):
    print("    " + "  ".join(f"{a:<16}" for a in gain[i:i + 8]))

if old - new:
    print("\n⚠️ 系统有但新版没有的:", sorted(old - new))

print("\n" + "=" * 70)
print("5) 真机命令实测（重点看老内核 4.1.25 上会不会崩）")
TESTS = [
    ("uname -a", "uname -a"),
    ("id", "id"),
    ("ls -la /", "ls -la / | head -3"),
    ("stat /bin/busybox", "stat /bin/busybox"),
    ("df -h", "df -h | head -3"),
    ("free", "free"),
    ("ps (完整)", "ps | head -3"),
    ("top -b -n1", "top -b -n1 | head -4"),
    ("netstat -ltn", "netstat -ltn | head -4"),
    ("ifconfig br0", "ifconfig br0 | head -2"),
    ("route -n", "route -n | head -3"),
    ("ip addr (若有)", "ip -4 addr show br0 2>&1 | head -4"),
    ("grep 正则", "echo abc123 | grep -E '[a-z]+[0-9]+'"),
    ("sed", "echo hello | sed 's/l/L/g'"),
    ("awk", "echo 'a b c' | awk '{print $2}'"),
    ("find", "find /f4610u -maxdepth 1 -name '*.sh' | head -3"),
    ("sort|uniq", "printf 'b\\na\\nb\\n' | sort | uniq -c"),
    ("cut/tr", "echo a:b:c | cut -d: -f2 | tr 'a-z' 'A-Z'"),
    ("md5sum", "echo x | md5sum"),
    ("sha256sum", "echo x | sha256sum"),
    ("base64", "echo hi | base64"),
    ("xxd", "printf 'AB' | xxd"),
    ("hexdump", "printf 'AB' | hexdump -C"),
    ("od", "printf 'AB' | od -An -tx1"),
    ("tar --help", "tar --help 2>&1 | head -2"),
    ("gzip", "echo hello | gzip | gunzip"),
    ("xz", "echo hello | xz 2>&1 | wc -c"),
    ("unzip -h", "unzip -h 2>&1 | head -2"),
    ("bc", "echo '2^10' | bc"),
    ("diff", "diff <(echo a) <(echo b) 2>&1 | head -2"),
    ("cmp", "echo a > /tmp/_a; echo a > /tmp/_b; cmp /tmp/_a /tmp/_b && echo same"),
    ("realpath/readlink", "realpath /f4610u/../f4610u 2>&1; readlink -f /bin/busybox"),
    ("seq/printf", "seq 1 3 | tr '\\n' ' '; printf '\\n%d\\n' 42"),
    ("flock", "flock -h 2>&1 | head -2"),
    ("insmod/lsmod/rmmod", "lsmod 2>&1 | head -3"),
    ("modprobe -h", "modprobe -h 2>&1 | head -2"),
    ("crontab -l", "crontab -l 2>&1 | tail -2"),
    ("dmesg", "dmesg 2>&1 | tail -2"),
    ("mdev -h", "mdev -h 2>&1 | head -2"),
    ("syslogd -h", "syslogd -h 2>&1 | head -2"),
    ("httpd -h", "httpd -h 2>&1 | head -2"),
    ("ssl_client -h", "ssl_client -h 2>&1 | head -2"),
    ("chpasswd/cryptpw", "cryptpw -h 2>&1 | head -2"),
    ("watch -h", "watch -h 2>&1 | head -2"),
    ("mount (列出)", "mount | head -3"),
    ("dumpleases -h", "dumpleases -h 2>&1 | head -2"),
    ("setpriv -h", "setpriv -h 2>&1 | head -2"),
    ("nsenter -h", "nsenter -h 2>&1 | head -2"),
]

ok = fail = 0
for label, cmd in TESTS:
    out = sh(f"{REMOTE} {cmd} 2>&1; echo __RC=$?", t=25)
    rc = out.rsplit("__RC=", 1)[-1].strip() if "__RC=" in out else "?"
    body = out.rsplit("__RC=", 1)[0].strip().replace("\n", " | ")[:88]
    mark = "✅" if rc == "0" else "⚠️"
    if rc == "0":
        ok += 1
    else:
        fail += 1
    print(f"    {mark} {label:<20} rc={rc:<4} {body}")

print(f"\n    合计: {ok} 成功 / {fail} 非零")

print("\n6) 跑完确认光猫还活着")
print("   ", sh("uptime; echo '---'; cat /proc/loadavg"))
print("\n7) 清理")
print("   ", sh("rm -f /tmp/_a /tmp/_b; echo ok"))
cli.close()
