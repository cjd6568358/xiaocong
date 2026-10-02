#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
S = "/tmp/bbsrc/busybox-1.36.1"
cmds = [
    "ls %s | head -30" % S,
    "ls %s/Kconfig; grep -c . %s/Kconfig" % (S, S),
    "grep -rn OPTIMIZE %s --include=Kconfig | head" % S,
    "grep -n 'O2\\|Os\\|O0' %s/Makefile | head -20" % S,
    "sed -n '1,40p' %s/Makefile" % S,
]
for c in cmds:
    print("$", c)
    print(sshutil.run(cli, c, timeout=120))
    print("-" * 60)
cli.close()
