package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class l implements Runnable {
    final /* synthetic */ a a;
    private Context b;
    private Intent c;

    public l(a aVar, Context context, Intent intent) {
        this.a = aVar;
        this.b = null;
        this.c = null;
        this.b = context;
        this.c = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String action = this.c.getAction();
            if (action != null) {
                if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REPLACED".equals(action)) {
                    this.a.a(this.b, this.c);
                    XGWatchdog.getInstance(this.b).sendAllLocalXGAppList();
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                    this.a.b(this.b, this.c);
                    XGWatchdog.getInstance(this.b).sendAllLocalXGAppList();
                } else if ("com.tencent.android.tpush.action.REGISTER.V3".equals(action)) {
                    this.a.c(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.UNREGISTER.V3".equals(action)) {
                    this.a.e(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.ENABLE_DEBUG.V3".equals(action)) {
                    this.a.h(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.MSG_ACK.V3".equals(action)) {
                    com.tencent.android.tpush.service.c.a.a().a(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.TAG.V3".equals(action)) {
                    this.a.d(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.PUSH_CLICK.RESULT.V3".equals(action) || "com.tencent.android.tpush.action.PUSH_CANCELLED.RESULT.V3".equals(action)) {
                    com.tencent.android.tpush.service.c.a.a().c(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.ack.sdk2srv.V3".equals(action)) {
                    com.tencent.android.tpush.service.d.a.a(this.c);
                } else if ("com.tencent.android.tpush.action.UPDATE_OTHER_PUSH_TOKEN.V3".equals(action)) {
                    this.a.f(this.b, this.c);
                } else if ("com.tencent.android.tpush.action.COMM_REPORT.V3".equals(action)) {
                    this.a.g(this.b, this.c);
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(a.a, a.a + " run error.", th);
        }
    }
}
