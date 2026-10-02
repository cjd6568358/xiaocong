package com.youzan.androidsdk.model.trade;

import com.youzan.androidsdk.model.goods.GoodsShareModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private TradePaidPromotionModel f497;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f498;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private TradePaidMemberCardModel f499;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private TradePaidVirtualModel f500;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private boolean f501;

    /* JADX INFO: renamed from: ˈ, reason: contains not printable characters */
    private boolean f502;

    /* JADX INFO: renamed from: ˉ, reason: contains not printable characters */
    private boolean f503;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f504;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private boolean f505;

    /* JADX INFO: renamed from: ˌ, reason: contains not printable characters */
    private TradePaidFissionModel f506;

    /* JADX INFO: renamed from: ˍ, reason: contains not printable characters */
    private boolean f507;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private boolean f508;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private boolean f509;

    /* JADX INFO: renamed from: ˑ, reason: contains not printable characters */
    private TradePaidOrderModel f510;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private boolean f511;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private boolean f512;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private GoodsShareModel f513;

    public TradePaidModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f504 = o.optBoolean("isSelf");
            this.f505 = o.optBoolean("isHasFission");
            this.f508 = o.optBoolean("isVirtualTicket");
            this.f509 = o.optBoolean("isPaidPromotion");
            this.f513 = o.optJSONObject("share") != null ? new GoodsShareModel(o.optJSONObject("share")) : null;
            this.f497 = new TradePaidPromotionModel(o.optJSONObject("paidPromotionExt"));
            this.f498 = o.optBoolean("isRedirect");
            this.f499 = o.optJSONObject("memberCardExt") != null ? new TradePaidMemberCardModel(o.optJSONObject("memberCardExt")) : null;
            this.f511 = o.optBoolean("isGiftCard");
            this.f512 = o.optBoolean("isWishOrder");
            this.f500 = o.optJSONObject("virtualTicketExt") != null ? new TradePaidVirtualModel(o.optJSONObject("virtualTicketExt")) : null;
            this.f501 = o.optBoolean("isHideSaveButton");
            this.f502 = o.optBoolean("isAllowShare");
            this.f503 = o.optBoolean("isHaveMemberCard");
            this.f506 = o.optJSONObject("fissionExt") != null ? new TradePaidFissionModel(o.optJSONObject("fissionExt")) : null;
            this.f507 = o.optBoolean("isSelfFetch");
            this.f510 = o.optJSONObject("order") != null ? new TradePaidOrderModel(o.optJSONObject("order")) : null;
        }
    }

    public boolean isSelf() {
        return this.f504;
    }

    public boolean isHasFission() {
        return this.f505;
    }

    public boolean isVirtualTicket() {
        return this.f508;
    }

    public boolean isPaidPromotion() {
        return this.f509;
    }

    public GoodsShareModel getShare() {
        return this.f513;
    }

    public TradePaidPromotionModel getPaidPromotionExt() {
        return this.f497;
    }

    public boolean isRedirect() {
        return this.f498;
    }

    public TradePaidMemberCardModel getMemberCardExt() {
        return this.f499;
    }

    public boolean isGiftCard() {
        return this.f511;
    }

    public boolean isWishOrder() {
        return this.f512;
    }

    public TradePaidVirtualModel getVirtualTicketExt() {
        return this.f500;
    }

    public boolean isHideSaveButton() {
        return this.f501;
    }

    public boolean isAllowShare() {
        return this.f502;
    }

    public boolean isHaveMemberCard() {
        return this.f503;
    }

    public TradePaidFissionModel getFissionExt() {
        return this.f506;
    }

    public boolean isSelfFetch() {
        return this.f507;
    }

    public TradePaidOrderModel getOrder() {
        return this.f510;
    }
}
