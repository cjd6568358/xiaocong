package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeSkuModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f529;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f530;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f531;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f532;

    public TradeSkuModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f529 = o.optInt("k_id");
            this.f530 = o.optString("k");
            this.f531 = o.optInt("v_id");
            this.f532 = o.optString("v");
        }
    }

    public int getkId() {
        return this.f529;
    }

    public String getK() {
        return this.f530;
    }

    public int getvId() {
        return this.f531;
    }

    public String getV() {
        return this.f532;
    }
}
