package com.tencent.wxop.stat;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class k implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ h b;
    final /* synthetic */ i c;

    k(i iVar, List list, h hVar) {
        this.c = iVar;
        this.a = list;
        this.b = hVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a(this.a, this.b);
    }
}
