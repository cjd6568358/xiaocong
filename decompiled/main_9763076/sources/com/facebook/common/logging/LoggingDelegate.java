package com.facebook.common.logging;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface LoggingDelegate {
    void d(String str, String str2);

    void d(String str, String str2, Throwable th);

    void e(String str, String str2);

    void e(String str, String str2, Throwable th);

    void i(String str, String str2);

    boolean isLoggable(int i);

    void v(String str, String str2);

    void w(String str, String str2);

    void w(String str, String str2, Throwable th);

    void wtf(String str, String str2);

    void wtf(String str, String str2, Throwable th);
}
