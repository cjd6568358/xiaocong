package com.xiaomi.channel.commonutils.logger;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a implements LoggerInterface {
    private String a = "xiaomi";

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str) {
        Log.v(this.a, str);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str, Throwable th) {
        Log.v(this.a, str, th);
    }
}
