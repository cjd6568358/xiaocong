# 03 · 配网协议与 native 实现

> 覆盖：APK 基本信息、native 库、两套配网引擎、局域网 CoAP 通道。
> native 结论来自 Ghidra 12.1.4 对 `libsoftAp.so` / `libxcsdk.so` 的完整反编译
> （两个库均带 DWARF，符号未剥离）。反编译产物见 [`ghidra_out/`](../ghidra_out/)，
> 操作方法与踩坑见 [06-Ghidra操作手册.md](06-Ghidra操作手册.md)。

---

## 0. 结论速览

| 问题 | 结论 |
|---|---|
| 配网加密 | **secp256k1 ECDH + AES-128-CBC** |
| AES key 来源 | ECDH 共享密钥（32B）的低 16 字节 |
| AES IV | **硬编码 `abcdefghijklmnop`** |
| `domain`/`crt` 会发给设备吗 | **会**，编进 `ch_data` 报文 ★ 主线 A 成立 |
| 局域网控制 | **有**，CoAP `255.255.255.255:13078`，路径 `Scan`/`Getkey` |
| xconfig 配网 | 加密 UDP 广播（目标由参数定），`SendType=0x23` 走 AES |

---

## 1. APK 基本信息

| 项 | 值 |
|---|---|
| 包名 | `com.ixiaocong.smarthome.phone` |
| 版本 | 2.2.1（versionCode 221） |
| minSdkVersion | 16（Android 4.1） |
| targetSdkVersion | **23**（Android 6.0） |
| 构建日期 | 2018-07-19 |
| 加固 | 腾讯乐固（`libshella-2.8.so` / `libshellx-2.8.so`） |

> ⚠️ **不要提升 `targetSdkVersion`**。App 按 Android 6 行为编译，配网重度使用
> Android 10 受限的 WiFi API（`addNetwork`/`enableNetwork`/`getConfiguredNetworks`/
> `getScanResults`）；提到 29+ 反而会把配网废掉。低 targetSdk 在 Android 10 上仍走兼容路径。

> 两个根因不要混：**App 无法启动** = 乐固壳（见 [00-总览](00-总览.md)）；
> **配网失效** = `targetSdkVersion=23`。

敏感权限（历史包袱）：`CAMERA` `READ_CONTACTS` `CALL_PHONE` `PROCESS_OUTGOING_CALLS`
`READ_PHONE_STATE` `SYSTEM_ALERT_WINDOW` `READ_LOGS` `GET_TASKS` `KILL_BACKGROUND_PROCESSES`

---

## 2. assets

| 文件 | 内容 |
|---|---|
| `test.crt` | MQTT broker 的 TLS 固定证书 |
| `tpl.html` | echarts 图表库（电量统计），无价值 |

### test.crt 关键属性

```
subject   = C=CN, ST=beijing, O=ixiaocong.com, CN=ixiaocong.com
issuer    = 同上（自签）
notBefore = Mar 30 10:04:01 2017 GMT
notAfter  = Mar 31 10:04:01 2017 GMT      ← 有效期仅 1 天
无 SAN 扩展；RSA-2048；只含证书，**不含私钥**
```

**结论**
- **App 端**：它是 App 唯一的信任根，我们要改 App 时可直接替换。
- **服务端**：**不能复用它对外提供 TLS**（没有私钥）。正解是自签一套 CA + 服务端证书，
  再把 App 的 `assets/test.crt` 换成我们的 CA。
- **设备端**：若固件也 pin 了这张证书 → 只能靠配网的 `crt` 参数下发（见 §5.6），
  这是主线 A 的残余风险点。

---

## 3. native 库总览

APK 含 `armeabi-v7a` + `armeabi` 两套（32 位 ARM），**符号表未剥离**。

| 库 | 大小 (v7a) | 作用 |
|---|---|---|
| `libsoftAp.so` | 244 KB | SoftAP 配网（UDP 5658 + secp256k1） |
| `libxcsdk.so` | 273 KB | xconfig 配网 + 局域网 CoAP（13078） |

