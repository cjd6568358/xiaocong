package com.huawei.hms.api;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: HuaweiApiClientImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Handler.Callback {
    final /* synthetic */ HuaweiApiClientImpl a;

    b(HuaweiApiClientImpl huaweiApiClientImpl) {
        this.a = huaweiApiClientImpl;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message == null || message.what != 2) {
            return false;
        }
        com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "In connect, bind core service time out");
        if (this.a.f.get() != 5) {
            return true;
        }
        this.a.a(1);
        if (this.a.l == null) {
            return true;
        }
        this.a.l.onConnectionFailed(new ConnectionResult(6));
        return true;
    }
}
