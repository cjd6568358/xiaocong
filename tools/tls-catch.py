#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tls-catch.py —— 坐在本机监听，等插座被"引导"过来，抓它的 TLS 握手

配合 `lan-mitm.py --mode dns` 使用：
    1) lan-mitm 伪造 DNS，把 iot.ixiaocong.com 解析到本机 192.168.1.20
    2) 插座以为在连官方云端，实际 TCP 连到我们这里
    3) 本脚本接住连接，记录：
         - 它连的是哪个端口（暴露固件用的真实服务端口）
         - TLS ClientHello 里的 SNI（再次确认域名）
         - 支持的密码套件 / ALPN / 扩展（判断是不是 ESP8266 mbedTLS）
         - 我们用自签证书完成握手时，插座**是否接受**（→ 判断它有没有校验证书）

用法：
    python tools/tls-catch.py                       # 默认监听 443,80,8883,1883,8443,8080
    python tools/tls-catch.py --ports 443,8883
    python tools/tls-catch.py --seconds 900         # 监听时长

输出：tls-catch.log（可读）  tls-catch.jsonl（结构化）
"""
import argparse
import json
import os
import socket
import ssl
import struct
import threading
import time
from datetime import datetime

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
CERTS = os.path.join(ROOT, "server", "certs")

LOG = None
RECORDS = []


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOG:
        LOG.write(line + "\n")
        LOG.flush()


def rec(kind, **kw):
    r = {"wall": datetime.now().isoformat(timespec="seconds"), "kind": kind}
    r.update(kw)
    RECORDS.append(r)
    return r


def parse_client_hello(data):
    """从 TLS record 里抠出 ClientHello 关键信息。"""
    out = {"sni": None, "version": None, "ciphers": [], "alpn": [], "ext_types": []}
    try:
        if len(data) < 6 or data[0] != 0x16:
            return out
        out["version"] = f"0x{data[1]:02x}{data[2]:02x}"
        if data[5] != 0x01:
            return out
        p = 9 + 2 + 32
        sid_len = data[p]
        p += 1 + sid_len
        cs_len = struct.unpack(">H", data[p:p + 2])[0]
        p += 2
        cs = data[p:p + cs_len]
        out["ciphers"] = [f"0x{cs[i]:02x}{cs[i+1]:02x}" for i in range(0, len(cs), 2)]
        p += cs_len
        comp_len = data[p]
        p += 1 + comp_len
        if p + 2 > len(data):
            return out
        ext_total = struct.unpack(">H", data[p:p + 2])[0]
        p += 2
        end = min(len(data), p + ext_total)
        while p + 4 <= end:
            etype = struct.unpack(">H", data[p:p + 2])[0]
            elen = struct.unpack(">H", data[p + 2:p + 4])[0]
            p += 4
            out["ext_types"].append(etype)
            if etype == 0 and p + 5 <= len(data):      # server_name
                nlen = struct.unpack(">H", data[p + 3:p + 5])[0]
                out["sni"] = data[p + 5:p + 5 + nlen].decode("ascii", "replace")
            if etype == 16 and p + 2 <= len(data):     # ALPN
                try:
                    alen = struct.unpack(">H", data[p + 2:p + 4])[0]
                    q = p + 4
                    qe = min(len(data), q + alen)
                    while q < qe:
                        l = data[q]
                        out["alpn"].append(data[q + 1:q + 1 + l].decode("ascii", "replace"))
                        q += 1 + l
                except Exception:
                    pass
            p += elen
    except Exception:
        pass
    return out


def peek_client_hello(conn, timeout=6.0):
    """
    用 MSG_PEEK **偷看**客户端发来的第一条 TLS record（ClientHello），不消费它。
    为什么必须用 PEEK：
        如果先把 ClientHello 读走，再交给 ssl.wrap_socket() 握手，
        对方会因收不到 ClientHello 而僵死（本工具最初就是这么错的）。
    """
    conn.settimeout(0.4)
    deadline = time.time() + timeout
    buf = b""
    while time.time() < deadline:
        try:
            d = conn.recv(4096, socket.MSG_PEEK)
        except socket.timeout:
            continue
        except Exception:
            break
        if d:
            buf = d                      # PEEK 每次都返回当前缓冲区全部内容
            if len(buf) >= 5:
                if buf[0] != 0x16:
                    return buf
                need = 5 + struct.unpack(">H", buf[3:5])[0]
                if len(buf) >= need:
                    return buf[:need]
        else:
            break
    return buf


def make_ssl_context():
    ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    # 优先用 EC 证书（插座是 ESP8266/mbedTLS，通常只支持 ECDHE）
    for crt, key in [("ec-server.crt", "ec-server.key"),
                     ("server.crt", "server.key")]:
        c, k = os.path.join(CERTS, crt), os.path.join(CERTS, key)
        if os.path.exists(c) and os.path.exists(k):
            try:
                ctx.load_cert_chain(c, k)
                log(f"TLS 服务使用证书：{crt}")
                return ctx
            except Exception as e:
                log(f"加载 {crt} 失败：{e}")
    log("⚠️ 没找到可用证书（server/certs/），将只记录 ClientHello、不做完整握手")
    return None


def handle_conn(conn, addr, port, ctx, ssl_handshake):
    try:
        peer = f"{addr[0]}:{addr[1]}"
        log(f"★ 有连接进来 {peer} → 本机:{port}", important=True)
        data = peek_client_hello(conn)
        info = parse_client_hello(data)
        log(f"  ClientHello: ver={info['version']} SNI={info['sni']} "
            f"ALPN={info['alpn']} ciphers={len(info['ciphers'])}个 扩展={len(info['ext_types'])}个")
        rec("client_hello", peer=peer, port=port, **info)

        if not ssl_handshake or ctx is None:
            return
        if not data or data[0] != 0x16:
            log(f"  （不是 TLS 流量，首字节=0x{data[0]:02x}；前 64 字节 {data[:64]!r}）")
            rec("non_tls", peer=peer, port=port, hex=data[:256].hex())
            return
        # 用自签证书完成握手 —— 插座接不接受，就是"有没有校验证书"的判据
        try:
            conn.settimeout(15.0)
            tls = ctx.wrap_socket(conn, server_side=True)
            log(f"  ✅ TLS 握手**成功**（插座接受了我们的自签证书 → 很可能不校验证书！）",
                important=True)
            rec("tls_handshake", peer=peer, port=port, result="accepted",
                cipher=tls.cipher()[0] if tls.cipher() else None,
                version=tls.version())
            try:
                tls.settimeout(10)
                app = tls.recv(4096)
                if app:
                    log(f"  ← 应用层数据 {len(app)} 字节：{app[:120]!r}")
                    rec("app_data", peer=peer, port=port, length=len(app),
                        hex=app[:256].hex())
            except Exception:
                pass
            try:
                tls.close()
            except Exception:
                pass
        except ssl.SSLError as e:
            # 区分「证书被拒」（会收到 TLS alert）与「连接中断」（不可判）
            msg = str(e)
            is_alert = ("alert" in msg.lower()) or ("certificate" in msg.lower())
            if is_alert:
                log(f"  ❌ TLS 握手被**拒绝**（收到告警）：{msg}", important=True)
                log(f"     → 插座校验了证书！必须走 path A（crt 方案）", important=True)
                rec("tls_handshake", peer=peer, port=port, result="rejected", error=msg)
            else:
                log(f"  ⚠️ 握手未完成（非证书原因，可能对端提前断开）：{msg}")
                rec("tls_handshake", peer=peer, port=port, result="inconclusive", error=msg)
        except (ConnectionAbortedError, ConnectionResetError, TimeoutError) as e:
            log(f"  ⚠️ 握手未完成（连接中断/超时，不可判）：{type(e).__name__} {e}")
            rec("tls_handshake", peer=peer, port=port, result="inconclusive",
                error=f"{type(e).__name__} {e}")
        except Exception as e:
            log(f"  ⚠️ 握手异常：{type(e).__name__} {e}")
            rec("tls_handshake", peer=peer, port=port, result="error", error=str(e))
    except Exception as e:
        log(f"处理连接出错：{type(e).__name__} {e}")
    finally:
        try:
            conn.close()
        except Exception:
            pass


def listen(port, ctx, ssl_handshake, stop):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    try:
        s.bind(("0.0.0.0", port))
        s.listen(8)
    except Exception as e:
        log(f"❌ 监听 {port} 失败：{e}")
        return
    s.settimeout(1.0)
    log(f"监听 0.0.0.0:{port} …")
    while not stop["v"]:
        try:
            conn, addr = s.accept()
        except socket.timeout:
            continue
        except Exception:
            break
        threading.Thread(target=handle_conn,
                         args=(conn, addr, port, ctx, ssl_handshake), daemon=True).start()
    try:
        s.close()
    except Exception:
        pass


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--ports", default="443,80,8883,1883,8443,8080")
    ap.add_argument("--seconds", type=int, default=900)
    ap.add_argument("--log", default="tls-catch.log")
    ap.add_argument("--json", default="tls-catch.jsonl")
    ap.add_argument("--no-handshake", action="store_true",
                    help="只记录 ClientHello，不用自签证书完成握手")
    a = ap.parse_args()

    global LOG
    LOG = open(a.log, "w", encoding="utf-8")
    LOG.write(f"===== tls-catch  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    ctx = None if a.no_handshake else make_ssl_context()
    ports = [int(p) for p in a.ports.split(",") if p.strip()]
    stop = {"v": False}

    log(f"目标：接住被引导过来的插座连接（监听端口 {ports}）")
    ths = []
    for p in ports:
        t = threading.Thread(target=listen, args=(p, ctx, not a.no_handshake, stop), daemon=True)
        t.start()
        ths.append(t)

    t0 = time.time()
    try:
        while time.time() - t0 < a.seconds:
            time.sleep(1)
    except KeyboardInterrupt:
        pass
    stop["v"] = True

    with open(a.json, "w", encoding="utf-8") as f:
        for r in RECORDS:
            f.write(json.dumps(r, ensure_ascii=False) + "\n")

    log("")
    log(f"===== 结束：共 {len(RECORDS)} 条记录 =====")
    if LOG:
        LOG.close()


if __name__ == "__main__":
    main()
