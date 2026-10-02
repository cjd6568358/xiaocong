package com.huawei.hms.support.api.push;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.support.api.ResolvePendingResult;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.entity.push.AgreementReq;
import com.huawei.hms.support.api.entity.push.AgreementResp;
import com.huawei.hms.support.api.entity.push.DeleteTokenReq;
import com.huawei.hms.support.api.entity.push.DeleteTokenResp;
import com.huawei.hms.support.api.entity.push.EnableNotifyReq;
import com.huawei.hms.support.api.entity.push.EnableNotifyResp;
import com.huawei.hms.support.api.entity.push.PushNaming;
import com.huawei.hms.support.api.entity.push.PushStateReq;
import com.huawei.hms.support.api.entity.push.PushStateResp;
import com.huawei.hms.support.api.entity.push.TokenReq;
import com.huawei.hms.support.api.push.a.a.a.c;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HuaweiPushApiImp implements HuaweiPushApi {
    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public PendingResult<TokenResult> getToken(ApiClient apiClient) {
        Context context = apiClient.getContext();
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("HuaweiPushApiImp", "get token, pkgName:" + context.getPackageName());
        }
        c cVar = new c(context, "push_client_self_info");
        cVar.a("hasRequestToken", true);
        TokenReq tokenReq = new TokenReq();
        tokenReq.setPackageName(apiClient.getPackageName());
        if (!cVar.a("hasRequestAgreement")) {
            tokenReq.setFirstTime(true);
            cVar.a("hasRequestAgreement", true);
        } else {
            tokenReq.setFirstTime(false);
        }
        return new GetTokenPendingResultImpl(apiClient, PushNaming.getToken, tokenReq);
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public void deleteToken(ApiClient apiClient, String str) throws PushException {
        Context context = apiClient.getContext();
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("HuaweiPushApiImp", "invoke method: deleteToken, pkgName:" + context.getPackageName() + com.huawei.hms.support.api.push.a.a.b.c.a(str));
        }
        if (TextUtils.isEmpty(str)) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("HuaweiPushApiImp", "token is null, can not deregister token");
            }
            throw new PushException(PushException.EXCEPITON_TOKEN_INVALID);
        }
        try {
            c cVar = new c(context, "push_client_self_info");
            if (str.equals(com.huawei.hms.support.api.push.a.a.a.a(context, "push_client_self_info", "token_info"))) {
                cVar.a("hasRequestToken", false);
                com.huawei.hms.support.api.push.a.a.a.b(context, "push_client_self_info", "token_info");
            }
            DeleteTokenReq deleteTokenReq = new DeleteTokenReq();
            deleteTokenReq.setPkgName(context.getPackageName());
            deleteTokenReq.setToken(str);
            ResolvePendingResult.build(apiClient, PushNaming.deleteToken, deleteTokenReq, DeleteTokenResp.class).get();
            com.huawei.hms.support.api.push.a.a.a.a.a(apiClient, PushNaming.deleteToken);
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("HuaweiPushApiImp", "delete token failed, e=" + e.getMessage());
            }
            throw new PushException(e + PushException.EXCEPITON_DEL_TOKEN_FAILED);
        }
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public PendingResult<HandleTagsResult> setTags(ApiClient apiClient, Map<String, String> map) throws PushException {
        return new a().a(apiClient, map);
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public PendingResult<GetTagResult> getTags(ApiClient apiClient) throws PushException {
        return new a().a(apiClient);
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public PendingResult<HandleTagsResult> deleteTags(ApiClient apiClient, List<String> list) throws PushException {
        return new a().a(apiClient, list);
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public boolean getPushState(ApiClient apiClient) {
        PushStateReq pushStateReq = new PushStateReq();
        pushStateReq.setPkgName(apiClient.getPackageName());
        ResolvePendingResult.build(apiClient, PushNaming.getPushState, pushStateReq, PushStateResp.class).get();
        com.huawei.hms.support.api.push.a.a.a.a.a(apiClient, PushNaming.getPushState);
        return true;
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public void enableReceiveNormalMsg(ApiClient apiClient, boolean z) {
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("HuaweiPushApiImp", "invoke enableReceiveNormalMsg, set flag:" + z);
        }
        new c(apiClient.getContext(), "push_switch").a("normal_msg_enable", !z);
        com.huawei.hms.support.api.push.a.a.a.a.a(apiClient, PushNaming.enableReceiveNormalMsg);
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public void enableReceiveNotifyMsg(ApiClient apiClient, boolean z) {
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("HuaweiPushApiImp", "invoke enableReceiveNotifyMsg, set flag:" + z);
        }
        EnableNotifyReq enableNotifyReq = new EnableNotifyReq();
        enableNotifyReq.setPackageName(apiClient.getPackageName());
        enableNotifyReq.setEnable(z);
        ResolvePendingResult.build(apiClient, PushNaming.setNotifyFlag, enableNotifyReq, EnableNotifyResp.class).get();
    }

    @Override // com.huawei.hms.support.api.push.HuaweiPushApi
    public void queryAgreement(ApiClient apiClient) throws PushException {
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("HuaweiPushApiImp", "invoke queryAgreement");
        }
        Context context = apiClient.getContext();
        AgreementReq agreementReq = new AgreementReq();
        agreementReq.setPkgName(context.getPackageName());
        String strA = com.huawei.hms.support.api.push.a.a.a.a(context, "push_client_self_info", "token_info");
        if (!new c(context, "push_client_self_info").a("hasRequestAgreement")) {
            agreementReq.setFirstTime(true);
        } else {
            agreementReq.setFirstTime(false);
        }
        agreementReq.setToken(strA);
        ResolvePendingResult.build(apiClient, PushNaming.handleAgreement, agreementReq, AgreementResp.class).get();
    }
}
