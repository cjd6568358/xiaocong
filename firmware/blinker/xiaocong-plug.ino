/*
 * 小葱智能插座 · Blinker 固件（修复版）
 * ============================================================
 * 这是仓库里 wukongcong.ino 的「可编译」重写版。
 *
 * 原文件的问题（无法编译）：
 *   - button1_callback 定义了 3 次
 *   - setup() 定义了 3 次（且第二个还是嵌套在 if 块里）
 *   - 141~280 行是全局作用域的 if 语句（C++ 不合法）
 *   - miotPowerState / duerQuery / aligeniePowerState / heartbeat 嵌在 if 里定义
 *   - 用了 WiFiManager 却没有 #include
 *   - 引用了未定义的 BUILTIN_Button / miotQuery / aligenieQuery / duerPowerState
 *
 * 本版本：合并去重、结构归位、补全依赖、删掉不存在的符号。
 *
 * 依赖（Arduino IDE）：
 *   - esp8266 board package
 *   - Blinker 库
 *   - WiFiManager 库 (tzapu)
 *
 * ⚠️ 引脚来自社区线索，务必实测；见 firmware/README.md
 * ⚠️ 需要 Blinker 账号（本质仍是第三方云）。更自主的方案见 esphome/
 * ============================================================
 */

#define BLINKER_WIFI
#define BLINKER_MIOT_LIGHT
#define BLINKER_ALIGENIE_LIGHT
#define BLINKER_DUEROS_LIGHT
#define BLINKER_PRINT Serial

#include <Blinker.h>
#include <ESP8266WiFi.h>
#include <WiFiManager.h>

// ---------------- 用户配置 ----------------
char auth[] = "改这里";   // Blinker 设备 key
char ssid[] = "";         // 留空则用 WiFiManager 配网
char pswd[] = "";

// ---------------- 硬件引脚 ----------------
const int PIN_RELAY = 14;   // 继电器输出
const int PIN_LED   = 4;    // 指示灯（低电平点亮）
const int PIN_KEY   = 13;   // 物理按键（按下拉低）

// ---------------- Blinker 组件 ----------------
BlinkerButton Button1("btn-abc");
BlinkerNumber Number1("num-abc");

int counter = 0;

// ============ 状态同步 ============
// 把当前继电器状态刷给所有语音平台和 App
void syncState() {
  bool on = (digitalRead(PIN_RELAY) == HIGH);
  if (on) {
    Button1.print("on");
    Button1.color("#0000FF");
    Button1.text("开启");
    BlinkerMIOT.powerState("on");
    BlinkerMIOT.print();
    BlinkerAliGenie.powerState("on");
    BlinkerAliGenie.print();
    BlinkerDuerOS.powerState("on");
    BlinkerDuerOS.print();
  } else {
    Button1.print("off");
    Button1.color("#00FFFF");
    Button1.text("关闭");
    BlinkerMIOT.powerState("off");
    BlinkerMIOT.print();
    BlinkerAliGenie.powerState("off");
    BlinkerAliGenie.print();
    BlinkerDuerOS.powerState("off");
    BlinkerDuerOS.print();
  }
}

// 设置继电器（on=true 开）
void setRelay(bool on) {
  digitalWrite(PIN_RELAY, on ? HIGH : LOW);
  digitalWrite(PIN_LED, on ? LOW : HIGH);   // 开=灯灭
  syncState();
}

// ============ 回调 ============
// App 按键
void button1_callback(const String & state) {
  BLINKER_LOG("App 按键: ", state);
  setRelay(state == BLINKER_CMD_ON);
}

// 小爱
void miotPowerState(const String & state) {
  BLINKER_LOG("小爱: ", state);
  setRelay(state == BLINKER_CMD_ON);
}
void miotQuery(int32_t queryCode) {
  BlinkerMIOT.powerState(digitalRead(PIN_RELAY) == HIGH ? "on" : "off");
  BlinkerMIOT.print();
}

