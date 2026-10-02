package com.meizu.cloud.pushsdk.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class WorkReceiver extends BroadcastReceiver {
    public abstract void onHandleIntent(Context context, Intent intent);

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        com.meizu.cloud.pushsdk.common.a.a.d().a(context);
        com.meizu.cloud.pushsdk.common.a.a.d().a(new Runnable() { // from class: com.meizu.cloud.pushsdk.common.base.WorkReceiver.1
            @Override // java.lang.Runnable
            public void run() {
                WorkReceiver.this.onHandleIntent(context, intent);
                com.meizu.cloud.pushsdk.common.a.a.d().c();
            }
        });
    }
}
