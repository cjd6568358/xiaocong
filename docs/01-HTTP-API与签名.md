# 01 · HTTP API 契约与签名算法

网关：`https://gw.ixiaocong.com/`（调试回退 `http://gw.ixiaocong.net/`，可用密令 `xc://setdebug` 切换）
方法：**仅 POST**，`application/x-www-form-urlencoded`
超时：连接/读/写各 15s

---

## 签名算法（MD5）

来源：`sdk/http/util/SignatureUtil.java`

```java
// 1. 收集参与签名的参数
signParams = endpoint 的 sign 参数 (setParamsMap)
signParams.put("xc-timestamp", 毫秒时间戳)
signParams.put("xc-token",     token)
signParams.put("clientId",     clientId)
signParams.put("appId",        appId)
signParams.put("udid",         udid)

// 2. 按 key 大小写不敏感排序
Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

// 3. 拼接：k=v&k=v&...&  （每组后都带 &，包括最后一个）
builder.append(key + "=" + val).append("&");

// 4. 追加密钥（无分隔符）
//    client/config 用 appKey（此时还没有 clientKey），其余用 clientKey
builder.append(isConfig ? appKey : clientKey);

// 5. MD5 → 十六进制 → 转小写
xc-sign = hex(MD5(builder.toString())).toLowerCase()
```

**要点**
- 排序是**大小写不敏感**的
- 每对参数后都有 `&`，**末尾也有**
- 密钥直接拼接，**无** `&` 分隔
- 哈希 **MD5 小写十六进制**（不是 HMAC）
- `setParamsMapNoSign` 里的参数**不参与签名**，但会随请求发送

### 请求信封（所有请求都带）

```
xc-token, xc-timestamp, xc-sign,
appId, clientId, udid,
platform="phone", clientVersion, os="android", osVersion,
network, brand, model, screen="HxW", clientUdid, channel
```

### 响应信封

```json
{ "code": 0, "msg": "", "data": "<JSON 字符串>", "success": true }
```
- `code == 0` 成功
- `code == 100` token 失效 → 强制登出
- `code == 102` → 重新绑定手机

---

## 端点清单（按功能分组）

> S = 参与签名的参数；N = 不参与签名但仍发送的参数

### 引导 / 客户端
| 路径 | 参数 | 说明 |
|---|---|---|
| `client/config` | 无；`needSign=false`，签名用 appKey | **返回 clientId/clientKey/liveServerUrl（MQTT 地址）/uid** |
| `client/xinge/token` | S: xingeToken | 推送 token 上报 |
| `device/subscribe/all` | 无 | **服务端绑定 MQTT 订阅** |

### 认证 / 用户
| 路径 | 参数 |
|---|---|
| `sms/send` | S: phone；N: code |
| `user/login` | phone, code, verifycode → `{token, uid, nickname, portrait, openId}` |
| `user/wx/login` | S: code, openId |
| `user/wx/bindPhone` | phone, code, verifycode, openId |
| `user/qrcode/login` | code |
| `qr/check` | qrcode |
| `authorize/qrcode/info` / `authorize/qrcode/oauth` / `authorize/list` / `authorize/cancel` | 第三方授权登录 |
| `user/info` | 无 |
| `user/logout` | 无 |
| `user/config` / `user/update/config` | notification |
| `user/modifyNickname` / `user/modifyPortrait` | |
| `user/feedback` | content |
| `user/message/find` | page, pageSize |
| `user/message/config/find` / `.../update` / `user/message/device/config/update` | notificationStatus |

### 家庭 / 分组
`index`（首页聚合）、`home/list`、`home/listDetail`、`home/detail`、`home/add`、`home/update`、`home/delete`、`home/move`、`home/check/nobody`、`home/device/list`、`group/list`、`group/add`、`group/update`、`group/delete`、`group/device/list`、`district/list`

