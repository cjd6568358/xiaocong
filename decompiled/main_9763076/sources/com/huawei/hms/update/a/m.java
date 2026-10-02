package com.huawei.hms.update.a;

/* JADX INFO: compiled from: ThreadWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ com.huawei.hms.update.a.a.c b;
    final /* synthetic */ l c;

    m(l lVar, int i, com.huawei.hms.update.a.a.c cVar) {
        this.c = lVar;
        this.a = i;
        this.b = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a.a(this.a, this.b);
    }
}
