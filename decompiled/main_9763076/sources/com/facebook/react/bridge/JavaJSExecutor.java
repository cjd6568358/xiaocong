package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public interface JavaJSExecutor {
    void close();

    @DoNotStrip
    String executeJSCall(String str, String str2) throws ProxyExecutorException;

    @DoNotStrip
    void loadApplicationScript(String str) throws ProxyExecutorException;

    @DoNotStrip
    void setGlobalVariable(String str, String str2);

    public static class ProxyExecutorException extends Exception {
        public ProxyExecutorException(Throwable cause) {
            super(cause);
        }
    }
}
