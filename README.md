# 小葱智能插座（ixiaocong）逆向修复

厂商已倒闭，云端已停服。目标：**免拆机**让插座重新可控。

---

## 现状

- **协议栈已完整还原**（含 native 层）：HTTP（MD5 签名）+ MQTT（明文 JSON）+ SoftAP 配网 + 局域网 CoAP。
- **配网加密已破译**：**secp256k1 ECDH + AES-128-CBC**，IV 硬编码 `abcdefghijklmnop`。
- **★ 配网链路已在真机跑通**（自写客户端，不依赖厂商 App）：真插座 MAC `B4:E6:2D:3A:6E:7C` /
  productId `381785`，配网后成功切入 2.4GHz 家庭网并上线 `192.168.1.23`。
  详见 [03 §5.7](docs/03-配网与native.md)。
- **★★★ 已抓到固件内置云端域名：`iot.ixiaocong.com`**（NXDOMAIN，已注销）。
  用 `tools/lan-mitm.py` 二层旁观（ARP 欺骗网关 + DNS 中继），不改插座任何配置。
  同时测出：插座**硬编码 DNS `223.5.5.5`**、每轮 3 次查询、每 ~11s 一轮。
  详见 [03 §5.9](docs/03-配网与native.md)。
- **★★★★ 真机云端协议已完整拿到，且确认「免拆机可完全接管」**（2026-10-01 深夜）：
  - 云端是 **MQTT over TLS，端口 `8188`**（不是 443）
  - 固件用 **TLS 1.1 + 仅 RSA 密钥交换 + 零扩展**（连 SNI 都没有）
  - **★ 固件不校验证书** —— 自签证书直接握手成功 ⇒ 可直接冒充官方云端
  - 凭据：clientId `6587529711423210`、username `381785`、password 96 字符 hex（= 48 字节签名）
  - 上报主题：`baseInfo`（上线一次）+ `snapshot`（每 **60s**，含 switch 状态与 rssi）
  - 工具 `tools/fake-cloud.py`（用户态 TCP + TLS + MQTT）
  - 详见 **[07-真机云端协议](docs/07-真机云端协议.md)**
- **★★★★★ 免拆机接管已实机跑通 —— 插座重新可控了**（2026-10-02 00:24）：
  - 下发 `PUBLISH topic=control {"command":{"switch":1}}` → 插座**立即执行**，
    回执 `messageId` 原样回显、`receiveId` 指向我们、`snapshot.switch` 由 0→1
  - 状态**跨心跳稳定保持**；再下发 `switch=0` 同样立即生效 ⇒ **双向闭环**
  - **★ 插座从不发 SUBSCRIBE** —— 这套"MQTT"只是借用报文框架做点对点，
    服务端**直接往同一条 TLS 连接写 PUBLISH** 即可，不需要订阅关系
  - 关键字段名是 **`switch`**（不是 App 侧的 `{"1":1}`）
  - 新工具：`tools/mqtt-decode.py`（完整解码被日志截断的 payload）、
    `control.cmd`（运行时命令注入：`echo "switch=1" >> control.cmd`）
- **`domain`/`crt` 会随配网下发给设备**（报文侧已证实），但固件侧行为已测出分化：
  `crt=""` → 设备接受；**`crt` 非空（1338B/627B 两次实测）→ 设备整包拒绝**。
- **`domain` 未被测出被采用**：早期 `domain=192.168.1.20` 实测设备未连向自建服务端。
  → **2026-10-02 复测定案**：改用**公网 IP** 后，设备仍去查内置的 `iot.ixiaocong.com`，
    并连到劫持 IP。⇒ **固件忽略 `domain`**（见 [05 §11](docs/05-免拆机方案.md)）。
    ⚠️ 早期那次用的是私有 IP，本就无法区分"忽略"与"被防重绑定拦下"，属于无效实验。
- **自建服务端已跑通全链路**（[server/](server/)）：零依赖纯 Node + 手写 MQTT broker（HTTP + MQTT + TLS）。
- **APK 已重打包并签名** → [build/xiaocong-fixed.apk](build/xiaocong-fixed.apk)。
- **★★★★★ 已把整套接管搬到光猫上常驻（2026-10-02）** —— 单文件 Go 程序
  [`firmware/ont-go/`](firmware/ont-go/)，UPX 压缩后放在光猫 **`/f4610u/app/ixc-go-arm64_upx`**，
  由 `/f4610u/user_init.sh` 开机拉起。**`allin.dns.army`（DDNS）可直接用**
  （靠 `user_init.sh` 里一条不写死 WAN IP 的回环规则，见 [05 §12.2②](docs/05-免拆机方案.md)）。
