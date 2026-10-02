# 固件路线（推荐）

> 为什么不选自建服务端：见 [../docs/04-路线与建议.md](../docs/04-路线与建议.md)。
> 一句话——刷开源固件**与厂商云端死活完全无关**，一次搞定，且能接 Home Assistant。

---

## ⚠️ 先做这一步：备份原固件

**在刷任何东西之前**，务必读出原厂固件并存档。万一要回滚，或者以后想研究原厂协议，都靠它。

```bash
pip install esptool

# 1. 接线进入下载模式（见下）
# 2. 先探一下芯片和 flash 大小
esptool.py --port COM3 flash_id

# 3. 全片备份（把 0x100000 换成 flash_id 报的实际大小）
esptool.py --port COM3 read_flash 0x0 0x100000 backup-factory.bin

# 4. 校验
esptool.py --port COM3 verify_flash 0x0 backup-factory.bin
```

> **这一步还能顺带回答一个悬而未决的问题**：烧在固件里的云端地址是什么？
> 备份出来后 `strings backup-factory.bin | grep -iE "ixiaocong|gw\.|mqtt"` 就知道了。
> 这同时也补上了路线 A 唯一缺的那块拼图。

---

## 接线

ESP8266 模块（多半是 ESP-12F / ESP-01M）需要 USB-TTL（CH340/CP2102，几块钱）。

| ESP8266 | USB-TTL |
|---|---|
| 3V3 | 3.3V（**千万不要接 5V**） |
| GND | GND |
| TX | RX |
| RX | TX |
| GPIO0 | **接 GND 进下载模式**（刷完断开） |
| EN/CH_PD | 接 3.3V |
| RST | 刷完复位用 |

**建议**：先用探针/免焊接线夹，不要直接焊死；或引 4 根线出来。

> 插座是**非隔离电源**，市电侧带电。**务必拔掉电源、只从 USB-TTL 取电再操作**，
> 否则有触电和烧板风险。这一条最重要。

---

## 方案 A：ESPHome（推荐，接 Home Assistant）

文件：[xiaocong-plug.yaml](esphome/xiaocong-plug.yaml)

```bash
pip install esphome
# 建 secrets.yaml：
cat > firmware/esphome/secrets.yaml <<'EOF'
wifi_ssid: "你的WiFi"
wifi_password: "你的密码"
ap_password: "fallback1234"
api_key: "用 esphome 生成，或 openssl rand -base64 32"
ota_password: "ota12345"
EOF

esphome run firmware/esphome/xiaocong-plug.yaml
```

---

## 方案 B：Tasmota（不依赖服务器也能用）

1. 打开 https://tasmota.github.io/install/ （Web 刷机，或本地刷 `tasmota.bin`）
2. 刷入后连上 `tasmota-xxxx` 热点配置 WiFi
3. 在 **Configuration → Configure Template** 填入：

```json
{"NAME":"XiaoCong Plug","GPIO":[0,0,0,0,224,0,0,0,0,0,0,0,0,0],"FLAG":0,"BASE":18}
```

> 上面是**候选模板**：只把 **GPIO14 设为 Relay1(224)**，GPIO13 设为 Button1(32)，
> GPIO4 设为 Led1(320) 的组合。**引脚必须实测确认**，见下。

更稳妥的模板（含按键与指示灯）：

```json
{"NAME":"XiaoCong Plug","GPIO":[0,0,0,0,320,0,0,0,0,0,32,0,224,0],"FLAG":0,"BASE":18}
```

| GPIO | 功能 | Tasmota 编号 |
|---|---|---|
| GPIO4 | Led1（指示灯） | 320 |
| GPIO13 | Button1（按键） | 32 |
| GPIO14 | Relay1（继电器） | 224 |

---

## 方案 C：Blinker（仓库里那份 `.ino` 的思路，但已修复）

原 `wukongcong.ino` **无法编译**（`button1_callback`×3、`setup`×3、全局作用域的 `if`）。
修复版在 [blinker/xiaocong-plug.ino](blinker/xiaocong-plug.ino)，修复了：
- 去掉重复定义
- 把全局 `if` 移进 `loop()`
- 补 `WiFiManager` include
- 删掉未定义的 `BUILTIN_Button` / `miotQuery` / `aligenieQuery` / `duerPowerState`

> 需要 Blinker 账号，本质还是连第三方云。**不如 ESPHome/Tasmota 自主。**

---

## ⚠️ 引脚必须实测

`wukongcong.ino` 给的引脚（继电器 14 / 灯 4 / 按键 13）**来自社区，未经本项目验证**。
不同批次可能不同。实测方法：

1. 刷一个**通用探测固件**（或用 ESPHome 的 GPIO 测试）
2. 万用表/示波器量：
   - **继电器**：找到继电器线圈驱动三极管（8050/S8050）基极 → 对应的 GPIO
   - **按键**：按住按键，量哪个 GPIO 被拉低
   - **指示灯**：点亮时哪个 GPIO 有电平变化
3. 若反了（高电平触发 vs 低电平触发），改 `inverted: true/false`

> **刷错引脚的后果**：可能让继电器常吸合、指示灯长亮，一般不至于烧毁，但要重刷。

---

## Flash 大小

- 若 `esptool flash_id` 报 **1MB** → ESPHome 配置里用 `esp01_1m`，**去掉 web_server 等大组件**
- 若 **2MB/4MB** → 用 `esp_wroom_02` / `esp8266` 通用
- **ESPHome 固件通常 >1MB，1MB 的模块建议用 Tasmota（更小）**

---

## 刷完之后

- **本地直控**：按键切换、HA 里控制，都不需要网络
- **断电记忆**：`restore_from_flash: true`
- **重新配网**：长按 5 秒 → 恢复出厂
