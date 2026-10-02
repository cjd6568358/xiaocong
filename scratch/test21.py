#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
cmds = [
    "ls -la /tmp/bbbuild/busybox-build-logs/; wc -l /tmp/bbbuild/busybox-build-logs/make.log",
    "head -5 /tmp/bbbuild/busybox-build-logs/make.log",
    "ls /tmp/bbsrc/ 2>&1",
    "cd /tmp/bbsrc 2>/dev/null && ls -d busybox-* 2>&1",
    "find /tmp/bbsrc -maxdepth 2 -name Kconfig 2>/dev/null | head",
]
for c in cmds:
    print("$", c)
    print(sshutil.run(cli, c, timeout=120))
    print("-" * 60)
cli.close()
