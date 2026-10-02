#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""验证「目标不是内网段」形式的回环规则能否被这台 iptables 接受，并试跑一次。"""
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

o = sshutil.connect('192.168.1.1', 10022)
IPT = 'export LD_LIBRARY_PATH=/f4610u/lib; I=/f4610u/bin/iptables_upx; '

print("=== 目录 ===")
print(sshutil.run(o, 'ls -ld /f4610u/etc /f4610u/app /f4610u/bin 2>&1', timeout=60))

print("=== 加规则（! -d 形式）===")
print(sshutil.run(o, IPT +
    '$I -t nat -D PREROUTING -i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188 -j REDIRECT --to-ports 8188 2>/dev/null; '
    '$I -t nat -I PREROUTING 1 -i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188 -j REDIRECT --to-ports 8188; '
    'echo "rc=$?"', timeout=60))

print("=== 看规则（-v 才显示 -i）===")
print(sshutil.run(o, IPT + '$I -t nat -L PREROUTING -n -v --line-numbers | head -6', timeout=60))

print("=== 幂等性：再执行一次 -C 检查 ===")
print(sshutil.run(o, IPT +
    '$I -t nat -C PREROUTING -i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188 -j REDIRECT --to-ports 8188 '
    '&& echo "已存在(-C 命中)" || echo "不存在"', timeout=60))

print("=== 实测：从光猫本机连自己的 WAN IP:8188 会不会被 REDIRECT ===")
print(sshutil.run(o, 'WANIP=$(ifconfig ppp0 | sed -n "s/.*inet addr:\\([0-9.]*\\).*/\\1/p"); '
                     'echo "WAN=$WANIP"; '
                     'echo -e "GET / HTTP/1.0\\r\\n\\r\\n" | nc -w 3 $WANIP 8188 2>&1 | head -3; '
                     'echo "nc rc=$?"', timeout=90))

print("=== 撤销刚才的测试规则 ===")
print(sshutil.run(o, IPT +
    '$I -t nat -D PREROUTING -i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188 -j REDIRECT --to-ports 8188; '
    'echo "rc=$?"; $I -t nat -L PREROUTING -n -v --line-numbers | head -5', timeout=60))
o.close()
