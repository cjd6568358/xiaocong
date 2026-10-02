package com.tencent.android.tpush.service;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class q implements Runnable {
    final /* synthetic */ o a;

    q(o oVar) {
        this.a = oVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.android.tpush.a.a.e("PushServiceManager", "swicth main service then pull up sloveSerice");
        com.tencent.android.tpush.service.e.m.g(n.a);
    }
}
