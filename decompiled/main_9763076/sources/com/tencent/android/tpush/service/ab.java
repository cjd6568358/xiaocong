package com.tencent.android.tpush.service;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ab implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ ad b;
    final /* synthetic */ XGWatchdog c;

    ab(XGWatchdog xGWatchdog, String str, ad adVar) {
        this.c = xGWatchdog;
        this.a = str;
        this.b = adVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String strDirectSendContent = this.c.directSendContent(this.a);
            if (this.b != null) {
                this.b.a(strDirectSendContent);
            }
        } catch (Throwable th) {
        }
    }
}
