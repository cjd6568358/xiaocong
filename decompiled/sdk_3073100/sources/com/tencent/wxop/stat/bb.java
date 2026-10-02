package com.tencent.wxop.stat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bb implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ au b;

    bb(au auVar, int i) {
        this.b = auVar;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a, true);
        this.b.b(this.a, false);
    }
}
