package com.tencent.android.tpush.service.channel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class n implements Runnable {
    final /* synthetic */ b a;
    private com.tencent.android.tpush.service.channel.a.a b;
    private com.tencent.android.tpush.service.channel.b.i c;

    public n(b bVar, com.tencent.android.tpush.service.channel.a.a aVar, com.tencent.android.tpush.service.channel.b.i iVar) {
        this.a = bVar;
        this.b = null;
        this.c = null;
        this.b = aVar;
        this.c = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.tencent.android.tpush.service.s.a().a(com.tencent.android.tpush.service.channel.c.d.a(this.c.h(), this.c.k()), this.b.f());
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "run", th);
        }
    }
}
