package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartAddResultModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f298;

    public TradeCartAddResultModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f298 = o.optBoolean("is_success");
        }
    }

    public boolean isSuccess() {
        return this.f298;
    }
}
