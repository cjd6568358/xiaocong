package com.youzan.androidsdk.model.trade;

import com.youzan.androidsdk.SDKUtil;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeOrderModel {

    /* JADX INFO: renamed from: ʳ, reason: contains not printable characters */
    private String f457;

    /* JADX INFO: renamed from: ʹ, reason: contains not printable characters */
    private String f458;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f459;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f460;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private List<TradeSkuModel> f461;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private String f462;

    /* JADX INFO: renamed from: ʿ, reason: contains not printable characters */
    private String f463;

    /* JADX INFO: renamed from: ˈ, reason: contains not printable characters */
    private String f464;

    /* JADX INFO: renamed from: ˉ, reason: contains not printable characters */
    private String f465;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f466;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f467;

    /* JADX INFO: renamed from: ˌ, reason: contains not printable characters */
    private String f468;

    /* JADX INFO: renamed from: ˍ, reason: contains not printable characters */
    private String f469;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f470;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f471;

    /* JADX INFO: renamed from: ˑ, reason: contains not printable characters */
    private int f472;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f473;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f474;

    /* JADX INFO: renamed from: ՙ, reason: contains not printable characters */
    private String f475;

    /* JADX INFO: renamed from: י, reason: contains not printable characters */
    private String f476;

    /* JADX INFO: renamed from: ـ, reason: contains not printable characters */
    private String f477;

    /* JADX INFO: renamed from: ٴ, reason: contains not printable characters */
    private String f478;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f479;

    /* JADX INFO: renamed from: ᐧ, reason: contains not printable characters */
    private String f480;

    /* JADX INFO: renamed from: ᐨ, reason: contains not printable characters */
    private String f481;

    /* JADX INFO: renamed from: ᴵ, reason: contains not printable characters */
    private String f482;

    /* JADX INFO: renamed from: ᵎ, reason: contains not printable characters */
    private String f483;

    /* JADX INFO: renamed from: ᵔ, reason: contains not printable characters */
    private String f484;

    /* JADX INFO: renamed from: ᵢ, reason: contains not printable characters */
    private String f485;

    /* JADX INFO: renamed from: ⁱ, reason: contains not printable characters */
    private String f486;

    /* JADX INFO: renamed from: ﹳ, reason: contains not printable characters */
    private String f487;

    /* JADX INFO: renamed from: ﹶ, reason: contains not printable characters */
    private TradeGoodsInfoModel f488;

    /* JADX INFO: renamed from: ﹺ, reason: contains not printable characters */
    private String f489;

    /* JADX INFO: renamed from: ｰ, reason: contains not printable characters */
    private String f490;

    /* JADX INFO: renamed from: ﾞ, reason: contains not printable characters */
    private TradeShopInfoModel f491;

    public TradeOrderModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f466 = o.optString("sku_id");
            this.f467 = o.optString("order_no");
            this.f470 = o.optString("goods_url");
            this.f471 = o.optString("num");
            this.f479 = o.optString("id");
            this.f459 = o.optString("pay_price");
            this.f460 = o.optString("goods_type");
            this.f461 = SDKUtil.jsonToList(o, "sku", TradeSkuModel.class);
            this.f473 = o.optString("goods_id");
            this.f474 = o.optString("title");
            this.f462 = o.optString("s3");
            this.f463 = o.optString("s2");
            this.f464 = o.optString("s1");
            this.f465 = o.optString("kdt_id");
            this.f468 = o.optString("s5");
            this.f469 = o.optString("s4");
            this.f472 = o.optInt("shipment");
            this.f477 = o.optString("item_total_price");
            this.f480 = o.optString("price");
            this.f481 = o.optString("is_visual");
            this.f487 = o.optString("sku_code");
            this.f491 = new TradeShopInfoModel(m80(o));
            this.f458 = o.optString("item_id");
            this.f475 = o.optString("goods_snap");
            this.f476 = o.optString("postage");
            this.f478 = o.optString("created");
            this.f482 = o.optString("url");
            this.f483 = o.optString("is_present");
            this.f484 = o.optString("alias");
            this.f485 = o.optString("shop_id");
            this.f486 = o.optString("image_url");
            this.f488 = new TradeGoodsInfoModel(o.optJSONObject("goods_info"));
            this.f489 = o.optString("tc_order_item_id");
            this.f490 = o.optString("use_ump");
            this.f457 = o.optString("delivery_template_id");
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private JSONObject m80(JSONObject o) {
        JSONObject infoObj = o.optJSONObject("shop_info");
        if (infoObj == null) {
            String infoStr = o.optString("shop_info");
            if (infoStr != null) {
                try {
                    return new JSONObject(infoStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            infoObj = new JSONObject();
        }
        return infoObj;
    }

    public String getSkuId() {
        return this.f466;
    }

    public String getOrderNo() {
        return this.f467;
    }

    public String getGoodsUrl() {
        return this.f470;
    }

    public String getNum() {
        return this.f471;
    }

    public String getId() {
        return this.f479;
    }

    public String getPayPrice() {
        return this.f459;
    }

    public String getGoodsType() {
        return this.f460;
    }

    public List<TradeSkuModel> getSku() {
        return this.f461;
    }

    public String getGoodsId() {
        return this.f473;
    }

    public String getTitle() {
        return this.f474;
    }

    public String getS3() {
        return this.f462;
    }

    public String getS2() {
        return this.f463;
    }

    public String getS1() {
        return this.f464;
    }

    public String getKdtId() {
        return this.f465;
    }

    public String getS5() {
        return this.f468;
    }

    public String getS4() {
        return this.f469;
    }

    public int getShipment() {
        return this.f472;
    }

    public String getItemTotalPrice() {
        return this.f477;
    }

    public String getPrice() {
        return this.f480;
    }

    public String getIsVisual() {
        return this.f481;
    }

    public String getSkuCode() {
        return this.f487;
    }

    public TradeShopInfoModel getShopInfo() {
        return this.f491;
    }

    public String getItemId() {
        return this.f458;
    }

    public String getGoodsSnap() {
        return this.f475;
    }

    public String getPostage() {
        return this.f476;
    }

    public String getCreated() {
        return this.f478;
    }

    public String getUrl() {
        return this.f482;
    }

    public String getIsPresent() {
        return this.f483;
    }

    public String getAlias() {
        return this.f484;
    }

    public String getShopId() {
        return this.f485;
    }

    public String getImageUrl() {
        return this.f486;
    }

    public TradeGoodsInfoModel getGoodsInfo() {
        return this.f488;
    }

    public String getTcOrderItemId() {
        return this.f489;
    }

    public String getUseUmp() {
        return this.f490;
    }

    public String getDeliveryTemplateId() {
        return this.f457;
    }

    public String getSkuValue() {
        if (this.f461 == null || this.f461.size() <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean isFirstOne = true;
        for (TradeSkuModel item : this.f461) {
            if (!isFirstOne) {
                builder.append("，");
                isFirstOne = true;
            }
            builder.append(item.getV());
        }
        return builder.toString();
    }
}
