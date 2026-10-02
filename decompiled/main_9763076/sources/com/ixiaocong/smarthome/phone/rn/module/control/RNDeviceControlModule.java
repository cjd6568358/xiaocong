package com.ixiaocong.smarthome.phone.rn.module.control;

import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNDeviceControlModule extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNDeviceControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DeviceControl";
    }

    @ReactMethod
    public void controlDevice(String deviceId, String contrlId, int status, String clientId) {
        try {
            XCDeviceController.getInstance().publishJsonObject(this.mContext, deviceId, contrlId, Integer.valueOf(status));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void controlStrDevice(String deviceId, String contrlId, String status, String clientId) {
        try {
            XCDeviceController.getInstance().publishJsonObject(this.mContext, deviceId, contrlId, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void commonControlDevice(String topic, String deviceId, ReadableMap readableMap) {
        try {
            ReadableNativeMap map2 = (ReadableNativeMap) readableMap;
            Map<String, Object> params = map2.toHashMap();
            if (params != null && params.size() > 0) {
                Map<String, Object> commandParams = new HashMap<>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() instanceof Double) {
                        commandParams.put(entry.getKey(), Integer.valueOf(new Double(((Double) entry.getValue()).doubleValue()).intValue()));
                    } else {
                        commandParams.put(entry.getKey(), entry.getValue());
                    }
                }
                XCDeviceController.getInstance().publishJsonObject(this.mContext, deviceId, commandParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
