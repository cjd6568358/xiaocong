#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""在 busybox 源码里查这批 applet 的配置符号名与默认值/依赖。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

cli = sshutil.connect('10.0.0.4', 22)
S = '/tmp/bbsrc/busybox-1.36.1'
SCRIPT = r'''
cd %s || exit 1
for n in ar bbconfig devfsd inotifyd flashcp flash_eraseall flash_lock flash_unlock \
         lzopcat unlzop uncompress minips nuke rfkill tune2fs unit mkfs.reiser netcat \
         tc unshare; do
  printf '===== %%s =====\n' "$n"
  grep -rn -B4 -A6 "^[[:space:]]*bool \"$n\"" --include=Config.in . 2>/dev/null | head -22
done
''' % S
print(sshutil.run(cli, SCRIPT, timeout=180))
cli.close()