// 天猫精灵
void aligeniePowerState(const String & state) {
  BLINKER_LOG("天猫: ", state);
  setRelay(state == BLINKER_CMD_ON);
}
void aligenieQuery(int32_t queryCode) {
  BlinkerAliGenie.powerState(digitalRead(PIN_RELAY) == HIGH ? "on" : "off");
  BlinkerAliGenie.print();
}

// 小度
void duerPowerState(const String & state) {
  BLINKER_LOG("小度: ", state);
  setRelay(state == BLINKER_CMD_ON);
}
void duerQuery(int32_t queryCode) {
  BlinkerDuerOS.powerState(digitalRead(PIN_RELAY) == HIGH ? "on" : "off");
  BlinkerDuerOS.print();
}

// 心跳：App 定时拉状态
void heartbeat() {
  syncState();
}

// 自定义数据
void dataRead(const String & data) {
  BLINKER_LOG("readString: ", data);
  counter++;
  Number1.print(counter);
}

// ============ 配网（按键长按 5 秒）============
// 返回 true 表示刚完成一次配网请求，需要重启
bool handleResetKey() {
  static uint32_t pressStart = 0;
  if (digitalRead(PIN_KEY) == LOW) {
    if (pressStart == 0) pressStart = millis();
    if (millis() - pressStart >= 5000) {
      Serial.println("长按 5 秒，清除配网信息");
      for (int i = 0; i < 3; i++) {
        digitalWrite(PIN_LED, LOW);  Blinker.delay(200);
        digitalWrite(PIN_LED, HIGH); Blinker.delay(200);
      }
      WiFiManager wm;
      wm.resetSettings();
      Blinker.reset();
      delay(500);
      ESP.restart();
      return true;
    }
  } else {
    pressStart = 0;
  }
  return false;
}

// ============ 短按切换 ============
void handleToggleKey() {
  static uint32_t lastPress = 0;
  static bool wasDown = false;
  bool down = (digitalRead(PIN_KEY) == LOW);
  if (down && !wasDown && (millis() - lastPress > 400)) {
    lastPress = millis();
    setRelay(digitalRead(PIN_RELAY) != HIGH);
  }
  wasDown = down;
}

// ============ setup ============
void setup() {
  Serial.begin(115200);
  delay(100);
  BLINKER_DEBUG.stream(Serial);

  pinMode(PIN_RELAY, OUTPUT);
  digitalWrite(PIN_RELAY, LOW);    // 上电默认关
  pinMode(PIN_LED, OUTPUT);
  digitalWrite(PIN_LED, HIGH);     // 关=灯灭
  pinMode(PIN_KEY, INPUT_PULLUP);

  // 配网：优先用编译期写死的 ssid/pswd，否则 WiFiManager
  if (strlen(ssid) == 0) {
    WiFiManager wm;
    if (!wm.autoConnect("xiaocong-plug-setup", "password")) {
      Serial.println("配网失败，重启");
      ESP.restart();
    }
    Blinker.begin(auth, wm.getWiFiSSID().c_str(), wm.getWiFiPass().c_str());
  } else {
    WiFi.begin(ssid, pswd);
    Blinker.begin(auth, ssid, pswd);
  }

  // 注册回调
  Button1.attach(button1_callback);
  BlinkerMIOT.attachPowerState(miotPowerState);
  BlinkerMIOT.attachQuery(miotQuery);
  BlinkerAliGenie.attachPowerState(aligeniePowerState);
  BlinkerAliGenie.attachQuery(aligenieQuery);
  BlinkerDuerOS.attachPowerState(duerPowerState);
  BlinkerDuerOS.attachQuery(duerQuery);
  Blinker.attachHeartbeat(heartbeat);
  Blinker.attachData(dataRead);

  syncState();
}

// ============ loop ============
void loop() {
  Blinker.run();

  if (handleResetKey()) return;   // 已重启
  handleToggleKey();

  // 断网 90 秒自动重启
  static uint32_t offlineSince = 0;
  if (Blinker.connected()) {
    offlineSince = 0;
  } else {
    if (offlineSince == 0) offlineSince = millis();
    else if (millis() - offlineSince >= 90000) {
      Serial.println("断网 90s，重启");
      ESP.restart();
    }
  }
}
