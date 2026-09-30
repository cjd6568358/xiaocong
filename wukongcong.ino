#define BLINKER_WIFI                    //官方wifi协议库
#define BLINKER_MIOT_LIGHT              // 设置小爱灯类库
#define BLINKER_ALIGENIE_LIGHT          // 设置天猫灯类库
#define BLINKER_DUEROS_LIGHT            // 设置小度灯类库
#define BLINKER_PRINT Serial            //串口协议库
#include <Blinker.h>                    //官方库
#include <ESP8266WiFi.h>                //官方库
WiFiServer server(80);                  // 服务器端口号


char auth[] = "改这里";     //设备key
char ssid[] = "改这里";     //路由器wifi ssid
char pswd[] = "改这里";     //路由器wifi 密码

bool oState = false;

int kg = 14; //继电器输出
int de = 4; //灯输出
int key = 13 ; //按键
//*******新建组件对象

BlinkerButton Button1("btn-abc");              //设置app按键的键名
BlinkerNumber Number1("num-abc");

int counter = 0;

void button1_callback(const String & state)
{
  BLINKER_LOG("get button state: ", state);
  digitalWrite(LED_BUILTIN, !digitalRead(LED_BUILTIN));
}

void dataRead(const String & data)
{
  BLINKER_LOG("Blinker readString: ", data);
  counter++;
  Number1.print(counter);
}

void setup()
{
  Serial.begin(115200);
  BLINKER_DEBUG.stream(Serial);
  BLINKER_DEBUG.debugAll();

  WiFi.mode(WIFI_STA);
  WiFiManager wm;

  bool res;
  res = wm.autoConnect("AutoConnectAP", "password");
  if (!res) {
    Serial.println("Failed to connect");
    ESP.restart();
  }
  else {
    Serial.println("connected...yeey :)");

    Blinker.begin(auth, wm.getWiFiSSID().c_str(), wm.getWiFiPass().c_str());
    Blinker.attachData(dataRead);

    Button1.attach(button1_callback);
  }

  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
}

//*******app上按下按键即会执行该函数app里按键
void button1_callback(const String & state)
{
  if (digitalRead(kg) == LOW)
  {
    BLINKER_LOG("get button state: ", state);
    digitalWrite(kg, HIGH);
    digitalWrite(de, LOW);
    Button1.print("on");
    Button1.color("#0000FF");              //设置app按键是浅蓝色
    Button1.text("开启中");
  }
  else if (digitalRead(kg) == HIGH)
  {
    BLINKER_LOG("get button state: ", state);
    digitalWrite(kg, LOW);
    digitalWrite(de, HIGH);
    Button1.print("off");
    Button1.color("#00FFFF");              //设置app按键是深蓝色
    Button1.text("关闭中");
  }
}

//利用resetFunc（）内置函数，实现断网重启，定义相关变量
uint32_t con_time = 0;    //断网记时
int con_flag = 0;    //断网标记，1为断网
void(*resetFunc) (void) = 0;

//硬件重置WIFI配网信息
uint32_t rst_time = 0;    //记录RESET_IO低电平前系统时间


void button1_callback(const String & state)
{

  if (state == BLINKER_CMD_ON) {
    BLINKER_LOG("Toggle on!");
    digitalWrite(LED_BUILTIN, LOW);
    Button1.icon("fas fa-lightbulb-on");
    Button1.color("#FFFF00");
    Button1.text("开");
    Button1.print("on");
    BUILTIN_Button.print("on"); // Blinker主界面设备开关按钮状态
  }
  else if (state == BLINKER_CMD_OFF) {
    BLINKER_LOG("Toggle off!");
    digitalWrite(LED_BUILTIN, HIGH);
    Button1.icon("fas fa-lightbulb");
    Button1.color("#808080");
    Button1.text("关");
    Button1.print("off");
    BUILTIN_Button.print("off");
  }
}


