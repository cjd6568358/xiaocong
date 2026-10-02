#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
cmds = [
    # 找优化相关符号
    r"grep -rn 'OPTIMIZE\|optimize for size' /tmp/bbbuild/../ --include=Kconfig 2>/dev/null | head",
    r"cd /tmp && rm -rf bbsrc && mkdir bbsrc && cd bbsrc && tar xjf /tmp/bbbuild/*.tar.bz2 2>/dev/null || "
    r"curl -fsSL -o bb.tar.bz2 https://busybox.net/downloads/busybox-1.36.1.tar.bz2 && tar xjf bb.tar.bz2 && "
    r"grep -rn 'OPTIMIZE' busybox-1.36.1/ --include=Kconfig | head -20",
    # 看实际编译命令里用的 -O 级别
    r"grep -m3 -o '\-O[0-9s]' /tmp/bbbuild/busybox-build-logs/make.log | sort | uniq -c",
    r"grep -m2 'gcc.*-Os\|gcc.*-O2\|gcc.*-O0' /tmp/bbbuild/busybox-build-logs/make.log | head -2 | cut -c1-300",
]
for c in cmds:
    print("$", c[:120])
    print(sshutil.run(cli, c, timeout=180))
    print("-" * 60)
cli.close()
