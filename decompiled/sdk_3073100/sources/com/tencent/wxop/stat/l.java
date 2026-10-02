package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class l implements Runnable {
    final /* synthetic */ Context a;

    l(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        a.a(StatServiceImpl.t).h();
        com.tencent.wxop.stat.common.l.a(this.a, true);
        au.a(this.a);
        i.b(this.a);
        Thread.UncaughtExceptionHandler unused = StatServiceImpl.r = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new ao());
        if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH) {
            StatServiceImpl.commitEvents(this.a, -1);
        }
        if (StatConfig.isDebugEnable()) {
            StatServiceImpl.q.d("Init MTA StatService success.");
        }
    }
}
