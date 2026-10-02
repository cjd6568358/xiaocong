package com.tencent.android.tpush.stat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class i implements Runnable {
    final /* synthetic */ Context a;

    i(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.a(h.i).e();
        com.tencent.android.tpush.stat.a.e.a(this.a, true);
        f.b(this.a);
        Thread.UncaughtExceptionHandler unused = h.h = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new u());
        if (c.a() == StatReportStrategy.APP_LAUNCH) {
            h.a(this.a, -1);
        }
        if (c.b()) {
            h.g.h("Init MTA StatService success.");
        }
        String strI = com.tencent.android.tpush.stat.a.e.i(h.i);
        if (strI == null || strI.trim().length() == 0) {
            strI = "default";
        }
        String str = strI + ".xg.stat.";
        if (Build.VERSION.SDK_INT >= 11) {
            SharedPreferences unused2 = h.k = this.a.getSharedPreferences("." + str, 0);
        } else {
            SharedPreferences unused3 = h.k = this.a.getSharedPreferences("." + str, 0);
        }
    }
}