其余为第三方：`libBugly.so` `libbdplayer.so` `libjsc.so`（React Native）
`libluajava.so` `libsqlcipher.so` `libkookong.so`（遥控精灵）等。

Java 层包装：

```java
// com.ixiaocong.smarthome.phone.softap.sdk.SoftApSDK
native String startCoap(String addr, String productId, String mac);
native void   startSoftAp(String broadAddress, String ssid, String password,
                          String domain, String crt, String clientId, String checkCode);
System.loadLibrary("softAp");

// com.xiaocong.smarthome.phone.xcsdk.DeviceSdk
native void   XConfigStart(String a, String b, String c, byte[] d);
native String cmdExec(int opcode, String json);
native int    polling();
System.loadLibrary("xcsdk");
```

> Java 层没有 UDP / CoAP / socket / `5658` / `13078` / `192.168.4` / `ch_pubk` 任何字样
> —— 全在 `.so` 里；dex 里只有 Android 胶水代码。

源码路径泄露（同一批人写的）：
```
/Users/maizi/Public/ndk-worspace/SoftApTest/app/src/main/jni/{aes,base64,cJSON,coap}.c
/Users/maizi/Public/ndk-worspace/embed-gw/sdk_smartphone/.../sdk.Shared/smnt.c
/Users/maizi/Public/ndk-worspace/embed-gw/sdk_smartphone/.../sdk.Android/androidjni.c
```

---

## 4. 配网方式分派

`DeviceAddStateActivity` 调 `product/help` 拿 `netconfigCode`，据此分派：

| netconfigCode | 引擎 | 说明 |
|---|---|---|
| `softap1.0` | SoftAP 广播（旧） | UDP 5658，`ch_pubk` 那套 |
| `xconfig` | xcsdk（新） | Android 10+ 走这套 |
| `easylink` | 未实现 | UI 标"暂不支持"（但 so 里已实现） |

> 服务端已停服，`product/help` 拿不到 → App **无法自动**选配网方式。
> 但可以**手工指定**，或直接调 native，不依赖该接口。

---

## 5. SoftAP 配网（softap1.0）

### 5.1 完整时序

```
1. 长按 5s → 设备开热点 AP：smart-<产品号>-<MAC后6位>-<校验码>，服务于 192.168.4.1
2. App 扫描并校验校验码：末 2 位十六进制 == 前段 SSID 字节和 & 0xFF
3. App 生成 6 位随机 checkCode，连上该开放热点
4. 每 4.5s 向 192.168.4.255:5658 广播 {"type":"ch_pubk","pubkey":"<b64 公钥 64B>"}
5. 设备以 ch_pubk 回应 → uECC_shared_secret → AES key = 共享密钥低 16B
6. AES-128-CBC 加密发送 ch_data（含 ssid/password/clientId/checkCode/domain/crt）
7. 设备回 {"mac":..,"product_id":..} → 再发 {"type":"end"}
8. App 切回家庭网络：每 2.5s CoAP 探测 + 每 3s HTTP getDiscovered 兜底
9. 发现设备 → device/getBindInfo → device/bind（+ bind/log）
10. 稳态控制 = 云端 MQTT over TLS（非局域网）
```

### 5.2 调用链

```
Java SoftApSDK.startSoftAp(addr, ssid, password, domain, crt, clientId, checkCode)
  └─ JNI  Java_..._SoftApSDK_startSoftAp
       └─ start_softap_app(addr, ssid, password, clientId, check, domain, crt)
            ├─ strcpy 到全局变量
            ├─ create_ecdh_key()   生成 secp256k1 密钥对
            ├─ create_network()    socket(AF_INET, SOCK_DGRAM)
            ├─ setskt()            设广播 + 目标 <bcast>:5658
            ├─ app_step0()         握手，发 ch_pubk
            ├─ app_step1()         发 ch_data，收 mac/product_id
            └─ app_step2()         发 {"type":"end"}
```

