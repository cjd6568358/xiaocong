package com.xiaocong.smarthome.sdk.mqtt.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.helper.PublishMsgManager;
import com.xiaocong.smarthome.sdk.mqtt.helper.XCMqttOberserverManager;
import com.xiaocong.smarthome.sdk.mqtt.model.XCSnapshotMessage;
import com.xiaocong.smarthome.sdk.mqtt.utils.SSLSocketFactoryUtils;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.constant.XCConfig;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.uilib.widget.XCToastUtil;
import com.xiaocong.smarthome.util.log.XCLog;
import com.xiaocong.smarthome.xcnetwork.dns.XCDns;
import com.xiaocong.smarthome.xcnetwork.utils.SPUtil;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCMqttService extends Service implements IMqttActionListener, MqttCallbackExtended {
    private static MqttAndroidClient mClient;
    private static MqttMessage mMessage;
    private static MqttConnectOptions mOpts;
    private static String mReceiverDeviceId;
    private Intent devStatusIntent;
    private boolean isConnectting = false;
    private boolean isStopping = false;
    private AlarmManager mAlarmManager;
    private Handler mConnHandler;
    private JSONObject mJsonMsg;
    private static volatile boolean mStarted = false;
    private static volatile boolean mCountDown = false;
    private static int mMqttstatus = -1;

    public static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, (Class<?>) XCMqttService.class);
        i.setAction("MqttService.START");
        ctx.startService(i);
    }

    public static void actionReconnect(Context ctx) {
        Intent i = new Intent(ctx, (Class<?>) XCMqttService.class);
        i.setAction("MqttService.RECONNECT");
        ctx.startService(i);
    }

    public static void actionStop(Context ctx) {
        Intent i = new Intent(ctx, (Class<?>) XCMqttService.class);
        i.setAction("MqttService.STOP");
        ctx.startService(i);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.devStatusIntent = new Intent("ACTION_UPDATE_STATUS");
        this.mConnHandler = new Handler(getMainLooper());
        this.mAlarmManager = (AlarmManager) getSystemService("alarm");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String action = intent.getAction();
        XCLog.e("MqttService", "Received action of " + action);
        if (action == null) {
            XCLog.e("MqttService", "Starting service with no action\n Probably from a crash");
            return 3;
        }
        if (action.equals("MqttService.START")) {
            XCLog.i("MqttService", "Received ACTION_START");
            start();
            return 3;
        }
        if (action.equals("MqttService.STOP")) {
            XCLog.i("MqttService", "Received ACTION_STOP");
            stop();
            return 3;
        }
        if (action.equals("MqttService.RECONNECT")) {
            reconnectIfNecessary();
            XCLog.i("MqttService", "Received ACTION_RECONNECT");
            return 3;
        }
        if (action.equals("MqttService.KEEPALIVE")) {
            reconnectIfNecessary();
            XCLog.i("MqttService", "Received ACTION_KEEPALIVE");
            return 3;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void start() {
        this.isStopping = false;
        if (mStarted) {
            XCLog.e("MqttService", "Attempt to start while already started");
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            XCLog.e("MqttService", "isNetworkAvailable false");
        } else {
            if (hasScheduledKeepAlives()) {
                stopKeepAlives();
            }
            connect();
            XCLog.i("MqttService", "mqtt start============");
        }
    }

    private synchronized void stop() {
        this.isStopping = true;
        if (!mStarted) {
            XCLog.e("MqttService", "Attemtpign to stop connection that isn't running");
        } else {
            XCLog.e("MqttService", "Attemtpign to stop");
            if (mClient != null) {
                this.mConnHandler.post(new Runnable() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (XCMqttService.isConnected()) {
                                IMqttToken disToken = XCMqttService.mClient.disconnect();
                                XCMqttService.mClient.setCallback(XCMqttService.this);
                                disToken.setActionCallback(new IMqttActionListener() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.1.1
                                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                                    public void onSuccess(IMqttToken iMqttToken) {
                                        XCLog.e("MqttService", "disconnect Success");
                                    }

                                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                                    }
                                });
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        MqttAndroidClient unused = XCMqttService.mClient = null;
                        boolean unused2 = XCMqttService.mStarted = false;
                        XCMqttService.this.changeStatus(3);
                    }
                });
                XCLog.e("MqttService", "mqtt stop");
            }
            stopSelf();
            stopKeepAlives();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    private synchronized void connect() {
        this.mConnHandler.post(new Runnable() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    XCMqttService.this.changeStatus(0);
                    if (XCMqttService.mClient == null) {
                        if (!TextUtils.isEmpty(XCHelp.mLive) && !TextUtils.isEmpty(XCHelp.mClientId)) {
                            XCLog.e("MqttService", "mqtt live--" + XCHelp.mLive);
                            String live = XCHelp.mLive;
                            String host = live.substring(0, live.lastIndexOf(":"));
                            String port = live.substring(live.lastIndexOf(":") + 1);
                            XCDns.getInstance().addPreResolveHosts(host);
                            String ip = XCDns.getInstance().getIp("mqtt_dns_ip", host);
                            if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
                                live = ip + ":" + port;
                            }
                            XCLog.e("MqttService", "mqtt live ip:" + live);
                            MemoryPersistence persistence = new MemoryPersistence();
                            MqttAndroidClient unused = XCMqttService.mClient = new MqttAndroidClient(XCMqttService.this.getApplicationContext(), "ssl://" + live, XCHelp.mClientId, persistence);
                            MqttMessage unused2 = XCMqttService.mMessage = new MqttMessage();
                        } else {
                            return;
                        }
                    }
                    String userName = XCConfig.getInstance().getAppId();
                    XCLog.e("MqttService", "connect,userName--" + userName);
                    XCMqttService.this.setKeyStore(userName, XCConfig.getInstance().getToken());
                    XCMqttService.mClient.setCallback(XCMqttService.this);
                    IMqttToken iMqttToken = XCMqttService.mClient.connect(XCMqttService.mOpts);
                    iMqttToken.setActionCallback(XCMqttService.this);
                    XCLog.e("MqttService", "Successfully connected and subscribed starting keep alives..." + XCMqttService.isConnected());
                } catch (Exception e) {
                    XCLog.e("MqttService", "connectStartError=" + e.toString());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setKeyStore(String userName, String psw) {
        XCLog.i("MqttService--setKeyStore--", userName + "---" + psw);
        mOpts = new MqttConnectOptions();
        mOpts.setUserName(userName);
        mOpts.setPassword(psw.toCharArray());
        mOpts.setCleanSession(false);
        mOpts.setConnectionTimeout(10);
        mOpts.setAutomaticReconnect(false);
        mOpts.setKeepAliveInterval(30);
        mOpts.setMqttVersion(4);
        try {
            SSLSocketFactory sslSocktet = SSLSocketFactoryUtils.initSSLSocket(getResources().getAssets().open("test.crt"));
            mOpts.setSocketFactory(sslSocktet);
            return true;
        } catch (Exception e) {
            XCLog.e("MqttService", "connectStartError---请检查证书是否存在");
            return false;
        }
    }

    private synchronized void reconnectIfNecessary() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            XCLog.e("MqttService", "reconnectIfNecessary network is not available");
        } else if (mClient == null) {
            start();
            XCLog.e("MqttService", "reconnectIfNecessary start");
        } else if (!isConnected()) {
            reconnection();
            XCLog.e("MqttService", "reconnectIfNecessary reconnect - " + this.isConnectting);
        } else {
            XCLog.e("MqttService", "reconnectIfNecessary do not reconnect check it");
        }
    }

    public static boolean isConnected() {
        if (mClient != null) {
            XCLog.e("MqttService", "isConnected========" + mClient.isConnected());
            return mClient.isConnected();
        }
        XCLog.e("MqttService", "isConnected====null====false");
        return false;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void connectionLost(Throwable throwable) {
        try {
            changeStatus(3);
            mStarted = false;
            XCLog.e("MqttService", "connectionLost-----------" + mOpts.getUserName());
            this.devStatusIntent.putExtra("paho_mqtt_isconnect", false);
            LocalBroadcastManager.getInstance(this).sendBroadcast(this.devStatusIntent);
            PublishMsgManager.countDownFinish();
            if (!this.isStopping) {
                reconnectIfNecessary();
            }
        } catch (Exception e) {
        }
    }

    private void reconnection() {
        if (!this.isConnectting) {
            new Thread(new Runnable() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.3
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (XCMqttService.class) {
                        if (XCMqttService.mClient == null) {
                            XCMqttService.this.start();
                            XCLog.e("MqttService", "reconnection--start()");
                        } else {
                            while (!XCMqttService.isConnected()) {
                                XCLog.e("MqttService", "reconnection start");
                                try {
                                    XCMqttService.this.isConnectting = true;
                                    if (NetworkUtils.isNetworkAvailable(XCMqttService.this)) {
                                        XCMqttService.this.mConnHandler.post(new Runnable() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.3.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                XCMqttService.this.changeStatus(0);
                                            }
                                        });
                                        String userName = XCConfig.getInstance().getAppId();
                                        XCLog.e("MqttService", "reconnection,userName--" + userName);
                                        XCMqttService.this.setKeyStore(userName, XCConfig.getInstance().getToken());
                                        XCLog.e("MqttService", "reconnection--" + XCMqttService.mOpts.getUserName());
                                        if (XCMqttService.mClient != null) {
                                            XCMqttService.mClient.setCallback(XCMqttService.this);
                                            IMqttToken iMqttToken = XCMqttService.mClient.connect(XCMqttService.mOpts);
                                            iMqttToken.setActionCallback(XCMqttService.this);
                                        }
                                        try {
                                            Thread.sleep(5000L);
                                            if (XCMqttService.isConnected()) {
                                                XCMqttService.this.isConnectting = false;
                                            }
                                        } catch (InterruptedException e) {
                                            if (XCMqttService.isConnected()) {
                                                XCMqttService.this.isConnectting = false;
                                            }
                                            XCLog.e("MqttService", "reconnection -- InterruptedException " + e.toString());
                                        }
                                    } else {
                                        XCLog.e("MqttService", "reconnection network is not available");
                                        XCMqttService.this.isConnectting = false;
                                        break;
                                    }
                                } catch (Exception e2) {
                                    XCLog.e("MqttService", "reconnection -- Exception " + e2.toString());
                                }
                                throw th;
                            }
                        }
                    }
                }
            }).start();
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        XCLog.i("MqttService", topic + "---Topic:\t" + mqttMessage.getId() + "  Message:\t" + new String(mqttMessage.getPayload()) + "  QoS:\t" + mqttMessage.getQos());
        setMessageArrivedListener(topic, mqttMessage);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            XCLog.i("MqttService", "deliveryComplete=" + iMqttDeliveryToken.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if (!mCountDown) {
            PublishMsgManager.countDownStart(this);
            XCLog.i("MqttService", "countDownStart");
        }
    }

    public static void publishMsg(Context context, String topic, String msg, String deviceId) {
        if (topic.equals("manage")) {
            mCountDown = true;
        } else {
            if (TextUtils.isEmpty(deviceId)) {
                mReceiverDeviceId = null;
            } else {
                mReceiverDeviceId = deviceId;
            }
            mCountDown = false;
        }
        try {
            mMessage.setQos(0);
            mMessage.setRetained(false);
            mMessage.setPayload(msg.getBytes());
            mClient.publish(topic, mMessage);
        } catch (Exception e) {
            XCLog.i("MqttService", "publishError=" + e.toString());
        }
    }

    public void setMessageArrivedListener(final String topic, MqttMessage mqttMessage) {
        final String msg = mqttMessage.toString();
        try {
            if (!TextUtils.isEmpty(msg)) {
                this.mJsonMsg = new JSONObject(msg);
                this.mConnHandler.post(new Runnable() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.4
                    /* JADX WARN: switch over string: strings are not added: [[ack]] */
                    @Override // java.lang.Runnable
                    public void run() {
                        XCMqttService.this.changeStatus(1);
                        switch (topic) {
                            case "error":
                                PublishMsgManager.countDownFinish();
                                XCToastUtil.showToast(XCMqttService.this, XCMqttService.this.mJsonMsg.optString("msg"), 0);
                                XCMqttService.this.devStatusIntent.putExtra("snapshotDeviceId", XCMqttService.this.mJsonMsg.optString("receiveId"));
                                XCMqttService.this.devStatusIntent.putExtra("paho_mqtt_isconnect", true);
                                LocalBroadcastManager.getInstance(XCMqttService.this).sendBroadcast(XCMqttService.this.devStatusIntent);
                                break;
                            case "manage":
                                Intent mangeIntent = new Intent("device.append.zigbee.action");
                                mangeIntent.putExtra("manageMsg", msg);
                                LocalBroadcastManager.getInstance(XCMqttService.this).sendBroadcast(mangeIntent);
                                break;
                            case "control":
                            case "snapshot":
                                XCSnapshotMessage snapshotMessage = (XCSnapshotMessage) JSON.parseObject(msg, XCSnapshotMessage.class);
                                if (XCMqttService.mReceiverDeviceId != null && XCMqttService.mReceiverDeviceId.equals(snapshotMessage.getSenderId())) {
                                    PublishMsgManager.countDownFinish();
                                }
                                XCMqttOberserverManager.getDefault().post(topic, msg);
                                if (XCMqttService.this.mJsonMsg.optInt("code") != 0) {
                                    XCToastUtil.showToast(XCMqttService.this, XCMqttService.this.mJsonMsg.optString("msg"), 0);
                                    if (!TextUtils.isEmpty(snapshotMessage.getReceiveId())) {
                                        XCMqttService.this.devStatusIntent.putExtra("snapshotDeviceId", snapshotMessage.getReceiveId());
                                    }
                                } else if (!TextUtils.isEmpty(msg) && !TextUtils.isEmpty(snapshotMessage.getSenderId()) && snapshotMessage.getSnapshot() != null) {
                                    XCLog.i("MqttService", snapshotMessage.getStatus() + "-SenderId- " + snapshotMessage.getSenderId() + "-Snapshot-" + snapshotMessage.getSnapshot() + "-MessageId-" + snapshotMessage.getMessageId());
                                    XCMqttService.this.devStatusIntent.putExtra("snapshotDeviceId", snapshotMessage.getSenderId());
                                    XCMqttService.this.devStatusIntent.putExtra("snapshotMsg", JSON.toJSONString(snapshotMessage.getSnapshot()));
                                    XCMqttService.this.devStatusIntent.putExtra("snapshotOnlineStatus", snapshotMessage.getStatus());
                                    XCMqttService.this.devStatusIntent.putExtra("mqttTopic", topic);
                                }
                                XCMqttService.this.devStatusIntent.putExtra("paho_mqtt_isconnect", true);
                                LocalBroadcastManager.getInstance(XCMqttService.this).sendBroadcast(XCMqttService.this.devStatusIntent);
                                break;
                            case "offline":
                                XCMqttService.this.devStatusIntent.putExtra("snapshotDeviceId", XCMqttService.this.mJsonMsg.optString("senderId"));
                                XCMqttService.this.devStatusIntent.putExtra("snapshotOnlineStatus", 0);
                                XCMqttService.this.devStatusIntent.putExtra("paho_mqtt_isconnect", true);
                                XCMqttService.this.devStatusIntent.putExtra("mqttTopic", topic);
                                LocalBroadcastManager.getInstance(XCMqttService.this).sendBroadcast(XCMqttService.this.devStatusIntent);
                                break;
                            case "online":
                                XCMqttService.this.devStatusIntent.putExtra("snapshotDeviceId", XCMqttService.this.mJsonMsg.optString("senderId"));
                                XCMqttService.this.devStatusIntent.putExtra("snapshotOnlineStatus", 1);
                                XCMqttService.this.devStatusIntent.putExtra("paho_mqtt_isconnect", true);
                                XCMqttService.this.devStatusIntent.putExtra("mqttTopic", topic);
                                LocalBroadcastManager.getInstance(XCMqttService.this).sendBroadcast(XCMqttService.this.devStatusIntent);
                                break;
                        }
                    }
                });
            }
        } catch (JSONException e) {
            XCLog.i("MqttService", "setMessageArrivedListener=" + e.toString());
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
    public void connectComplete(boolean reconnect, String serverURI) {
        XCLog.i("MqttService", "mqtt connection successfull---" + reconnect + "----" + serverURI + "----" + mOpts.getUserName() + "---" + isConnected());
        if (mClient != null) {
            mClient.registerResources(this);
        }
        this.devStatusIntent.putExtra("paho_mqtt_isconnect", isConnected());
        LocalBroadcastManager.getInstance(this).sendBroadcast(this.devStatusIntent);
        subscribeAllTopic();
        mStarted = true;
        startKeepAlives();
        changeStatus(1);
    }

    public void subscribeAllTopic() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("device/subscribe/all");
        XCRequest.getInstance().request(getApplicationContext(), httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService.5
            @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback
            public void onComplete(XCResponseBean var1) {
                XCLog.i("MqttService", "subscribeAllTopic successfull");
            }

            @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCErrorCallback
            public void onError(XCErrorMessage var1) {
                XCLog.i("MqttService", "subscribeAllTopic failure");
            }
        });
    }

    private void startKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, XCMqttService.class);
        i.setAction("MqttService.KEEPALIVE");
        XCLog.i("MqttService", "startKeepAlives");
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        this.mAlarmManager.setRepeating(0, System.currentTimeMillis() + 100000, 100000L, pi);
    }

    private void stopKeepAlives() {
        Intent i = new Intent();
        XCLog.i("MqttService", "stopKeepAlives");
        i.setClass(this, XCMqttService.class);
        i.setAction("MqttService.KEEPALIVE");
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        this.mAlarmManager.cancel(pi);
    }

    private synchronized boolean hasScheduledKeepAlives() {
        boolean z;
        synchronized (this) {
            Intent i = new Intent();
            i.setClass(this, XCMqttService.class);
            i.setAction("MqttService.KEEPALIVE");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 536870912);
            XCLog.i("MqttService", "hasScheduledKeepAlives" + (pi != null));
            z = pi != null;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeStatus(int status) {
        XCMqttOberserverManager.getDefault().status(status);
        mMqttstatus = status;
        XCLog.i("DEBUG_TAG", "XCDeviceControllerStatus" + status);
    }

    public static int getMqttStatus() {
        return mMqttstatus;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken asyncActionToken) {
        try {
            if (mClient != null) {
                DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                disconnectedBufferOptions.setBufferEnabled(true);
                disconnectedBufferOptions.setBufferSize(100);
                disconnectedBufferOptions.setPersistBuffer(false);
                disconnectedBufferOptions.setDeleteOldestMessages(false);
                mClient.setBufferOpts(disconnectedBufferOptions);
                changeStatus(1);
                Log.i("MqttService", "disconnectedBufferOptions---onSuccess--");
            }
        } catch (Exception e) {
            XCLog.e("MqttService", e.toString());
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        if (asyncActionToken.getException() != null) {
            Log.e("MqttService", "disconnectedBufferOptions---onFailure--" + asyncActionToken.getException().getReasonCode() + "---" + exception.toString());
            int reasonCode = asyncActionToken.getException().getReasonCode();
            if (reasonCode == 0) {
                changeStatus(3);
                SPUtil.getInstance(getApplicationContext()).removeSP("mqtt_dns_ip");
                if (mClient != null) {
                    try {
                        mClient.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mClient = null;
                }
                this.devStatusIntent.putExtra("paho_mqtt_isconnect", false);
            } else if (reasonCode == 32100) {
                this.devStatusIntent.putExtra("paho_mqtt_isconnect", true);
            } else if (reasonCode != 3) {
                changeStatus(3);
                reconnectIfNecessary();
                this.devStatusIntent.putExtra("paho_mqtt_isconnect", false);
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(this.devStatusIntent);
        }
    }
}
