# 09 · 局域网控制与 OTA 伪造升级 —— 实测结论

> 本轮目标：把「局域网直控」和「伪造固件升级刷 Tasmota」两条路走到底。
> **结论：局域网无可用控制面（已严格证实）；OTA 无法靠盲探拿下（已穷举 210 条探针）。**
> 刷 Tasmota 的可靠路径只剩**物理刷机**（见 [firmware/README.md](../firmware/README.md)）。

---

## 一、局域网控制：**没有可用入口**（高置信度结论）

### 1.1 方法学（为什么这次结论可信）

上一轮用「没收到 ICMP 端口不可达 ⇒ 端口开放」判定，**有丢包假阳性**
（1-1024 全扫时 18~32 号端口集体"开放"，物理上不可能）。
本轮加了**阳性对照端口**：每次探测都混入一个几乎不可能开放的高位端口（45678）。

- 对照端口**正常回 ICMP** ⇒ 方法有效，结论可信
- 工具：`tools/udp-port-verify.py`

### 1.2 结果

| 端口 | 结果 | 判定 |
|---|---|---|
| UDP/13078（文档所述 CoAP 端口） | **8/8 次 ICMP 端口不可达** | ❌ **关闭** |
| UDP/5684 | 8/8 ICMP | ❌ 关闭 |
| UDP/45678（阳性对照） | 8/8 ICMP | ❌ 关闭（证明方法有效） |
| **UDP/5683**（CoAP 标准端口） | **0/8 ICMP** | ⚠️ 被绑定但静默 |

**TCP 端口**：24 个常见端口（21/22/23/53/80/81/443/1883/5555/8080/8188/13078…）**全部关闭**。

### 1.3 5683 是什么？

被动监听 75 秒（`tools/passive-udp-watch.py`），插座全部 UDP 流量只有：

| 源端口 | 目的 | 次数 |
|---|---|---|
| 49154 | 203.0.113.9:123（NTP） | 3 |
| 49153 | 223.5.5.5:53（DNS） | 1 |
| — | TCP 8188（MQTT） | 1 |

**插座从不使用 5683 收发** ⇒ 它不是客户端 socket。
结合「48 种 CoAP 请求变体全静默」⇒ 5683 上**没有可用的服务逻辑**
（残留 PCB 或特殊丢弃规则），**不构成控制入口**。

### 1.4 结论

> **固件在稳态下不运行任何局域网控制服务。**
> 文档 §7 说的 CoAP（13078，路径 `Scan`/`Getkey`/`Snapshot`/`Control`）
> **只在配网模式下运行**——那时插座是 AP（192.168.4.1），App 连上去发现设备。
> 稳态控制**只能走云端 MQTT**（也就是我们现在用的方式）。

**待验证的残余可能**：插座**开机窗口**或**掉线窗口**里 13078 是否会短暂打开。
`tools/lan-port-watch.py` 已在后台以 15s/次的极低速率长期蹲守，端口状态一翻转就记录。

---

## 二、OTA 伪造升级：**盲探已穷举，拿不下**

### 2.1 App 侧已知（反编译确认）

```
device/sdk/check    → {sdkId, upgrade, upgradeMsg}      有无新固件
device/sdk/detail   → {deviceVersion, sdkVersion, sdkIntro, status}
device/sdk/upgrade  → {deviceId, sdkId}                 ★ 触发升级
device/detail/mini  → {deviceVersion}                   轮询版本判断成功
```

**关键**：App **永远拿不到固件 URL**——`DevDetailModel` 里 `sdkId` 只是个 int，
`DeviceDetailV2Model.sdkInfo` 是空类。固件地址与推送协议**全在服务端+固件侧**。

MQTT 消息词表（`sdk/mqtt/model/`）只有 5 个类，**没有 OTA 专用类**：

| 类 | 字段 |
|---|---|
| `XCMessage`(基类) | code, messageId, protocolVersion, receiveId, senderId |
| `XCControlMessage` | + `command`(自由 Map) |
| `XCManageMessage` | + deviceId, moduleId, productId, `type` |
| `XCSubscribeMessage` | + subscribeIds |
| `XCSnapshotMessage`(设备→App) | code, messageId, networkType, snapshot(Map), status |

⇒ OTA 命令若走 MQTT，必然是 `command` 里一个**我们不知道的键**。

### 2.2 探测方法与结果

工具：`tools/ota-inject.py`（逐条注入 + 实时监控三个命中信号）

**命中信号**（任一出现即说明命令被接受）：
1. `dns_query` 含标记域名（`*.ota*.test`）→ 它去解析固件地址了
2. `new_port_syn` → 它开了非 MQTT 端口的新连接
3. `http_request` → 它真的在 GET 固件

**四轮共 210 条探针：**

| 轮次 | 条数 | 覆盖 |
|---|---|---|
| 1 `ota-probe.cmd` | 24 | 17 主题 × 8 形状（command.upgrade/ota/update、顶层 upgrade/ota/firmware 对象、扁平 url+version） |
| 2 `ota-probe2.cmd` | 169 | control 26 键 × 5 值形态；manage 的 `type` 变体；多主题顶层变体 |
| 3 `ota-probe3.cmd` | 17 | `command` 作字符串；嵌套 `cmd`/`action`/`type`/`op`/`function`/`method`/`task`/`job`；空 command |
| 4 `ota-probe4.cmd` | 14 | 进入升级模式型：`mode`/`enterOta`/`dfu`/`boot`/`startOta`/`force` |

**结果：**

- ✅ **全部送达**：设备对每条报文都**回显了 messageId**（v2 那轮回显 204 次引用）
  ⇒ 证明「主题不影响投递」在固件侧同样成立，报文确实进了设备
