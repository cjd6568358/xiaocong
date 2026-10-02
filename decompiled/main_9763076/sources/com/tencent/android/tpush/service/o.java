package com.tencent.android.tpush.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class o extends Handler {
    final /* synthetic */ n a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    o(n nVar, Looper looper) {
        super(looper);
        this.a = nVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message != null) {
            switch (message.what) {
                case 1:
                    if (!this.a.n()) {
                        if (this.a.o()) {
                            com.tencent.android.tpush.a.a.e("PushServiceManager", "start as slave service......");
                            if (!n.i) {
                                if (XGPushConfig.enableDebug) {
                                    com.tencent.android.tpush.a.a.f("PushServiceManager", "Slave Service's first running at " + n.a.getPackageName() + " version : 3.24");
                                }
                                boolean unused = n.i = true;
                                com.tencent.android.tpush.service.channel.b.a().h();
                            }
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(n.a, XGPushServiceV3.class);
                            com.tencent.android.tpush.a.a.g("PushServiceManager", n.a.getPackageName() + " XGPushServiceV3 try to stop self.");
                            n.a.stopService(intent);
                        }
                    } else {
                        com.tencent.android.tpush.a.a.e("PushServiceManager", "start as main service......");
                        if (!n.h) {
                            if (XGPushConfig.enableDebug) {
                                com.tencent.android.tpush.a.a.f("PushServiceManager", "Service's first running at " + n.a.getPackageName() + " version : 3.24");
                            }
                            boolean unused2 = n.h = true;
                            if (!com.tencent.android.tpush.common.l.a()) {
                                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "permission check failed, kill service!");
                                this.a.d();
                                com.tencent.android.tpush.service.e.m.y(n.f());
                            }
                            a.a().a(n.a);
                        }
                        com.tencent.android.tpush.service.channel.b.a().b();
                        this.a.e();
                        com.tencent.android.tpush.service.channel.b.a().g();
                        com.tencent.android.tpush.common.g.a().a(new p(this), 20000L);
                    }
                    break;
                case 2:
                    com.tencent.android.tpush.service.channel.b.a().b();
                    break;
                case 3:
                    com.tencent.android.tpush.service.channel.b.a().c();
                    break;
                case 4:
                    com.tencent.android.tpush.a.a.e("PushServiceManager", "go to slave to main service ...");
                    if (this.a.n()) {
                        com.tencent.android.tpush.a.a.e("PushServiceManager", "swicth main service ...");
                        if (!n.h) {
                            if (XGPushConfig.enableDebug) {
                                com.tencent.android.tpush.a.a.f("PushServiceManager", "Service's first running at " + n.a.getPackageName() + " version : 3.24");
                            }
                            boolean unused3 = n.h = true;
                            if (!com.tencent.android.tpush.common.l.a()) {
                                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "permission check failed, kill service!");
                                this.a.d();
                                com.tencent.android.tpush.service.e.m.y(n.f());
                            }
                            a.a().a(n.a);
                        }
                        com.tencent.android.tpush.service.channel.b.a().b();
                        this.a.e();
                        com.tencent.android.tpush.service.channel.b.a().g();
                        com.tencent.android.tpush.common.g.a().a(new q(this), 20000L);
                    }
                    break;
                default:
                    com.tencent.android.tpush.a.a.i("PushServiceManager", "unknown handler msg = " + message.what);
                    break;
            }
        }
    }
}
