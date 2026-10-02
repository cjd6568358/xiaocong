#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
gen-ota-probes2.py —— 生成第二轮 OTA 探针（覆盖面更广）

第一轮（ota-probe.cmd）24 条全部被设备**回显 messageId 但无动作**，
说明设备收到了报文、只是不认识这些形状。第二轮据此扩展：

  1. ★ 每条用**唯一 messageId**（避免固件按 messageId 去重把后续全吃掉）；
  2. ★ 每条用**唯一标记域名** t<key>-v<n>.ota2.test，命中即可精确定位是哪个键；
  3. 键词表扩到 26 个（含 doUpgrade / startUpgrade / otaUpgrade / setUpgrade / sdkId …）；
  4. 值形态 5 种：url 字符串 / 数字 1 / 字符串 "1" / {url,md5,size} / {url,version}；
  5. 额外覆盖 manage 主题的 type 字段变体（App 侧 XCManageMessage 有 type）。

输出：ota-probe2.cmd（每行 raw:topic@@{json}）
"""
import json
import os

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
DEV = "6587529711423210"
APP = "f9ee07acc3412b0f538d778ab05e5e80"

KEYS = [
    "upgrade", "ota", "update", "firmware", "fw", "version", "sdk", "sdkId",
    "file", "url", "download", "flash", "bin", "patch", "self", "app",
    "doUpgrade", "startUpgrade", "otaUpgrade", "setUpgrade", "system", "sys",
    "device", "module", "reboot", "reset",
]
VALS = ["url", "num1", "str1", "obj_md5", "obj_ver"]
MGR_TYPES = ["upgrade", "ota", "update", "firmware", "sdk"]


def marker(key, val):
    return f"t{key}-{val}.ota2.test"


def value_for(v, key, val):
    url = f"http://{marker(key, val)}/fw.bin"
    if v == "url":
        return url
    if v == "num1":
        return 1
    if v == "str1":
        return "1"
    if v == "obj_md5":
        return {"url": url, "md5": "0" * 32, "size": 524288}
    return {"url": url, "version": "2019090909"}


def env(payload):
    d = {"messageId": 0, "protocolVersion": "1.0.0",
         "receiveId": DEV, "senderId": APP}
    d.update(payload)
    return d


def main():
    out = []
    mid = 810000

    # --- A. control 主题：26 键 × 5 值 ---
    for key in KEYS:
        for v in VALS:
            mid += 1
            body = env({"command": {key: value_for(v, key, v)}})
            body["messageId"] = mid
            out.append(("control", json.dumps(body, separators=(",", ":"))))

    # --- B. manage 主题：type 字段变体 ---
    for t in MGR_TYPES:
        for v in ["url", "obj_md5", "str1"]:
            mid += 1
            url = f"http://tmanage-{t}-{v}.ota2.test/fw.bin"
            payload = {"type": t, "productId": "381785", "moduleId": str(mid)}
            if v == "url":
                payload["url"] = url
            elif v == "str1":
                payload["sdkId"] = "1"
            else:
                payload.update({"url": url, "md5": "0" * 32, "size": 524288})
            body = env(payload)
            body["messageId"] = mid
            out.append(("manage", json.dumps(body, separators=(",", ":"))))

    # --- C. 顶层（不进 command）在几个主题上的变体 ---
    for topic in ["control", "ota", "upgrade", "firmware", "update", "sdk", "system", "device"]:
        for v in ["url", "obj_md5", "obj_ver"]:
            mid += 1
            url = f"http://t{topic}-top-{v}.ota2.test/fw.bin"
            payload = {"upgrade": value_for(v, topic, f"top-{v}")}
            body = env(payload)
            body["messageId"] = mid
            out.append((topic, json.dumps(body, separators=(",", ":"))))

    path = os.path.join(ROOT, "ota-probe2.cmd")
    with open(path, "w", encoding="utf-8") as f:
        for topic, js in out:
            f.write(f"raw:{topic}@@{js}\n")
    print(f"已生成 {len(out)} 条 → {path}")
    print(f"  唯一 messageId: {out[0][1][:40]} … {mid}")
    print(f"  主题集合: {sorted(set(t for t, _ in out))}")


if __name__ == "__main__":
    main()
