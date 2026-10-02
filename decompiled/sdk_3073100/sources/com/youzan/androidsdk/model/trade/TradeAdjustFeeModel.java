package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeAdjustFeeModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f280;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f281;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f282;

    public TradeAdjustFeeModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f280 = o.optString("post_change");
            this.f281 = o.optString("change");
            this.f282 = o.optString("pay_change");
        }
    }
}
