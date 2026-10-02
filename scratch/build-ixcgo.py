#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""把 ixc-go 源码传到编译服务器，跑 build.sh（编译 + UPX）。"""
import os, sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

SRC = r"C:/workspace/xiaocong/firmware/ont-go"
DST = "/tmp/ixc-go-build"
FILES = ["main.go", "mqtt.go", "dns.go", "rules.go", "go.mod", "build.sh"]

b = sshutil.connect('10.0.0.4', 22)
print(sshutil.run(b, 'rm -rf %s && mkdir -p %s && echo ok' % (DST, DST), timeout=60))

for f in FILES:
    data = open(os.path.join(SRC, f), 'rb').read().replace(b'\r\n', b'\n')
    ch = b.get_transport().open_session()
    ch.exec_command('cat > %s/%s' % (DST, f))
    ch.sendall(data)
    ch.shutdown_write()
    ch.recv_exit_status()
    print("  上传 %-10s %7d 字节" % (f, len(data)))

print(sshutil.run(b, 'chmod +x %s/build.sh; ls -la %s' % (DST, DST), timeout=60))
print("\n================ build.sh 输出 ================")
print(sshutil.run(b, 'cd %s && sh build.sh 2>&1' % DST, timeout=900))
b.close()
