#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""收尾核对：脚本一致性、光猫状态、残留物。"""
import sys, io, hashlib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/firmware/build_busybox_static.sh"
DEST = "/media/disk/backup/script/build_busybox_static.sh"

b = sshutil.connect('10.0.0.4', 22)
print("=== 编译服务器 ===")
print(sshutil.run(b, 'md5sum %s; ls -la /media/disk/backup/script/' % DEST, timeout=60))
local_md5 = hashlib.md5(open(LOCAL, 'rb').read().replace(b'\r\n', b'\n')).hexdigest()
print("本地脚本 md5:", local_md5)

print("\n=== 光猫 ===")
o = sshutil.connect('192.168.1.1', 10022)
print(sshutil.run(o, 'uptime; echo ---; cat /proc/loadavg; echo ---; '
                     'df -h /tmp | tail -1; echo ---; ls -la /tmp/busybox-full 2>&1; echo ---; '
                     'mount | grep -c tmpfs; echo "tmpfs 挂载数(上行)"; '
                     'for p in $(ps | grep busybox-full | grep -v grep | awk \'{print $1}\'); '
                     'do echo "残留进程 $p"; done; echo "残留检查完成"', timeout=60))
print(sshutil.run(o, '/tmp/busybox-full --list 2>/dev/null | wc -l', timeout=60))
o.close()
b.close()
