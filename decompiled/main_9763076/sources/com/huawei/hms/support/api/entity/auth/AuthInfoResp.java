package com.huawei.hms.support.api.entity.auth;

import com.huawei.hms.core.aidl.a.a;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AuthInfoResp extends AbstractResp {

    @a
    private AuthorizationInfo authInfo;

    @Override // com.huawei.hms.support.api.entity.auth.AbstractResp
    public int getRtnCode() {
        return super.getRtnCode();
    }

    public AuthorizationInfo getAuthInfo() {
        return this.authInfo;
    }

    public void setAuthInfo(AuthorizationInfo authorizationInfo) {
        this.authInfo = authorizationInfo;
    }
}
