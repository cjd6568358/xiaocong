package com.youzan.androidsdk.model.ump;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PromotionModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private PromotionItemModel f544;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private List<PromotionOrderModel> f545;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private PromotionPointsExchangeModel f546;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private PromotionPackageBuyDetailModel f547;

    public PromotionModel(JSONObject o) throws JSONException {
        if (o != null) {
            JSONObject itemPromotionObj = o.optJSONObject("item_promotion");
            if (itemPromotionObj != null) {
                this.f544 = new PromotionItemModel(itemPromotionObj);
            }
            JSONArray orderPromotionsArray = o.optJSONArray("order_promotions");
            if (orderPromotionsArray != null && orderPromotionsArray.length() > 0) {
                this.f545 = new ArrayList(orderPromotionsArray.length());
                for (int i = 0; i < orderPromotionsArray.length(); i++) {
                    this.f545.add(new PromotionOrderModel(orderPromotionsArray.optJSONObject(i)));
                }
            }
            JSONObject goodsPointsObj = o.optJSONObject("goods_points");
            if (goodsPointsObj != null) {
                this.f546 = new PromotionPointsExchangeModel(goodsPointsObj);
            }
            JSONObject packageBuyObj = o.optJSONObject("package_buy");
            if (packageBuyObj != null) {
                this.f547 = new PromotionPackageBuyDetailModel(packageBuyObj);
            }
        }
    }

    public PromotionItemModel getItemPromotion() {
        return this.f544;
    }

    public List<PromotionOrderModel> getOrderPromotions() {
        return this.f545;
    }

    public PromotionPointsExchangeModel getGoodsPoints() {
        return this.f546;
    }

    public PromotionPackageBuyDetailModel getPackageBuy() {
        return this.f547;
    }
}
