package com.ixiaocong.smarthome.phone.rn.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNMessageModule extends ReactContextBaseJavaModule {
    public static final String MESSAGE_UPDATE_DEVICE_STATUS = "UPDATE_DEVICE_STATUS";
    public static final String NAME = "name";
    public static final String PARAMS = "params";
    private BroadcastReceiver mBroadcastReceiver;

    public RNMessageModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNMessageModule.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    XcLogger.i("RNMessageModule", "Receive:" + intent.getStringExtra("snapshotMsg"));
                    if (intent != null && intent.getAction() != null) {
                        if (intent.getAction().equals("ACTION_UPDATE_STATUS")) {
                            String deviceId = intent.getStringExtra("snapshotDeviceId");
                            String snapshot = intent.getStringExtra("snapshotMsg");
                            Object topic = intent.getStringExtra("mqttTopic");
                            if (!TextUtils.isEmpty(snapshot) && RNMessageModule.this.getReactApplicationContext() != null) {
                                ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNMessageModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(deviceId, snapshot);
                            }
                            if (!TextUtils.isEmpty(snapshot) && RNMessageModule.this.getReactApplicationContext() != null) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("topic", topic);
                                map.put("msg", snapshot);
                                map.put("senderId", deviceId);
                                ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNMessageModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("revMqttMsg", JSON.toJSONString(map));
                                return;
                            }
                            return;
                        }
                        if (intent.getAction().equals("renameParameterAction")) {
                            String deviceId2 = intent.getStringExtra(Constants.FLAG_DEVICE_ID);
                            String deviceName = intent.getStringExtra("deviceName");
                            if (!TextUtils.isEmpty(deviceName) && RNMessageModule.this.getReactApplicationContext() != null) {
                                ((DeviceEventManagerModule.RCTDeviceEventEmitter) RNMessageModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(deviceId2, deviceName);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if (reactContext != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("ACTION_UPDATE_STATUS");
            intentFilter.addAction("renameParameterAction");
            LocalBroadcastManager.getInstance(reactContext).registerReceiver(this.mBroadcastReceiver, new IntentFilter(intentFilter));
        }
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "MessageCenter";
    }

    public BroadcastReceiver getReceiver() {
        return this.mBroadcastReceiver;
    }
}