### 5.3 全局变量表（`.bss`）

| 地址 | 大小 | 用途 |
|---|---|---|
| `0x1f0e8` | 64 | 公钥 raw（`uECC_make_key` 生成） |
| `0x1f108` | 32 | 私钥 |
| `0x1f148` | 32 | **AES KEY** = ECDH 共享密钥 |
| `0x1f00c` | 16 | **AES IV** = `"abcdefghijklmnop"`（硬编码） |
| `0x1f1a8` | ~ | ssid |
| `0x1f1e8` | ~ | password |
| `0x1f228` | ~ | clientId |
| `0x1f268` | ~ | checkCode |
| `0x1f270` | ~ | **domain**（App 传入） |
| `0x1f2f0` | ~ | **crt**（App 传入） |
| `0x1fb28` | 4 | curve 句柄（`uECC_secp256k1()`） |

### 5.4 三个报文

**`app_step0` —— 握手**

```c
base64_encode(g_public_key, 0x40, buf);
send = {"type":"ch_pubk", "pubkey": buf};        // 明文 JSON
sendto(sock, json, ..., <broadcast>:5658);
for (i = 0; i < 2000; i++) {                     // 每次 select 1ms
    recvfrom(sock, buf, 256, ...);
    root = cJSON_Parse(buf);
    if (strcmp(GetString(root,"type"), "ch_pubk") == 0) {
        b64decode(GetString(root,"pubkey"), peer_pub /*64B*/);
        uECC_shared_secret(peer_pub, g_private_key, g_aes_key, curve);
        return 1;                                // g_aes_key 就绪
    }
}
```

- 曲线 **secp256k1**；公钥 **64 字节**（X‖Y，不压缩，无 `0x04` 前缀）→ base64 后 88 字符
- `uECC_compress`（33B）在本流程中未被使用

**`app_step1` —— 发配网信息**

```c
send = cJSON_CreateObject();
cJSON_AddItemToObject(send, "type",      cJSON_CreateString("ch_data"));
cJSON_AddItemToObject(send, "ssid",      cJSON_CreateString(g_ssid));
cJSON_AddItemToObject(send, "password",  cJSON_CreateString(g_password));
cJSON_AddItemToObject(send, "clientId",  cJSON_CreateString(g_clientId));
cJSON_AddItemToObject(send, "checkCode", cJSON_CreateString(g_checkCode));
cJSON_AddItemToObject(send, "domain",    cJSON_CreateString(g_domain));
cJSON_AddItemToObject(send, "crt",       cJSON_CreateString(g_crt));

json = cJSON_PrintUnformatted(send);
n    = AES128_CBC_encrypt_buffer(aesout, json, strlen(json), g_aes_key, g_iv);
base64_encode(aesout, n, base64out);
sendto(sock, base64out, ...);

for (i = 0; i < 2000; i++) {                     // 等设备回包
    recvfrom(...);
    base64_decode(buf, ..., tmp);
    AES128_CBC_decrypt_buffer(plain, tmp, len, g_aes_key, g_iv);
    root = cJSON_Parse(plain);
    mac = GetObjectItem(root, "mac");  product_id = GetObjectItem(root, "product_id");
    if (mac && product_id) { softap_callback(mac->valuestring, product_id->valuestring); return 2; }
}
```

明文 JSON 字段顺序（cJSON 按插入顺序输出）：

```json
{"type":"ch_data","ssid":"<ssid>","password":"<pwd>","clientId":"<cid>","checkCode":"<6位>","domain":"<domain>","crt":"<crt>"}
```

设备回包（解密后）：`{"mac":"<MAC>","product_id":"<产品号>", ...}`

**`app_step2` —— 结束包**

```json
{"type":"end"}
```

