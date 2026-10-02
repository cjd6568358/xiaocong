package com.huawei.hms.api;

import com.huawei.hms.core.aidl.ResponseHeader;
import com.huawei.hms.core.aidl.f;
import com.huawei.hms.support.api.client.BundleResult;
import com.huawei.hms.support.api.client.ResultCallback;

/* JADX INFO: compiled from: HuaweiApiClientImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends com.huawei.hms.core.aidl.d.a {
    final /* synthetic */ ResultCallback a;
    final /* synthetic */ HuaweiApiClientImpl b;

    c(HuaweiApiClientImpl huaweiApiClientImpl, ResultCallback resultCallback) {
        this.b = huaweiApiClientImpl;
        this.a = resultCallback;
    }

    @Override // com.huawei.hms.core.aidl.d
    public void a(com.huawei.hms.core.aidl.b bVar) {
        if (bVar != null) {
            f fVarA = com.huawei.hms.core.aidl.a.a(bVar.c());
            ResponseHeader responseHeader = new ResponseHeader();
            fVarA.a(bVar.b, responseHeader);
            this.a.onResult(new BundleResult(responseHeader.getStatusCode(), bVar.a()));
            return;
        }
        this.a.onResult(new BundleResult(-1, null));
    }
}
