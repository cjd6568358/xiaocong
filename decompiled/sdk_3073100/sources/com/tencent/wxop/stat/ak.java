package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class ak implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    ak(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = str;
        this.b = context;
        this.c = statSpecifyReportedInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a == null || this.a.trim().length() == 0) {
            StatServiceImpl.q.w("qq num is null or empty.");
        } else {
            StatConfig.f = this.a;
            StatServiceImpl.b(this.b, new StatAccount(this.a), this.c);
        }
    }
}
