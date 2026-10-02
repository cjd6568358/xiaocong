package com.huawei.hms.api.internal;

import android.os.Bundle;
import com.huawei.hms.api.HuaweiApiClientImpl;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.RequestHeader;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.transport.DatagramTransport;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IPCTransport implements DatagramTransport {
    private final String a;
    private final IMessageEntity b;
    private final Class<? extends IMessageEntity> c;

    public IPCTransport(String str, IMessageEntity iMessageEntity, Class<? extends IMessageEntity> cls) {
        this.a = str;
        this.b = iMessageEntity;
        this.c = cls;
    }

    @Override // com.huawei.hms.support.api.transport.DatagramTransport
    public final void a(ApiClient apiClient, DatagramTransport.a aVar) {
        int iA = a(apiClient, new f(this.c, aVar));
        if (iA != 0) {
            aVar.a(iA, null);
        }
    }

    @Override // com.huawei.hms.support.api.transport.DatagramTransport
    public final void b(ApiClient apiClient, DatagramTransport.a aVar) {
        a(apiClient, aVar);
    }

    private int a(ApiClient apiClient, com.huawei.hms.core.aidl.d dVar) {
        com.huawei.hms.core.aidl.b bVar = new com.huawei.hms.core.aidl.b(this.a, g.a().b());
        com.huawei.hms.core.aidl.f fVarA = com.huawei.hms.core.aidl.a.a(bVar.c());
        bVar.a(fVarA.a(this.b, new Bundle()));
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setAppID(apiClient.getAppID());
        requestHeader.setPackageName(apiClient.getPackageName());
        requestHeader.setSdkVersion(20502300);
        if (apiClient instanceof HuaweiApiClientImpl) {
            requestHeader.setSessionId(apiClient.getSessionId());
        }
        bVar.b = fVarA.a(requestHeader, new Bundle());
        try {
            ((HuaweiApiClientImpl) apiClient).getService().a(bVar, dVar);
            return 0;
        } catch (Exception e) {
            return CommonCode.ErrorCode.INTERNAL_ERROR;
        }
    }
}
