package com.youzan.androidsdk.model.shop;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ShopStatusModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private boolean f270;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f271;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private boolean f272;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f273;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private boolean f274;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private boolean f275;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private boolean f276;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private boolean f277;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private boolean f278;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private boolean f279;

    public ShopStatusModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f273 = o.optBoolean("is_bind_weixin");
            this.f274 = o.optBoolean("is_weixin_service");
            this.f275 = o.optBoolean("is_weixin_unauthorized_service");
            this.f276 = o.optBoolean("is_weixin_publisher");
            this.f279 = o.optBoolean("is_weixin_unauthorized_publisher");
            this.f270 = o.optBoolean("is_secured_transactions");
            this.f271 = o.optBoolean("is_set_shopping_cart");
            this.f272 = o.optBoolean("is_set_buy_record");
            this.f277 = o.optBoolean("is_set_customer_reviews");
            this.f278 = o.optBoolean("is_set_fans_only");
        }
    }

    public boolean isBindWeixin() {
        return this.f273;
    }

    public boolean isSecuredTransactions() {
        return this.f270;
    }

    public boolean isSetBuyRecord() {
        return this.f272;
    }

    public boolean isSetCustomerReviews() {
        return this.f277;
    }

    public boolean isSetFansOnly() {
        return this.f278;
    }

    public boolean isSetShoppingCart() {
        return this.f271;
    }

    public boolean isWeixinPublisher() {
        return this.f276;
    }

    public boolean isWeixinService() {
        return this.f274;
    }

    public boolean isWeixinUnauthorizedPublisher() {
        return this.f279;
    }

    public boolean isWeixinUnauthorizedService() {
        return this.f275;
    }
}
