package com.facebook.react.cxxbridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class ProxyJavaScriptExecutor extends JavaScriptExecutor {
    private JavaJSExecutor mJavaJSExecutor;

    private static native HybridData initHybrid(JavaJSExecutor javaJSExecutor);

    static {
        SoLoader.loadLibrary("reactnativejnifb");
    }

    @Override // com.facebook.react.cxxbridge.JavaScriptExecutor
    public void close() {
        if (this.mJavaJSExecutor != null) {
            this.mJavaJSExecutor.close();
            this.mJavaJSExecutor = null;
        }
    }
}
