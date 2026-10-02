#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""实验：用 iptables 把 局域网→WAN IP:8188 的流量拉回本地（修好"路径 A"）。
两种打法：DNAT 到 192.168.1.1:8188、REDIRECT 到本地 8188。结束自动回滚。
"""
import os
import socket
import ssl
import time
import paramiko

PW = os.environ["SSH_PW"]
WAN_IP = "139.227.20.142"
PORT = 8188
PRE = "export LD_LIBRARY_PATH=/f4610u/lib; "
IPT = "/f4610u/bin/iptables_upx"

cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=PW, timeout=12,
            look_for_keys=False, allow_agent=False)


def sh(c, t=40):
    _, o, e = cli.exec_command(PRE + c, timeout=t)
    return o.read().decode("utf-8", "replace").strip()


def probe(ip, label):
    ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
    ctx.minimum_version = ssl.TLSVersion.TLSv1
    ctx.maximum_version = ssl.TLSVersion.TLSv1_1
    ctx.set_ciphers("AES256-SHA:@SECLEVEL=0")
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    try:
        raw = socket.create_connection((ip, PORT), timeout=5)
    except Exception as e:
        print(f"   [{label}] TCP 不通: {type(e).__name__}")
        return False
    try:
        t = ctx.wrap_socket(raw)
        print(f"   [{label}] ★ TLS 成功 {t.version()} {t.cipher()}")
        t.close()
        return True
    except Exception as e:
        print(f"   [{label}] TCP 通/TLS 失败: {type(e).__name__} {e}")
        return False


print("## 先看 nat PREROUTING 全貌")
print(sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers 2>&1 | head -40"))

print("\n## 起 fakecloud")
print("  ", sh("killall fakecloud 2>/dev/null; rm -f /tmp/fc.log; "
                "nohup /tmp/fakecloud -cert /tmp/server.crt -key /tmp/server.key "
                "-listen :8188 -jsonl /tmp/fc.jsonl </dev/null >>/tmp/fc.log 2>&1 & sleep 2; echo ok")[0])

results = {}

# --- 打法 1: DNAT ---
print("\n## 打法 1：DNAT --to-destination 192.168.1.1:8188")
print("  ", sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j DNAT --to-destination 192.168.1.1:{PORT}; echo rc=$?")[0])
time.sleep(1)
results["DNAT"] = probe(WAN_IP, "A/DNAT")
print("  ", sh(f"{IPT} -t nat -D PREROUTING -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j DNAT --to-destination 192.168.1.1:{PORT}; echo 已删 rc=$?")[0])

# --- 打法 2: REDIRECT ---
print("\n## 打法 2：REDIRECT --to-ports 8188")
print("  ", sh(f"{IPT} -t nat -I PREROUTING 1 -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j REDIRECT --to-ports {PORT}; echo rc=$?")[0])
time.sleep(1)
results["REDIRECT"] = probe(WAN_IP, "A/REDIRECT")
print("  ", sh(f"{IPT} -t nat -D PREROUTING -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j REDIRECT --to-ports {PORT}; echo 已删 rc=$?")[0])

# --- 打法 3: 在 mangle 里先 ACCEPT 掉 wanipbind 的 MARK ---
print("\n## 打法 3：REDIRECT + 同时清掉 mangle 的 wanipbind 影响（加 RETURN）")
print("  ", sh(f"{IPT} -t mangle -I PREROUTING 1 -i br0 -d {WAN_IP} -j RETURN; "
                f"{IPT} -t nat -I PREROUTING 1 -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j REDIRECT --to-ports {PORT}; echo rc=$?")[0])
time.sleep(1)
results["REDIRECT+mangle"] = probe(WAN_IP, "A/RED+mangle")
print("  ", sh(f"{IPT} -t nat -D PREROUTING -i br0 -d {WAN_IP} -p tcp --dport {PORT} "
                f"-j REDIRECT --to-ports {PORT} 2>/dev/null; "
                f"{IPT} -t mangle -D PREROUTING -i br0 -d {WAN_IP} -j RETURN 2>/dev/null; echo 已删")[0])

print("\n## 服务端日志")
print(sh("cat /tmp/fc.log 2>/dev/null | head -30"))
print("\n## conntrack 里 WAN IP 相关")
print(sh(f"cat /proc/net/nf_conntrack 2>/dev/null | grep -c {WAN_IP}; "
         f"cat /proc/net/nf_conntrack 2>/dev/null | grep {WAN_IP} | head -5"))
print("\n## 收尾")
print(sh("killall fakecloud 2>/dev/null; echo done"))
print("  nat PREROUTING 现在前 3 条：")
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers 2>&1 | head -6"))

cli.close()
print("\n" + "=" * 60)
print("结论：", "  ".join(f"{k}={'✅' if v else '❌'}" for k, v in results.items()))
