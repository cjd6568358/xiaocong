#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

o = sshutil.connect('192.168.1.1', 10022)
print(sshutil.run(o, 'cat /f4610u/user_init.sh', timeout=60))
print("\n=========== 当前进程 / 端口 / 规则 ===========")
print(sshutil.run(o, 'ps | grep -E "[i]xc|[b]usybox-full|[f]akecloud" || echo "(无相关进程)"; echo ---; '
                     'netstat -ltn | head -20', timeout=60))
print(sshutil.run(o, 'export LD_LIBRARY_PATH=/f4610u/lib; '
                     '/f4610u/bin/iptables_upx -t nat -L PREROUTING -n --line-numbers', timeout=60))
o.close()
