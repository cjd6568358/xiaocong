package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsSkuModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f206;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f207;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private int f208;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private String f209;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f210;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f211;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f212;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f213;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private double f214;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private String f215;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f216;

    public GoodsSkuModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f210 = o.optString("outer_id");
            this.f211 = o.optString("sku_id");
            this.f212 = o.optString("sku_unique_code");
            this.f213 = o.optString("num_iid");
            this.f206 = o.optString("properties_name");
            this.f207 = o.optString("properties_name_json");
            this.f216 = o.optInt("quantity");
            this.f208 = o.optInt("with_hold_quantity");
            this.f214 = o.optDouble("price", 0.0d);
            this.f215 = o.optString("created");
            this.f209 = o.optString("modified");
        }
    }

    public String getOuterId() {
        return this.f210;
    }

    public void setOuterId(String outerId) {
        this.f210 = outerId;
    }

    public String getSkuId() {
        return this.f211;
    }

    public void setSkuId(String skuId) {
        this.f211 = skuId;
    }

    public String getSkuUniqueCode() {
        return this.f212;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.f212 = skuUniqueCode;
    }

    public String getNumIid() {
        return this.f213;
    }

    public void setNumIid(String numIid) {
        this.f213 = numIid;
    }

    public int getQuantity() {
        return this.f216;
    }

    public void setQuantity(int quantity) {
        this.f216 = quantity;
    }

    public String getPropertiesName() {
        return this.f206;
    }

    public void setPropertiesName(String propertiesName) {
        this.f206 = propertiesName;
    }

    public String getPropertiesNameJson() {
        return this.f207;
    }

    public void setPropertiesNameJson(String propertiesNameJson) {
        this.f207 = propertiesNameJson;
    }

    public int getWithHoldQuantity() {
        return this.f208;
    }

    public void setWithHoldQuantity(int withHoldQuantity) {
        this.f208 = withHoldQuantity;
    }

    public double getPrice() {
        return this.f214;
    }

    public void setPrice(double price) {
        this.f214 = price;
    }

    public String getCreated() {
        return this.f215;
    }

    public void setCreated(String created) {
        this.f215 = created;
    }

    public String getModified() {
        return this.f209;
    }

    public void setModified(String modified) {
        this.f209 = modified;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoodsSkuModel that = (GoodsSkuModel) o;
        return this.f211.equals(that.f211);
    }

    public int hashCode() {
        return this.f211.hashCode();
    }
}
