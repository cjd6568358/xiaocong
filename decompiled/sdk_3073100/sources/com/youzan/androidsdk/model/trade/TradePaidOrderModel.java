package com.youzan.androidsdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradePaidOrderModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f514;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private int f515;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f516;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f517;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f518;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f519;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f520;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private int f521;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f522;

    public TradePaidOrderModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f517 = o.optInt("realPay");
            this.f518 = o.optString("orderStateStr");
            this.f519 = o.optInt("pay");
            this.f520 = o.optString("detailUrl");
            this.f522 = o.optString("buyWay");
            this.f514 = o.optInt("orderState");
            this.f515 = o.optInt("orderType");
            this.f516 = o.optString("orderTypeStr");
            this.f521 = o.optInt("kdtId");
        }
    }

    public int getRealPay() {
        return this.f517;
    }

    public String getOrderStateStr() {
        return this.f518;
    }

    public int getPay() {
        return this.f519;
    }

    public String getDetailUrl() {
        return this.f520;
    }

    public String getBuyWay() {
        return this.f522;
    }

    public int getOrderState() {
        return this.f514;
    }

    public int getOrderType() {
        return this.f515;
    }

    public String getOrderTypeStr() {
        return this.f516;
    }

    public int getKdtId() {
        return this.f521;
    }
}