- **★★★★ 真插座闭环已打通（2026-10-02 01:07–01:08）** —— 插座自动连上假云端，
  `switch=1` / `switch=0` 都生效且**设备回报了状态快照**。不是"理论上能控"。
- **★★★★ 自编完整版 busybox（2026-10-02）** —— 光猫自带只有 120 个 applet，
  已交叉编译出 **busybox 1.36.1 / 418 个 applet** 的静态版本，
  真机 105 条命令全过、光猫无异常。脚本 [`firmware/build_busybox_static.sh`](firmware/build_busybox_static.sh)，
  产物 [`firmware/busybox-full-aarch64`](firmware/busybox-full-aarch64)。详见 [05 §13](docs/05-免拆机方案.md)。

> **局域网直控已排除**：真机实测 25 个 TCP 端口全关、CoAP `13078` 六种 payload 零响应
> → 固件在未绑定状态下不提供任何局域网接口，稳态控制只能走它自己的云端。
>
> **路径 E 已走通并验证成功** ★★：不改插座任何配置、不拆机，
> 用 ARP 欺骗 + 伪造 DNS 把 `iot.ixiaocong.com` 引到自建服务器，
> 以用户态 TCP + TLS 冒充官方云端 —— **固件不校验证书，握手直接通过**，
> 抓到完整 MQTT 交互，**并且已经能真正控制开关**（`switch=1/0` 双向验证通过）。
> **项目原始目标「免拆机让插座重新可控」已达成。**
>
> **两个必须知道的坑**：
> 1. **固件拒绝私有网段 IP**（防 DNS 重绑定）→ 伪造应答**必须给公网 IP**，
>    否则插座解析成功却**永远不发 TCP**。
> 2. 端口是 **8188**，不是 443。

## 文档导航

| 文档 | 内容 |
|---|---|
| [00-总览](docs/00-总览.md) | 项目全貌、App 架构、关键凭据 |
| [01-HTTP-API与签名](docs/01-HTTP-API与签名.md) | ~120 端点 + MD5 签名算法 |
| [02-MQTT协议](docs/02-MQTT协议.md) | 5 个主题 + 明文 JSON 报文（**App 侧**） |
| [03-配网与native](docs/03-配网与native.md) | APK 信息、native 库、配网协议、CoAP（**权威**） |
| [04-路线与建议](docs/04-路线与建议.md) | 决策树 + 三条路线评估 |
| [05-免拆机方案](docs/05-免拆机方案.md) | 免拆机方案 + 执行步骤 |
| [06-Ghidra操作手册](docs/06-Ghidra操作手册.md) | 跑通 Ghidra、脚本、踩坑 |
| **[07-真机云端协议](docs/07-真机云端协议.md)** | **★★ 真机实测抓包还原的完整云端协议 + 控制指令（权威）** |
| [08-RN热更新](docs/08-RN热更新.md) | 设备面板 jsbundle 的来源、加载条件、官方服务端可达性、三条自喂路径 |
| [09-OTA与局域网控制](docs/09-OTA与局域网控制.md) | **★ 局域网无控制入口（已严格证实）+ OTA 盲探 210 条全失败** |

---

## App 无法启动的根因：乐固壳

**结论：App 打不开的根因是乐固壳，不是业务代码。**

| 事实 | 证据 |
|---|---|
| 壳入口 | `<application android:name="com.tencent.StubShell.TxAppEntry">` |
| 真入口 | `<meta-data name="TxAppEntry" value="com.ixiaocong.smarthome.phone.android.XcApplication"/>` |
| 业务代码**不依赖壳** | grep `StubShell` 在 `com/ixiaocong/**`、`com/xiaocong/**` 结果为空 |
| 壳 dex | `dex/...phone13313100.dex`（含 `Lcom/tencent/StubShell/a…f`，是壳） |
| 真代码 dex | `9763076`(主App) / `3073100`(SDK) / `1799620`(Chromium WebView) |

> ⚠️ **千万不要提升 `targetSdkVersion`**。App 是 23，而配网重度使用 Android 10
> 受限的 WiFi API（`addNetwork`/`enableNetwork`/`getConfiguredNetworks`/`getScanResults`）。
> 提到 29+ 反而会把配网废掉。低 targetSdk 在 Android 10 上仍走兼容路径。

