package com.huawei.hms.api.internal;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: BindingFailedResolution.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Handler.Callback {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message == null || message.what != 2) {
            return false;
        }
        com.huawei.hms.support.log.a.d("BindingFailedResolution", "In connect, bind core try timeout");
        this.a.b(false);
        return true;
    }
}
