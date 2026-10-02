package com.huawei.hms.support.api.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.hms.api.HuaweiApiAvailability;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PushEventReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushEventReceiver", "context== null or intent == null");
                return;
            }
            return;
        }
        String action = intent.getAction();
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("PushEventReceiver", "receive self show message, action is " + action);
        }
        if ("com.huawei.intent.action.PUSH".equals(action) && intent.hasExtra("selfshow_info")) {
            a(context, intent);
        } else if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("PushEventReceiver", "invalid action.");
        }
    }

    private static void a(Context context, Intent intent) {
        if (com.huawei.hms.support.api.push.a.a.a(context, HuaweiApiAvailability.SERVICES_PACKAGE)) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushEventReceiver", "transfer this message to HMS to depose selfshow msg");
            }
            Intent intent2 = new Intent(intent.getAction());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                intent2.putExtras(extras);
                intent2.setFlags(32);
                intent2.setPackage(HuaweiApiAvailability.SERVICES_PACKAGE);
                context.sendBroadcast(intent2);
                return;
            }
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushEventReceiver", "self show failure, msg is null");
                return;
            }
            return;
        }
        if (com.huawei.hms.support.log.a.c()) {
            com.huawei.hms.support.log.a.c("PushEventReceiver", "HMS is not installed, can't depose selfshow message");
        }
    }
}
