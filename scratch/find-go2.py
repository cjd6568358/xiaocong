#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""定位 go 与 upx，确认编译环境。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

b = sshutil.connect('10.0.0.4', 22)
print(sshutil.run(b, 'ls -d /usr/local/go /opt/go /root/go /usr/lib/go* 2>/dev/null; '
                     'find / -maxdepth 4 -name "go" -type f -perm -u+x 2>/dev/null | head -5; '
                     'echo "--- PATH ---"; echo $PATH; '
                     'echo "--- upx ---"; upx --version | head -1; '
                     'echo "--- 磁盘 ---"; df -h /tmp /root | tail -3', timeout=180))
b.close()
