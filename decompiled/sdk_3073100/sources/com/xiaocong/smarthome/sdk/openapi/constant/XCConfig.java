package com.xiaocong.smarthome.sdk.openapi.constant;

import android.text.TextUtils;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class XCConfig {
    private String appId;
    private String appKey;
    private String token;

    public static XCConfig getInstance() {
        return XCConfigHolder.INSTANCE;
    }

    public String getAppId() {
        return TextUtils.isEmpty(this.appId) ? XCHelp.getString("appId", "") : this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return TextUtils.isEmpty(this.appKey) ? XCHelp.getString("appKey", "") : this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getToken() {
        return TextUtils.isEmpty(this.token) ? XCHelp.getString("NLC_ahe_9l", "") : this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static final class XCConfigHolder {
        private static final XCConfig INSTANCE = new XCConfig();
    }
}
