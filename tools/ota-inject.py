#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
ota-inject.py —— 逐条注入 OTA 伪造升级指令，实时捕捉插座的反应

原理：
    1. 从探针文件读出一条 raw:topic@@{json} 指令，追加到 fake-cloud 的 --cmd-file；
    2. fake-cloud 每 1s 轮询该文件，把新行通过已建立的 MQTT 连接下发给插座；
    3. 我们同时盯 fake-cloud.jsonl，任何下列事件都说明**这条指令被接受了**：
         - dns_query  名字里含 "ota-probe"   → 它去解析固件下载地址了
         - new_port_syn                       → 它开了新 TCP 连接（不是 MQTT 端口）
         - http_request                       → 它真的在 GET 固件
    4. 一旦命中，立刻停止（避免继续骚扰插座），并打印是哪一条指令生效。

用法：
    python tools/ota-inject.py --gap 3 --limit 20
    python tools/ota-inject.py --order control-first   # 把 control 主题排前面
"""
import argparse
import json
import os
import time
from datetime import datetime

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)

MARKER = "ota"

# ★ 安全护栏：这些键是设备**确认识别**的控制键，一旦出现在探针里就会真的动作。
#   曾经因为一条 {"command":{"switch":1,"upgrade":"..."}} 把插座继电器打开了。
#   注入前一律拦下，除非显式 --allow-danger。
DANGEROUS_KEYS = ['"switch"', '"repower"', '"countdown"', '"delay"', '"reboot"', '"reset"']


def is_marker(name):
    """标记域名：ota-probe.test（第一轮）或 *.ota2.test（第二轮）"""
    n = str(name)
    return MARKER in n and ".test" in n


def dangerous(probe):
    hits = [k for k in DANGEROUS_KEYS if k in probe]
    return hits


def log(msg, important=False):
    ts = datetime.now().strftime("%H:%M:%S")
    line = f"[{ts}] {msg}"
    if important:
        line = "\n" + "★" * 3 + " " + line
    print(line, flush=True)
    if LOGF:
        LOGF.write(line + "\n")
        LOGF.flush()


LOGF = None


def load_probes(path, order):
    out = []
    with open(path, "r", encoding="utf-8") as f:
        for ln in f:
            ln = ln.strip()
            if ln and not ln.startswith("#"):
                out.append(ln)
    if order == "control-first":
        def rank(s):
            topic = s[4:].split("@@", 1)[0] if s.startswith("raw:") else ""
            return 0 if topic == "control" else 1
        out.sort(key=rank)
    return out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--probe-file", default=os.path.join(ROOT, "ota-probe.cmd"))
    ap.add_argument("--cmd-file", default=os.path.join(ROOT, "control.cmd"))
    ap.add_argument("--json", default=os.path.join(ROOT, "fake-cloud.jsonl"))
    ap.add_argument("--log", default=os.path.join(ROOT, "ota-inject.log"))
    ap.add_argument("--gap", type=float, default=3.0, help="每条之间等多久")
    ap.add_argument("--limit", type=int, default=0, help="最多试多少条（0=全部）")
    ap.add_argument("--order", default="control-first", choices=["as-is", "control-first"])
    ap.add_argument("--allow-danger", action="store_true",
                    help="★ 允许下发含 switch/reboot 等**会真的动作**的键（默认拦截）")
    a = ap.parse_args()

    global LOGF
    LOGF = open(a.log, "w", encoding="utf-8")
    LOGF.write(f"===== ota-inject  {datetime.now():%Y-%m-%d %H:%M:%S} =====\n")

    probes = load_probes(a.probe_file, a.order)
    if a.limit:
        probes = probes[:a.limit]
    log(f"探针 {len(probes)} 条，间隔 {a.gap}s（预计 {len(probes)*a.gap/60:.1f} 分钟）")
    log(f"探针文件={a.probe_file}\n命令文件={a.cmd_file}\n监控={a.json}")

    # 记录 jsonl 起始位置
    off = os.path.getsize(a.json) if os.path.exists(a.json) else 0
    log(f"jsonl 起始偏移 = {off}\n")

    HIT = {"v": False, "line": None}

    def poll_events():
        nonlocal off
        if not os.path.exists(a.json):
            return
        size = os.path.getsize(a.json)
        if size < off:          # 被截断/重建
            off = 0
        if size == off:
            return
        with open(a.json, "r", encoding="utf-8", errors="replace") as f:
            f.seek(off)
            for ln in f:
                ln = ln.strip()
                if not ln:
                    continue
                try:
                    d = json.loads(ln)
                except Exception:
                    continue
                k = d.get("kind")
                if k == "dns_query" and is_marker(d.get("name", "")):
                    log(f"🎯🎯🎯 命中！插座在解析固件域名：{d.get('name')}", important=True)
                    HIT.update(v=True, line=ln)
                elif k == "new_port_syn":
                    log(f"🎯🎯🎯 命中！插座发起新 TCP 连接 → 端口 {d.get('dport')}"
                        f"（sport={d.get('sport')}）", important=True)
                    HIT.update(v=True, line=ln)
                elif k == "http_request":
                    log(f"🎯🎯🎯 命中！插座 HTTP 请求：{d.get('text','')[:200]}", important=True)
                    HIT.update(v=True, line=ln)
                elif k == "dns_query":
                    log(f"    (DNS: {d.get('name')})")
            off = f.tell()

    for i, probe in enumerate(probes, 1):
        topic = probe[4:].split("@@", 1)[0] if probe.startswith("raw:") else "?"
        shape = probe.split("@@", 1)[-1][:110] if "@@" in probe else probe[:110]

        # ★ 安全护栏
        bad = dangerous(probe)
        if bad and not a.allow_danger:
            log(f"\n──── [{i}/{len(probes)}] ⛔ 已拦截（含危险键 {bad}）────")
            log(f"  跳过：{shape}")
            log("  （这些键设备是真认的，发出去会真的动继电器。要发请加 --allow-danger）")
            continue

        log(f"\n──── [{i}/{len(probes)}] topic={topic} ────")
        log(f"  下发：{shape}")

        # 追加一行 → fake-cloud 下一轮轮询就发出去
        with open(a.cmd_file, "a", encoding="utf-8") as f:
            f.write(probe + "\n")

        # 等待 + 期间持续看事件
        t_end = time.time() + a.gap
        while time.time() < t_end:
            time.sleep(0.4)
            poll_events()
            if HIT["v"]:
                break
        if HIT["v"]:
            log("")
            log("=" * 62, important=True)
            log(f"✅ 找到了！第 {i} 条指令被插座接受：", important=True)
            log(f"    {probe}", important=True)
            log(f"    事件：{HIT['line']}", important=True)
            log("=" * 62, important=True)
            break

    if not HIT["v"]:
        log("")
        log("=" * 62)
        log(f"⚠️ 试完 {len(probes)} 条，插座均无任何反应（无新 DNS / 无新连接 / 无 HTTP）。")
        log("   ⇒ 说明 OTA 不是靠这些 MQTT 主题触发的，或需要额外的握手/前置条件。")
        log("=" * 62)

    if LOGF:
        LOGF.close()
    return 0 if HIT["v"] else 2


if __name__ == "__main__":
    raise SystemExit(main())
