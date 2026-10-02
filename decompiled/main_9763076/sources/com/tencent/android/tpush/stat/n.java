package com.tencent.android.tpush.stat;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class n implements Runnable {
    final /* synthetic */ com.tencent.android.tpush.stat.event.d a;

    n(com.tencent.android.tpush.stat.event.d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            h.a(this.a);
        } catch (Throwable th) {
            h.g.b(th);
        }
    }
}
