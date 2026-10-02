package com.tencent.android.tpush.rpc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.tencent.android.tpush.service.n;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGRemoteService extends Service {
    private b a = new h();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        n.a(getApplication(), "pullup", 1000L);
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        n.d(getApplicationContext());
        return this.a;
    }
}
