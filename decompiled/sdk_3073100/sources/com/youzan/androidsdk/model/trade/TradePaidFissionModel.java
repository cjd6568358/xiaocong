package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidFissionModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f492;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f493;

    public TradePaidFissionModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f492 = o.optString("url");
            this.f493 = o.optInt("num");
        }
    }

    public String getUrl() {
        return this.f492;
    }

    public int getNum() {
        return this.f493;
    }
}
