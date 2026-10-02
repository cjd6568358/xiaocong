package com.youzan.androidsdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionPackageBuyGoodsModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f561;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f562;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f563;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f564;

    public PromotionPackageBuyGoodsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f561 = o.optString("price");
            this.f562 = o.optString("pic_thumb_url");
            this.f563 = o.optString("title");
            this.f564 = o.optString("pic_url");
        }
    }

    public String getPrice() {
        return this.f561;
    }

    public String getPicThumbUrl() {
        return this.f562;
    }

    public String getTitle() {
        return this.f563;
    }

    public String getPicUrl() {
        return this.f564;
    }
}
