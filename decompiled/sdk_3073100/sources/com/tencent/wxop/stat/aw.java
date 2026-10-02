package com.tencent.wxop.stat;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class aw implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;
    final /* synthetic */ boolean c;
    final /* synthetic */ au d;

    aw(au auVar, List list, boolean z, boolean z2) {
        this.d = auVar;
        this.a = list;
        this.b = z;
        this.c = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.d.a((List<bd>) this.a, this.b);
        if (this.c) {
            this.a.clear();
        }
    }
}
