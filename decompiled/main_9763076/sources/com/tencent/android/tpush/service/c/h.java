package com.tencent.android.tpush.service.c;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h implements ServiceConnection {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.a.c = com.tencent.android.tpush.rpc.b.a(iBinder);
            if (this.a.c != null) {
                this.a.c.a(this.a.a.toURI(), this.a.d);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "SendBroadcastByRPC", th);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.a.e = null;
        this.a.c = null;
        this.a.d = null;
    }
}
