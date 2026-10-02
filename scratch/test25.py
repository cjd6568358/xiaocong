#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""完整版 busybox 真机测试 —— 修正版

上一版 test18.py 的致命缺陷：
    sh(f"{REMOTE} {cmd}")   # 例：/tmp/busybox-full echo x | sha256sum
管道里**只有第一个命令**走的是新 busybox，后面的 sha256sum/base64/... 全是
系统 shell 从 PATH 里找的。于是：
  - 报 "ash: sha256sum: not found" —— 不是新 busybox 缺这个 applet，
    而是**系统自带 busybox 缺**，被误判成新 busybox 的问题；
  - 反过来 "43 项成功" 里有一大半测的是系统自带工具，等于没测。

本版改为所有 applet 一律经 `{BB} <applet> <args>` 调用，才是真的在测新 busybox。
另外补一段 --install -s 软链安装后按裸命令名调用的验证（真实使用姿势）。
"""
import os
import time

import paramiko

PW = os.environ["SSH_PW"]
LOCAL = r"C:\workspace\xiaocong\.scratch\bb-new-aarch64"
BB = "/tmp/busybox-full"          # 新 busybox
BBIN = "/tmp/bbin"                # --install -s 的软链目录

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=15,
            look_for_keys=False, allow_agent=False)


def sh(c, t=40):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


print("=" * 72)
print("0) 清掉上一轮可能残留的挂起进程")
print("   ", sh("for p in $(ps | grep busybox-full | grep -v grep | awk '{print $1}'); "
               "do kill -9 $p 2>/dev/null; done; echo ok"))

print("1) 上传到光猫 /tmp")
cli.exec_command(f"rm -f {BB}")
time.sleep(0.3)
stdin, stdout, _ = cli.exec_command(f"cat > {BB}", timeout=300)
with open(LOCAL, "rb") as f:
    while True:
        c = f.read(65536)
        if not c:
            break
        stdin.write(c)
stdin.flush()
stdin.channel.shutdown_write()
stdout.channel.recv_exit_status()
print("   ", sh(f"chmod +x {BB}; ls -la {BB}; md5sum {BB}"))

print("\n2) 能不能跑起来")
print("   ", sh(f"{BB} 2>&1 | head -3"))
print("    退出码:", sh(f"{BB} >/dev/null 2>&1; echo $?"))

print("\n3) applet 数量对比")
old = set(sh("busybox --list 2>/dev/null").split())
new = set(sh(f"{BB} --list 2>/dev/null").split())
print(f"    系统自带 busybox : {len(old)} 个")
print(f"    自编完整版       : {len(new)} 个")
print(f"    新增             : {len(new - old)} 个")
print(f"    丢失             : {len(old - new)} 个")
if old - new:
    print("    ⚠️ 系统有但新版没有:", sorted(old - new))
    # 逐个查系统 busybox 里这些是不是厂商自定义 applet
    for a in sorted(old - new):
        print(f"       {a:<12} 系统里: {sh(f'busybox {a} 2>&1 | head -1')[:60]}")

print("\n" + "=" * 72)
print("4) 真机 applet 实测（全部经 `busybox <applet>` 调用，才是真在测新版）")
TESTS = [
    ("uname -a",            "{B} uname -a"),
    ("id",                  "{B} id"),
    ("ls -la /",            "{B} ls -la / | head -3"),
    ("stat",                "{B} stat /bin/busybox | head -4"),
    ("df -h",               "{B} df -h | head -3"),
    ("free",                "{B} free"),
    ("ps",                  "{B} ps | head -3"),
    ("top -b -n1",          "{B} top -b -n1 | head -4"),
    ("netstat -ltn",        "{B} netstat -ltn | head -4"),
    ("ifconfig br0",        "{B} ifconfig br0 | head -2"),
    ("route -n",            "{B} route -n | head -3"),
    ("ip -4 addr",          "{B} ip -4 addr show br0 2>&1 | head -4"),
    ("ip -4 route",         "{B} ip -4 route 2>&1 | head -3"),
    ("grep -E 正则",         "echo abc123 | {B} grep -E '[a-z]+[0-9]+'"),
    ("sed",                 "echo hello | {B} sed 's/l/L/g'"),
    ("awk",                 "echo 'a b c' | {B} awk '{{print $2}}'"),
    ("find",                "{B} find /f4610u -maxdepth 1 -name '*.sh' | head -3"),
    ("sort|uniq",           "printf 'b\\na\\nb\\n' | {B} sort | {B} uniq -c"),
    ("cut|tr",              "echo a:b:c | {B} cut -d: -f2 | {B} tr 'a-z' 'A-Z'"),
    ("md5sum",              "echo x | {B} md5sum"),
    ("sha1sum",             "echo x | {B} sha1sum"),
    ("sha256sum",           "echo x | {B} sha256sum"),
    ("sha512sum",           "echo x | {B} sha512sum"),
    ("base64",              "echo hi | {B} base64"),
    ("base64 -d",           "echo aGk= | {B} base64 -d"),
    ("xxd",                 "printf 'AB' | {B} xxd"),
    ("hexdump -C",          "printf 'AB' | {B} hexdump -C"),
    ("od -An -tx1",         "printf 'AB' | {B} od -An -tx1"),
    ("strings",             "{B} strings /bin/busybox | head -2"),
    ("crc32",               "echo hi | {B} crc32"),
    ("dos2unix",            "printf 'a\\r\\n' | {B} dos2unix | {B} od -An -tx1"),
    ("tar --help",          "{B} tar --help 2>&1 | head -2"),
    ("gzip|gunzip",         "echo hello | {B} gzip | {B} gunzip"),
    ("xz",                  "echo hello | {B} xz | wc -c"),
    ("unzip -h",            "{B} unzip -h 2>&1 | head -2"),
    ("bc",                  "echo '2^10' | {B} bc"),
    ("expr",                "{B} expr 2 + 3"),
    ("diff",                "{B} diff /etc/hostname /etc/hostname; echo rc=$?"),
    ("cmp",                 "echo a > /tmp/_a; echo a > /tmp/_b; {B} cmp /tmp/_a /tmp/_b && echo same"),
    ("realpath/readlink",   "{B} realpath /f4610u/../f4610u; {B} readlink -f /bin/busybox"),
    ("seq/printf",          "{B} seq 1 3 | tr '\\n' ' '; {B} printf '\\n%d\\n' 42"),
    ("yes|head",            "{B} yes | head -2 | tr '\\n' ' '"),
    ("env",                 "{B} env | head -3"),
    ("date -u +%s",         "{B} date -u +%s"),
    ("timeout",             "{B} timeout 1 sleep 5; echo rc=$?"),
    ("mountpoint",          "{B} mountpoint -q /tmp; echo rc=$?"),
    ("flock -h",            "{B} flock -h 2>&1 | head -2"),
    ("lsmod",               "{B} lsmod 2>&1 | head -3"),
    ("modprobe -h",         "{B} modprobe -h 2>&1 | head -2"),
    ("insmod -h",           "{B} insmod -h 2>&1 | head -2"),
    ("crontab -l",          "{B} crontab -l 2>&1 | tail -2"),
    ("crond -h",            "{B} crond -h 2>&1 | head -2"),
    ("dmesg",               "{B} dmesg 2>&1 | tail -2"),
    ("mdev -h",             "{B} mdev -h 2>&1 | head -2"),
    ("syslogd -h",          "{B} syslogd -h 2>&1 | head -2"),
    ("httpd -h",            "{B} httpd -h 2>&1 | head -2"),
    ("ssl_client -h",       "{B} ssl_client -h 2>&1 | head -2"),
    ("cryptpw -h",          "{B} cryptpw -h 2>&1 | head -2"),
    ("watch -h",            "{B} watch -h 2>&1 | head -2"),
    ("mount",               "{B} mount | head -3"),
    ("dumpleases -h",       "{B} dumpleases -h 2>&1 | head -2"),
    ("udhcpc -h",           "{B} udhcpc -h 2>&1 | head -2"),
    ("setpriv -h",          "{B} setpriv -h 2>&1 | head -2"),
    ("nsenter -h",          "{B} nsenter -h 2>&1 | head -2"),
    ("unshare -h",          "{B} unshare -h 2>&1 | head -2"),
    ("taskset -h",          "{B} taskset -h 2>&1 | head -2"),
    ("chrt -h",             "{B} chrt -h 2>&1 | head -2"),
    ("ionice -h",           "{B} ionice -h 2>&1 | head -2"),
    ("setsid",              "{B} setsid -h 2>&1 | head -2"),
    ("wget --help",         "{B} wget --help 2>&1 | head -2"),
    ("nc -h",               "{B} nc -h 2>&1 | head -2"),
    ("telnet -h",           "{B} telnet -h 2>&1 | head -2"),
    ("telnetd -h",          "{B} telnetd -h 2>&1 | head -2"),
    ("ftpd -h",             "{B} ftpd -h 2>&1 | head -2"),
    ("tftp -h",             "{B} tftp -h 2>&1 | head -2"),
    ("ntpd -h",             "{B} ntpd -h 2>&1 | head -2"),
    ("tunctl -h",           "{B} tunctl -h 2>&1 | head -2"),
    ("pgrep",               "{B} pgrep -h 2>&1 | head -2"),
    ("pkill -h",            "{B} pkill -h 2>&1 | head -2"),
    ("hwclock -h",          "{B} hwclock -h 2>&1 | head -2"),
    ("blkid",               "{B} blkid 2>&1 | head -2"),
    ("sh -c 子shell",        "{B} sh -c 'echo sh-ok'"),
    ("ash -c 子shell",       "{B} ash -c 'echo ash-ok' 2>&1 | head -1"),
    ("lock（系统有）",        "{B} lock 2>&1 | head -1"),
    ("netmsg（系统有）",      "{B} netmsg 2>&1 | head -1"),
    # ---- 追加启用的 20 个（defconfig 默认 n）----
    ("bbconfig",            "{B} bbconfig 2>&1 | head -2; echo ...; {B} bbconfig 2>/dev/null | wc -l"),
    ("ar",                  "{B} ar --help 2>&1 | head -2"),
    ("inotifyd",            "{B} inotifyd 2>&1 | head -2"),
    ("rfkill",              "{B} rfkill -h 2>&1 | head -2"),
    ("netcat（nc 别名）",     "echo hi | {B} netcat 2>&1 | head -2"),
    ("readahead",           "{B} readahead -h </dev/null 2>&1 | head -2"),
    ("fbset",               "{B} fbset -h 2>&1 | head -2"),
    ("tune2fs",             "{B} tune2fs -h 2>&1 | head -2"),
    ("mkfs.reiser",         "{B} mkfs.reiser -h 2>&1 | head -2"),
    ("lzopcat",             "{B} lzopcat </dev/null 2>&1 | head -2"),
    ("unlzop",              "{B} unlzop </dev/null 2>&1 | head -2"),
    ("uncompress",          "{B} uncompress </dev/null 2>&1 | head -2"),
    ("nuke",                "{B} nuke </dev/null 2>&1 | head -2"),
    ("minips",              "{B} minips </dev/null 2>&1 | head -2"),
    ("nanddump",            "{B} nanddump </dev/null 2>&1 | head -2"),
    ("nandwrite",           "{B} nandwrite </dev/null 2>&1 | head -2"),
    ("flashcp",             "{B} flashcp </dev/null 2>&1 | head -2"),
    ("flash_eraseall",      "{B} flash_eraseall </dev/null 2>&1 | head -2"),
    ("flash_lock",          "{B} flash_lock </dev/null 2>&1 | head -2"),
    ("flash_unlock",        "{B} flash_unlock </dev/null 2>&1 | head -2"),
]

ok = fail = 0
bad = []
for label, cmd in TESTS:
    real = cmd.format(B=BB)
    out = sh(f"{real} 2>&1; echo __RC=$?", t=25)
    rc = out.rsplit("__RC=", 1)[-1].strip() if "__RC=" in out else "?"
    body = out.rsplit("__RC=", 1)[0].strip().replace("\n", " | ")[:86]
    good = rc == "0"
    mark = "✅" if good else "⚠️"
    if good:
        ok += 1
    else:
        fail += 1
        bad.append((label, rc, body))
    print(f"    {mark} {label:<18} rc={rc:<4} {body}")

print(f"\n    合计: {ok} 成功 / {fail} 非零")
if bad:
    print("    非零明细:")
    for l, r, b in bad:
        print(f"      - {l} rc={r} :: {b}")

print("\n" + "=" * 72)
print("5) --install -s 软链安装后，按裸命令名调用（真实使用姿势）")
print("   ", sh(f"rm -rf {BBIN}; mkdir -p {BBIN}; {BB} --install -s {BBIN} 2>&1 | head -3; "
              f"ls {BBIN} | wc -l"))
print("   ", sh(f"export PATH={BBIN}:$PATH; sha256sum </dev/null >/dev/null 2>&1; "
              f"echo hi | sha256sum; echo hi | base64; printf AB | xxd | head -1; "
              f"echo '2^8' | bc"))

print("\n6) 跑完确认光猫还活着")
print("   ", sh("uptime; echo '---'; cat /proc/loadavg"))

print("\n7) 清理")
print("   ", sh(f"rm -f /tmp/_a /tmp/_b; rm -rf {BBIN}; echo ok"))
cli.close()
