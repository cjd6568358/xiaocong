package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public interface CatalystInstance extends MemoryPressureListener {
    void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener notThreadSafeBridgeIdleDebugListener);

    @DoNotStrip
    void callFunction(ExecutorToken executorToken, String str, String str2, NativeArray nativeArray);

    void destroy();

    <T extends JavaScriptModule> T getJSModule(ExecutorToken executorToken, Class<T> cls);

    <T extends JavaScriptModule> T getJSModule(Class<T> cls);

    <T extends NativeModule> T getNativeModule(Class<T> cls);

    ReactQueueConfiguration getReactQueueConfiguration();

    String getSourceURL();

    void initialize();

    @DoNotStrip
    void invokeCallback(ExecutorToken executorToken, int i, NativeArray nativeArray);

    boolean isDestroyed();

    void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener notThreadSafeBridgeIdleDebugListener);

    void runJSBundle();
}
