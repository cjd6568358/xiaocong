package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class l implements Runnable {
    final /* synthetic */ b a;
    private com.tencent.android.tpush.service.channel.a.a b;
    private com.tencent.android.tpush.service.channel.b.i c;

    public l(b bVar, com.tencent.android.tpush.service.channel.a.a aVar, com.tencent.android.tpush.service.channel.b.i iVar) {
        this.a = bVar;
        this.b = null;
        this.c = null;
        this.b = aVar;
        this.c = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.a.B.c;
            a aVarF = this.b.f();
            aVarF.a(3, Long.valueOf(jCurrentTimeMillis));
            t tVar = this.a.B.f;
            if (tVar != null) {
                this.a.t.removeCallbacks((q) this.a.w.remove(this.a.B));
                tVar.a(this.a.B.e, this.c.l(), null, aVarF);
            } else {
                com.tencent.android.tpush.a.a.i("TpnsChannel", ">> messageHandler is null");
                this.a.J.a(this.a.B.e, this.c.l(), null, aVarF);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", Constants.MAIN_VERSION_TAG, th);
        }
    }
}
