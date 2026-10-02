package com.baidu.lbsapi.auth;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements Runnable {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        a.a("postWithHttps start Thread id = " + String.valueOf(Thread.currentThread().getId()));
        this.a.a(new g(this.a.a).a(this.a.b));
    }
}
