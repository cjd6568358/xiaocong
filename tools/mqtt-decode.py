#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
mqtt-decode.py —— 把 fake-cloud.jsonl 里的 MQTT 明文完整解出来（不再被日志截断）

fake-cloud.log 里 `app[:200]!r` 只留了前 200 个字符，关键字段（比如 baseInfo 尾部的
clientId / checkCode、CONNECT 的 96 字节 password）都被截掉了。
JSONL 里存了 1024 字节的完整 hex —— 这个脚本负责把它还原成可读报文。

用法：
    python tools/mqtt-decode.py fake-cloud.jsonl
"""
import json
import struct
import sys

NAMES = {1: "CONNECT", 2: "CONNACK", 3: "PUBLISH", 4: "PUBACK", 5: "PUBREC", 6: "PUBREL",
         7: "PUBCOMP", 8: "SUBSCRIBE", 9: "SUBACK", 10: "UNSUBSCRIBE", 11: "UNSUBACK",
         12: "PINGREQ", 13: "PINGRESP", 14: "DISCONNECT"}


def rl_decode(b, i):
    """MQTT 剩余长度（变长整数）"""
    mult, val, j = 1, 0, i
    while j < len(b) and j - i < 4:
        d = b[j]
        val += (d & 0x7F) * mult
        mult *= 128
        j += 1
        if not (d & 0x80):
            return val, j
    return None, i


def u16(b, o):
    return struct.unpack(">H", b[o:o + 2])[0]


def decode_connect(body):
    o = 0
    pl = u16(body, o); o += 2
    proto = body[o:o + pl].decode(); o += pl
    level = body[o]; o += 1
    cf = body[o]; o += 1
    keepalive = u16(body, o); o += 2
    cl = u16(body, o); o += 2
    client_id = body[o:o + cl].decode(errors="replace"); o += cl
    d = {"协议名": proto, "协议级别": level, "连接标志": f"0x{cf:02x}", "keepalive": keepalive,
         "clientId": client_id,
         "标志解读": (f"{'cleanSession ' if cf & 0x02 else ''}"
                  f"{'will ' if cf & 0x04 else ''}"
                  f"{'username ' if cf & 0x80 else ''}"
                  f"{'password' if cf & 0x40 else ''}").strip()}
    if cf & 0x04:
        wl = u16(body, o); o += 2
        d["willTopic"] = body[o:o + wl].decode(errors="replace"); o += wl
        wp = u16(body, o); o += 2
        d["willPayload"] = body[o:o + wp].hex(); o += wp
    if cf & 0x80:
        ul = u16(body, o); o += 2
        d["username"] = body[o:o + ul].decode(errors="replace"); o += ul
    if cf & 0x40:
        pw = u16(body, o); o += 2
        raw = body[o:o + pw]; o += pw
        d["password 长度(字段)"] = pw
        d["password 原文"] = raw.decode(errors="replace")
        # 96 字节 ASCII 十六进制 = 48 字节真实签名
        try:
            real = bytes.fromhex(raw.decode())
            d["password 解码"] = f"{len(real)} 字节真实签名 = {real.hex()}"
        except Exception:
            pass
    d["剩余未解析字节"] = len(body) - o
    return d


def decode_publish(b0, body):
    tl = u16(body, 0)
    topic = body[2:2 + tl].decode(errors="replace")
    o = 2 + tl
    qos = (b0 >> 1) & 3
    pid = None
    if qos > 0:
        pid = u16(body, o); o += 2
    payload = body[o:]
    d = {"topic": topic, "qos": qos, "retain": bool(b0 & 1)}
    if pid is not None:
        d["packetId"] = pid
    try:
        d["payload"] = json.loads(payload.decode())
    except Exception:
        d["payload_raw"] = payload.decode(errors="replace")
    return d


def decode_subscribe(body):
    pid = u16(body, 0); o = 2
    subs = []
    while o < len(body):
        tl = u16(body, o); o += 2
        t = body[o:o + tl].decode(errors="replace"); o += tl
        q = body[o]; o += 1
        subs.append({"topic": t, "qos": q})
    return {"packetId": pid, "订阅列表": subs}


def parse_stream(buf):
    """从字节流里解出尽可能多的 MQTT 报文"""
    out, i, n = [], 0, len(buf)
    while i + 1 < n:
        b0 = buf[i]
        rl, j = rl_decode(buf, i + 1)
        if rl is None:
            break
        total = (j - i) + rl
        if n - i < total:
            break
        out.append((b0, buf[j:i + total]))
        i += total
    return out, buf[i:]


def main():
    path = sys.argv[1] if len(sys.argv) > 1 else "fake-cloud.jsonl"
    rows = [json.loads(l) for l in open(path, encoding="utf-8") if l.strip()]
    print(f"===== {path}：{len(rows)} 条记录 =====\n")

    for r in rows:
        if r.get("kind") != "app_data":
            continue
        raw = bytes.fromhex(r["hex"])
        pkts, rest = parse_stream(raw)
        print(f"── {r['wall']}  TLS 明文 {len(raw)} 字节"
              f"{'  （⚠️ 有 %d 字节残缺/未解）' % len(rest) if rest else ''}")
        for b0, body in pkts:
            ptype = b0 >> 4
            name = NAMES.get(ptype, str(ptype))
            print(f"   ▸ {name}")
            if ptype == 1:
                info = decode_connect(body)
            elif ptype == 3:
                info = decode_publish(b0, body)
            elif ptype == 8:
                info = decode_subscribe(body)
            else:
                info = {"len": len(body)}
            for k, v in info.items():
                if isinstance(v, dict):
                    print(f"       {k}:")
                    for kk, vv in v.items():
                        print(f"         {kk} = {vv}")
                else:
                    print(f"       {k} = {v}")
        print()


if __name__ == "__main__":
    main()
