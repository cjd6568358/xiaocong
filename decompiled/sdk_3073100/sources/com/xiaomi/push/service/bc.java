package com.xiaomi.push.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bc implements ServiceConnection {
    final /* synthetic */ XMPushService a;

    bc(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.xiaomi.channel.commonutils.logger.b.b("onServiceConnected " + iBinder);
        Service serviceA = XMJobService.a();
        if (serviceA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("XMService connected but innerService is null " + iBinder);
            return;
        }
        this.a.startForeground(XMPushService.g, XMPushService.a((Context) this.a));
        serviceA.startForeground(XMPushService.g, XMPushService.a((Context) this.a));
        serviceA.stopForeground(true);
        this.a.unbindService(this);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
