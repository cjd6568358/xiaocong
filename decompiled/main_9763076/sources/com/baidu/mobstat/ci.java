package com.baidu.mobstat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ci implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ ch b;

    ci(ch chVar, long j) {
        this.b = chVar;
        this.a = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
