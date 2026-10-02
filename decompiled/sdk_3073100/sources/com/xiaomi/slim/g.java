package com.xiaomi.slim;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class g extends Thread {
    final /* synthetic */ f a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(f fVar, String str) {
        super(str);
        this.a = fVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            f.a(this.a).a();
        } catch (Exception e) {
            this.a.c(9, e);
        }
    }
}
