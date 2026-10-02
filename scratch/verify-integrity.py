#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""核实 user_init.sh 除了 ixc-go 段以外，一个字节都没被改动。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

o = sshutil.connect('192.168.1.1', 10022)

print("=== A) diff 到底能不能用 ===")
print("  ", sshutil.run(o, 'diff /etc/hostname /etc/hostname; echo "diff rc=$?"', timeout=60))

print("\n=== B) 去掉 ixc-go 段后，与插入前的备份逐字节比对 ===")
print(sshutil.run(o,
    'sed "/^# ==== ixc-go begin ====$/,/^# ==== ixc-go end ====$/d" /f4610u/user_init.sh | md5sum; '
    'echo "  ↑ 当前文件(剥掉 ixc-go 段)"; '
    'md5sum /f4610u/user_init.sh.bak-20261002-030321; '
    'echo "  ↑ 插入前的备份"', timeout=60))

print("\n=== C) 行数 ===")
print(sshutil.run(o, 'for f in /f4610u/user_init.sh.bak-20261002-030321 /f4610u/user_init.sh; do '
                     'printf "%-52s %s 行  %s 字节\\n" "$f" "$(wc -l < $f)" "$(wc -c < $f)"; done', timeout=60))

print("\n=== D) 关键结构：段边界前后各几行 ===")
print(sshutil.run(o, 'echo "--- 第 145-153 行 ---"; sed -n "145,153p" /f4610u/user_init.sh; '
                     'echo "--- 第 232-236 行（结尾）---"; sed -n "232,236p" /f4610u/user_init.sh', timeout=60))

print("\n=== E) 插入前备份的结尾（对比确认 exit 0 位置）===")
print(sshutil.run(o, 'tail -5 /f4610u/user_init.sh.bak-20261002-030321', timeout=60))
o.close()
