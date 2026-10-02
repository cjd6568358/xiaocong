package com.facebook.react.cxxbridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class JSCJavaScriptExecutor extends JavaScriptExecutor {
    private static native HybridData initHybrid(ReadableNativeArray readableNativeArray);

    public static class Factory implements JavaScriptExecutor.Factory {
        private ReadableNativeArray mJSCConfig;

        public Factory(WritableNativeMap jscConfig) {
            WritableNativeArray array = new WritableNativeArray();
            array.pushMap(jscConfig);
            this.mJSCConfig = array;
        }

        @Override // com.facebook.react.cxxbridge.JavaScriptExecutor.Factory
        public JavaScriptExecutor create() throws Exception {
            return new JSCJavaScriptExecutor(this.mJSCConfig);
        }
    }

    static {
        SoLoader.loadLibrary("reactnativejnifb");
    }

    public JSCJavaScriptExecutor(ReadableNativeArray jscConfig) {
        super(initHybrid(jscConfig));
    }
}
