#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""逐字节核实：插入 ixc-go 段之外，原文件一个字节都没动。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

o = sshutil.connect('192.168.1.1', 10022)
CUR = "/f4610u/user_init.sh"
BAK = "/f4610u/user_init.sh.bak-20261002-030321"   # 插入前的备份

print("=== 1) 前 148 行（原文件去掉最后一行 exit 0）是否逐字节相同 ===")
print(sshutil.run(o, f'sed -n "1,148p" {CUR} | md5sum; sed -n "1,148p" {BAK} | md5sum', timeout=60))

print("\n=== 2) 文件尾部的字节（看换行结构）===")
print(sshutil.run(o, f'echo "-- 当前 尾部 24 字节 --"; tail -c 24 {CUR} | od -c | head -4; '
                     f'echo "-- 备份 尾部 24 字节 --"; tail -c 24 {BAK} | od -c | head -4', timeout=60))

print("\n=== 3) 剥掉 ixc-go 段之后，尾部与行数 ===")
print(sshutil.run(o, f'sed "/^# ==== ixc-go begin ====$/,/^# ==== ixc-go end ====$/d" {CUR} '
                     f'> /tmp/stripped.sh; wc -l < /tmp/stripped.sh; '
                     f'wc -c < /tmp/stripped.sh; tail -c 24 /tmp/stripped.sh | od -c | head -4; '
                     f'echo "-- 剥段后 vs 备份 --"; md5sum /tmp/stripped.sh {BAK}', timeout=60))

print("\n=== 4) 用 awk 剥段（换个实现交叉验证，避免 sed 范围语法坑）===")
AWK = ('awk "/ixc-go begin/{s=1} !s{print} /ixc-go end/{s=0}" ' + CUR +
       ' > /tmp/stripped2.sh; wc -l < /tmp/stripped2.sh; md5sum /tmp/stripped2.sh ' + BAK)
print(sshutil.run(o, AWK, timeout=60))
o.close()
