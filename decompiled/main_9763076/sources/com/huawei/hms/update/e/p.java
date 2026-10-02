package com.huawei.hms.update.e;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: SilentUpdateWizard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p extends Handler {
    final /* synthetic */ o a;

    p(o oVar) {
        this.a = oVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Bundle bundle = (Bundle) message.obj;
        switch (message.what) {
            case 101:
                this.a.a(bundle);
                break;
            case 102:
                this.a.b(bundle);
                break;
            case 103:
                this.a.c(bundle);
                break;
        }
    }
}
