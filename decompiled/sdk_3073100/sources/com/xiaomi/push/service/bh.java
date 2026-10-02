package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bh implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ bg d;

    bh(bg bgVar, Context context, String str, String str2) {
        this.d = bgVar;
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        for (com.xiaomi.push.service.module.b bVar : g.a(this.a).c(this.b)) {
            if (XMPushService.a(bVar.e(), this.c)) {
                if (bVar.a() >= System.currentTimeMillis()) {
                    byte[] bArrD = bVar.d();
                    if (bArrD == null) {
                        com.xiaomi.channel.commonutils.logger.b.a("Geo canBeShownMessage content null");
                    } else {
                        Intent intentA = s.a(bArrD, System.currentTimeMillis());
                        if (intentA == null) {
                            com.xiaomi.channel.commonutils.logger.b.a("Geo canBeShownMessage intent null");
                        } else {
                            s.a(this.d.a, (String) null, bArrD, intentA, true);
                            if (g.a(this.d.a).a(bVar.b()) == 0) {
                                com.xiaomi.channel.commonutils.logger.b.a("show some exit geofence message. then remove this message failed. message_id:" + bVar.b());
                            }
                        }
                    }
                } else if (g.a(this.a).a(bVar.b()) == 0) {
                    com.xiaomi.channel.commonutils.logger.b.a("XMPushService remove some geofence message failed. message_id:" + bVar.b());
                }
            }
        }
    }
}
