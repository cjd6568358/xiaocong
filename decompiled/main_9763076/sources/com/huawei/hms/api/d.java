package com.huawei.hms.api;

import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.entity.core.ConnectResp;

/* JADX INFO: compiled from: HuaweiApiClientImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements Runnable {
    final /* synthetic */ ResolveResult a;
    final /* synthetic */ HuaweiApiClientImpl.a b;

    d(HuaweiApiClientImpl.a aVar, ResolveResult resolveResult) {
        this.b = aVar;
        this.a = resolveResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        HuaweiApiClientImpl.this.b((ResolveResult<ConnectResp>) this.a);
    }
}
