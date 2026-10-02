package com.tencent.android.tpush.common;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.service.XGWatchdog;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    private Context a;
    private Intent b;

    public c(Context context, Intent intent) {
        this.a = null;
        this.b = null;
        this.a = context;
        this.b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        String action = this.b.getAction();
        if (action != null) {
            if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REPLACED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action)) {
                XGWatchdog.getInstance(this.a).sendAllLocalXGAppList();
            }
        }
    }
}
