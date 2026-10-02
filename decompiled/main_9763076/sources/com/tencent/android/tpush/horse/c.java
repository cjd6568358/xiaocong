package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.channel.protocol.TpnsRedirectReq;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends Thread {
    protected o a = new d(this);
    final /* synthetic */ a b;
    private n c;
    private int d;
    private StrategyItem e;

    public c(a aVar, int i) {
        this.b = aVar;
        this.d = i;
    }

    public n a() {
        return this.c;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (this.b.b.size() > 0) {
            try {
                this.e = (StrategyItem) this.b.b.remove();
                try {
                    TpnsRedirectReq tpnsRedirectReq = new TpnsRedirectReq();
                    tpnsRedirectReq.network = com.tencent.android.tpush.service.e.m.k(com.tencent.android.tpush.service.n.f());
                    tpnsRedirectReq.op = com.tencent.android.tpush.service.e.m.l(com.tencent.android.tpush.service.n.f());
                    this.c = new n();
                    this.c.a(this.a);
                    com.tencent.android.tpush.a.a.c("HorseThread", " HorseThread:" + getClass().getSimpleName() + Thread.currentThread() + "current NetworkType:" + ((int) tpnsRedirectReq.network) + ",strategyItem:" + this.e);
                    this.c.a(this.e);
                    this.c.a(tpnsRedirectReq);
                    this.c.b();
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.c("HorseThread", "HorseThread error", th);
                }
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c("HorseThread", "Can not get strateItem from strategyItems>>", e);
                try {
                    Thread.sleep(5000L);
                } catch (Exception e2) {
                    com.tencent.android.tpush.a.a.i(Constants.HorseLogTag, e2.toString());
                }
            }
        }
    }
}
