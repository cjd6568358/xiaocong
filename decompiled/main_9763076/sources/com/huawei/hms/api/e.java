package com.huawei.hms.api;

import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.entity.core.DisconnectResp;

/* JADX INFO: compiled from: HuaweiApiClientImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements Runnable {
    final /* synthetic */ ResolveResult a;
    final /* synthetic */ HuaweiApiClientImpl.b b;

    e(HuaweiApiClientImpl.b bVar, ResolveResult resolveResult) {
        this.b = bVar;
        this.a = resolveResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        HuaweiApiClientImpl.this.a((ResolveResult<DisconnectResp>) this.a);
    }
}