- ❌ **零接受**：0 次新 DNS（除 NTP/MQTT 主机）、0 次新连接、0 次 HTTP 请求
- `reboot_count` 全程稳定在 **96** ⇒ 也没走「MQTT 分块 OTA」

### 2.3 ★ 意外收获：设备会**部分执行** `command`

探针里有一条 `{"command":{"switch":1,"upgrade":"http://tcombo.ota3.test/fw.bin"}}`
——设备**认出了 `switch:1` 就真的把继电器打开了**，同时忽略不认识的 `upgrade`。

这证明：
1. 设备**逐键解析** `command`，认识的键立即生效，不认识的键静默忽略；
2. **不会因为存在未知键就整体拒绝**——所以若 OTA 键存在且被识别，一定会动作。

⇒ 反过来说，210 条全无反应，**说明这些键/形状确实都不是固件认的 OTA 入口**。

> ⚠️ 已给 `ota-inject.py` 加**安全护栏**：探针含 `switch`/`repower`/`reboot`/`reset` 等
> **设备确认识别的键**时默认拦截，需 `--allow-danger` 才放行。

### 2.4 为什么盲探拿不下

- 键空间无上界（固件私有实现，无任何文档/符号可参考）
- 无法从设备侧学习主题或键（CONNECT 无 Will topic、设备**从不 SUBSCRIBE**）
- 无固件镜像可静态分析

### 2.5 结论与出路

> **伪造 OTA 升级在无固件镜像的前提下不可行。**
> 就算猜中协议，ESP8266 的 OTA 还要求镜像头合法（magic `0xE9` + 校验），
> 厂商若启用签名则直接拒绝。

**唯一可靠路径：物理刷机**（USB-TTL 读 Flash + 写 Tasmota/ESPHome）。
`firmware/README.md` 已写好完整步骤，且**读 Flash 还能顺带回答"固件里的云端地址/OTA 协议"**
——这一步做完，本节的未知项就全部补上了。

---

### 2.6 ★ 2026-10-02 更新：多了一个新变量，但结论不变

**盲探部分依然成立**（210 条零接受，别再重复投入）。但今天新跑通的
**「DNS 上游劫持」改变了一个前提**：

> 光猫现在能劫持**任意域名**的解析（`-dns-domains` 可配）。
> ⇒ **一旦知道固件 OTA 时访问哪个域名，就能把它劫到我们自己的服务器，直接喂镜像。**

这把 OTA 从「猜 MQTT 键」变成了「猜域名」——后者是**可观测**的
（设备一解析就会出现在 ixc-go 日志里）。但当前设备根本不发起 OTA
（原厂云端停服），所以这条路现在无的放矢。

**因此建议改为**：物理刷机**读一次** Flash → 拿到 OTA 域名、固件格式、有无签名、
以及 MQTT 的 OTA 键名 → **之后所有插座全走 OTA**。
这正好解决"有多台插座"时的规模化问题。

⚠️ 注意：ESP8266 OTA 要求镜像头合法，厂商若启用签名会直接拒绝 —— 先读出来看看，别盲目推。

### 2.7 本节尚未排除的 1%（迁移前记下）

| 未验证项 | 线索 | 价值 |
|---|---|---|
| **配网模式下的 CoAP 13078** | 插座进配网后是 AP `192.168.4.1`，CoAP **确定在运行**；之前只测了 `domain`/`crt`，**没试过下发 OTA 或更多配置** | ★★★ |
| **`generate_getlocalkey` 是干什么的** | `ghidra_out/libxcsdk.so/generate_getlocalkey.c` + `getGenKeyPara.c`。若 localkey 是**局域网加密的钥匙**，则 §一 的"局域网无控制面"要修正为"有控制面但需要密钥" | ★★★ |
| **EasyLink 配网**（第二种配网方式） | `ghidra_out/libxcsdk.so/easylinkLoop.c` + `broadcast.c` | ★★ |
| `cmdExec` 链路上有没有 OTA 键 | `ghidra_out/libxcsdk.so/cmdExec.c`、`cmdforward.c` | ★★ |
| **MCU 固件是否独立 OTA** | baseInfo 报 `firmwareType:"B"` + `mcuVersion:"2017101910"` ⇒ 有独立 MCU | ★★ |
| 开机/掉线窗口 13078 | `tools/lan-port-watch.py` 在蹲守 | ★ |

完整的 1% 清单见 [`docs/11`](11-归档与清理清单.md) §四。

---

## 三、本轮工具

| 工具 | 用途 |
|---|---|
| `tools/udp-port-verify.py` | ★ 严格 UDP 端口判定（多次重复 + 阳性对照，排除丢包假阳性） |
| `tools/passive-udp-watch.py` | 被动监听插座全部 UDP 流量，判断某端口是服务端还是客户端 |
| `tools/lan-port-watch.py` | 轻量长期哨兵，蹲守 13078/5683 的开合状态变化 |
| `tools/gen-ota-probes2.py` | 生成 v2 OTA 探针（唯一 messageId + 唯一标记域名） |
| `tools/ota-inject.py` | 逐条注入 + 实时命中监控（含危险键安全护栏） |

## 四、fake-cloud.py 本轮修复

- **★ 修复关键 bug**：原 SYN 分支无条件先建会话、日志端口硬编码 `a.port`(8188)，
  导致「插座连到别的端口」这个**最强 OTA 生效信号永远不会触发**。
  现改为：非 8188 端口的 SYN 高亮告警 + 记为 `new_port_syn`，
  并当 **HTTP 会话**处理——只记录它请求的 URL 就 RST（**绝不真发固件，避免刷成砖**）。
- `Session` 增加 `my_port` / `is_http`；`tcp_send` 用会话自己的源端口（HTTP 回复必须端口正确）。
