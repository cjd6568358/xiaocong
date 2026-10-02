package com.tencent.android.tpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ak extends BroadcastReceiver {
    Context a;
    Intent b;
    XGIOperateCallback c;
    int d;

    public ak(Context context, Intent intent, XGIOperateCallback xGIOperateCallback) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 0;
        this.a = context;
        this.b = intent;
        this.c = xGIOperateCallback;
        this.d = intent.getIntExtra("opType", 0);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            com.tencent.android.tpush.common.g.a().b().removeCallbacks((al) XGPushManager.e.remove(this));
            switch (this.d) {
                case 0:
                    XGPushManager.c(this.a, this.b, this.c);
                    break;
                case 1:
                    XGPushManager.d(this.a, this.b, this.c);
                    break;
                default:
                    com.tencent.android.tpush.a.a.i(XGPushManager.a, "RegisterStartReceiver error optype:" + this.d);
                    break;
            }
            com.tencent.android.tpush.common.t.a(this.a, this);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(XGPushManager.a, "RegisterStartReceiver error", e);
        }
    }
}
