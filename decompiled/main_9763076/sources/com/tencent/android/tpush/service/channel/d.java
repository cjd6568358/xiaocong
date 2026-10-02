package com.tencent.android.tpush.service.channel;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d extends Handler {
    final /* synthetic */ b a;

    d(b bVar) {
        this.a = bVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 1000:
                try {
                    this.a.e();
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.i("TpnsChannel", "checkAndSetupClient expected:" + th);
                    return;
                }
                break;
            default:
                com.tencent.android.tpush.a.a.i("TpnsChannel", "Unexpected: unhandled msg - " + message.what);
                break;
        }
    }
}
