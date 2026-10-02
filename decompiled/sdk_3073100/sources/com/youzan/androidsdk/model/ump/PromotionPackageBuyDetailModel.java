package com.youzan.androidsdk.model.ump;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionPackageBuyDetailModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f554;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f555;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f556;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f557;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private List<PromotionPackageBuyGoodsModel> f558;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f559;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f560;

    public PromotionPackageBuyDetailModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f556 = o.optString("end_date");
            this.f557 = o.optString("promotion_name");
            this.f559 = o.optInt("promotion_id");
            this.f560 = o.optInt("promotion_type_id");
            this.f554 = o.optString("desc");
            this.f555 = o.optString("start_date");
            JSONArray itemsArray = o.optJSONArray("goods_list");
            if (itemsArray != null && itemsArray.length() > 0) {
                this.f558 = new ArrayList(itemsArray.length());
                for (int i = 0; i < itemsArray.length(); i++) {
                    this.f558.add(new PromotionPackageBuyGoodsModel(itemsArray.optJSONObject(i)));
                }
            }
        }
    }

    public String getEndDate() {
        return this.f556;
    }

    public String getPromotionName() {
        return this.f557;
    }

    public List<PromotionPackageBuyGoodsModel> getGoodsList() {
        return this.f558;
    }

    public int getPromotionId() {
        return this.f559;
    }

    public int getPromotionTypeId() {
        return this.f560;
    }

    public String getDesc() {
        return this.f554;
    }

    public String getStartDate() {
        return this.f555;
    }
}