---

## 重打包与签名

**关键技巧：外科式修改二进制 XML** —— AXML 里属性存的是「字符串池索引」，
所以只需把 `<application android:name>` 的引用从 `274`（壳）改成 `138`（`XcApplication`），
**4 字节改动，无需 apktool 重编译 XML**。

字符串池索引（已实测确认）：

```
[138] com.ixiaocong.smarthome.phone.android.XcApplication
[274] com.tencent.StubShell.TxAppEntry
[275] TxAppEntry
```

流程（脚本：[tools/build_apk.sh](tools/build_apk.sh) → [tools/sign_apk.sh](tools/sign_apk.sh)）：

| 步骤 | 工具 |
|---|---|
| 1. 解包原 APK | `7z` |
| 2. 删 `META-INF/`、`classes.dex`、`libshella/shellx/xguardian*.so` | shell |
| 3. 放入脱壳 dex 为 `classes.dex` / `classes2..4.dex` | shell |
| 4. 打补丁 manifest | [tools/patch_axml.js](tools/patch_axml.js) |
| 5. 重新打包 | `7z a -tzip` |
| 6. 签名 | `jarsigner`（JDK 21 自带） |

**产物**：

```
build/xiaocong-fixed-unsigned.apk   24.5 MB   重建好、未签名（流程可复现）
build/xiaocong-fixed.apk            25.4 MB   ★ 已签名，可直接 adb install
build/keystore/xiaocong.jks                  自签证书（口令 xiaocong123）
  ✓ AndroidManifest.xml 已打补丁（application -> XcApplication）
  ✓ classes.dex  = 9763076（主App）      classes2.dex = 3073100（SDK）
  ✓ classes3.dex = 1799620（Chromium）   classes4.dex = 13313100
  ✓ 壳库（libshella/shellx/xguardian）已删   ✓ META-INF 已删   ✓ 无 StubShell 残留
  ✓ resources.arsc 已改回 STORED（不压缩）
  ✓ jarsigner -verify 返回 0，1053 个条目
```

重跑：`sh tools/build_apk.sh && sh tools/sign_apk.sh`

手工签名步骤：

```bash
JDK="C:/Users/caojiecn/Downloads/jdk-21.0.12.1+1/bin"
"$JDK/keytool.exe" -genkeypair -keystore build/keystore/xiaocong.jks \
  -alias xiaocong -keyalg RSA -keysize 2048 -validity 10950 \
  -storepass xiaocong123 -keypass xiaocong123 -dname "CN=Xiaocong Mod, ..."
"$JDK/jarsigner.exe" -keystore build/keystore/xiaocong.jks \
  -storepass xiaocong123 -keypass xiaocong123 \
  -sigalg SHA256withRSA -digestalg SHA-256 -sigfile CERT \
  -signedjar build/xiaocong-fixed.apk build/xiaocong-aligned.apk xiaocong
```

> ⚠️ **踩到的坑**：`7z a -tzip` 会把 `resources.arsc` **压缩**存储，
> 而 Android 7.0+ 要求它必须是 STORED（不压缩），否则**直接拒装**。
> `sign_apk.sh` 里已用 `7z d` + `7z a -mx=0` 单独把它改回 STORED —— 重打包后记得重跑。

> **签名档位**：只有 **v1**。本 App `minSdk=16`/`targetSdk=23`，v1 有效；
> 但 **API 30+ 设备**上 `apksigner verify` 会报 "not signed with v2"，
> 部分 ROM 可能拒绝安装。若装不上，需补 `apksigner`（Android build-tools）重签 v2。
>
> **签名不同 → 不能覆盖安装原版**，必须先卸载原 App。安装：`adb install build/xiaocong-fixed.apk`

---

## 免拆机路线的两条主线

### 主线 A：重定向到自建服务器（首选）

配网的 native 函数签名里有 **`domain` / `crt` 两个参数**：

```java
// SoftApSDK.java
native void startSoftAp(String broadAddress, String ssid, String password,
                        String domain,   // ★ 云端域名
                        String crt,      // ★ 云端 CA 证书
                        String clientId, String checkCode);
```

Ghidra 已证实：`start_softap_app` 把二者 `strcpy` 到全局变量 `0x1f270` / `0x1f2f0`，
`app_step1` 把它们编进 `ch_data` 报文（7 个字段）。而原厂 App 传的是**空串**
（`DeviceAddSoftApActivity.java:164`，`Constants.MAIN_VERSION_TAG = ""`），
且两个 `.so` 里都搜不到任何域名。

