package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bw implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ bv b;

    bw(bv bvVar, Context context) {
        this.b = bvVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (!ao.b(this.a)) {
                ao.a(2).a(this.a);
            }
        } catch (Throwable th) {
        }
        this.b.e = false;
    }
}
