package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeGoodsInfoModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f443;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f444;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private int f445;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private int f446;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private String f447;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f448;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f449;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f450;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f451;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private int f452;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f453;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f454;

    public TradeGoodsInfoModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f448 = o.optInt("points_price");
            this.f449 = o.optInt("buy_way");
            this.f450 = o.optInt("goods_id");
            this.f451 = o.optString("title");
            this.f454 = o.optString("goods_no");
            this.f443 = o.optInt("quota");
            this.f444 = o.optBoolean("is_virtual");
            this.f445 = o.optInt("mark");
            this.f452 = o.optInt("supplier_kdt_id");
            this.f453 = o.optString("alias");
            this.f446 = o.optInt("supplier_goods_id");
            this.f447 = o.optString("img_url");
        }
    }

    public int getPointsPrice() {
        return this.f448;
    }

    public int getBuyWay() {
        return this.f449;
    }

    public int getGoodsId() {
        return this.f450;
    }

    public String getTitle() {
        return this.f451;
    }

    public String getGoodsNo() {
        return this.f454;
    }

    public int getQuota() {
        return this.f443;
    }

    public boolean getIsVirtual() {
        return this.f444;
    }

    public int getMark() {
        return this.f445;
    }

    public int getSupplierKdtId() {
        return this.f452;
    }

    public String getAlias() {
        return this.f453;
    }

    public int getSupplierGoodsId() {
        return this.f446;
    }

    public String getImgUrl() {
        return this.f447;
    }
}