→ 推论（标准白牌设计）：不传则用固件内置默认，传了就用指定的。
→ **若设备遵守这两个参数，配网时填自己的域名 / 证书，设备就连向自建服务器，全程免拆机。**

加密握手可直接 JNI 复用厂商 `libsoftAp.so`，也可按
[03 §9](docs/03-配网与native.md)
用 20 行标准库代码自己实现。

> ❓ 唯一残余风险：固件是否**无条件**遵守这两个参数 —— 需真机验证。

### 主线 B：OTA 偷渡固件

借助 `device/sdk/upgrade` 把自制固件推给设备。**障碍**：没有固件镜像、
OTA 协议未知、bootloader 可能校验签名。

---

## 设备控制协议速查

### MQTT（无加密，5 个主题）

```
ssl://<host>:<port>   证书固定 assets/test.crt
username = appId      27e33ff8a0634cc4b6f696c9872a2d1b
password = 登录 token
```

| 主题 | 方向 |
|---|---|
| `control` / `manage` | App → 设备 |
| `snapshot` / `online` / `offline` / `error` | 设备 → App |

**路由靠 payload 的 `receiveId`，不靠主题；订阅由服务端绑定**（App 无 `subscribe()` 调用）。

开灯：

```json
{"messageId":523847123,"protocolVersion":"1.0.0",
 "receiveId":"<设备ID>","senderId":"<clientId>","command":{"1":1}}
```

设备回：

```json
{"code":0,"messageId":523847123,"protocolVersion":"1.0.0",
 "receiveId":"<clientId>","senderId":"<设备ID>","status":1,"snapshot":{"1":1}}
```

### HTTP（~120 端点，MD5 签名）

```
POST https://gw.ixiaocong.com/<path>  (application/x-www-form-urlencoded)
签名 = MD5( key 大小写不敏感排序后 "k=v&" 拼接 + clientKey ).toLowerCase()
      client/config 阶段用 appKey
```

关键端点：`client/config`（下发 MQTT 地址）、`user/login`、`device/subscribe/all`、
`device/bind`、`device/getDiscovered`、`parameter/list`、`product/help`（决定配网方式）

### 配网（SoftAP）

```
长按5秒 → AP "smart-<产品号>-<MAC后6位>-<校验码>"
校验码 = 前段SSID字节和 & 0xFF 的十六进制
→ 连上 192.168.4.1 热点
→ 每4.5s 广播 192.168.4.255:5658  {"type":"ch_pubk","pubkey":"<b64 公钥 64B>"}
→ secp256k1 ECDH，AES key = 共享密钥低 16 字节
→ AES-128-CBC(json, key, iv="abcdefghijklmnop") → base64 发送：
  {"type":"ch_data","ssid":..,"password":..,"clientId":..,"checkCode":..,"domain":..,"crt":..}
→ 设备回 {"mac":..,"product_id":..} → 再发 {"type":"end"}
```

### 局域网（CoAP）

```
UDP 255.255.255.255:13078（CoAP）
  GET Uri-Path="Scan"    {"scanType":N,"productId":"..."}
  GET Uri-Path="Getkey"  {"Query":"1"}         ← 取局域网密钥
  DeviceSdk.cmdExec(2=Scan / 3=GetLocalKey / 4=Snapshot / 5=Control, json)
```

> ⚠️ App 里该通道**只用于配网期发现设备**；稳态控制仍走云端 MQTT。

---

## 下一步

**✅ 路径 E 已全部走完（含控制闭环）—— 目标达成。**
**✅ 且已搬到光猫上常驻（[05 §12](docs/05-免拆机方案.md)）—— 不再需要 PC 常开。**

一键跑起来（不需要管理员，需要 Npcap）：

```bash
# 常驻假云端 + 运行时命令注入
python tools/fake-cloud.py --target 192.168.1.23 --seconds 1800 --cmd-file control.cmd

# 另开终端：追加一行就立刻下发
echo "switch=1" >> control.cmd     # 开
echo "switch=0" >> control.cmd     # 关
```

光猫上常驻版（推荐，24/7）：

