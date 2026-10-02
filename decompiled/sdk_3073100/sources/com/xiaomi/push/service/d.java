package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d extends com.xiaomi.channel.commonutils.misc.f.a {
    private XMPushService a;

    public d(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.channel.commonutils.misc.f.a
    public int a() {
        return 15;
    }

    @Override // java.lang.Runnable
    public void run() {
        for (com.xiaomi.push.service.module.b bVar : g.a(this.a).a()) {
            if (bVar.a() < System.currentTimeMillis()) {
                if (g.a(this.a).a(bVar.b()) == 0) {
                    com.xiaomi.channel.commonutils.logger.b.a("GeofenceDbCleaner delete a geofence message failed message_id:" + bVar.b());
                }
                s.a(this.a, s.a(bVar.d()), false, false, true);
            }
        }
    }
}
