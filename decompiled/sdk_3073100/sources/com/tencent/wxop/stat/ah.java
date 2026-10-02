package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class ah implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    ah(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = context;
        this.b = str;
        this.c = statSpecifyReportedInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Long l;
        try {
            StatServiceImpl.flushDataToDB(this.a);
            synchronized (StatServiceImpl.o) {
                l = (Long) StatServiceImpl.o.remove(this.b);
            }
            if (l == null) {
                StatServiceImpl.q.e("Starttime for PageID:" + this.b + " not found, lost onResume()?");
                return;
            }
            Long lValueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
            if (lValueOf.longValue() <= 0) {
                lValueOf = 1L;
            }
            String str = StatServiceImpl.n;
            if (str != null && str.equals(this.b)) {
                str = "-";
            }
            com.tencent.wxop.stat.event.j jVar = new com.tencent.wxop.stat.event.j(this.a, str, this.b, StatServiceImpl.a(this.a, false, this.c), lValueOf, this.c);
            if (!this.b.equals(StatServiceImpl.m)) {
                StatServiceImpl.q.warn("Invalid invocation since previous onResume on diff page.");
            }
            new aq(jVar).a();
            String unused = StatServiceImpl.n = this.b;
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
            StatServiceImpl.a(this.a, th);
        }
    }
}
