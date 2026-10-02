package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class WritableNativeMap extends ReadableNativeMap implements WritableMap {
    private static native HybridData initHybrid();

    private native void mergeNativeMap(ReadableNativeMap readableNativeMap);

    private native void putNativeArray(String str, WritableNativeArray writableNativeArray);

    private native void putNativeMap(String str, WritableNativeMap writableNativeMap);

    @Override // com.facebook.react.bridge.WritableMap
    public native void putBoolean(String str, boolean z);

    @Override // com.facebook.react.bridge.WritableMap
    public native void putDouble(String str, double d);

    @Override // com.facebook.react.bridge.WritableMap
    public native void putInt(String str, int i);

    public native void putNull(String str);

    @Override // com.facebook.react.bridge.WritableMap
    public native void putString(String str, String str2);

    static {
        ReactBridge.staticInit();
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putMap(String key, WritableMap value) {
        Assertions.assertCondition(value == null || (value instanceof WritableNativeMap), "Illegal type provided");
        putNativeMap(key, (WritableNativeMap) value);
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putArray(String key, WritableArray value) {
        Assertions.assertCondition(value == null || (value instanceof WritableNativeArray), "Illegal type provided");
        putNativeArray(key, (WritableNativeArray) value);
    }

    public WritableNativeMap() {
        super(initHybrid());
    }
}
