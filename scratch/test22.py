#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
cmds = [
    "grep -rn 'OPTIMIZE' /tmp/bbsrc/busybox-1.36.1/*/Kconfig /tmp/bbsrc/busybox-1.36.1/Kconfig 2>/dev/null | head -20",
    "grep -rn 'optimize' /tmp/bbsrc/busybox-1.36.1/Makefile /tmp/bbsrc/busybox-1.36.1/scripts/Makefile* 2>/dev/null | head -20",
    "grep -n 'CFLAGS' /tmp/bbbuild/busybox-build-logs/make.log | head -5",
    "grep -n 'gcc' /tmp/bbbuild/busybox-build-logs/make.log | head -3 | cut -c1-400",
]
for c in cmds:
    print("$", c)
    print(sshutil.run(cli, c, timeout=120))
    print("-" * 60)
cli.close()