void reset_callback(const String & state) {
  BLINKER_LOG("get button state:", state);
  //当长按"Reset"释放后清除配网信息
  if (state == "pressup") {
    for (int i = 0; i < 3 ; i++)
    {
      digitalWrite(LED_BUILTIN, LOW);
      Blinker.delay(300);
      digitalWrite(LED_BUILTIN, HIGH);
      Blinker.delay(300);
    }
    Blinker.reset();
  }
}


//断网自动重连程序
if (Blinker.connected())
{
  con_flag = 0;
}
else
{
  if (con_flag == 0)
  {
    con_time = millis();    //给断网时间赋初始值
    con_flag = 1;
  }
  else
  {
    if ((millis() - con_time) >= 90000)    //判断断网时间超90秒后执行重启，这个时间可根据实际需要调整
    {
      resetFunc();
    }
  }
}

//复位清除配网
if (digitalRead(Button1) == HIGH)
{
  rst_time = millis();    //刷新复位针脚复位之前的系统时间
}
if (digitalRead(Button1) == LOW)
{
  if ((millis() - rst_time) >= 3000) //复位按钮按下时长大于3秒，开始清除配网信息
  {
    //清除配网前LED灯闪烁
    for (int i = 0; i < 3 ; i++)
    {
      digitalWrite(LED_BUILTIN, LOW);
      Blinker.delay(300);
      digitalWrite(LED_BUILTIN, HIGH);
      Blinker.delay(300);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //*******如果小爱有对设备进行操作就执行下面
    void miotPowerState(const String & state)
    {
      BLINKER_LOG("小爱语音操作!");              //串口打印
      if (state == BLINKER_CMD_ON) {

        digitalWrite(kg, HIGH);
        digitalWrite(de, LOW);
        Button1.print("on");
        Button1.color("#0000FF");              //设置app按键是浅蓝色
        Button1.text("开启中");
        BlinkerMIOT.powerState("on");
        BlinkerMIOT.print();
      }
      else if (state == BLINKER_CMD_OFF)
      {

        digitalWrite(kg, LOW);
        digitalWrite(de, HIGH);
        Button1.print("off");
        Button1.color("#00FFFF");              //设置app按键是深蓝色
        Button1.text("关闭中");
        BlinkerMIOT.powerState("off");
        BlinkerMIOT.print();
      }


    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //*******如果小度有对设备进行操作就执行下面
    void duerQuery(int32_t queryCode)         //小度设备查询的回调函数
    {
      BLINKER_LOG("DuerOS Query codes: ", queryCode);

      switch (queryCode)
      {
        case BLINKER_CMD_QUERY_ALL_NUMBER :
          BLINKER_LOG("DuerOS Query All");
          BlinkerDuerOS.powerState(kg ? "on" : "off");
          BlinkerDuerOS.print();
          break;
        case BLINKER_CMD_QUERY_POWERSTATE_NUMBER :
          BLINKER_LOG("DuerOS Query Power State");
          BlinkerDuerOS.powerState(kg ? "on" : "off");
          BlinkerDuerOS.print();
          break;

        default :
          BlinkerDuerOS.powerState(kg ? "on" : "off");
          BlinkerDuerOS.print();
          break;
      }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //*******如果天猫精灵有对设备进行操作就执行下面
    void aligeniePowerState(const String & state)
    {
      BLINKER_LOG("need set power state: ", state);
      if (state == BLINKER_CMD_ON)
      {

        digitalWrite(kg, HIGH);
        digitalWrite(de, LOW);
        Button1.print("on");
        Button1.color("#0000FF");              //设置app按键是浅蓝色
        Button1.text("开启中");
        BlinkerAliGenie.powerState("on");
        BlinkerAliGenie.print();
      }
      else if (state == BLINKER_CMD_OFF)
      {

        digitalWrite(kg, LOW);
        digitalWrite(de, HIGH);
        Button1.print("off");
        Button1.color("#00FFFF");              //设置app按键是深蓝色
        Button1.text("关闭中");
        BlinkerAliGenie.powerState("off");
        BlinkerAliGenie.print();
      }


    }
    //*******app定时向设备发送心跳包, 设备收到心跳包后会返回设备当前状态30s~60s一次
    void heartbeat()
    {
      BLINKER_LOG("状态同步!");
      if (digitalRead(kg) == HIGH)
      {
        Button1.print("on");
        Button1.color("#0000FF");              //设置app按键是浅蓝色
        Button1.text("开启中");
      }
      else
      {
        Button1.print("off");
        Button1.color("#00FFFF");              //设置app按键是深蓝色
        Button1.text("关闭中");

      }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void setup()
    {
      // 初始化串口
      Serial.begin(115200);
      delay(10);
      BLINKER_DEBUG.stream(Serial);
      // 初始化有LED的IO
      pinMode(kg, OUTPUT);
      digitalWrite(kg, LOW);//初始化继电器上电状态
      pinMode(de, OUTPUT);
      digitalWrite(de, HIGH);
      pinMode(key, INPUT);
      Serial.println();
      Serial.print("Connecting to ");
      Serial.println(ssid);
      WiFi.begin(ssid, pswd);
      while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
      }
      Serial.println("");
      Serial.println("WiFi connected");
      server.begin();
      Serial.println("Server started @ ");
      Serial.println(WiFi.localIP());
      //打印出IP地址，后期可以制作显示器来外部硬件显示ip
      Serial.println("To control GPIO, open your web browser.");
      Serial.println("To set GPIO 0 high, type:");
      Serial.print(WiFi.localIP());
      Serial.println("/gpio/1");
      Serial.println("To set GPIO 0 low, type:");
      Serial.print(WiFi.localIP());
      Serial.println("/gpio/0");
      Serial.println("To toggle GPIO 0, type:");
      Serial.print(WiFi.localIP());
      Serial.println("/gpio/4");

      // 初始化blinker
      Blinker.begin(auth, ssid, pswd);
      BlinkerMIOT.attachPowerState(miotPowerState);              //小爱语音操作注册函数
      BlinkerAliGenie.attachPowerState(aligeniePowerState);      //天猫语音操作注册函数
      BlinkerDuerOS.attachPowerState(duerPowerState);            //小度语音操作注册函数
      BlinkerMIOT.attachPowerState(aligeniePowerState);         //小爱电源类操作的回调函数
      BlinkerDuerOS.attachPowerState(aligeniePowerState);       //小度电源类操作的回调函数
      BlinkerAliGenie.attachPowerState(aligeniePowerState);    //天猫电源类操作的回调函数
      Blinker.attachHeartbeat(heartbeat);              //app定时向设备发送心跳包, 设备收到心跳包后会返回设备当前状态进行语音操作和app操作同步。
      BlinkerMIOT.attachQuery(miotQuery);//小爱设备查询的回调函数
      BlinkerDuerOS.attachQuery(duerQuery);//小度设备查询的回调函数
      BlinkerAliGenie.attachQuery(aligenieQuery);//天猫设备查询的回调函数
      BUILTIN_SWITCH.attach(button1_callback);   //注册设备主界面开关按钮回调函数
      RESET.attach(reset_callback);              //注册RESET回调函数

      Button1.attach(button1_callback);             //app上操作必须的注册回调函数关联按键名“Button1”和判断程序“button1_callback
    }
    void dataRead(const String & data)
    {
      BLINKER_LOG("Blinker readString: ", data);

      Blinker.vibrate();

      uint32_t BlinkerTime = millis();

      Blinker.print("millis", BlinkerTime);
    }
    void loop()
    {
      Blinker.run();
      if (digitalRead(key) == LOW)
      {
        Blinker.delay(200);
        if (digitalRead(key) == LOW)
        {
          if (digitalRead(kg) == LOW)
          {

            digitalWrite(kg, HIGH);
            digitalWrite(de, LOW);
            Button1.print("on");
            Button1.color("#0000FF");              //设置app按键是浅蓝色
            Button1.text("开启中");
          }
          else if (digitalRead(kg) == HIGH)
          {
            digitalWrite(kg, LOW);
            digitalWrite(de, HIGH);
            Button1.print("off");
            Button1.color("#00FFFF");              //设置app按键是深蓝色
            Button1.text("关闭中");
          }
        }
      }
    }
