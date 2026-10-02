package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class ThreadScopeSupport {
    private static native void runStdFunctionImpl(long j);

    static {
        SoLoader.loadLibrary("fb");
    }

    @DoNotStrip
    private static void runStdFunction(long ptr) {
        runStdFunctionImpl(ptr);
    }
}
