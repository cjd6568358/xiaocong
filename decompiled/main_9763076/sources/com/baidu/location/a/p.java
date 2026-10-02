package com.baidu.location.a;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p implements Runnable {
    final /* synthetic */ o a;

    p(o oVar) {
        this.a = oVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.baidu.location.b.h.i() || this.a.a(com.baidu.location.f.getServiceContext())) {
            this.a.d();
        }
    }
}
