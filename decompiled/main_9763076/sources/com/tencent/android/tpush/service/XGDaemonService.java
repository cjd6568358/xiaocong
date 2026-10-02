package com.tencent.android.tpush.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGDaemonService extends Service {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        com.tencent.android.tpush.a.a.e("XGDaemonService", "XGDaemonService onCreate");
        n.a((Service) this);
        com.tencent.android.tpush.a.a.e("XGDaemonService", "stopSelf");
        stopSelf();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }
}
