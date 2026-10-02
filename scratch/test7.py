#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""重测路径 A：从局域网主机连光猫真实 WAN IP:8188，看是否被内核"本地投递"到 fakecloud。
（用户已去掉光猫上的回环规则，重测对比。）

服务端保持后台运行，便于随后用真插座验证。
"""
import os
import socket
import ssl
import time

import paramiko

PW = os.environ["SSH_PW"]
ONT = ("192.168.1.1", 10022)
WAN_IP = os.environ.get("WAN_IP", "139.227.20.142")
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


def tls_probe(ip, label, tries=2):
    ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
    ctx.minimum_version = ssl.TLSVersion.TLSv1
    ctx.maximum_version = ssl.TLSVersion.TLSv1_1
    ctx.set_ciphers("AES256-SHA:@SECLEVEL=0")
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    for i in range(tries):
        try:
            raw = socket.create_connection((ip, PORT), timeout=5)
        except Exception as e:
            print(f"   [{label}] 第{i+1}次 TCP 不通: {type(e).__name__} {e}")
            time.sleep(1)
            continue
        try:
            t = ctx.wrap_socket(raw)
            print(f"   [{label}] ★ 第{i+1}次 TLS 握手成功 {t.version()} {t.cipher()} —— 通！")
            t.close()
            return True
        except Exception as e:
            print(f"   [{label}] 第{i+1}次 TCP 通但 TLS 失败: {type(e).__name__} {e}")
            return False
    return False


print("=" * 68)
print("0) 重新拉起服务端")
print("   ", sh("killall fakecloud 2>/dev/null; rm -f /tmp/fc.log /tmp/fc.jsonl; "
                "nohup /tmp/fakecloud -cert /tmp/server.crt -key /tmp/server.key "
                "-listen :8188 -jsonl /tmp/fc.jsonl </dev/null >>/tmp/fc.log 2>&1 & "
                "sleep 2; echo started")[0])
print("   ", sh("netstat -ltn 2>/dev/null | grep 8188 || echo '未监听'")[0])
print("   当前 ppp0 / 别名：")
print("   ", sh("ifconfig 2>/dev/null | grep -A1 -E 'ppp0|br0:0' | grep -E 'ppp0|br0:0|inet addr' | head -8")[0])

print(f"\n1) 路径 A：PC → {WAN_IP}:{PORT}（真实 WAN IP / DDNS 解析值）")
okA = tls_probe(WAN_IP, "A/WAN")

print(f"\n2) 对照：路径 B：PC → {FAKE_IP}:{PORT}（伪造公网 IP 别名）")
print("   ", sh(f"ifconfig br0:0 {FAKE_IP} netmask 255.255.255.255 up 2>/dev/null; echo rc=$?")[0])
time.sleep(1)
okB = tls_probe(FAKE_IP, "B/FAKE")

print("\n3) 服务端日志")
o, _ = sh("cat /tmp/fc.log 2>/dev/null | head -40")
print(o if o else "(空)")

cli.close()
print(f"\n{'='*68}\n结论:  路径A(真实WAN IP) = {'✅ 通' if okA else '❌ 不通'}   路径B(伪造IP别名) = {'✅ 通' if okB else '❌ 不通'}")
