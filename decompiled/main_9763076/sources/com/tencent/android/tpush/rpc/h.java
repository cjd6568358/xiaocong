package com.tencent.android.tpush.rpc;

import android.content.Intent;
import com.tencent.android.tpush.b.i;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.n;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h extends b {
    @Override // com.tencent.android.tpush.rpc.a
    public void a(String str, d dVar) {
        try {
            i.a(n.f()).a(Intent.parseUri(str, 0));
            dVar.a();
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "Show", th);
        }
    }

    @Override // com.tencent.android.tpush.rpc.a
    public void a() {
        try {
            n.a(n.f());
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "startService", th);
        }
    }

    @Override // com.tencent.android.tpush.rpc.a
    public void b() {
    }
}