同样 AES + base64 发出。它**先于**收到设备回包发生
（`app_step2()` → `sleep(1)` → `softap_callback(mac, product_id)`）。

### 5.5 日志字符串（动态分析时定位用）

```
>>>> app_step0 socket check -> %d <<<<
>>>> app_step0 recvfrom buf -> %s <<<<
>>>> app_step0 pub_base64 -> %s <<<<
>>>> app_step1 sendjson -> %s <<<<
>>>> app_step1 json -> %s <<<<
>>>> app_step2 sendto check -> %d <<<<
send data to UDP server %s:%d!
```

Java 侧配置字段名：
`c_addr` `c_ssid` `c_password` `c_domain` `c_crt` `c_clientId` `c_checkCode` `c_mac` `c_productId`

### 5.6 ★ 主线 A 的关键结论：domain / crt

- `start_softap_app` 把 `domain`/`crt` `strcpy` 到全局变量；`app_step1` 把它们
  `cJSON_CreateString` 编进 `ch_data` 报文 —— **二者确实一路传到了设备**。
- 原厂 App 传的是**空串**（`DeviceAddSoftApActivity.java:164`，
  `Constants.MAIN_VERSION_TAG` 经核实就是 `""`）：

  ```java
  SoftApManager.getInstance().sendSoftApTimer(
      apBroadAddress, this.mHomeSSID, this.mPassword,
      Constants.MAIN_VERSION_TAG,   // domain = ""
      Constants.MAIN_VERSION_TAG,   // crt    = ""
      XCHelp.mClientId, checkCode);
  ```

- 两个 `.so` 里**搜不到任何域名** → 排除"so 内置默认域名"。
- **推论（标准白牌设计）**：`domain=""`/`crt=""` → 用固件内置默认；
  填入指定值 → 用指定的云端。**改成自建服务地址，设备即连向我们。**

> ❓ 唯一残余风险：固件是否**无条件**尊重这两个参数（需真机验证）。

### 5.7 ★ 真机实测结果（真插座验证）

用 `tools/live-provision.ps1`（内含自写的 `tools/provision.js`）对真插座
（MAC `B4:E6:2D:3A:6E:7C` / productId `381785`）完整跑通配网：

```
[3] 本机地址：192.168.4.2
[4] → 广播 ch_pubk
    ← 设备 ch_pubk（来自 192.168.4.1:5658）
    ECDH 完成，AES key = 52256a5b22571143b920f0e7117a6ab2
    → ch_data {ssid:"PDCN_IOT", domain:"", crt:""}
    ← 设备回包 {"type":"ch_data","mac":"B4E62D3A6E7C","product_id":"381785"}
    → {"type":"end"}      退出码 0
[5] 回连家庭 WiFi → 插座上线 192.168.1.23
```

**结论（已验证，不再是推论）**

| 项 | 结果 |
|---|---|
| SSID 校验码算法 | ✅ 真机吻合（`"smart-381785-3a6e7c-"` 字节和 & 0xFF = `0xB7`） |
| 握手 / 加密 | ✅ secp256k1 ECDH + AES-128-CBC 与固件完全互通 |
| 设备回包字段 | ⚠️ 比预期**多一个 `"type":"ch_data"`**（不只是 `mac`/`product_id`） |
| `domain=""` / `crt=""` | ✅ 设备接受、不报错（走固件内置默认） |
| 配网后行为 | ✅ 插座切入 2.4GHz 家庭网，`192.168.1.23`，ping 通（TTL=255） |
| 局域网控制面 | ❌ **25 个 TCP 端口全关**；CoAP `Scan`（13078，6 种 payload × 广播/单播）**零响应** |

→ **未绑定状态下固件不提供任何局域网控制接口**，稳态控制只能走它自己的云端。
这也解释了"在局域网里找不到插座"——它根本没有可探测的服务，而非网络问题。

