package com.facebook.react.bridge.queue;

import com.facebook.jni.Countable;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class NativeRunnableDeprecated extends Countable implements Runnable {
    @Override // java.lang.Runnable
    public native void run();

    @DoNotStrip
    private NativeRunnableDeprecated() {
    }
}
