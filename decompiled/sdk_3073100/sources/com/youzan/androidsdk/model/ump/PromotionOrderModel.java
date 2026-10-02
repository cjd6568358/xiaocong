package com.youzan.androidsdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionOrderModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f548;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f549;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f550;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f551;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f552;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f553;

    public PromotionOrderModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f549 = o.optString("promotion_name");
            this.f550 = o.optInt("promotion_id");
            this.f551 = o.optInt("promotion_type_id");
            this.f552 = o.optString("desc");
            this.f553 = o.optString("start_date");
            this.f548 = o.optString("end_date");
        }
    }

    public String getPromotionName() {
        return this.f549;
    }

    public int getPromotionId() {
        return this.f550;
    }

    public int getPromotionTypeId() {
        return this.f551;
    }

    public String getDesc() {
        return this.f552;
    }

    public String getEndDate() {
        return this.f548;
    }

    public String getStartDate() {
        return this.f553;
    }
}
