package com.facebook.react.uimanager.events;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RCTEventEmitter extends JavaScriptModule {
    void receiveEvent(int i, String str, WritableMap writableMap);

    void receiveTouches(String str, WritableArray writableArray, WritableArray writableArray2);
}