```bash
cd firmware/ont-go

# 1) 编译（本机或编译服务器都行）—— 一条命令，产物自带 UPX 压缩
./build.sh                                   # 需要 /usr/local/go/bin/go + upx
#   等价于：
#   GOOS=linux GOARCH=arm64 CGO_ENABLED=0 go build -trimpath -ldflags "-s -w" -o ixc-go-arm64 .
#   upx --best --lzma -o ixc-go-arm64_upx ixc-go-arm64 && upx -t ixc-go-arm64_upx
#   为什么必须 UPX：光猫 rootfs 是 overlay，只剩 8~12 MB。未压缩 6.7 MB → 压缩后 ~2 MB。

# 2) 部署：二进制进 /f4610u/app/ixc-go-arm64_upx，回环规则写进 /f4610u/user_init.sh
SSH_PW=xxxx python deploy.py                 # 部署 + 挂开机自启（可反复执行，字节级幂等）
SSH_PW=xxxx python deploy.py --rules         # 只看 iptables 现状
SSH_PW=xxxx python deploy.py --status        # 进程 / 规则 / 日志 一次看全
SSH_PW=xxxx python deploy.py --uninstall     # 回滚（user_init.sh 逐字节还原）

# 3) 用
curl 'http://192.168.1.1:8080/on'            # 开
curl 'http://192.168.1.1:8080/off'           # 关
# 远程（推荐走 easytier 虚拟网，不暴露 WAN）：
curl 'http://10.0.0.1:8080/on'            # et_2_pmog 接口 IP
# 公网直连（DDNS 已解析到光猫 WAN IP，仅测试用，不建议长期暴露）：
curl 'http://allin.dns.army:8080/on'

# 4) 光猫上没有 man，直接 -h 看帮助
/f4610u/app/ixc-go-arm64_upx -h
```

> **回环规则为什么放在 `user_init.sh` 而不是程序里**：光猫没有 NAT 回环
> （LAN 打自己的 WAN IP，包在进 netfilter 之前就被 ZTE 转发引擎吃掉，连 conntrack 都不建），
> 所以必须有一条 `REDIRECT` 兜底。规则写成 `-i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188`
> —— **用"目标不是内网段"判断，PPPoE 重播换 IP 也不会失效**；
> 早期写死 WAN IP 的那条重播后会静默失效（表里还在，其实已经打不通）。
> 程序里保留了 `-hairpin` 开关（默认关），但**部署默认不铺**，规则常驻开机脚本即可。

### 从"实验"到"日常可用"还剩的事

| # | 待办 | 说明 |
|---|---|---|
| 1 | ~~**常驻 broker**~~ | ✅ **已完成** —— [`firmware/ont-go/`](firmware/ont-go/) 手写 MQTT，TLS 放开到 1.1 + 静态 RSA，按 `receiveId` 路由、不依赖订阅 |
| 2 | ~~**长期部署**~~ | ✅ **已完成** —— 服务端跑在光猫上（`/f4610u/app/ixc-go-arm64_upx`），开机自启；**控制面**：局域网 `192.168.1.1:8080`，远程**走 easytier**（`et_2_pmog=10.0.0.1`，把 `-http` 改 `:8080` 即监听所有网卡）；公网 `allin.dns.army:8080` 虽能通但**不建议直暴露 WAN**，优先 easytier |
| 3 | ~~**消除副作用**~~ | ✅ **已完成** —— 劫持改为 **`nat OUTPUT` 劫持 DNS 上游**（`/etc/resolv.conf` 的 `210.22.70.225` → 光猫 `/sbin/proxy`），天然覆盖所有插座、不写死 MAC、不误伤其他设备；**根因**见 [05 §12.3.1](docs/05-免拆机方案.md)：PREROUTING 按 MAC 对插座 DNS 永不命中（conntrack 已建流不查 nat），早期 `-m mac` 方案实际 9 小时 0 命中 |
| 4 | **补全指令集** | 目前只验证了 `switch`。`repower`（断电记忆）等参数待逐个试探 |
| 5 | ~~**`domain` 边界（方案 2）**~~ | ✅ **已定案：固件【忽略】domain** —— 03:27 真机，给了**公网 IP** 它仍去查内置的 `iot.ixiaocong.com`。两条"让设备主动指向我们"的路（`crt` / `domain`）**都堵死**，路径 E（DNS 劫持）是唯一可行路线。见 [05 §11](docs/05-免拆机方案.md) |
| 6 | **装机测试** | `adb install build/xiaocong-fixed.apk`（**需先卸载原版**，签名不同） |
| 7 | ~~**真插座闭环**~~ | ✅ **已完成**（2026-10-02 01:06–01:08）—— 插座自己连上光猫，`switch=1` / `switch=0` 都生效并回报了状态快照 |
| 8 | ~~**光猫上的工具链太弱**~~ | ✅ **已完成** —— 自编 busybox 1.36.1 / 418 applet，[05 §13](docs/05-免拆机方案.md) |
| 9 | **把完整 busybox 装机** | 目前只在 `/tmp` 验证过。**建议不要替换 `/bin/busybox`**（`procd`/启动脚本依赖它），放 `/f4610u/app/` 用绝对路径调用 |
| 10 | **rootfs 空间紧** | overlay 已用 ~89%，只剩 8~12 MB。所以二进制一律 UPX；日志一律写 `/tmp`（tmpfs） |
| 11 | **多插座串台待修** | `/on` `/off` `/status` 走 `PublishToAll` 广播所有会话、`/status` 只显示 IP:port 分不清设备；需改为按 `deviceId` 下发（见 [09 §2.7](docs/09-OTA与局域网控制.md)）。当前劫持已天然覆盖多插座，但**一条指令会误触你手上所有插座** |
| 12 | **`deploy.py` 已废弃** | 规则唯一来源改为 [`firmware/ont-go/ixc-ont.sh`](firmware/ont-go/ixc-ont.sh)；`deploy.py` 仅文档标注废弃、代码未删，迁移后可整体删 |

