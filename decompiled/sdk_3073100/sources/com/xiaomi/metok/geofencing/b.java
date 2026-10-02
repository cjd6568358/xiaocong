package com.xiaomi.metok.geofencing;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class b implements ServiceConnection {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.v("GeoFencingServiceWrapper", "*** GeoFencingService connected (yay!)");
        this.a.b = c.a.a(iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.v("GeoFencingServiceWrapper", "*** GeoFencingService disconnected (boo!)");
        this.a.b = null;
    }
}
