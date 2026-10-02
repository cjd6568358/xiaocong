package com.xiaomi.push.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bg extends BroadcastReceiver {
    final /* synthetic */ XMPushService a;

    bg(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.xiaomi.metok.geofencing.state_change")) {
            String stringExtra = intent.getStringExtra("Location");
            String stringExtra2 = intent.getStringExtra("Describe");
            String stringExtra3 = intent.getStringExtra("State");
            if (TextUtils.isEmpty(stringExtra2)) {
                return;
            }
            if (!this.a.a(stringExtra3, stringExtra2, context)) {
                stringExtra3 = "Unknown";
                com.xiaomi.channel.commonutils.logger.b.a(" updated geofence statue about geo_id:" + stringExtra2 + " falied. current_statue:Unknown");
            }
            com.xiaomi.smack.util.e.a(new bh(this, context, stringExtra2, stringExtra3));
            com.xiaomi.channel.commonutils.logger.b.c("ownresilt结果:state= " + stringExtra3 + "\n describe=" + stringExtra2 + "\n location=" + stringExtra);
        }
    }
}
