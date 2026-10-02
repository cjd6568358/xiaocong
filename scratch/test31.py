#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""把 build_busybox_static.sh 安装到 /media/disk/backup/script/，并清理临时目录。"""
import sys, io, hashlib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

LOCAL = r"C:/workspace/xiaocong/firmware/build_busybox_static.sh"
DEST = "/media/disk/backup/script/build_busybox_static.sh"

cli = sshutil.connect('10.0.0.4', 22)

print("=== 安装前，脚本目录内容 ===")
print(sshutil.run(cli, 'ls -la /media/disk/backup/script/', timeout=60))

data = open(LOCAL, 'rb').read().replace(b'\r\n', b'\n')
print("本地 md5:", hashlib.md5(data).hexdigest(), len(data), "字节")

print("\n=== 安装（若已存在先备份）===")
print(sshutil.run(cli,
    'D=%s; if [ -e "$D" ]; then cp -a "$D" "$D.bak-$(date +%%Y%%m%%d-%%H%%M%%S)"; fi; '
    'echo "备份检查完成"' % DEST, timeout=60))

ch = cli.get_transport().open_session()
ch.exec_command('cat > %s' % DEST)
ch.sendall(data)
ch.shutdown_write()
ch.recv_exit_status()

print(sshutil.run(cli, 'chmod 755 %s; ls -la /media/disk/backup/script/; md5sum %s' % (DEST, DEST),
                  timeout=60))
print(sshutil.run(cli, 'sh -n %s && echo "语法检查 OK"' % DEST, timeout=60))

print("\n=== 清理我在 /tmp 里留下的临时目录（保留脚本与产物）===")
print(sshutil.run(cli, 'rm -rf /tmp/bbsrc /tmp/bbsrc2 /tmp/list.txt /tmp/probe.txt /tmp/probe.sh; '
                       'ls -la /tmp/ | head -20', timeout=60))
print("\n=== 保留的产物 ===")
print(sshutil.run(cli, 'ls -la /tmp/build_busybox_static.sh /tmp/bbbuild/', timeout=60))
cli.close()
