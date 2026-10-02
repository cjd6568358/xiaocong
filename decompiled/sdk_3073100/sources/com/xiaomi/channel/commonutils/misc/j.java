package com.xiaomi.channel.commonutils.misc;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class j implements Runnable {
    final /* synthetic */ h.b a;
    final /* synthetic */ h b;

    j(h hVar, h.b bVar) {
        this.b = hVar;
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