**方案 2（`domain`）—— 已跑完，结论：固件【忽略】domain，此路不通。**

```bash
# 蹲插座自己开的 smart-* 热点，发现即自动连上去配网、再回连家庭网（全自动）
pwsh -File tools/watch-and-provision.ps1 -Domain "139.227.20.142"

# 只想看一眼周边真实 WiFi（netsh 只读缓存，必须强扫）
pwsh -File tools/wifi-scan.ps1
```

> **⚠️ 守望脚本必须用 `tools/` 里这两个，别用 `netsh wlan show networks` 直接轮询。**
> `netsh` 读的是 **WLAN 缓存**：PC 已连上信号很强的网络时 Windows 基本不扫描，
> 实测"周边 26 个网络"它只报 1 个，目标热点永远等不到。
> `tools/wifi-scan.ps1` 用 `wlanapi.dll` 的 `WlanScan()` 强制扫一次 —— 同一时刻 1 个 → 26 个。

实验记录见 [05 §11](docs/05-免拆机方案.md)。要点：

- 路径 A 走 `crt` → **不可行**（`crt` 非空时设备整包拒绝）
- 方案 2 走 `domain` → **不可行**（03:27 真机：`domain` 给了**公网 IP**，
  设备仍去查内置的 `iot.ixiaocong.com`；`conntrack` 显示它连的是劫持 IP，不是我们给的）
- 探针选值有坑：不能拿 `allin.dns.army` 当探针 —— 它**本来就在劫持名单里**，
  "认了 domain" 和 "命中劫持" 会落到同一个 IP，实验无法判决

⇒ **路径 E（DNS 劫持，§12 已落地）是当前唯一可行路线。**

详见 [07-真机云端协议](docs/07-真机云端协议.md) §4 / §6。

---

## 目录

