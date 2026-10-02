#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""取回 UPX 产物 → 推到光猫 /tmp → 冒烟测试（-h、UPX 壳能否在 4.1.25 上跑）。"""
import os, sys, io, hashlib, time
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/firmware/ont-go/ixc-go-arm64_upx"

# 1) 从编译服务器取回
b = sshutil.connect('10.0.0.4', 22)
sftp = b.open_sftp()
sftp.get('/tmp/ixc-go-build/ixc-go-arm64_upx', LOCAL)
sftp.close()
b.close()
data = open(LOCAL, 'rb').read()
print("取回 %d 字节  md5=%s" % (len(data), hashlib.md5(data).hexdigest()))

# 2) 推到光猫 /tmp
o = sshutil.connect('192.168.1.1', 10022)
REMOTE = "/tmp/ixc-go-arm64_upx"
sshutil.run(o, 'rm -f %s' % REMOTE, timeout=30)
ch = o.get_transport().open_session()
ch.exec_command('cat > %s' % REMOTE)
ch.sendall(data)
ch.shutdown_write()
ch.recv_exit_status()
print(sshutil.run(o, 'chmod +x %s; ls -la %s; md5sum %s' % (REMOTE, REMOTE, REMOTE), timeout=60))

print("\n========== 冒烟测试 1：UPX 壳能不能在 4.1.25 上启动 ==========")
print("退出码:", sshutil.run(o, '%s -h >/dev/null 2>&1; echo $?' % REMOTE, timeout=60))

print("\n========== 冒烟测试 2：-h 帮助文本 ==========")
print(sshutil.run(o, '%s -h 2>&1' % REMOTE, timeout=60))

print("\n========== 冒烟测试 3：不给 -cert 时应打帮助并以 2 退出 ==========")
print(sshutil.run(o, '%s >/dev/null 2>&1; echo "rc=$?"' % REMOTE, timeout=60))

print("\n========== 冒烟测试 4：--help 长选项 ==========")
print(sshutil.run(o, '%s --help 2>&1 | head -3' % REMOTE, timeout=60))

print("\n========== 冒烟测试 5：光猫还活着 ==========")
print(sshutil.run(o, 'uptime; cat /proc/loadavg', timeout=60))
o.close()
