package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.common.Constants;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p implements Runnable {
    final /* synthetic */ b a;
    private com.tencent.android.tpush.service.channel.a.a b;
    private com.tencent.android.tpush.service.channel.b.i c;

    public p(b bVar, com.tencent.android.tpush.service.channel.a.a aVar, com.tencent.android.tpush.service.channel.b.i iVar) {
        this.a = bVar;
        this.b = null;
        this.c = null;
        this.b = aVar;
        this.c = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.a.v.get(this.b);
        if (concurrentHashMap != null) {
            s sVar = (s) concurrentHashMap.get(Integer.valueOf(this.c.i()));
            if (sVar != null) {
                this.a.t.removeCallbacks((q) this.a.w.remove(sVar));
                concurrentHashMap.remove(Integer.valueOf(this.c.i()));
                t tVar = sVar.f;
                if (tVar == null) {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> messageHandler is null");
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis() - sVar.c;
                a aVarF = this.b.f();
                aVarF.a(3, Long.valueOf(jCurrentTimeMillis));
                try {
                    tVar.a(sVar.e, this.c.l(), com.tencent.android.tpush.service.channel.c.d.a(this.c.h(), this.c.k()), aVarF);
                    return;
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c("TpnsChannel", Constants.MAIN_VERSION_TAG, e);
                    return;
                }
            }
            com.tencent.android.tpush.a.a.i("TpnsChannel", ">> NetCallBackRunnable >>> 请求已被回调过，响应对应的request不存在。" + this.c);
            try {
                com.tencent.android.tpush.a.a.i("TpnsChannel", "onRequestSuccRunnable unhandle message type" + com.tencent.android.tpush.service.channel.c.d.a(this.c.h(), this.c.k()).getClass().getName());
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c("TpnsChannel", Constants.MAIN_VERSION_TAG, e2);
            }
        }
    }
}
