package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartPayWayModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f347;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f348;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f349;

    public TradeCartPayWayModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f347 = o.optString("code");
            this.f348 = o.optString("name");
            this.f349 = o.optInt("key");
        }
    }

    public String getCode() {
        return this.f347;
    }

    public String getName() {
        return this.f348;
    }

    public int getKey() {
        return this.f349;
    }
}
