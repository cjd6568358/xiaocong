package com.tencent.android.tpush.stat;

import android.content.Context;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class m implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ long c;
    final /* synthetic */ long d;
    final /* synthetic */ long e;

    m(String str, Context context, long j, long j2, long j3) {
        this.a = str;
        this.b = context;
        this.c = j;
        this.d = j2;
        this.e = j3;
    }

    @Override // java.lang.Runnable
    public void run() {
        Long l;
        try {
            synchronized (h.b) {
                l = (Long) h.b.remove(this.a);
            }
            if (l == null) {
                h.g.f("Starttime for PageID:" + this.a + " not found, lost onResume()?");
                return;
            }
            Long lValueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
            if (lValueOf.longValue() <= 0) {
                lValueOf = 1L;
            }
            String str = h.f;
            if (str != null && str.equals(this.a)) {
                str = "-";
            }
            com.tencent.android.tpush.stat.event.f fVar = new com.tencent.android.tpush.stat.event.f(this.b, str, this.a, h.b(this.b, this.c), lValueOf, this.c);
            if (this.d > 0) {
                fVar.n = this.d;
            }
            if (this.e > 0) {
                fVar.n = this.e;
            }
            if (!this.a.equals(h.e)) {
                h.g.c("Invalid invocation since previous onResume on diff page.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(fVar);
            h.a(arrayList);
            String unused = h.f = this.a;
        } catch (Throwable th) {
            h.g.b(th);
        }
    }
}
