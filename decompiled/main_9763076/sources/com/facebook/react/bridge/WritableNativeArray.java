package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class WritableNativeArray extends ReadableNativeArray implements WritableArray {
    private static native HybridData initHybrid();

    private native void pushNativeArray(WritableNativeArray writableNativeArray);

    private native void pushNativeMap(WritableNativeMap writableNativeMap);

    @Override // com.facebook.react.bridge.WritableArray
    public native void pushBoolean(boolean z);

    public native void pushDouble(double d);

    @Override // com.facebook.react.bridge.WritableArray
    public native void pushInt(int i);

    @Override // com.facebook.react.bridge.WritableArray
    public native void pushNull();

    @Override // com.facebook.react.bridge.WritableArray
    public native void pushString(String str);

    static {
        ReactBridge.staticInit();
    }

    public WritableNativeArray() {
        super(initHybrid());
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushArray(WritableArray array) {
        Assertions.assertCondition(array == null || (array instanceof WritableNativeArray), "Illegal type provided");
        pushNativeArray((WritableNativeArray) array);
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushMap(WritableMap map) {
        Assertions.assertCondition(map == null || (map instanceof WritableNativeMap), "Illegal type provided");
        pushNativeMap((WritableNativeMap) map);
    }
}
