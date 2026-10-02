package com.youzan.androidsdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionPointsExchangeModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f565;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f566;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f567;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f568;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f569;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f570;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f571;

    public PromotionPointsExchangeModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f567 = o.optString("exchange_price");
            this.f568 = o.optString("end_date");
            this.f569 = o.optString("promotion_name");
            this.f570 = o.optInt("promotion_id");
            this.f571 = o.optInt("promotion_type_id");
            this.f565 = o.optInt("exchange_points");
            this.f566 = o.optString("start_date");
        }
    }

    public String getExchangePrice() {
        return this.f567;
    }

    public String getEndDate() {
        return this.f568;
    }

    public String getPromotionName() {
        return this.f569;
    }

    public int getPromotionId() {
        return this.f570;
    }

    public int getPromotionTypeId() {
        return this.f571;
    }

    public int getExchangePoints() {
        return this.f565;
    }

    public String getStartDate() {
        return this.f566;
    }
}
