package com.facebook.react.modules.core;

import android.net.Uri;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceEventManagerModule extends ReactContextBaseJavaModule {
    private final Runnable mInvokeDefaultBackPressRunnable;

    public interface RCTDeviceEventEmitter extends JavaScriptModule {
        void emit(String str, Object obj);
    }

    public DeviceEventManagerModule(ReactApplicationContext reactContext, final DefaultHardwareBackBtnHandler backBtnHandler) {
        super(reactContext);
        this.mInvokeDefaultBackPressRunnable = new Runnable() { // from class: com.facebook.react.modules.core.DeviceEventManagerModule.1
            @Override // java.lang.Runnable
            public void run() {
                UiThreadUtil.assertOnUiThread();
                backBtnHandler.invokeDefaultOnBackPressed();
            }
        };
    }

    public void emitHardwareBackPressed() {
        ((RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(RCTDeviceEventEmitter.class)).emit("hardwareBackPress", null);
    }

    public void emitNewIntentReceived(Uri uri) {
        WritableMap map = Arguments.createMap();
        map.putString("url", uri.toString());
        ((RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(RCTDeviceEventEmitter.class)).emit("url", map);
    }

    @ReactMethod
    public void invokeDefaultBackPressHandler() {
        getReactApplicationContext().runOnUiQueueThread(this.mInvokeDefaultBackPressRunnable);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DeviceEventManager";
    }
}
