#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""把探测脚本写到服务器再跑，结果落文件后 cat 回来 —— 避免 SSH 流式读取被截断导致假结果。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

S = '/tmp/bbsrc2/busybox-1.36.1'

SH = r'''#!/bin/sh
S=__S__
{
echo "### 1) 到底有几个 Config.in"
find "$S" -name Config.in | wc -l
find "$S" -name Config.in | head -5
echo
echo "### 2) 关键文件是否存在"
ls -la "$S/miscutils/Config.in" "$S/sysklogd/Config.in" 2>&1
echo
echo "### 3) 剩余 applet 的符号名（从 .c 的 //config: 块里取）"
grep -rn "^//config:config \(FLASH_ERASEALL\|FLASH_LOCK\|FLASH_UNLOCK\|UNIT\|MINIPS\|NETCAT\|NANDWRITE\|NANDDUMP\|FBSET\|MTD\|READAHEAD\|IONICE\)$" "$S" --include=*.c
echo
echo "### 4) 这些符号在 defconfig 下的默认值"
for k in AR BBCONFIG DEVFSD FLASHCP INOTIFYD RFKILL UNCOMPRESS UNLZOP LZOPCAT TUNE2FS MKFS_REISER NUKE FLASH_ERASEALL FLASH_LOCK FLASH_UNLOCK UNIT MINIPS NETCAT; do
  printf '%-18s ' "$k"
  grep -m1 -E "^(CONFIG_$k=|# CONFIG_$k is not set)" "$S/.config" || echo "(不在 .config 里)"
done
echo
echo "### 5) defconfig 下 =y 的项数"
grep -c '=y$' "$S/.config"
} > /tmp/probe.txt 2>&1
cat /tmp/probe.txt
'''.replace('__S__', S)

cli = sshutil.connect('10.0.0.4', 22)
ch = cli.get_transport().open_session()
ch.exec_command('cat > /tmp/probe.sh && sh /tmp/probe.sh')
ch.sendall(SH.replace('\r\n', '\n').encode())
ch.shutdown_write()
buf = b''
while True:
    if ch.recv_ready():
        buf += ch.recv(65536)
    elif ch.exit_status_ready():
        break
print(buf.decode('utf-8', 'replace'))
cli.close()
