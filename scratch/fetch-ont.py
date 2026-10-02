# -*- coding: utf-8 -*-
"""1) 确认 user_init.sh 真实路径
   2) 把光猫上【重启即丢失】的产物拉回本机归档"""
import os, sys, hashlib
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

OUT = r"C:\workspace\xiaocong\ont"
os.makedirs(os.path.join(OUT, "snapshots"), exist_ok=True)
os.makedirs(os.path.join(OUT, "logs"), exist_ok=True)

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=180): return sshutil.run(cli, c, timeout=t).strip()

print("=== 1. user_init.sh 到底在哪 ===")
print(sh("ls -la /f4610u/user_init.sh /f4610u/etc/user_init.sh 2>&1"))
print("find:", sh('find / -name "user_init.sh" 2>/dev/null | head'))
print()

print("=== 2. 证书与本机 server/certs 比对 ===")
ONT_MD5 = {"server.crt": "c6e12780d62d18334e676e5af677d503",
           "server.key": "5db3c80591f64b7a2b72a1ccd2251ac9"}
for f, m in ONT_MD5.items():
    local = os.path.join(r"C:\workspace\xiaocong\server\certs", f)
    if os.path.exists(local):
        lm = hashlib.md5(open(local, "rb").read()).hexdigest()
        print(f"  {f}: 光猫={m}  本机={lm}  {'✅ 一致' if lm == m else '❌ 不一致'}")
    else:
        print(f"  {f}: 本机没有 → {local}")
print()

# ---- 拉文件 ----
# 这台 busybox 【没有 base64】（编码和解码都不行，实测 base64 xxx 只回 17 字节错误串），
# 所以不能用 base64 传。要拉的都是纯文本（PEM 证书本身就是文本），直接 cat 读回即可。
def fetch(remote, local_path):
    data = sh(f"cat {remote} 2>&1", t=180)
    if "No such" in data or not data.strip():
        print(f"  ✘ 拉不到 {remote}"); return False
    with open(local_path, "w", encoding="utf-8", newline="") as f:
        f.write(data + "\n")
    print(f"  ✔ {remote} → {local_path}  ({os.path.getsize(local_path)} 字节)")
    return True

print("=== 3. 拉回重启即丢的产物 ===")
files = [
    ("/tmp/ixc-go.log",                 "logs/ixc-go-20261002.log"),
    ("/tmp/ixc.jsonl",                  "logs/ixc-20261002.jsonl"),
    ("/tmp/iptables-nat-backup-20261002-135948.txt",     "snapshots/iptables-nat-before-20261002.txt"),
    ("/tmp/iptables-filter-backup-20261002-135948.txt",  "snapshots/iptables-filter-before-20261002.txt"),
    ("/tmp/iptables-mangle-backup-20261002-135948.txt",  "snapshots/iptables-mangle-before-20261002.txt"),
    ("/f4610u/etc/server.crt",          "certs/server.crt"),
    ("/f4610u/etc/server.key",          "certs/server.key"),
]
os.makedirs(os.path.join(OUT, "certs"), exist_ok=True)
for remote, rel in files:
    fetch(remote, os.path.join(OUT, rel))
print()

print("=== 4. 规则当前快照（写进 snapshots）===")
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"
snap = []
snap.append("# 光猫 nat 表快照  2026-10-02 14:2x  —— 本次接管生效后的状态")
snap.append("# 说明：nat OUTPUT 两条是本次新增（DNS 上游劫持主路径）；")
snap.append("#       nat PREROUTING 第一条是回环规则（之前就部署好的）。")
for tbl in ["nat", "filter", "mangle"]:
    snap.append("")
    snap.append(f"# ---------- -t {tbl} ----------")
    snap.append(sh(f"{IPT} -t {tbl} -S 2>&1", t=180))
open(os.path.join(OUT, "snapshots", "iptables-after-20261002.txt"), "w",
     encoding="utf-8").write("\n".join(snap))
print("  ✔ snapshots/iptables-after-20261002.txt")
cli.close()
