# 小葱智能插座 · 自建服务端

零依赖纯 Node 实现（手写 MQTT broker），目的是让原厂 App 重新连上你自建的服务器。

```
server/
  server.js        HTTP 网关 + 启动入口
  mqtt-broker.js   手写 MQTT 3.1.1 broker（TCP + TLS）
  sign.js          网关签名算法（逆向自 SignatureUtil#signMD5）
  store.js         内存态设备/用户存储
  gen-certs.sh     生成自签证书
  test-device.js   模拟插座（无真机也能验证全链路）
```

## 快速开始

```bash
# 1. 明文模式（先跑通链路，不需要证书）
node server/server.js
#   HTTP http://0.0.0.0:8080
#   MQTT tcp://0.0.0.0:1883

# 2. 另开一个终端，启动模拟插座
node server/test-device.js

# 3. 验证
curl http://127.0.0.1:8080/health
curl -X POST -d "productId=1" http://127.0.0.1:8080/product/help
```

已实测通过的闭环：
```
设备上线 → [mqtt] online
App 下发控制 → control {"receiveId":"sim-plug-01","command":{"1":1}}
设备执行 → [mqtt] snapshot sim-plug-01 -> {"1":1}
```

## TLS 模式（App 用 ssl://）

```bash
sh server/gen-certs.sh          # 生成 server/certs/{ca,server}.{crt,key}
node server/server.js --tls     # HTTPS + ssl:// MQTT
```

> **为什么不能直接用原 APK 的 `assets/test.crt`**：
> 它**只含证书、不含私钥**，无法对外提供 TLS。
> 正解是自签一套，然后**改 App 的 `assets/test.crt` 为我们的 CA**
> （我们本来就要改 App 来适配 Android 10）。

## 关键配置

| 环境变量 | 默认 | 说明 |
|---|---|---|
| `HTTP_PORT` | 8080 | HTTP 端口 |
| `TCP_PORT` | 1883 | 明文 MQTT |
| `TLS_PORT` | 8883 | TLS MQTT |
| `LIVE_SERVER` | `127.0.0.1:8883` | **下发给 App 的 MQTT 地址**（`client/config` 的 `liveServerUrl`） |
| `NETCONFIG` | `softap1.0` | `product/help` 返回的配网方式 |

> `LIVE_SERVER` 必须设成**插座能访问到的地址**（局域网 IP 或公网），否则插座连不上。

## 已实现的端点

引导 `client/config` ｜ 认证 `sms/send` `user/login` `user/info` ｜
设备 `device/list` `device/detail` `device/v2/detail` `device/bind` `device/getBindInfo`
`device/getDiscovered` `device/subscribe/all` `device/sdk/*` ｜
参数 `parameter/list` ｜ 产品 `product/category` `product/list` `product/help` ｜
首页 `index` ｜ 天气/电量/家庭/分组/场景 等存根

未实现的端点会**返回成功+空数据**（不报错），App 不会崩。

## 安全说明

- **不校验签名**：已死厂商，没有需要保护的资产。`sign.js` 已实现正确算法，
  需要时在 `server.js` 里打开校验即可。
- **接受任何账号密码**：`sms/send` 返回固定验证码，`user/login` 一律成功。
- 仅供本地/内网使用，**不要暴露到公网**。

## 已知限制

1. **设备端 TLS 证书固定**：若插座固件 pin 了原厂证书，自签证书会被拒绝 →
   需要在固件/网络层处理（DNS 劫持 + 透明代理，或改固件）。
2. **首次配网**：仍需要 **secp256k1 ECDH + AES-128-CBC** 握手
   （见 [../docs/03-配网与native.md](../docs/03-配网与native.md)）；
   可用厂商 `libsoftAp.so` 的 JNI，或按 [03 §9](../docs/03-配网与native.md)
   用 20 行标准库代码自己实现（推荐）；也可用 Android 10 以下的老手机配一次网，之后不再需要。
3. **设备连哪个服务器**：取决于固件里烧死的地址 → **先备份固件并 strings 找域名**。

## 相关文档

- [../docs/01-HTTP-API与签名.md](../docs/01-HTTP-API与签名.md)
- [../docs/02-MQTT协议.md](../docs/02-MQTT协议.md)
- [../docs/03-配网与native.md](../docs/03-配网与native.md)
