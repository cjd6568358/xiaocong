package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidPromotionModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f523;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f524;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f525;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f526;

    public TradePaidPromotionModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f523 = o.optString("detailUrl");
            this.f524 = o.optString("imgUrl");
            this.f525 = o.optString("promotionType");
            this.f526 = o.optString("title");
        }
    }

    public String getDetailUrl() {
        return this.f523;
    }

    public String getImgUrl() {
        return this.f524;
    }

    public String getPromotionType() {
        return this.f525;
    }

    public String getTitle() {
        return this.f526;
    }
}
