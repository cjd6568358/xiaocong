package com.alibaba.sdk.android.httpdns;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i implements Thread.UncaughtExceptionHandler {
    private void b(Throwable th) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.j(th.getMessage());
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Log.e("HttpDnsSDK", "Catch an uncaught exception, " + thread.getName() + ", error message: " + th.getMessage());
        b(th);
        th.printStackTrace();
    }
}
