package com.youzan.androidsdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartFormatModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private List<TradeCartGoodsModel> f299;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f300;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f301;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f302;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f303;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private List<TradeCartGoodsModel> f304;

    public TradeCartFormatModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f300 = o.optString("title");
            this.f301 = o.optString("kdt_id");
            this.f302 = o.optString("store_name");
            this.f303 = o.optString("shop_url");
            JSONArray array = o.optJSONArray("goods_list");
            if (array != null && array.length() > 0) {
                this.f304 = new ArrayList(array.length());
                for (int i = 0; i < array.length(); i++) {
                    this.f304.add(new TradeCartGoodsModel(array.optJSONObject(i)));
                }
            }
            JSONArray array2 = o.optJSONArray("unavailable_goods_list");
            if (array2 != null && array2.length() > 0) {
                this.f299 = new ArrayList(array2.length());
                for (int i2 = 0; i2 < array2.length(); i2++) {
                    this.f299.add(new TradeCartGoodsModel(array2.optJSONObject(i2)));
                }
            }
        }
    }

    public List<TradeCartGoodsModel> getGoodsList() {
        return this.f304;
    }

    public String getKdtId() {
        return this.f301;
    }

    public String getShopUrl() {
        return this.f303;
    }

    public String getStoreName() {
        return this.f302;
    }

    public String getTitle() {
        return this.f300;
    }

    public List<TradeCartGoodsModel> getUnavailableGoodsList() {
        return this.f299;
    }
}
