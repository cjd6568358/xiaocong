package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class m implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatSpecifyReportedInfo b;

    m(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = context;
        this.b = statSpecifyReportedInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a == null) {
            StatServiceImpl.q.error("The Context of StatService.onPause() can not be null!");
        } else {
            StatServiceImpl.trackEndPage(this.a, com.tencent.wxop.stat.common.l.f(this.a), this.b);
        }
    }
}