### 设备
| 路径 | 参数 | 说明 |
|---|---|---|
| `device/list` | 无 | 设备列表 |
| `device/list/gw` | productId | 网关列表 |
| `device/list/gw/child` | gatewayId | 子设备 |
| `device/detail` | deviceId | |
| `device/detail/mini` | deviceId | |
| `device/v2/detail` | S: deviceId；N: rnVersion | **RN bundle 版本检查** |
| `device/update` | S: deviceId；N: deviceName, homeId, groupId | 重命名/移动 |
| `device/getName` | deviceId | |
| `device/snapshots` | S: deviceId, pageSize；N: queryId | 历史快照 |
| `device/top` / `device/top/cancel` | deviceId | 置顶 |
| `device/bind` | S: deviceId；N: deviceMac, discoverWay, discoverTime | **绑定设备** |
| `device/unbind` | deviceId, productId | |
| `device/bind/log` | S: productId, errorCode；N: ... | 绑定遥测 |
| `device/getBindInfo` | deviceId | |
| `device/getDiscovered` | checkCode | **配网后按 checkCode 找设备** |
| `device/sdk/check` / `device/sdk/detail` / `device/sdk/upgrade` | deviceId[, sdkId] | **固件 OTA** |
| `parameter/list` / `parameter/modifiable/list` / `parameter/rename` | deviceId, ... | 设备参数 |
| `electricity/count` / `electricity/list` | deviceId, type, date, ... | 电量统计 |
| `room/air/v2` | deviceId, pageSize, type, date | 空气数据 |
| `weather/today/outdoors` | 无 | 天气 |
| `product/category` / `product/list` / `product/help` | categoryId / productId | 产品目录；`product/help` 返回 `netconfigCode`（决定配网方式） |

### 场景 / 自动化 / 联动
`ifttt/list`、`ifttt/list/recommend`、`ifttt/detail`、`ifttt/detailRecommend`、`ifttt/log`、`ifttt/add`、`ifttt/update`、`ifttt/delete`、`ifttt/execute`、`ifttt/addAction`、`ifttt/updateAction`、`ifttt/deleteAction`、`ifttt/trigger/device/list`、`ifttt/trigger/device/parameter/list`、`ifttt/action/device/list`、`ifttt/action/device/parameter/list`、`scene/start`、`relation/device/parameter/list`、`relation/sceneDetail`、`relation/tigger/device/update`、`relation/add`、`switch/relation/add`、`switch/relation/parameters`、`switch/relation/trigger/parameters`、`switch/relation/delete`

### 分享
`share/users`、`share/checkByPhone`、`share/bind`、`share/cancel`、`share/device/users`、`share/devices`、`share/devices/shareable`、`share/devices/sharedToMine`、`share/devices/sharedToOther`、`share/device/accept`

### 摄像头（Aoni）
`camera/index`、`camera/config/detail`、`camera/config/update`、`camera/device/ping`、`camera/setCodeStream`、`camera/getLiveStreamUrl`、`camera/getPlayStreamUrl`、`camera/getVideoList`、`camera/getAlarmList`、`camera/getServiceInfo`、`camera/order/list`、`camera/pay/template`、`camera/pay/getPayIdByOrder`

### 企业 / 其他
`mensuo/role/list`、`mensuo/role/update`（门锁）、`infrared/code/list`、`infrared/code/add`（红外）、`youzan/login`（有赞商城）

---

## 引导流程

```
App 启动
  └─ XCHelp.init() 读本地 prefs xiao_cong_config
  └─ XCManager.initialWithAppId(appId, appKey)
       └─ POST client/config (签名用 appKey)
            ├─ clientKey      → prefs "NLC_ahe_key"
            ├─ clientId       → prefs "vic_jastion_dd"
            ├─ liveServerUrl  → prefs "vic_klmn_jast_like"  ← MQTT broker 地址
            └─ uid            → prefs "NLC_ahe_uid"
  └─ 登录 user/login → token → prefs "NLC_ahe_9l"
  └─ XCManager.loginWithToken(token) → startMqtt()
```

**DNS**：不走系统 DNS。用阿里 HTTPDNS（account `192338`）解析
`gw.ixiaocong.com` 和 MQTT host，结果缓存在 `dns_ip` / `mqtt_dns_ip`。

## 内部 prefs 键名（混淆过）

| 键 | 含义 |
|---|---|
| `NLC_ahe_key` | clientKey |
| `vic_jastion_dd` | clientId |
| `vic_klmn_jast_like` | liveServerUrl (MQTT 地址) |
| `NLC_ahe_9l` | 登录 token |
| `NLC_ahe_uid` / `xiao_cong_uid` | uid |
| `device_UUID` | udid |
| `xiao_cong_jfnda` | phone |
| `config_http_url` | 网关地址覆盖（调试用） |
