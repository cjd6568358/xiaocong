package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidVirtualModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f527;

    public TradePaidVirtualModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f527 = o.optString("url");
        }
    }

    public String getUrl() {
        return this.f527;
    }
}
