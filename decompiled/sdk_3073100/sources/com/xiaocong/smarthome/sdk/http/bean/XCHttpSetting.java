package com.xiaocong.smarthome.sdk.http.bean;

import com.xiaocong.smarthome.network.bean.CommonHttpSetting;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCHttpSetting extends CommonHttpSetting {
    private boolean needSign = true;
    private boolean needConfig = false;

    public boolean getNeedSign() {
        return this.needSign;
    }

    public void setNeedSign(boolean needSign) {
        this.needSign = needSign;
    }

    public boolean getNeedConfig() {
        return this.needConfig;
    }

    public void setNeedConfig(boolean needConfig) {
        this.needConfig = needConfig;
    }
}
