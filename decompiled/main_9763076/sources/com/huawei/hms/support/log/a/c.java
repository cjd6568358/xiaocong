package com.huawei.hms.support.log.a;

/* JADX INFO: compiled from: FileLogNode.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ int b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ a.C0020a e;

    c(a.C0020a c0020a, String str, int i, String str2, String str3) {
        this.e = c0020a;
        this.a = str;
        this.b = i;
        this.c = str2;
        this.d = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.e.a.a(this.a, this.b, this.c, this.d);
    }
}
