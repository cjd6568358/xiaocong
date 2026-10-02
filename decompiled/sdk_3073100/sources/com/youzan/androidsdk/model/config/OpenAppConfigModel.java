package com.youzan.androidsdk.model.config;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class OpenAppConfigModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private boolean f119;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f120;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private boolean f121;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f122;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private boolean f123;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private boolean f124;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private boolean f125;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private boolean f126;

    public OpenAppConfigModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f122 = o.optBoolean("hide_foot_nav");
            this.f123 = o.optBoolean("hide_shop_nav");
            this.f124 = o.optBoolean("hide_pay_success_page");
            this.f125 = o.optBoolean("hide_share_order");
            this.f126 = o.optBoolean("hide_stroll");
            this.f119 = o.optBoolean("use_native_address");
            this.f120 = o.optBoolean("bind_yz_account");
            this.f121 = o.optBoolean("login_without_pwd");
        }
    }

    public boolean getBindYzAccount() {
        return this.f120;
    }
}
