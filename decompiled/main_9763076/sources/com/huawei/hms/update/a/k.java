package com.huawei.hms.update.a;

/* JADX INFO: compiled from: ThreadWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k implements Runnable {
    final /* synthetic */ com.huawei.hms.update.a.a.b a;
    final /* synthetic */ com.huawei.hms.update.a.a.c b;
    final /* synthetic */ i c;

    k(i iVar, com.huawei.hms.update.a.a.b bVar, com.huawei.hms.update.a.a.c cVar) {
        this.c = iVar;
        this.a = bVar;
        this.b = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a.a(i.c(this.a), this.b);
    }
}
