#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""把本地 build_busybox_static.sh 传到编译服务器 /tmp，并在 /tmp 的干净目录里跑一遍。"""
import os, sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/firmware/build_busybox_static.sh"
REMOTE = "/tmp/build_busybox_static.sh"

cli = sshutil.connect('10.0.0.4', 22)
print("== 连通性 ==")
print(sshutil.run(cli, 'hostname; uname -m; nproc; ls -d /tmp/bbbuild 2>/dev/null'))

data = open(LOCAL, 'rb').read().replace(b'\r\n', b'\n')
print("== 上传 %d 字节 ==" % len(data))
ch = cli.get_transport().open_session()
ch.exec_command('cat > %s' % REMOTE)
ch.sendall(data)
ch.shutdown_write()
ch.recv_exit_status()
print(sshutil.run(cli, 'ls -la %s; md5sum %s' % (REMOTE, REMOTE)))

import hashlib
print("   本地 md5:", hashlib.md5(data).hexdigest())

print("== 跑起来（干净目录 /tmp/bbbuild）==")
print(sshutil.run(cli, 'rm -rf /tmp/bbbuild && mkdir -p /tmp/bbbuild && cd /tmp/bbbuild && '
                       'bash %s 2>&1 | tail -45' % REMOTE, timeout=900))
print("== 产物 ==")
print(sshutil.run(cli, 'ls -la /tmp/bbbuild/; file /tmp/bbbuild/busybox-full-aarch64 2>/dev/null || true'))
cli.close()