```
dex/                    4 个脱壳 dex
  9763076 = 主 App 业务代码      3073100 = 厂商 SDK（协议层）
  1799620 = Chromium WebView     13313100 = 乐固壳
decompiled/             jadx 反编译产物（sdk_3073100/ + main_9763076/）
apk_extract/            原始 APK 解包（lib/armeabi-v7a/*.so、assets/test.crt）
apk_decompiled/         APK 资源反编译（含 AndroidManifest.xml）
patched/                已打补丁的 AndroidManifest.xml（application→XcApplication）
docs/                   分析文档（00~11）
  10-控制方式与OTA全景  ★ 远程/局域网控制的所有路 + OTA 可能性穷举 + 多插座串台问题
  11-归档与清理清单    ★ 迁移必带清单 / 可删清单 / 已过时的结论（认知层面清理）
tools/                  自研工具
  patch_axml.js         AXML 外科补丁
  build_apk.sh          重建 APK 脚本
  sign_apk.sh           重打包 + v1 签名 + 校验
  ghidra_dump.java      Ghidra 批量导出 C 伪代码
  ghidra_disasm.java    反汇编 + 解引用字面量池指针（定位 AES key/iv 靠它）
  ghidra_strings.java   提取函数引用的字符串
  probe_server.js       线上服务器探测 / 热更新拉取
  ── 真机配网与探测 ──
  provision.js          ★ 自写 SoftAP 配网客户端（Node 零依赖，secp256k1 + AES-128-CBC）
                          退出码：0 接受 / 2 超时 / 3 设备 NACK（crt 被拒）
  live-provision.ps1    ★ 一键真机配网（扫描→连热点→配网→回连家庭 WiFi）
  experiment-official.ps1 ★ 出厂默认配网 + ARP 旁路采集（不改插座配置，看它连哪）
  lan-mitm.py           ★ 二层旁路采集/劫持（scapy+Npcap，**不需要管理员**）
                          watch 纯观察 / spoof ARP 欺骗 / dns 伪造应答 / --restore 恢复
  fake-cloud.py         ★★ 冒充官方云端：用户态 TCP + TLS1.1 + MQTT，**可下发控制**
                          --control "8:switch=1" 定时控制 / --cmd-file 运行时注入
  mqtt-decode.py        ★ 把 fake-cloud.jsonl 的完整 hex 解成可读 MQTT 报文（防日志截断）
  scan-lan.py           ★ 主动 ARP 扫描找插座（`arp -a` 有盲区，靠它才找得到）
  experiment-crt.ps1    ★ crt 接受边界诊断（9 个变体按长度升序，命中即停；支持 -Only N / -List）
  experiment-domain.ps1 domain/crt 隔离实验（⚠️ 分组 A 成功后设备退出配网，B–G 无效）
  experiment-domain2.ps1 ★★ 方案2 修正版：用**公网域名/IP**重测 domain 参数（旧实验用了私有 IP，结论无效）
                          自己等 smart-* 热点 → 配网 → 回连 → 观察 → 打印判决
  dns-since.py          ★ 统计 fake-cloud.jsonl 里某时刻之后的 DNS 查询名（方案2 的判别工具）
  ── 端口/能力探测 ──
  udp-port-verify.py    ★★ 严格 UDP 端口开放判定（N 次重试 + 阳性对照端口，避开 ICMP 丢包误判）
  passive-udp-watch.py  ★ 被动 UDP 监听（看插座到底用不用 5683 之类）
  lan-port-watch.py     ★ 长期哨兵：低速率盯 13078/5683 开关状态，只在变化时落盘
  ── OTA 盲探 ──
  gen-ota-probes2.py    生成 OTA 盲探探针（26 键 × 5 形状 + manage type + 顶层多主题）
  ota-inject.py         ★ 逐条注入 control.cmd 并盯 fake-cloud.jsonl 是否出现新 DNS/新连接/HTTP
                          **自带危险键拦截**（switch/repower/countdown/reboot/reset），要发得加 --allow-danger
  watch-plug.ps1        ★ 守望进程：轮询插座是否回到配网模式/局域网，可选 -AutoRun 自动开跑
  selftest-provision.js ★ 离线自检：用假设备模拟 H1/H2，验证 0/2/3 退出码判定（6/6 通过）
  wifi-scan.ps1         ★★ 强制 WiFi 扫描（netsh 读的是缓存，必须用 WlanScan 触发）
  watch-and-provision.ps1  ★★ 守望 smart-* 热点 → 连 → 配网 → 回连家庭网（全自动）
  wlanapi-scan.ps1      wlanapi 枚举网络（老工具，wifi-scan.ps1 更直接）
  diag-wlan.ps1         wlanapi 诊断（位置服务 / 扫描权限 / 全量网络）
  wifi.ps1              netsh 封装（scan / find-plug / connect / status）
                        ⚠️ 走 netsh 的部分会读缓存，扫新热点请用 wifi-scan.ps1
  fake-plug-softap.js   模拟插座（离线自测配网客户端；CRT_MAX/CRT_EMPTY_ONLY 可模拟固件拒绝）
  port-watch.js         多端口 TCP 监听网（看设备被重定向后连向哪个端口）
  coap-scan.js          局域网 CoAP 设备发现
  coap-probe.js         CoAP 深度探测（多 payload × 广播/单播 + 监听 13078）
  tcp-scan.js           TCP 端口扫描
  udp-port-test.js      UDP 端口开放判定（ICMP 法；本机 ICMP 被抑制时不可用）
  dex_verify.js / dex_parse.js / pdf_text.js / pdf_extract.js / ghidra_dump.py（旧/弃用）
ghidra_out/             Ghidra 反编译产物（按库分目录）
  libsoftAp.so/         配网库：app_step0/1/2、uECC、AES、JNI…
  libxcsdk.so/          SDK：CoAP、cmdExec、xconfig、device_aes…
server/                 自建服务端（零依赖纯 Node + 手写 MQTT broker）
firmware/               替代固件（ESPHome/Tasmota/Blinker 修复版）
  ont-go/               ★★ 光猫常驻版（Go 单二进制：DNS 劫持 + TLS 假云端 + iptables）
    main.go mqtt.go dns.go rules.go    入口 / 假云端 / DNS 应答器 / 规则管理
    build.sh             编译 + UPX → ixc-go-arm64_upx
    ixc-ont.sh         ★★★ 接管脚本（install / uninstall / status / resync）
                           ★ 规则的唯一来源，头部注释写清了"对光猫做的全部改动"
                           ★ 主路径：劫持 nat OUTPUT 的 DNS 上游（不是按 MAC 劫持 PREROUTING）
                           ★ 不带 -manage-rules / -plug-mac，避免与程序里那套机制打架
                           ★ 守护兜底：ixc-go 一退出就自动撤规则，不会连累全家 DNS
                           ★ 不改任何开机脚本 —— 持久化由你自己加（路径是
                             /f4610u/user_init.sh，不是 /f4610u/etc/user_init.sh）
    deploy.py           ⚠️ 已被 ixc-ont.sh 取代（它往 user_init.sh 注入 Python 生成的段）
  bin/                   归档二进制：busybox-full_upx、fakecloud-arm64/exe、
                         ixc-ont-arm64（旧版）、bb-new-aarch64
  build_busybox_static.sh  ★ 交叉编译完整版 busybox（musl 静态，418 applet；已装到编译服务器）
                           末段自带 UPX：产出 busybox-full-aarch64{,_upx}
  busybox-full-aarch64     产物：busybox 1.36.1 静态版，1.27MB（UPX 后 0.57MB）
tools/ont/              ★ 光猫运维与诊断脚本（13 个 + README）
                          sshutil.py 的 run() 注释必看（输出静默截断的坑）
                          putfile.py = 唯一可靠的写文件方式（分块 heredoc，≤3000 字符）
                          mtchtest.py = 测 iptables match 内核侧可用性的正确方法
ont/                    光猫侧产物归档：snapshots/（iptables 改前/改后）、logs/、certs/
scratch/                144 个一次性诊断脚本与中间输出（原 .scratch/）
                          ★ 本次结论的原始证据，别删；有复用价值的已提升到 tools/ont/
xiaocong.apk            原始 APK（未脱壳，v2.2.1）
wukongcong.ino          社区 Blinker 固件（无法编译，修复版见 firmware/blinker/）
```

