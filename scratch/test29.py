#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""重新下一份完整 busybox 源码树，查目标 applet 的配置符号。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
print(sshutil.run(cli, 'ls -la /tmp/bbsrc/busybox-1.36.1/ | head -60', timeout=60))
print(sshutil.run(cli, 'ls -la /tmp/bbsrc/bb.tar.bz2; md5sum /tmp/bbsrc/bb.tar.bz2', timeout=60))

print("=== 重新下载并解压 ===")
print(sshutil.run(cli,
    'rm -rf /tmp/bbsrc2 && mkdir -p /tmp/bbsrc2 && cd /tmp/bbsrc2 && '
    'curl -fL --retry 3 --max-time 300 -o bb.tar.bz2 '
    'https://busybox.net/downloads/busybox-1.36.1.tar.bz2 && '
    'ls -la bb.tar.bz2 && bzip2 -t bb.tar.bz2 && echo "bzip2 完整性 OK" && '
    'tar xjf bb.tar.bz2 && echo "解压 OK" && '
    'find busybox-1.36.1 -name Config.in | wc -l', timeout=300))
cli.close()
