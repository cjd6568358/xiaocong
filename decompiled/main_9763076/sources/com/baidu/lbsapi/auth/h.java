package com.baidu.lbsapi.auth;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h extends Handler {
    final /* synthetic */ LBSAuthManager a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    h(LBSAuthManager lBSAuthManager, Looper looper) {
        super(looper);
        this.a = lBSAuthManager;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (a.a) {
            a.a("handleMessage !!");
        }
        LBSAuthManagerListener lBSAuthManagerListener = (LBSAuthManagerListener) LBSAuthManager.f.get(message.getData().getString("listenerKey"));
        if (a.a) {
            a.a("handleMessage listener = " + lBSAuthManagerListener);
        }
        if (lBSAuthManagerListener != null) {
            lBSAuthManagerListener.onAuthResult(message.what, message.obj.toString());
        }
    }
}
