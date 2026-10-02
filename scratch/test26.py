#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""从编译服务器取回新版 busybox 到本地。"""
import sys, io, hashlib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/.scratch/bb-new-aarch64"
cli = sshutil.connect('10.0.0.4', 22)
sftp = cli.open_sftp()
sftp.get('/tmp/bbbuild/busybox-full-aarch64', LOCAL)
sftp.close()
cli.close()
d = open(LOCAL, 'rb').read()
print("取回 %d 字节, md5=%s" % (len(d), hashlib.md5(d).hexdigest()))