> 因此 **主线 A（改 `domain` 指向自建服务器）是唯一的免拆机出路**，
> 且前置条件已全部具备：配网链路已在真机验证可用。

### 5.8 ★ domain / crt 隔离实验（真机，修正版）

**实验一：`domain` 单独设值（21:17）**

```
→ ch_data {ssid:"PDCN_IOT", domain:"192.168.1.20", crt:""}
← {"type":"ch_data","mac":"B4E62D3A6E7C","product_id":"381785"}   ✅ 设备接受
```

**实验二：`crt` 非空（两次独立尝试）**

| crt | 大小 | 设备反应 |
|---|---|---|
| `ca.crt`（RSA CA，PEM） | 1338 B | ❌ 不回包，重发 `ch_pubk` |
| `ec-ca.crt`（EC CA，PEM） | 627 B | ❌ 不回包，重发 `ch_pubk` |
| `""` | 0 B | ✅ 回 `ch_data` |

**⚠️ 重要更正 —— 实验一的分组 B–G 全部无效**

`experiment-domain.ps1` 的分组 A 成功后，设备**立即退出配网模式**，
后续 B–G 的日志里本机 IPv4 全是 `192.168.1.20`（而非 `192.168.4.x`），
即根本没连上插座热点 —— 那 6 条 `超时未回包` **不能**用来证明任何事。
有效的 `crt` 数据点只有上表这 3 个（各自都是独立、已确认连上热点的会话）。

**`domain` 是否被"照做"？—— 证据指向「没有」**

实验一配置 `domain="192.168.1.20"` 后，插座确实切到 `192.168.1.23` 并 ping 通，
但**自建服务端（21:07 就已监听 1883/8883/8080）全程零连接**：

| 观察 | 结论 |
|---|---|
| 设备未连向 192.168.1.20 的任何端口 | `domain` 很可能被固件**忽略**（仍走内置云端） |
| 端口监听网（31 端口）在 21:44 窗口内只捕到 1 个连接 | 那是笔记本自己的企业安全客户端，**不是插座** |

> ### ★ 已定案（2026-10-02 03:27 复测，见 [05 §11](05-免拆机方案.md)）
>
> 上面这次用的是**私有 IP**，所以"没连过来"无法区分
> 「固件忽略 domain」和「固件采纳了但被防重绑定拦下」——**结论当时是无效的**。
>
> 复测改用了**公网 IP**（光猫 WAN `139.227.20.142`，不在劫持名单里），结果：
> - 设备**接受**了配置（回了 `mac`/`product_id`）；
> - 但随后仍发出 `iot.ixiaocong.com` 的 DNS 查询，并连到劫持 IP `203.0.113.9`；
> - `conntrack` 里 `dst` 是 `203.0.113.9`，**不是**我们下发的 `139.227.20.142`。
>
> ⇒ **固件【忽略】`domain`。本节"证据指向没有"升格为确定结论。**
> 探针选值的坑（不能拿已在劫持名单里的域名当探针）见 [05 §11.2](05-免拆机方案.md)。

**设备行为模型（当前最贴合证据的假设）**

```
crt=""        → 设备接受配置，但 domain 似乎未被采用（走固件内置云端，该域名已 NXDOMAIN）
crt 非空      → 设备整包拒绝（重发 ch_pubk），连 WiFi 配置都不应用
```

两种解释仍无法区分，需下一次真机实验（见 `tools/experiment-crt.ps1`）：

- **(H1) 固定缓冲区**：固件里是 `char crt[N]`，超长即整包拒绝 → 换更小的证书即可
- **(H2) 只收厂商 crt**：crt 是厂商签名槽位，任意非空值一律拒 → 主线 A 基本走死

判别方法：先测 **crt = 1 字节**。
- 1 字节**也被拒** → 指向 (H2)（与长度无关）
- 1 字节**被接受** → 指向 (H1)，再用升序变体（64/256/384/448/554…B）二分定位边界