---

## 环境备忘

| 项 | 状态 |
|---|---|
| Java | ⚠️ 系统 PATH 里没有 JDK；`jadx-gui-1.5.6-with-jre-win/jre/`（JRE 25，含 keytool，**无 jarsigner**） |
| jadx | ✅ `jadx-gui-1.5.6-with-jre-win/lib/jadx-gui-1.5.6-all.jar`，CLI: `java -cp <jar> jadx.cli.JadxCLI` |
| **Ghidra** | ✅ `ghidra_12.1.4_PUBLIC/`，需 **完整 JDK 21** |
| **JDK** | ✅ `C:/Users/caojiecn/Downloads/jdk-21.0.12.1+1`（Microsoft OpenJDK 21.0.12.1）—— jadx 自带的 JRE 25 是裁剪版，缺 `java.rmi`，跑不了 Ghidra；**签名也用它**（含 `keytool` + `jarsigner`） |
| **Android build-tools** | ❌ 无 `apksigner` / `zipalign`；只有 v1 签名 |
| openssl | ✅ `/mingw64/bin/openssl` 3.5.4（PKCS7 签名可用） |
| 7z / node / git | ✅ 可用 |
| 网络 | ⚠️ GitHub 主页/API/codeload 通；**releases 下载、objects.githubusercontent.com、npm、maven 全部超时** |
| 反汇编 | ⚠️ 本机 mingw objdump **不含 ARM 后端** → 用 Ghidra |

**注意**：Git Bash 下 `/tmp` 会被解析成 `C:\tmp`，用相对路径或 `%TEMP%`。

## 安全/伦理提醒

本项目针对**已停产、厂商倒闭**的设备做个人修复，属于合理的逆向与保有权
（right to repair / preservation）。注意：

- 自建服务端的代码默认**不校验签名、接受任意账号**，仅供内网，**勿暴露公网**
- OTA 偷渡固件若用于他人设备可能有法律风险，仅限自己的设备
