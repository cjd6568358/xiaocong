package com.tencent.android.tpush;

import android.content.Context;
import android.content.Intent;
import java.util.Iterator;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class al implements Runnable {
    Context a;
    Intent b;
    XGIOperateCallback c;
    int d;

    public al(Context context, Intent intent, XGIOperateCallback xGIOperateCallback) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 0;
        this.a = context;
        this.b = intent;
        this.c = xGIOperateCallback;
        this.d = intent.getIntExtra("opType", 0);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            switch (this.d) {
                case 0:
                    XGPushManager.c(this.a, this.b, this.c);
                    break;
                case 1:
                    XGPushManager.d(this.a, this.b, this.c);
                    break;
                default:
                    com.tencent.android.tpush.a.a.i(XGPushManager.a, "TimeoutRunnable error optype:" + this.d);
                    break;
            }
            Iterator it = XGPushManager.e.keySet().iterator();
            while (it.hasNext()) {
                com.tencent.android.tpush.common.t.a(this.a, (ak) it.next());
            }
            XGPushManager.e.clear();
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(XGPushManager.a, " RegisterTimeoutRunnable run error", e);
        }
    }
}
