package com.facebook.react.modules.appstate;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppStateModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    public static final String APP_STATE_ACTIVE = "active";
    public static final String APP_STATE_BACKGROUND = "background";
    protected static final String NAME = "AppState";
    private String mAppState;

    public AppStateModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mAppState = "uninitialized";
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @ReactMethod
    public void getCurrentAppState(Callback success, Callback error) {
        success.invoke(createAppStateEventMap());
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        this.mAppState = APP_STATE_ACTIVE;
        sendAppStateChangeEvent();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        this.mAppState = APP_STATE_BACKGROUND;
        sendAppStateChangeEvent();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
    }

    private WritableMap createAppStateEventMap() {
        WritableMap appState = Arguments.createMap();
        appState.putString("app_state", this.mAppState);
        return appState;
    }

    private void sendAppStateChangeEvent() {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("appStateDidChange", createAppStateEventMap());
    }
}
