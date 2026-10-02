package com.tencent.android.tpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class z extends BroadcastReceiver {
    final /* synthetic */ XGIOperateCallback a;

    z(XGIOperateCallback xGIOperateCallback) {
        this.a = xGIOperateCallback;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        com.tencent.android.tpush.common.t.a(context, this);
        if ((com.tencent.android.tpush.common.s.a(context).c() && XGPushConfig.isUsedFcmPush(context)) || (XGPushConfig.isUsedOtherPush(context) && com.tencent.android.tpush.c.e.a(context).a())) {
            try {
                com.tencent.android.tpush.common.g.a().a(new aa(this, context));
            } catch (Exception e) {
            }
        }
        if (this.a != null) {
            try {
                com.tencent.android.tpush.common.g.a().a(new aj(this.a, context, intent, 1, 1));
            } catch (Exception e2) {
            }
        }
    }
}
