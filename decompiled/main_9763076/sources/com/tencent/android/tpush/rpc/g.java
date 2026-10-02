package com.tencent.android.tpush.rpc;

import android.content.ServiceConnection;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.n;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends e {
    private ServiceConnection a;

    public void a(ServiceConnection serviceConnection) {
        this.a = serviceConnection;
    }

    @Override // com.tencent.android.tpush.rpc.d
    public void a() {
        try {
            if (n.f() != null) {
                n.f().unbindService(this.a);
                this.a = null;
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "unBind", th);
        }
    }
}
