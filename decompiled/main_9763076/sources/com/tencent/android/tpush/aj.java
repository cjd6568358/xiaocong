package com.tencent.android.tpush;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.XGWatchdog;
import com.tencent.android.tpush.service.cache.CacheManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class aj implements Runnable {
    private Context a;
    private Intent b;
    private XGIOperateCallback c;
    private int d;
    private int e;

    public aj(XGIOperateCallback xGIOperateCallback, Context context, Intent intent, int i, int i2) {
        this.e = 0;
        this.c = xGIOperateCallback;
        this.a = context;
        this.b = intent;
        this.d = i;
        this.e = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.b.removeExtra("storage");
            if (this.d == 1) {
                String stringExtra = this.b.getStringExtra("data");
                switch (this.b.getIntExtra("operation", -1)) {
                    case 0:
                        this.c.onSuccess(stringExtra, this.b.getIntExtra("flag", -1));
                        RegisterEntity registerEntity = new RegisterEntity();
                        if (this.e == 0) {
                            com.tencent.android.tpush.common.n.b(this.a, ".firstregister", 0);
                            registerEntity.state = 0;
                        } else {
                            registerEntity.state = 1;
                        }
                        registerEntity.accessId = this.b.getLongExtra("accId", 0L);
                        registerEntity.packageName = this.a.getPackageName();
                        registerEntity.token = stringExtra;
                        registerEntity.timestamp = System.currentTimeMillis() / 1000;
                        registerEntity.xgSDKVersion = 3.24f;
                        registerEntity.appVersion = com.tencent.android.tpush.common.t.f(this.a);
                        CacheManager.setCurrentAppRegisterEntity(this.a, registerEntity);
                        if (!com.tencent.android.tpush.common.t.c(registerEntity.packageName)) {
                            XGPushManager.lastSuccessRegisterMap.put(registerEntity.packageName, Long.valueOf(System.currentTimeMillis() / 1000));
                        }
                        if ((XGPushConfig.isUsedOtherPush(this.a) && com.tencent.android.tpush.c.e.a(this.a).g()) || (XGPushConfig.isUsedFcmPush(this.a) && com.tencent.android.tpush.common.s.a(this.a).c())) {
                            com.tencent.android.tpush.c.b.a(this.a);
                        }
                        break;
                    case 1:
                        this.c.onFail(stringExtra, this.b.getIntExtra("code", -1), this.b.getStringExtra("msg"));
                        break;
                }
            } else if (this.d == 0) {
                switch (this.b.getIntExtra("operation", -1)) {
                    case 100:
                        XGPushManager.c(this.a, this.b, this.c);
                        break;
                    case 101:
                        XGPushManager.d(this.a, this.b, this.c);
                        break;
                }
            }
            XGWatchdog.getInstance(this.a).sendAllLocalXGAppList();
            com.tencent.android.tpush.common.a.a(this.a);
            com.tencent.android.tpush.service.aa.a(this.a).a();
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(XGPushManager.a, "OperateRunnable", th);
        }
    }
}
