package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidMemberCardModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f494;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f495;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f496;

    public TradePaidMemberCardModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f494 = o.optString("url");
            this.f495 = o.optString("cardNo");
            this.f496 = o.optString("type");
        }
    }

    public String getUrl() {
        return this.f494;
    }

    public String getCardNo() {
        return this.f495;
    }

    public String getType() {
        return this.f496;
    }
}
