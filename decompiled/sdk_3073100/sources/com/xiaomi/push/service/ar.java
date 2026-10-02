package com.xiaomi.push.service;

import com.xiaocong.smarthome.network.constant.NetworkConstant;
import com.xiaomi.push.service.XMPushService.d;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class ar {
    private static int e = 300000;
    private XMPushService a;
    private int d = 0;
    private int b = 500;
    private long c = 0;

    public ar(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    private int b() {
        if (this.d > 8) {
            return 300000;
        }
        if (this.d > 4) {
            return 60000;
        }
        if (this.d > 1) {
            return NetworkConstant.HTTP_TIMEOUT;
        }
        if (this.c == 0) {
            return 0;
        }
        if (System.currentTimeMillis() - this.c >= 300000) {
            this.b = 500;
            return 0;
        }
        if (this.b >= e) {
            return this.b;
        }
        int i = this.b;
        this.b = (int) (((double) this.b) * 1.5d);
        return i;
    }

    public void a() {
        this.c = System.currentTimeMillis();
        this.a.a(1);
        this.d = 0;
    }

    public void a(boolean z) {
        if (!this.a.b()) {
            com.xiaomi.channel.commonutils.logger.b.c("should not reconnect as no client or network.");
            return;
        }
        if (z) {
            if (!this.a.b(1)) {
                this.d++;
            }
            this.a.a(1);
            XMPushService xMPushService = this.a;
            XMPushService xMPushService2 = this.a;
            xMPushService2.getClass();
            xMPushService.a(xMPushService2.new d());
            return;
        }
        if (this.a.b(1)) {
            return;
        }
        int iB = b();
        if (!this.a.b(1)) {
            this.d++;
        }
        com.xiaomi.channel.commonutils.logger.b.a("schedule reconnect in " + iB + "ms");
        XMPushService xMPushService3 = this.a;
        XMPushService xMPushService4 = this.a;
        xMPushService4.getClass();
        xMPushService3.a(xMPushService4.new d(), iB);
        if (this.d == 2 && com.xiaomi.stats.f.a().c()) {
            ae.b();
        }
        if (this.d == 3) {
            ae.a();
        }
    }
}
