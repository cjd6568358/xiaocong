package com.facebook.react.bridge.queue;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class NativeRunnable implements Runnable {
    private final HybridData mHybridData;

    @Override // java.lang.Runnable
    public native void run();

    @DoNotStrip
    private NativeRunnable(HybridData hybridData) {
        this.mHybridData = hybridData;
    }
}