> 为此 `tools/provision.js` 增加了 **NACK 判定**（退出码 3）：
> 设备重发明文 `ch_pubk` 即"收到了 ch_data 但拒绝"，与"超时没收到"（退出码 2）区分开，
> 并会先自动重发 `ch_data` 两次以排除 UDP 丢包。
> 该判定链已用 `tools/selftest-provision.js` + 假设备（模拟 H1/H2）自检通过 6/6。

**旁证：`smres.zdxcloud.net` 不是插座（已彻底证伪）**

21:44:21 端口 80 上那个带 SNI 的 TLS ClientHello，来源是**笔记本自己的企业安全客户端**：

- `zdxcloud.net` = **Zscaler ZDX** 域（解析到 147.161.209.63 / 165.225.246.34，均为 Zscaler 段）
- 本机装有 `Check Point Virtual Network Adapter For Endpoint VPN Client`
- 该连接出现在 Wi-Fi 于 21:44:15 重新拿到 DHCP 租约后 6 秒 —— 网络切换触发的连通性探测

**直接证据（二层抓包，2026-10-01 22:11）**：用 `tools/lan-mitm.py --all` 解析本机自身流量，抓到

```
★★★ DNS 查询 → 192.168.1.1:53   smres.zdxcloud.net   (type=1)
★★★ DNS 查询 → 223.5.5.5:53     smres.zdxcloud.net   (type=1)
★★★ TLS ClientHello → 112.80.40.69:443   SNI = smres.zdxcloud.net
```

→ 是**本机自己**先查这个域名、再带这个 SNI 去连。与插座无关，**排除**。


---

### 5.9 ★★★ 真机二层抓包：固件内置云端域名 = `iot.ixiaocong.com`

**背景**：APK 里搜不到任何域名（域名烧在固件里），而 `tools/probe_server.js` 之类的
离线手段也拿不到。于是改走**二层旁观**：不改插座任何配置，用
`tools/lan-mitm.py`（ARP 欺骗网关 + DNS 中继）看它到底往哪连。

**为什么必须 ARP 欺骗**：Wi-Fi 下看不到别的终端的单播流量（AP 只转发给目标站点），
但插座要访问的 DNS（`223.5.5.5`）和云端都在**子网外**，
所以骗它"网关 MAC 是我"，它出子网的流量就全落到我们网卡上。
（实测本机**无管理员权限也能抓包 + 发包**，见 [05 §9.2](05-免拆机方案.md)。）

**实验**（2026-10-01 22:32:25 → 22:37:27，300 秒，`--mode relay`）：

```
[22:32:25] 目标插座：192.168.1.23  MAC=b4:e6:2d:3a:6e:7c
[22:32:26] 开始 ARP 欺骗：告诉插座『192.168.1.1 在我这』
★★★ [22:32:26] DNS 查询 → 223.5.5.5:53   iot.ixiaocong.com   (type=1)
[22:32:27]   → 真实应答：NXDOMAIN（域名不存在！）
```

**统计结果（530 条样本）**

| 类型 | 数量 |
|---|---|
| `dns_query` | 66 |
| `dns_relayed` | 65 |
| `dns_relay_failed` | 1 |
| `arp_request` / `arp_reply` | 184 / 214 |
| **`tcp_syn`** | **0** |
| **`tls_clienthello`** | **0** |

- **查询域名：`iot.ixiaocong.com` × 66 —— 100% 只查这一个**
- **查询目标 DNS：`223.5.5.5` × 66 —— 从不用 DHCP 下发的 `192.168.1.1`**
- **应答：65 次全是 NXDOMAIN**（域名已注销）

**重试节奏**（时间戳）：

```
22:32:26 22:32:27 22:32:29 │ 22:32:40 22:32:41 22:32:43 │ 22:32:54 22:32:55 22:32:57 │ …
└──── 一轮 3 次（间隔约 1s）────┘ └── 每轮间隔约 11s ──┘
```

