package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cl implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ ch b;

    cl(ch chVar, Context context) {
        this.b = chVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.b.f > 0 && jCurrentTimeMillis - this.b.f > this.b.c()) {
            this.b.a(this.a, false);
        }
    }
}
