#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
从 fake-cloud.jsonl 里统计「某个时间点之后」出现过的 DNS 查询名。

用法：
    python tools/dns-since.py fake-cloud.jsonl 2026-10-02T02:10:00

为什么需要它：
    方案2（domain 参数）的判别信号就是 **插座查的域名**。
    - 认 domain  → 会去查我们编的那个域名（如 probe2.ixiaocong.test）
    - 不认 domain → 只会查固件内置的 iot.ixiaocong.com
    所以只要把配网时刻之后的所有 dns_query 拉出来看一眼，结论自明。
"""
import json
import sys


def main():
    if len(sys.argv) < 2:
        print(__doc__)
        return 2
    path = sys.argv[1]
    t0 = sys.argv[2] if len(sys.argv) > 2 else ""

    counts = {}
    n_total = 0
    try:
        fh = open(path, encoding="utf-8", errors="replace")
    except FileNotFoundError:
        print(f"找不到 {path}")
        return 1

    with fh:
        for line in fh:
            line = line.strip()
            if not line:
                continue
            try:
                r = json.loads(line)
            except Exception:
                continue
            if r.get("kind") != "dns_query":
                continue
            wall = r.get("wall", "")
            if t0 and wall < t0:
                continue
            n_total += 1
            name = r.get("name") or "(空)"
            counts[name] = counts.get(name, 0) + 1

    if not counts:
        print(f"[{t0} 之后] 没有任何 DNS 查询记录。")
        return 0

    print(f"[{t0} 之后] 共 {n_total} 次 DNS 查询，去重后 {len(counts)} 个域名：")
    for name, cnt in sorted(counts.items(), key=lambda kv: -kv[1]):
        print(f"  {cnt:5d}  {name}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
