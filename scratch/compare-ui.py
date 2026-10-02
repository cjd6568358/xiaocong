#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""把原始 user_init.sh 和当前文件都拉到本地，精确比对差异到底是什么。"""
import sys, io, difflib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

o = sshutil.connect('192.168.1.1', 10022)


def fetch(remote):
    return sshutil.run(o, 'cat %s' % remote, timeout=60)


orig = fetch("/f4610u/user_init.sh.bak-20261002-030321")
sshutil.run(o, 'sed "/^# ==== ixc-go begin ====$/,/^# ==== ixc-go end ====$/d" '
               '/f4610u/user_init.sh > /tmp/st.sh', timeout=60)
cur = fetch("/tmp/st.sh")

ol = orig.split("\n")
cl = cur.split("\n")
print("原始: %d 行 / %d 字节" % (len(ol), len(orig.encode())))
print("剥段: %d 行 / %d 字节" % (len(cl), len(cur.encode())))

d = list(difflib.unified_diff(ol, cl, "原始", "剥段后", lineterm="", n=2))
print("\n=== 完整 diff ===")
for line in d:
    print("   ", repr(line))

print("\n=== 去掉所有空行后再比 ===")
o2 = [l for l in ol if l.strip()]
c2 = [l for l in cl if l.strip()]
print("非空行数: 原始 %d / 剥段 %d" % (len(o2), len(c2)))
print("非空行内容是否完全一致:", "✅ 是" if o2 == c2 else "❌ 否")
if o2 != c2:
    for line in difflib.unified_diff(o2, c2, lineterm="", n=1):
        print("   ", repr(line))

print("\n=== 逐字节：把剥段结果的空行压掉后与原始比 ===")
import re
n_orig = re.sub(r"\n+", "\n", orig)
n_cur = re.sub(r"\n+", "\n", cur)
print("压缩连续空行后 md5 是否一致:", "✅" if n_orig == n_cur else "❌")
o.close()
