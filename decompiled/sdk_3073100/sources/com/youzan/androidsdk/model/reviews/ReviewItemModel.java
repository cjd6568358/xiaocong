package com.youzan.androidsdk.model.reviews;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ReviewItemModel {

    /* JADX INFO: renamed from: ʹ, reason: contains not printable characters */
    private int f230;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f231;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private int f232;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f233;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private int f234;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private int f235;

    /* JADX INFO: renamed from: ˈ, reason: contains not printable characters */
    private int f236;

    /* JADX INFO: renamed from: ˉ, reason: contains not printable characters */
    private String f237;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f238;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f239;

    /* JADX INFO: renamed from: ˌ, reason: contains not printable characters */
    private int f240;

    /* JADX INFO: renamed from: ˍ, reason: contains not printable characters */
    private boolean f241;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f242;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f243;

    /* JADX INFO: renamed from: ˑ, reason: contains not printable characters */
    private int f244;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f245;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f246;

    /* JADX INFO: renamed from: ՙ, reason: contains not printable characters */
    private int f247;

    /* JADX INFO: renamed from: י, reason: contains not printable characters */
    private List<String> f248;

    /* JADX INFO: renamed from: ـ, reason: contains not printable characters */
    private String f249;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f250;

    /* JADX INFO: renamed from: ᐧ, reason: contains not printable characters */
    private String f251;

    /* JADX INFO: renamed from: ᐨ, reason: contains not printable characters */
    private int f252;

    /* JADX INFO: renamed from: ﹳ, reason: contains not printable characters */
    private int f253;

    /* JADX INFO: renamed from: ﾞ, reason: contains not printable characters */
    private boolean f254;

    public ReviewItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f238 = o.optString("fansNickname");
            this.f239 = o.getInt("goodsId");
            this.f242 = o.optInt("supplierGoodsId");
            this.f243 = o.optInt("buyerId");
            this.f250 = o.optInt("likeNum");
            this.f231 = o.optString("fansPicture");
            this.f232 = o.optInt("rate");
            this.f233 = o.optString("review");
            this.f245 = o.optString("createdTime");
            this.f246 = o.optString("alias");
            this.f234 = o.optInt("id");
            this.f235 = o.optInt("skuId");
            this.f236 = o.optInt("logiRate");
            this.f237 = o.optString("orderNo");
            this.f240 = o.optInt("fansId");
            this.f241 = o.optBoolean("otherShop");
            this.f244 = o.optInt("kdtId");
            this.f249 = o.optString("updateTime");
            this.f251 = o.optString("sellerComment");
            this.f252 = o.optInt("supplierKdtId");
            this.f253 = o.optInt("descRate");
            this.f254 = o.optBoolean("ilike");
            this.f230 = o.optInt("servRate");
            this.f247 = o.optInt("fansType");
            JSONArray arrayObj = o.optJSONArray("picture");
            if (arrayObj != null && arrayObj.length() > 0) {
                int length = arrayObj.length();
                this.f248 = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    this.f248.add(arrayObj.optString(i));
                }
            }
        }
    }

    public String getAlias() {
        return this.f246;
    }

    public int getBuyerId() {
        return this.f243;
    }

    public String getCreatedTime() {
        return this.f245;
    }

    public int getDescRate() {
        return this.f253;
    }

    public int getFansId() {
        return this.f240;
    }

    public String getFansNickname() {
        return this.f238;
    }

    public String getFansPicture() {
        return this.f231;
    }

    public int getFansType() {
        return this.f247;
    }

    public int getGoodsId() {
        return this.f239;
    }

    public int getId() {
        return this.f234;
    }

    public boolean isIlike() {
        return this.f254;
    }

    public int getKdtId() {
        return this.f244;
    }

    public int getLikeNum() {
        return this.f250;
    }

    public int getLogiRate() {
        return this.f236;
    }

    public String getOrderNo() {
        return this.f237;
    }

    public boolean isOtherShop() {
        return this.f241;
    }

    public List<String> getPicture() {
        return this.f248;
    }

    public int getRate() {
        return this.f232;
    }

    public String getReview() {
        return this.f233;
    }

    public String getSellerComment() {
        return this.f251;
    }

    public int getServRate() {
        return this.f230;
    }

    public int getSkuId() {
        return this.f235;
    }

    public int getSupplierGoodsId() {
        return this.f242;
    }

    public int getSupplierKdtId() {
        return this.f252;
    }

    public String getUpdateTime() {
        return this.f249;
    }
}
