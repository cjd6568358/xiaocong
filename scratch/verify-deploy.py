#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""部署后验证：
  1) user_init.sh 语法 + 其余部分没被改坏
  2) 假 DNS 能不能把 iot.ixiaocong.com / allin.dns.army 应答成 203.0.113.9
  3) ★ 从 PC（LAN 侧）连【光猫自己的 WAN IP】:8188 —— 回环规则到底生效没有
  4) HTTP 控制接口
"""
import socket, ssl, struct, sys, io, time
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')
import sshutil

WAN = "139.227.20.142"
ONT = "192.168.1.1"

print("=" * 72)
print("1) user_init.sh 语法 + 完整性")
o = sshutil.connect(ONT, 10022)
print("  sh -n :", sshutil.run(o, 'sh -n /f4610u/user_init.sh && echo "语法 OK"', timeout=60))
print("  与上一版备份的差异（应只有 ixc-go 段）:")
print(sshutil.run(o, 'diff /f4610u/user_init.sh.bak-20261002-030321 /f4610u/user_init.sh '
                     '| head -8; echo "...(略)"; '
                     'diff /f4610u/user_init.sh.bak-20261002-030321 /f4610u/user_init.sh | wc -l', timeout=60))
print("  旧内容是否原样保留（dropbear/tun/easytier/table252 关键字计数）:")
print(sshutil.run(o, 'for k in dropbear wospeeder tun.ko easytier "table 252" ip_forward; do '
                     'printf "    %-12s %s\\n" "$k" "$(grep -c "$k" /f4610u/user_init.sh)"; done', timeout=60))


def dns_query(server, port, name, timeout=5):
    """手搓一个 DNS A 查询"""
    tid = 0x1234
    q = struct.pack(">HHHHHH", tid, 0x0100, 1, 0, 0, 0)
    for part in name.split("."):
        q += bytes([len(part)]) + part.encode()
    q += b"\x00" + struct.pack(">HH", 1, 1)
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.settimeout(timeout)
    s.sendto(q, (server, port))
    data, _ = s.recvfrom(2048)
    s.close()
    ancount = struct.unpack(">H", data[6:8])[0]
    rcode = data[3] & 0x0F
    # 跳过问题段
    i = 12
    while data[i] != 0:
        i += data[i] + 1
    i += 5
    answers = []
    for _ in range(ancount):
        if data[i] & 0xC0 == 0xC0:
            i += 2
        else:
            while data[i] != 0:
                i += data[i] + 1
            i += 1
        typ, cls, ttl, rdlen = struct.unpack(">HHIH", data[i:i + 10])
        i += 10
        if typ == 1 and rdlen == 4:
            answers.append(".".join(str(b) for b in data[i:i + 4]))
        i += rdlen
    return rcode, answers


print("\n2) 假 DNS 应答（直接问光猫 192.168.1.1:5353）")
for name in ["iot.ixiaocong.com", "allin.dns.army", "www.baidu.com"]:
    try:
        rcode, ans = dns_query(ONT, 5353, name)
        print(f"    {name:24s} rcode={rcode} answers={ans}")
    except Exception as e:
        print(f"    {name:24s} ❌ {e}")

print(f"\n3) ★ 从 PC(LAN 侧) 连光猫自己的 WAN IP {WAN}:8188 —— 回环规则的关键验证")
try:
    ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    ctx.minimum_version = ssl.TLSVersion.TLSv1
    ctx.maximum_version = ssl.TLSVersion.TLSv1_1
    ctx.set_ciphers("AES256-SHA:AES128-SHA:DES-CBC3-SHA:@SECLEVEL=0")
    t0 = time.time()
    with socket.create_connection((WAN, 8188), timeout=8) as raw:
        print(f"    TCP 连上了（{time.time()-t0:.2f}s）")
        with ctx.wrap_socket(raw, server_hostname="iot.ixiaocong.com") as s:
            print(f"    ✅ TLS 握手成功  版本={s.version()}  套件={s.cipher()[0]}")
            print(f"    ✅ ⇒ allin.dns.army 可直接用（回环规则生效）")
except Exception as e:
    print(f"    ❌ {type(e).__name__}: {e}")

print("\n   对照：连内网地址 192.168.1.1:8188（不走 br0，回环规则不匹配，但本机监听在）")
try:
    with socket.create_connection((ONT, 8188), timeout=5):
        print("    TCP 通（预期）")
except Exception as e:
    print(f"    ❌ {e}")

print("\n4) HTTP 控制接口")
for path in ["/status", "/off"]:
    try:
        import urllib.request
        print(f"    GET {path:10s} →", urllib.request.urlopen(
            f"http://{ONT}:8080{path}", timeout=6).read().decode().strip())
    except Exception as e:
        print(f"    GET {path:10s} → ❌ {e}")

print("\n5) 光猫状态")
print("  ", sshutil.run(o, 'uptime; echo ---; df -h / | tail -1; echo ---; '
                         'ls -la /f4610u/bin/ixc-go-arm64_upx /f4610u/etc/', timeout=60))
print("  日志尾:")
print(sshutil.run(o, 'tail -6 /tmp/ixc-go.log', timeout=60))
o.close()
