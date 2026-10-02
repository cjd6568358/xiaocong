package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public abstract class NativeMap {

    @DoNotStrip
    private HybridData mHybridData;

    public native String toString();

    static {
        ReactBridge.staticInit();
    }

    public NativeMap(HybridData hybridData) {
        this.mHybridData = hybridData;
    }
}
