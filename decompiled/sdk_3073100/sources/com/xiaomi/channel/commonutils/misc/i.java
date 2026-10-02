package com.xiaomi.channel.commonutils.misc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class i extends Handler {
    final /* synthetic */ h a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    i(h hVar, Looper looper) {
        super(looper);
        this.a = hVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        h.b bVar = (h.b) message.obj;
        if (message.what == 0) {
            bVar.a();
        } else if (message.what == 1) {
            bVar.c();
        }
        super.handleMessage(message);
    }
}
