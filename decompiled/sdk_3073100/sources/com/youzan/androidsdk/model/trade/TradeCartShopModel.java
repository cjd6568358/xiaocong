package com.youzan.androidsdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartShopModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private List<TradeCartGoodsModel> f350;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private long f351;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private List<TradeCartPayWayModel> f352;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f353;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private long f354;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f355;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f356;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private List<TradeCartGoodsModel> f357;

    public TradeCartShopModel(JSONObject o) throws JSONException {
        this.f353 = o.optString("title");
        this.f354 = o.optLong("kdt_id");
        this.f355 = o.optString("store_name");
        this.f356 = o.optString("shop_url");
        this.f351 = o.optLong("latest_addcart_timestamp");
        JSONArray goodsArray = o.optJSONArray("goods_list");
        if (goodsArray != null && goodsArray.length() > 0) {
            this.f357 = new ArrayList(goodsArray.length());
            for (int i = 0; i < goodsArray.length(); i++) {
                this.f357.add(new TradeCartGoodsModel(goodsArray.optJSONObject(i)));
            }
        }
        JSONArray unavailableArray = o.optJSONArray("unavailable_goods_list");
        if (unavailableArray != null && unavailableArray.length() > 0) {
            this.f350 = new ArrayList(unavailableArray.length());
            for (int i2 = 0; i2 < unavailableArray.length(); i2++) {
                this.f350.add(new TradeCartGoodsModel(unavailableArray.optJSONObject(i2)));
            }
        }
        JSONArray paysArray = o.optJSONArray("pay_ways");
        if (paysArray != null && paysArray.length() > 0) {
            this.f352 = new ArrayList(paysArray.length());
            for (int i3 = 0; i3 < paysArray.length(); i3++) {
                this.f352.add(new TradeCartPayWayModel(paysArray.optJSONObject(i3)));
            }
        }
    }

    public String getTitle() {
        return this.f353;
    }

    public long getKdtId() {
        return this.f354;
    }

    public String getStoreName() {
        return this.f355;
    }

    public String getShopUrl() {
        return this.f356;
    }

    public List<TradeCartGoodsModel> getGoodsList() {
        return this.f357;
    }

    public List<TradeCartGoodsModel> getUnavailableGoodsList() {
        return this.f350;
    }

    public long getLatestAddCartTimestamp() {
        return this.f351;
    }

    public List<TradeCartPayWayModel> getPayWays() {
        return this.f352;
    }
}
