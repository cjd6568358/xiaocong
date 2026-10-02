package com.baidu.location;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements ServiceConnection {
    final /* synthetic */ LocationClient a;

    b(LocationClient locationClient) {
        this.a = locationClient;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.a.g = new Messenger(iBinder);
        if (this.a.g == null) {
            return;
        }
        this.a.e = true;
        Log.d("baidu_location_client", "baidu location connected ...");
        if (this.a.x) {
            this.a.h.obtainMessage(2).sendToTarget();
            return;
        }
        try {
            Message messageObtain = Message.obtain((Handler) null, 11);
            messageObtain.replyTo = this.a.i;
            messageObtain.setData(this.a.c());
            this.a.g.send(messageObtain);
            this.a.e = true;
            if (this.a.c != null) {
                if (this.a.A.booleanValue()) {
                }
                this.a.h.obtainMessage(4).sendToTarget();
            }
        } catch (Exception e) {
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.a.g = null;
        this.a.e = false;
    }
}
