package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cj implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ Context b;
    final /* synthetic */ ch c;

    cj(ch chVar, long j, Context context) {
        this.c = chVar;
        this.a = j;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        this.c.b(this.a);
        if (bv.a().c()) {
            this.c.c(this.b);
        }
    }
}
