package com.facebook.react.cxxbridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public abstract class JavaScriptExecutor {
    private final HybridData mHybridData;

    public interface Factory {
        JavaScriptExecutor create() throws Exception;
    }

    protected JavaScriptExecutor(HybridData hybridData) {
        this.mHybridData = hybridData;
    }

    public void close() {
        this.mHybridData.resetNative();
    }
}
