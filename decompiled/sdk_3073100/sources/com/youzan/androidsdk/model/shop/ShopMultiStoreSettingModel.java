package com.youzan.androidsdk.model.shop;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ShopMultiStoreSettingModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f265;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f266;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f267;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f268;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f269;

    public ShopMultiStoreSettingModel(int status, int defaultOfflineId, int separateStock, int separatePrice, int soldOutRecommend) {
        this.f265 = status;
        this.f266 = defaultOfflineId;
        this.f267 = separateStock;
        this.f268 = separatePrice;
        this.f269 = soldOutRecommend;
    }

    public ShopMultiStoreSettingModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f265 = o.getInt("status");
            this.f266 = o.getInt("default_offline_id");
            this.f267 = o.getInt("separate_stock");
            this.f268 = o.getInt("separate_price");
            this.f269 = o.getInt("sold_out_recommend");
        }
    }

    public int getStatus() {
        return this.f265;
    }

    public int getDefaultOfflineId() {
        return this.f266;
    }

    public int getSeparateStock() {
        return this.f267;
    }

    public int getSeparatePrice() {
        return this.f268;
    }

    public int getSoldOutRecommend() {
        return this.f269;
    }
}
