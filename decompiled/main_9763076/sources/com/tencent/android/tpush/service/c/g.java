package com.tencent.android.tpush.service.c;

import android.content.Intent;
import android.content.ServiceConnection;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.service.n;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g implements Runnable {
    final /* synthetic */ Intent a;
    final /* synthetic */ a b;
    private com.tencent.android.tpush.rpc.a c;
    private com.tencent.android.tpush.rpc.g d = new com.tencent.android.tpush.rpc.g();
    private ServiceConnection e = new h(this);

    g(a aVar, Intent intent) {
        this.b = aVar;
        this.a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.setAction(this.a.getPackage() + Constants.RPC_SUFFIX);
            this.d.a(this.e);
            if (!n.f().bindService(this.a, this.e, 1)) {
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "Failed Send AIDL" + this.a + " failed  msgid = " + this.a.getLongExtra(MessageKey.MSG_ID, -1L));
                com.tencent.android.tpush.b.d.a().a(n.f(), this.a.getPackage(), this.a);
            } else {
                com.tencent.android.tpush.a.a.c("SrvMessageManager", "Succeed Send AIDL" + this.a + " success  msgid = " + this.a.getLongExtra(MessageKey.MSG_ID, -1L));
                this.b.b(n.f(), this.a);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SrvMessageManager", "SendBroadcastByRPC -> bindService", th);
            com.tencent.android.tpush.b.d.a().a(n.f(), this.a.getPackage(), this.a);
        }
    }
}
