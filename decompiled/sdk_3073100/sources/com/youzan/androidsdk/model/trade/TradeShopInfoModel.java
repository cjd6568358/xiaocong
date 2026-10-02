package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeShopInfoModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f528;

    public TradeShopInfoModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f528 = o.optString("shop_name");
        }
    }

    public String getShopName() {
        return this.f528;
    }
}
