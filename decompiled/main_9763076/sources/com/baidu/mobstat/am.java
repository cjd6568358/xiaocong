package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class am implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ al c;

    am(al alVar, String str, Context context) {
        this.c = alVar;
        this.a = str;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.c.a(this.a);
            if (this.b != null) {
                this.c.a(this.b.getApplicationContext());
            }
        } catch (Throwable th) {
            bd.b(th);
        }
    }
}
