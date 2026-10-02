package com.tencent.android.tpush.stat;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class o implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ long b;
    final /* synthetic */ com.tencent.android.tpush.stat.event.b c;
    final /* synthetic */ long d;

    o(Context context, long j, com.tencent.android.tpush.stat.event.b bVar, long j2) {
        this.a = context;
        this.b = j;
        this.c = bVar;
        this.d = j2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.tencent.android.tpush.stat.event.a aVar = new com.tencent.android.tpush.stat.event.a(this.a, h.b(this.a, this.b), this.c.a, this.b, this.d);
            aVar.a().c = this.c.c;
            h.a(aVar);
        } catch (Throwable th) {
            h.g.b(th);
        }
    }
}
