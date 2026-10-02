package com.huawei.hms.update.a;

/* JADX INFO: compiled from: ThreadWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements Runnable {
    final /* synthetic */ com.huawei.hms.update.a.a.b a;
    final /* synthetic */ i b;

    j(i iVar, com.huawei.hms.update.a.a.b bVar) {
        this.b = iVar;
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.a(i.c(this.a));
    }
}
