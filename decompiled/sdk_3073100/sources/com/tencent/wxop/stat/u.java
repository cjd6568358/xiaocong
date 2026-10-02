package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class u implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatSpecifyReportedInfo b;
    final /* synthetic */ com.tencent.wxop.stat.event.c c;

    u(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, com.tencent.wxop.stat.event.c cVar) {
        this.a = context;
        this.b = statSpecifyReportedInfo;
        this.c = cVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            com.tencent.wxop.stat.event.b bVar = new com.tencent.wxop.stat.event.b(this.a, StatServiceImpl.a(this.a, false, this.b), this.c.a, this.b);
            bVar.b().c = this.c.c;
            new aq(bVar).a();
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
            StatServiceImpl.a(this.a, th);
        }
    }
}
