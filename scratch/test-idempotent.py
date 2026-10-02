#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""部署幂等性测试：连跑两次 deploy，验证
   (a) 两次产出的 user_init.sh 逐字节相同
   (b) 剥掉 ixc-go 段后 == 最初那份原始文件（证明没碰任何原有内容）
"""
import subprocess, sys, io, os, hashlib
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

ROOT = r"C:/workspace/xiaocong"
PY = r"C:/Users/caojiecn/.workbuddy-ai/binaries/python/envs/xc/Scripts/python.exe"
ORIG = "/f4610u/user_init.sh.bak-20261002-030321"   # 最初那份（149 行 / 6813 字节）

o = sshutil.connect('192.168.1.1', 10022)


def md5(remote):
    return sshutil.run(o, 'md5sum %s | cut -d" " -f1' % remote, timeout=60).strip()


def stripped_md5():
    sshutil.run(o, 'sed "/^# ==== ixc-go begin ====$/,/^# ==== ixc-go end ====$/d" '
                   '/f4610u/user_init.sh > /tmp/st.sh', timeout=60)
    return md5('/tmp/st.sh')


for i in (1, 2):
    print("=" * 60)
    print("第 %d 次 deploy" % i)
    r = subprocess.run([PY, "firmware/ont-go/deploy.py"], cwd=ROOT,
                       env={**os.environ, "SSH_PW": os.environ["SSH_PW"]},
                       capture_output=True, text=True, encoding="utf-8", errors="replace")
    tail = [l for l in (r.stdout or "").splitlines() if "回环规则 rc" in l or "写入完成" in l]
    print("   ", " | ".join(tail) or "(无关键行)")
    if r.returncode != 0:
        print("    ❌ 退出码", r.returncode)
        print((r.stderr or "")[-800:])
    h = md5('/f4610u/user_init.sh')
    s = stripped_md5()
    print("    user_init.sh md5      :", h)
    print("    剥掉 ixc-go 段后 md5  :", s)
    if i == 1:
        first_h, first_s = h, s
    else:
        print("    (a) 两次产出是否相同  :", "✅ 相同" if h == first_h else "❌ 不同")
        print("    (b) 剥段后 == 原始文件:", "✅ 一致（原有内容一字节未动）" if s == md5(ORIG)
              else "❌ 不一致")

print("\n" + "=" * 60)
print("参考：原始文件 %s" % ORIG)
print("    md5 =", md5(ORIG))
print("    行数/字节 =", sshutil.run(o, f'wc -l < {ORIG}; wc -c < {ORIG}', timeout=60).split())
print("    当前文件 行数/字节 =", sshutil.run(o, 'wc -l < /f4610u/user_init.sh; '
                                                'wc -c < /f4610u/user_init.sh', timeout=60).split())
print("\n备份文件累积情况（每次 deploy 一份，可手工清理）:")
print(sshutil.run(o, 'ls -la /f4610u/user_init.sh.bak-* | wc -l; ls -la /f4610u/user_init.sh.bak-* | tail -5', timeout=60))
o.close()
