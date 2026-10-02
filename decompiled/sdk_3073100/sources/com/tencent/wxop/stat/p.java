package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class p implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    p(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = str;
        this.b = context;
        this.c = statSpecifyReportedInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (StatServiceImpl.a(this.a)) {
                StatServiceImpl.q.error("Error message in StatService.reportError() is empty.");
            } else {
                new aq(new com.tencent.wxop.stat.event.d(this.b, StatServiceImpl.a(this.b, false, this.c), this.a, 0, StatConfig.getMaxReportEventLength(), null, this.c)).a();
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
            StatServiceImpl.a(this.b, th);
        }
    }
}
