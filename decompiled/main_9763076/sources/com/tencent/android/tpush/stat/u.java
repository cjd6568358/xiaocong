package com.tencent.android.tpush.stat;

import com.tencent.android.tpush.XGPush4Msdk;
import com.tencent.android.tpush.XGPushConfig;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class u implements Thread.UncaughtExceptionHandler {
    u() {
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        com.tencent.android.tpush.common.t.a();
        if (c.c() && h.i != null) {
            long accessId = XGPushConfig.getAccessId(h.i);
            if (accessId <= 0) {
                accessId = XGPush4Msdk.getQQAccessId(h.i);
            }
            boolean z = true;
            if (h.j != null && !th.toString().contains(h.j)) {
                z = false;
            }
            if (z) {
                if (h.e(h.i) != null) {
                    h.c.post(new v(this, accessId, th, thread));
                }
                h.g.g("has caught the following uncaught exception:");
                h.g.a(th);
            }
            if (h.h != null) {
                h.g.h("Call the original uncaught exception handler.");
                if (!(h.h instanceof u)) {
                    h.h.uncaughtException(thread, th);
                }
            }
        }
    }
}
