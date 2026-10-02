package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsQrcodeModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f186;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f187;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f188;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f189;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f190;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f191;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f192;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private String f193;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f194;

    public GoodsQrcodeModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f189 = o.optInt("id");
            this.f190 = o.optString("name");
            this.f191 = o.optString("desc");
            this.f192 = o.optString("created");
            this.f194 = o.optString("type");
            this.f186 = o.optString("discount");
            this.f187 = o.optString("decrease");
            this.f188 = o.optString("link_url");
            this.f193 = o.optString("weixin_qrcode_url");
        }
    }

    public int getId() {
        return this.f189;
    }

    public void setId(int id) {
        this.f189 = id;
    }

    public String getName() {
        return this.f190;
    }

    public void setName(String name) {
        this.f190 = name;
    }

    public String getDesc() {
        return this.f191;
    }

    public void setDesc(String desc) {
        this.f191 = desc;
    }

    public String getCreated() {
        return this.f192;
    }

    public void setCreated(String created) {
        this.f192 = created;
    }

    public String getType() {
        return this.f194;
    }

    public void setType(String type) {
        this.f194 = type;
    }

    public String getDiscount() {
        return this.f186;
    }

    public void setDiscount(String discount) {
        this.f186 = discount;
    }

    public String getDecrease() {
        return this.f187;
    }

    public void setDecrease(String decrease) {
        this.f187 = decrease;
    }

    public String getLinkUrl() {
        return this.f188;
    }

    public void setLinkUrl(String linkUrl) {
        this.f188 = linkUrl;
    }

    public String getWeixinQrcodeUrl() {
        return this.f193;
    }

    public void setWeixinQrcodeUrl(String weixinQrcodeUrl) {
        this.f193 = weixinQrcodeUrl;
    }
}
