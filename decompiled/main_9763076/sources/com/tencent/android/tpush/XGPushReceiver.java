package com.tencent.android.tpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushReceiver extends BroadcastReceiver {
    private static final String a = XGPushReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        if (context != null && intent != null && com.tencent.android.tpush.common.t.h(context) && (action = intent.getAction()) != null) {
            com.tencent.android.tpush.service.n.d(context.getApplicationContext());
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c(a, "PushReceiver received " + action + " @@ " + context.getPackageName());
            }
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                com.tencent.android.tpush.horse.g.a().a(intent);
            } else if (!Constants.ACTION_INTERNAL_PUSH_MESSAGE.equals(action) && !Constants.ACTION_SDK_INSTALL.equals(action)) {
                com.tencent.android.tpush.service.n.a(context);
            }
        }
    }
}
