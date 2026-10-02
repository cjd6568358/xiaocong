# -*- coding: utf-8 -*-
"""把 busybox-full UPX 后放回光猫 /tmp/busybox-full_upx。

为什么这么绕：
  · 光猫上没有 upx，本机 Windows 上也没有 → 借编译服务器 10.0.0.4（upx 4.2.4）
  · 光猫的 dropbear【不支持 SFTP】（EOF during negotiation）→ 回传改用
    "base64 编码后走 SSH stdin，远端 /tmp/busybox-full base64 -d 还原"
  · 不需要从光猫下载：本机 firmware/busybox-full-aarch64 的 md5 与光猫
    /tmp/busybox-full 一致（d951d2aa…），直接压本机那份即可
"""
import os, sys, base64, time, socket
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil, paramiko

PW = os.environ.setdefault("SSH_PW", "e3eb773F")
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "firmware", "busybox-full-aarch64")
DST = os.path.join(ROOT, ".scratch", "busybox-full_upx")
RMT = "/tmp/busybox-full_upx"

def sh(cli, c, timeout=180):
    return sshutil.run(cli, c, timeout=timeout).strip()

def conn(host, port):
    cli = paramiko.SSHClient()
    cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    cli.connect(host, port, username="root", password=PW, timeout=15,
                look_for_keys=False, allow_agent=False)
    return cli

def put_base64(cli, local_path, remote_path, chunk=1 << 18):
    """远端没有 SFTP 时的上传兜底：base64 走 stdin。"""
    raw = open(local_path, "rb").read()
    b64 = base64.b64encode(raw)
    ch = cli.get_transport().open_session()
    ch.settimeout(120)
    ch.exec_command("/tmp/busybox-full base64 -d > %s 2>/dev/null" % remote_path)
    off = 0
    while off < len(b64):
        n = ch.send(b64[off:off + chunk])
        off += n if n else 0
        if n == 0:
            time.sleep(0.02)
    ch.shutdown_write()
    err = b""
    while True:
        try:
            d = ch.recv_stderr(65536)
        except socket.timeout:
            d = None
        if d:
            err += d
            continue
        if ch.exit_status_ready():
            break
        time.sleep(0.05)
    rc = ch.recv_exit_status()
    ch.close()
    return rc, err.decode("utf-8", "ignore")

srv = conn("10.0.0.4", 22)
ont = conn("192.168.1.1", 10022)

print("=" * 62); print("① 本机源文件"); print("=" * 62)
import hashlib
print("md5      :", hashlib.md5(open(SRC, "rb").read()).hexdigest())
print("size     :", os.path.getsize(SRC))

print("\n" + "=" * 62); print("② 上传编译服务器并 UPX"); print("=" * 62)
sh(srv, "mkdir -p /tmp/bb-upx && rm -f /tmp/bb-upx/*")
sf = paramiko.SFTPClient.from_transport(srv.get_transport())
sf.put(SRC, "/tmp/bb-upx/busybox-full"); sf.close()
print("uploaded ok")
# UPX 拒压没有执行位的文件（CantPackException: file not executable）→ 先补 +x。
# 本机 Windows 下 git 的 umask 把执行位丢了，SFTP put 过去就是 644。
print("chmod:", sh(srv, "cd /tmp/bb-upx && chmod 755 busybox-full && ls -l busybox-full | awk '{print $1, $5}'"))
print(sh(srv, "cd /tmp/bb-upx && upx --best --lzma -o busybox-full_upx busybox-full 2>&1"))
print("--- upx -t 完整性自检 ---")
print(sh(srv, "cd /tmp/bb-upx && upx -t busybox-full_upx 2>&1"))
print("--- 体积 ---")
print(sh(srv, "ls -l /tmp/bb-upx/busybox-full /tmp/bb-upx/busybox-full_upx | awk '{print $5, $9}'"))

print("\n" + "=" * 62); print("③ 拉回本机"); print("=" * 62)
sf2 = paramiko.SFTPClient.from_transport(srv.get_transport())
sf2.get("/tmp/bb-upx/busybox-full_upx", DST); sf2.close()
print("downloaded:", DST, os.path.getsize(DST), "bytes")

print("\n" + "=" * 62); print("④ 回传光猫（base64 over stdin）"); print("=" * 62)
rc, err = put_base64(ont, DST, RMT)
print("upload rc =", rc, ("stderr=" + err) if err else "")
sh(ont, "chmod 755 %s" % RMT)

print("\n" + "=" * 62); print("⑤ 光猫上验证"); print("=" * 62)
for title, c in [
    ("size    ", "ls -l /tmp/busybox-full /tmp/busybox-full_upx | awk '{print $5, $9}'"),
    ("md5     ", "md5sum /tmp/busybox-full_upx | awk '{print $1}'"),
    ("version ", "/tmp/busybox-full_upx 2>&1 | head -1"),
    ("applets ", "/tmp/busybox-full_upx --list 2>/dev/null | wc -l"),
    ("实跑    ", "echo hello | /tmp/busybox-full_upx sha256sum"),
    ("对照    ", "echo hello | /tmp/busybox-full sha256sum"),
    ("od/diff ", "/tmp/busybox-full_upx od -An -tx1 -N4 /dev/urandom >/dev/null 2>&1 && echo 'od 可用' || echo 'od 不可用'; /tmp/busybox-full_upx diff </dev/null >/dev/null 2>&1; echo \"diff 退出码=$?\""),
]:
    try:
        print(f"{title}: {sh(ont, c, timeout=60)}")
    except Exception as e:
        print(f"{title}: [ERR] {e}")

ont.close(); srv.close()
