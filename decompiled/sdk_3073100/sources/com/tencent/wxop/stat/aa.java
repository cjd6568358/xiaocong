package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class aa implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatSpecifyReportedInfo b;
    final /* synthetic */ StatAppMonitor c;

    aa(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, StatAppMonitor statAppMonitor) {
        this.a = context;
        this.b = statSpecifyReportedInfo;
        this.c = statAppMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            new aq(new com.tencent.wxop.stat.event.g(this.a, StatServiceImpl.a(this.a, false, this.b), this.c, this.b)).a();
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
            StatServiceImpl.a(this.a, th);
        }
    }
}
