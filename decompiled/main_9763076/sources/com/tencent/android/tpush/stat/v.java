package com.tencent.android.tpush.stat;

import java.util.Arrays;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class v implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ Throwable b;
    final /* synthetic */ Thread c;
    final /* synthetic */ u d;

    v(u uVar, long j, Throwable th, Thread thread) {
        this.d = uVar;
        this.a = j;
        this.b = th;
        this.c = thread;
    }

    @Override // java.lang.Runnable
    public void run() {
        h.b(Arrays.asList(new com.tencent.android.tpush.stat.event.c(h.i, h.b(h.i, this.a), 2, this.b, this.c, this.a)));
    }
}
