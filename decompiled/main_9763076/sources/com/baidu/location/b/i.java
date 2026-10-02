package com.baidu.location.b;

import com.baidu.location.a.l;
import com.baidu.location.a.t;
import com.baidu.location.a.v;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i implements Runnable {
    final /* synthetic */ h.a a;

    i(h.a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        h.this.r();
        l.c().h();
        if (System.currentTimeMillis() - t.b() <= 5000) {
            v.a(t.c(), h.this.n(), t.d(), t.a());
        }
    }
}
