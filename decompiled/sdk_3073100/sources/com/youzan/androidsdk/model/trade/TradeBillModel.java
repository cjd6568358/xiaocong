package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeBillModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f283;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f284;

    public TradeBillModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f283 = o.optString("book_key");
            this.f284 = o.optString("url");
        }
    }

    public String getBookKey() {
        return this.f283;
    }

    public String getUrl() {
        return this.f284;
    }
}
