package com.huawei.hms.support.api.push;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.entity.push.TokenResp;
import com.huawei.hms.support.api.push.a.a.a.c;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GetTokenPendingResultImpl extends com.huawei.hms.support.api.a<TokenResult, TokenResp> {
    private ApiClient a;

    public GetTokenPendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        super(apiClient, str, iMessageEntity);
        this.a = apiClient;
    }

    @Override // com.huawei.hms.support.api.a
    public TokenResult onComplete(TokenResp tokenResp) {
        TokenResult tokenResult = new TokenResult();
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("GetTokenPendingResultImpl", "get token complete, the return code:" + tokenResp.getRetCode());
        }
        tokenResult.setStatus(new Status(tokenResp.getRetCode()));
        tokenResult.setTokenRes(tokenResp);
        return tokenResult;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.huawei.hms.support.api.a
    public TokenResult onError(int i) {
        TokenResult tokenResult = new TokenResult();
        tokenResult.setStatus(new Status(i));
        new c(this.a.getContext(), "push_client_self_info").a("hasRequestToken", false);
        return tokenResult;
    }
}
