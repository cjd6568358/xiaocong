package com.baidu.location.b;

import android.location.Location;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f extends Handler {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (com.baidu.location.f.isServing) {
            switch (message.what) {
                case 1:
                    this.a.e((Location) message.obj);
                    break;
                case 3:
                    this.a.a("&og=1", (Location) message.obj);
                    break;
                case 4:
                    this.a.a("&og=2", (Location) message.obj);
                    break;
            }
        }
    }
}
