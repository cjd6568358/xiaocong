# 02 · MQTT 控制协议

> 这是**已完全还原、无黑盒**的部分。设备控制走的就是这条链路。

---

## 1. 连接

```
ssl://<host>:<port>
```

| 项 | 值 |
|---|---|
| host:port | 来自 `client/config` 的 `liveServerUrl`（不硬编码） |
| TLS | TLSv1.2，**证书固定**：只信任 `assets/test.crt` |
| username | **appId** = `27e33ff8a0634cc4b6f696c9872a2d1b` |
| password | **用户登录 token** |
| clientId | 服务端下发（`client/config` → prefs `vic_jastion_dd`） |
| cleanSession | `false` |
| keepAlive | 30s |
| MQTT 版本 | 3.1.1 (4) |
| 自动重连 | 关闭，靠 AlarmManager 每 100s 检查 + `reconnectIfNecessary()` |
| 离线缓存 | Paho `DisconnectedBufferOptions`，最多 100 条 |

> MQTT 用 appId 作用户名、token 作密码——**不是** clientId/clientKey。

## 2. 主题

只有 **5 个单词主题**，无设备路径段：

| 主题 | 方向 | 用途 |
|---|---|---|
| `control` | App → 设备 | 参数控制（开关等） |
| `manage` | App → 网关 | Zigbee 添加设备 |
| `snapshot` | 设备 → App | 状态快照 |
| `online` | 设备 → App | 上线 |
| `offline` | 设备 → App | 下线 |
| `error` | 设备 → App | 控制错误 |

### 关键设计

1. **路由不靠主题，靠 payload 的 `receiveId`**。所有设备共用 `control` 主题。
2. **订阅由服务端绑定**。App 里**没有 `subscribe()` 调用**，只调 HTTP
   `device/subscribe/all`，服务端按 clientId 绑定该用户的设备。

## 3. 报文（纯 UTF-8 JSON，无加密）

### 基类 `XCMessage`
| 字段 | 类型 | 说明 |
|---|---|---|
| `senderId` | String | 发送方 ID（App 发时为 clientId） |
| `receiveId` | String | 目标设备 ID |
| `messageId` | Long | 9 位随机数 |
| `protocolVersion` | String | `"1.0.0"` |
| `code` | Integer | 结果码（设备回复时） |

### 控制 `XCControlMessage`（topic `control`）

**开灯**：
```json
{"messageId":523847123,"protocolVersion":"1.0.0",
 "receiveId":"<设备ID>","senderId":"<clientId>",
 "command":{"1":1}}
```
- `command` 是 `Map<String,Object>`，key 是设备参数 ID（插座的开关通常是 `"1"`）
- 值 `1`=开，`0`=关
- 可一次下发多个参数

### 快照 `XCSnapshotMessage`（topic `snapshot`）

```json
{"code":0,"messageId":523847123,"networkType":1,
 "protocolVersion":"1.0.0",
 "receiveId":"<clientId>","senderId":"<设备ID>",
 "status":1,"snapshot":{"1":1}}
```
- `code == 0` 成功，非 0 时取 `msg` 提示
- `snapshot` 是设备状态 map，UI 用 `optInt(key)` 读取

### 管理 `XCManageMessage`（topic `manage`，Zigbee）
```json
{"messageId":812345678,"protocolVersion":"1.0.0",
 "senderId":"<clientId>","receiveId":"<网关ID>",
 "productId":"<产品ID>","moduleId":"<模块ID>","type":"add"}
```

### `online` / `offline` / `error`
只读 `senderId`（+ `error` 读 `msg`）。

> `XCSubscribeMessage` 类存在但**从未被实例化**，是废弃代码。

## 4. 完整控制往返（以开灯为例）

1. 用户点开关 → 读当前 `snapshot`，值为 0 则发 `1`
2. 构造 `XCControlMessage{command:{key:1}}` → publish 到 `control`
   （QoS 0，not retained）
3. 启动 5 秒倒计时，超时提示"设备控制超时"
4. 设备回 `snapshot` → 匹配 `senderId == 目标设备ID` → 取消倒计时
5. 广播 `ACTION_UPDATE_STATUS` → 更新 UI 列表项

## 5. 对自建服务端的含义

> **要复刻设备控制，不需要理解任何私有协议。**
> 需要：
> 1. 一个 MQTT broker（支持 TLS + 自签证书固定）
> 2. HTTP 桩实现 `client/config`（返回你 broker 的地址）、`user/login`、
>    `device/subscribe/all`、`device/bind` 等
> 3. 让设备连上你的 broker（**难点：设备固件里烧死的云端地址**）

**唯一障碍不在协议，在固件的云端地址。**
