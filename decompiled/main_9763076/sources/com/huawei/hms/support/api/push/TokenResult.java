package com.huawei.hms.support.api.push;

import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.entity.push.TokenResp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TokenResult extends Result {
    protected TokenResp resp;

    public void setTokenRes(TokenResp tokenResp) {
        this.resp = tokenResp;
    }

    public TokenResp getTokenRes() {
        return this.resp;
    }
}
