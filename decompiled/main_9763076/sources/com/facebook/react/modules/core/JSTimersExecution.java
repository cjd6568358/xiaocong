package com.facebook.react.modules.core;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface JSTimersExecution extends JavaScriptModule {
    void callIdleCallbacks(double d);

    void callTimers(WritableArray writableArray);

    void emitTimeDriftWarning(String str);
}
