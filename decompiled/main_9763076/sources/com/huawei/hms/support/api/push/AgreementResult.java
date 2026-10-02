package com.huawei.hms.support.api.push;

import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.entity.push.AgreementResp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AgreementResult extends Result {
    protected AgreementResp resp;

    public void setAgreementRes(AgreementResp agreementResp) {
        this.resp = agreementResp;
    }

    public AgreementResp getAgreementRes() {
        return this.resp;
    }

    public boolean isAgree() {
        if (this.resp != null) {
            return this.resp.isAgree();
        }
        return false;
    }
}
