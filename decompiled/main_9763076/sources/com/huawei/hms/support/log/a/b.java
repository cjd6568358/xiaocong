package com.huawei.hms.support.log.a;

import android.content.Context;

/* JADX INFO: compiled from: FileLogNode.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ a.C0020a c;

    b(a.C0020a c0020a, Context context, String str) {
        this.c = c0020a;
        this.a = context;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a.a(this.a, this.b);
    }
}
