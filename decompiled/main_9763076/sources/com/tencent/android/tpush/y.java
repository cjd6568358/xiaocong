package com.tencent.android.tpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class y extends BroadcastReceiver {
    final /* synthetic */ XGIOperateCallback a;

    y(XGIOperateCallback xGIOperateCallback) {
        this.a = xGIOperateCallback;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(XGPushManager.a, "Register call back to " + context.getPackageName());
        }
        try {
            com.tencent.android.tpush.common.g.a().a(new aj(this.a, context, intent, 1, 0));
        } catch (Exception e) {
        }
        com.tencent.android.tpush.common.t.a(context, this);
    }
}
