package com.huawei.hms.support.api.a;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.entity.core.ConnectResp;

/* JADX INFO: compiled from: ConnectService.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class b extends com.huawei.hms.support.api.a<ResolveResult<ConnectResp>, ConnectResp> {
    b(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        super(apiClient, str, iMessageEntity);
    }

    @Override // com.huawei.hms.support.api.a
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ResolveResult<ConnectResp> onComplete(ConnectResp connectResp) {
        ResolveResult<ConnectResp> resolveResult = new ResolveResult<>(connectResp);
        resolveResult.setStatus(Status.SUCCESS);
        return resolveResult;
    }

    @Override // com.huawei.hms.support.api.a
    protected boolean checkApiClient(ApiClient apiClient) {
        return apiClient != null;
    }
}
