package com.tencent.android.tpush.common;

import android.content.Context;
import android.content.IntentFilter;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static volatile d a = null;

    public static void a(Context context) {
        try {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null) {
                        a = new d();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addDataScheme("package");
                        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                        context.getApplicationContext().registerReceiver(a, intentFilter);
                    }
                }
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "AppChangesHandler setupHandler error", e);
        }
    }
}