**结论（全部为实测，非推论）**

| 项 | 结果 |
|---|---|
| 固件内置云端域名 | ✅ **`iot.ixiaocong.com`** ★ 逆向 APK 拿不到的关键信息 |
| 该域名现状 | ✅ **NXDOMAIN**（厂商未续费/已注销） |
| 插座用的 DNS | ✅ **硬编码 `223.5.5.5`**（AliDNS），**不理会** DHCP 下发的 `192.168.1.1` |
| 重试策略 | ✅ 每轮 3 次查询，每 ~11s 一轮，持续重试 |
| 为何没有 TCP/TLS | ✅ 域名解析不出 IP → 根本没走到连接阶段 |
| 约 5 分钟后 | ⚠️ 插座**离线**（停止重试）→ 固件有退避或复位逻辑 |

> **这条发现让路径 E 从"理论"变成"可执行"**：
> 我们不需要改插座配置，只要**在它查 `iot.ixiaocong.com` 时伪造 DNS 应答**
> 指向自建服务器即可 —— 而这正是 `tools/lan-mitm.py --mode dns` 做的事。
> 因为插座硬编码用 `223.5.5.5`，**在路由器上加静态 DNS 是没用的**，
> 只能靠 ARP 欺骗 + 伪造应答（或把自己的 DNS 变成它的 DNS）。

---

## 6. xconfig 配网（xcsdk）

```
DeviceSdk.setCallback(cb)
initPolling(true)              → 独立线程每 200ms 调 polling()
DeviceSdk.cmdExec(2, {"scanType":1,"productId":<pid>})
```

设备事件回调返回：

```json
{"deviceId":"...","mac":"...","productId":"...","firmwareVersion":"...",
 "scriptType":"...","publicKey":"...","productName":"...","productImg":"..."}
```

### xconfig_start（ESP-Touch 风格）

```c
xconfig_start(ctx, ssid, ssid_len, pwd, pwd_len, key, key_len, args):
    args 默认: SendType=0x23, bSyncInterval=5, bDataInterval=10,
               fullScaleTimer=40000, bSyncTimer=2000, bDataTimer=0x14
    payload[0..0x5f] = 0
    payload[2] = pwd_len
    payload[1] = pwd_len + ssid_len + 7
    memcpy(payload+3,          pwd,  pwd_len)
    *(u32*)(payload+pwd_len+3) = feedbackIp
    *(u16*)(payload+pwd_len+7) = feedbackPort
    memcpy(payload+pwd_len+9,  ssid, ssid_len)
    if (SendType >> 4 == 2)     // 0x23 → 2 = 加密路径
        device_aes_encrypt(key, 16, iv, payload+2, payload[1], payload+2, ...)
```

- `SendType = 0x23`：高位 2 = AES 加密，低位 3 = 广播模式
- key **由 App 经 JNI `XConfigStart(ssid, pass, key, args)` 传入**；`args` 传 NULL 即用默认参数
- `easylinkLoop` 按 `SendType & 0xF` 分模式：`1` = 组播 / `2` = 固定地址广播 / `3` = 全量广播

---

## 7. 局域网 CoAP 通道（libxcsdk.so）

### 7.1 网络层

```c
network_create(host):
    fd = socket(AF_INET, SOCK_DGRAM, 0x11);
    setsockopt(SO_RCVBUF, 0x8000);      // 32KB
    setsockopt(SO_BROADCAST, on);
    bind(fd, {AF_INET, port=0, addr=host});
    ctx->broadcast = { AF_INET, port = 13078, addr = 255.255.255.255 };
```

| 参数 | 值 |
|---|---|
| CoAP 端口 | **13078**（IANA 给 CoAP 的备用端口之一） |
| 广播地址 | `255.255.255.255` |
| 扫描间隔 | 2000 ms |
| 轮询间隔 | 200 ms（Java 侧 `XcSdkManager`） |

