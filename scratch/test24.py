#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""上传修正后的 build_busybox_static.sh，干净目录重跑，产物与本地已验证件对比。"""
import os, sys, io, hashlib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/firmware/build_busybox_static.sh"
VERIFIED = r"C:/workspace/xiaocong/.scratch/busybox-full-aarch64"
REMOTE = "/tmp/build_busybox_static.sh"

cli = sshutil.connect('10.0.0.4', 22)

data = open(LOCAL, 'rb').read().replace(b'\r\n', b'\n')
ch = cli.get_transport().open_session()
ch.exec_command('cat > %s' % REMOTE)
ch.sendall(data)
ch.shutdown_write()
ch.recv_exit_status()
print("脚本已上传 %d 字节, md5=%s" % (len(data), hashlib.md5(data).hexdigest()))
print(sshutil.run(cli, 'md5sum %s' % REMOTE))

print("\n===== 重跑 =====")
print(sshutil.run(cli, 'rm -rf /tmp/bbbuild && mkdir -p /tmp/bbbuild && cd /tmp/bbbuild && '
                       'bash %s 2>&1' % REMOTE, timeout=900))

print("===== 产物 =====")
print(sshutil.run(cli, 'ls -la /tmp/bbbuild/*.log /tmp/bbbuild/busybox-full-aarch64; '
                       'md5sum /tmp/bbbuild/busybox-full-aarch64'))
print("本地已验证件 md5:", hashlib.md5(open(VERIFIED,'rb').read()).hexdigest())
cli.close()
