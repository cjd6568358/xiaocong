package com.alibaba.mtl.appmonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppMonitorService extends Service {
    IMonitor a = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.a == null) {
            this.a = new Monitor(getApplication());
        }
        return (IBinder) this.a;
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (this.a != null) {
            try {
                this.a.triggerUpload();
            } catch (RemoteException e) {
            }
        }
        super.onDestroy();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        if (this.a != null) {
            try {
                this.a.triggerUpload();
            } catch (RemoteException e) {
            }
        }
        super.onLowMemory();
    }
}
