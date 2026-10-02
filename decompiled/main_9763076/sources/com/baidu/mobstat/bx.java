package com.baidu.mobstat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bx extends Thread {
    final /* synthetic */ bv a;
    private boolean b;

    public bx(bv bvVar, boolean z) {
        this.a = bvVar;
        this.b = z;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.a.a(this.a.b, true, this.b);
        by.a().a(this.a.b);
    }
}
