package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class q implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ Throwable b;

    q(Context context, Throwable th) {
        this.a = context;
        this.b = th;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (StatConfig.isEnableStatService()) {
                new aq(new com.tencent.wxop.stat.event.d(this.a, StatServiceImpl.a(this.a, false, (StatSpecifyReportedInfo) null), 99, this.b, com.tencent.wxop.stat.event.h.a)).a();
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e("reportSdkSelfException error: " + th);
        }
    }
}