### 7.2 报文

```
发现设备   CoAP v1, CON, GET, Uri-Path="Scan"
           payload {"scanType":"%d","productId":"%s"}     // 定向扫描可带 "mac"
                   {"scanType":"0"}                       // 探测
取局域网密钥 Uri-Path="Getkey" + Uri-Query="Getkey"
           payload {"Query":"1"}
```

设备在 `Location-Path` 选项里回自己的路径；回包 payload 是设备信息 JSON（`json2device` 解析）。

### 7.3 命令表 cmdExec(type, json)

| `type` | 枚举 | 参数解析 |
|---|---|---|
| 2 | `CMD_Scan` | `getScanPara`（`scanType`,`productId`） |
| 3 | `CMD_GetLocalKey` | `getGenKeyPara`（`deviceId`，<0x22 字节）★ |
| 4 | `CMD_Snapshot` | `getScanPara` |
| 5 | `CMD_Control` | `getScanPara` |

> `CMD_SmartConfig` **不在** `cmdExec` 命令表里 —— SmartConfig（ESP-Touch）走
> `xconfig_start` / `easylinkLoop`，独立于 cmdExec。

### 7.4 使用范围

`polling()` 主循环 = `easylinkLoop` + CoAP 广播/收包；Java 侧 `XcSdkManager` 目前
**只在 xconfig 配网流程里用 opcode 2 发现设备**。

> ⚠️ **稳态控制在 App 里仍走云端 MQTT。** 固件是否在配网之外也监听 13078 属未知，
> 需真机 `nmap -sU -p 13078 <插座IP>` 验证。若开放，则可实现纯局域网控制。

---

## 8. 两个 native 库对照

| 项 | libsoftAp.so（softap1.0） | libxcsdk.so（xconfig） |
|---|---|---|
| 传输 | UDP 广播 `:5658` | UDP 广播 `:13078`（CoAP） |
| 握手 | secp256k1 ECDH | 无（AES key 由 App 传） |
| 加密 | AES-128-CBC，IV 硬编码 | AES-128-CBC（`SendType=0x23`） |
| 密钥 | ECDH 共享密钥 | App 传入的 `key` |
| 配网报文 | `ch_data` JSON（含 domain/crt） | 二进制 payload（含 feedbackIp/Port） |
| 局域网控制 | 无 | CoAP `Scan`/`Getkey`/`Snapshot`/`Control` |
| 源码路径 | `.../SoftApTest/app/src/main/jni/` | `.../embed-gw/sdk_smartphone/.../sdk.Shared/` |

---

## 9. 可复用的参考实现（配网页，Node / 任何语言）

```js
// 1. 生成 secp256k1 密钥对，发送握手
const { publicKey, privateKey } = crypto.generateKeyPairSync('ec', { namedCurve: 'secp256k1' });
send(JSON.stringify({ type: 'ch_pubk', pubkey: pub64b64 }));

// 2. 收到设备公钥
const shared = crypto.diffieHellman({ privateKey, publicKey: peerKey });
const KEY = shared.slice(0, 16);
const IV  = Buffer.from('abcdefghijklmnop');       // 硬编码

// 3. 加密配网信息
const payload = JSON.stringify({ type:'ch_data', ssid, password, clientId, checkCode, domain, crt });
const cipher = crypto.createCipheriv('aes-128-cbc', KEY, IV);
const b64 = Buffer.concat([cipher.update(payload,'utf8'), cipher.final()]).toString('base64');
send(b64);
```

配套还需（原 App 已有逻辑，可照搬）：扫描并校验 `smart-<pid>-<mac6>-<ck>` 热点、
连上该开放热点、90 秒超时重试。

> 好处：报文构造、ECC 握手、AES 加密全部由标准库完成，**无需依赖厂商 `.so`**。
> 也可直接 JNI 复用 `libsoftAp.so`（省去曲线点运算的兼容性顾虑）。
