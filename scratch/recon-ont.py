#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""侦察：光猫 user_init.sh 结构 + 编译服务器 upx 情况。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

print("################ 光猫 ################")
o = sshutil.connect('192.168.1.1', 10022)
print("=== user_init.sh 全文 ===")
print(sshutil.run(o, 'cat -A /f4610u/user_init.sh | sed "s/\\$$//" | head -80', timeout=60))
print("=== 行数 / 权限 / 备份 ===")
print(sshutil.run(o, 'wc -l /f4610u/user_init.sh; ls -la /f4610u/user_init.sh*; '
                     'echo ---; ls -la /f4610u/bin/ | head -30; echo ---; '
                     'mount | grep -E "f4610u|overlay" | head -5; echo ---; '
                     'df -h /f4610u | tail -2', timeout=60))
print("=== rc.local ===")
print(sshutil.run(o, 'cat /etc/rc.local 2>/dev/null; echo "--- crontab ---"; crontab -l 2>/dev/null | head', timeout=60))
print("=== 当前 WAN ===")
print(sshutil.run(o, 'ifconfig ppp0 2>/dev/null | grep -E "inet addr|HWaddr"; echo ---; '
                     'ifconfig br0 | grep "inet addr"; echo ---; '
                     'ifconfig br0:0 2>/dev/null | grep "inet addr" || echo "(无别名)"', timeout=60))
o.close()

print("\n################ 编译服务器 ################")
b = sshutil.connect('10.0.0.4', 22)
print(sshutil.run(b, 'which upx || echo "(没有 upx)"; upx --version 2>/dev/null | head -2; '
                     'apt-cache policy upx 2>/dev/null | head -4; '
                     'ls /root/go/bin 2>/dev/null; go version', timeout=120))
b.close()
