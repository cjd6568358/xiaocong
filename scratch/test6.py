#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""把 Go 版 fakecloud 部署到光猫 /tmp，并实测"让公网地址落到光猫本地"的两条路径。

 路径 A：连 WAN IP  139.227.20.142:8188（配合 DDNS，无需任何伪造）
 路径 B：连伪造公网 IP 203.0.113.9:8188（配合 ifconfig 别名）

二进制/证书走 SSH stdin 直传（光猫无 sftp subsystem，也不用 PC 上的 HTTP 服务）。
所有改动可逆，结束自动清理。
"""
import os
import socket
import ssl
import sys
import time

import paramiko

PW = os.environ["SSH_PW"]
ONT = ("192.168.1.1", 10022)
ROOT = r"C:\workspace\xiaocong"
WAN_IP = "139.227.20.142"
FAKE_IP = "203.0.113.9"
PORT = 8188

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect(*ONT, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=30):
    _, o, e = cli.exec_command(c, timeout=t)
    return (o.read().decode("utf-8", "replace").strip(),
            e.read().decode("utf-8", "replace").strip())


def put(local, remote):
    """用 cat > remote 走 stdin 直传，绕开 sftp 不可用的问题。"""
    _, _, _ = cli.exec_command(f"rm -f {remote}")
    time.sleep(0.2)
    stdin, stdout, stderr = cli.exec_command(f"cat > {remote}", timeout=300)
    n = 0
    with open(local, "rb") as f:
        while True:
            chunk = f.read(65536)
            if not chunk:
                break
            stdin.write(chunk)
            n += len(chunk)
    stdin.flush()
    stdin.channel.shutdown_write()
    rc = stdout.channel.recv_exit_status()
    return n, rc


print("=" * 68)
print("1) 直传二进制 + 证书（含 server.key）到光猫 /tmp")
for local, remote in [
    (os.path.join(ROOT, ".scratch", "fakecloud-arm64"), "/tmp/fakecloud"),
    (os.path.join(ROOT, "server", "certs", "server.crt"), "/tmp/server.crt"),
    (os.path.join(ROOT, "server", "certs", "server.key"), "/tmp/server.key"),
]:
    n, rc = put(local, remote)
    print(f"    {os.path.basename(remote):14s} {n:>9,d} bytes  rc={rc}")
print("   ", sh("chmod +x /tmp/fakecloud; ls -la /tmp/fakecloud /tmp/server.crt /tmp/server.key 2>&1")[0])

print("\n2) 确认光猫 WAN IP / 网卡")
print("   ", sh("ifconfig 2>/dev/null | grep -E 'inet addr|UP|Link' | head -20")[0])
print("    --- 默认路由 ---")
print("   ", sh("route -n 2>/dev/null | head -8")[0])


def tls_probe(ip, label):
    """从 PC 用 TLS1.1 + 静态 RSA 连过去，验证整条链路"""
    ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
    ctx.minimum_version = ssl.TLSVersion.TLSv1
    ctx.maximum_version = ssl.TLSVersion.TLSv1_1
    ctx.set_ciphers("AES256-SHA:@SECLEVEL=0")
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    try:
        raw = socket.create_connection((ip, PORT), timeout=6)
    except Exception as e:
        print(f"   [{label}] TCP 不通: {type(e).__name__} {e}")
        return False
    print(f"   [{label}] TCP 通了 ✅")
    try:
        t = ctx.wrap_socket(raw)
        print(f"   [{label}] ★ TLS 握手成功 {t.version()} {t.cipher()} —— 整条链路完全打通！")
        t.close()
        return True
    except Exception as e:
        print(f"   [{label}] TLS 失败(但 TCP 可达): {type(e).__name__} {e}")
        return False


print("\n3) 起服务端（后台，完全脱离会话）")
print("   ", sh("killall fakecloud 2>/dev/null; rm -f /tmp/fc.log /tmp/fc.jsonl; "
                "nohup /tmp/fakecloud -cert /tmp/server.crt -key /tmp/server.key "
                "-listen :8188 -jsonl /tmp/fc.jsonl </dev/null >>/tmp/fc.log 2>&1 & "
                "sleep 2; echo started")[0])
print("   ", sh("netstat -ltn 2>/dev/null | grep -E '8188|Proto' | head -6")[0])

print(f"\n4) 路径 A：从 PC 连 WAN IP {WAN_IP}:{PORT}")
okA = tls_probe(WAN_IP, "A/WAN")

print(f"\n5) 路径 B：加别名 {FAKE_IP} 后从 PC 连它")
print("   ", sh(f"ifconfig br0:0 {FAKE_IP} netmask 255.255.255.255 up; echo rc=$?")[0])
time.sleep(1)
okB = tls_probe(FAKE_IP, "B/FAKE")

print("\n6) 光猫端服务日志（前 30 行）")
o, _ = sh("head -30 /tmp/fc.log 2>/dev/null")
print(o if o else "(空)")

print("\n7) 清理")
print("   ", sh(f"killall fakecloud 2>/dev/null; ifconfig br0:0 down 2>/dev/null; echo cleaned")[0])
print("   ", sh("sleep 1; netstat -ltn 2>/dev/null | grep 8188 || echo '8188 已释放'")[0])

cli.close()
print(f"\n{'='*68}\n结论:  路径A(WAN IP) = {'✅ 通' if okA else '❌ 不通'}   路径B(伪造IP) = {'✅ 通' if okB else '❌ 不通'}")
