package com.tencent.android.tpush.service.channel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k implements Runnable {
    final /* synthetic */ s a;
    final /* synthetic */ b b;

    k(b bVar, s sVar) {
        this.b = bVar;
        this.a = sVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.f.a(this.a.e, new a());
    }
}
