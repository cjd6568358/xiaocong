package com.youzan.androidsdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionItemModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f533;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private int f534;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private int f535;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private String f536;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f537;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f538;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private boolean f539;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f540;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f541;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f542;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f543;

    public PromotionItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f537 = o.optString("end_date");
            this.f538 = o.optString("promotion_name");
            this.f539 = o.optBoolean("can_join_cart");
            this.f540 = o.optString("sku_id_list");
            this.f543 = o.optString("promotion_id");
            this.f533 = o.optString("sku_price_list");
            this.f534 = o.optInt("stock");
            this.f535 = o.optInt("promotion_type_id");
            this.f541 = o.optString("desc");
            this.f542 = o.optString("start_date");
            this.f536 = o.optString("promotion_alias");
        }
    }

    public String getEndDate() {
        return this.f537;
    }

    public String getPromotionName() {
        return this.f538;
    }

    public boolean isCanJoinCart() {
        return this.f539;
    }

    public String getSkuIdList() {
        return this.f540;
    }

    public String getPromotionId() {
        return this.f543;
    }

    public String getSkuPriceList() {
        return this.f533;
    }

    public int getStock() {
        return this.f534;
    }

    public int getPromotionTypeId() {
        return this.f535;
    }

    public String getDesc() {
        return this.f541;
    }

    public String getStartDate() {
        return this.f542;
    }

    public String getPromotionAlias() {
        return this.f536;
    }
}
