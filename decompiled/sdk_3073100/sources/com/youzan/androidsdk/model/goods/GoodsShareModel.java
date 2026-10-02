package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsShareModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private int f195;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private String f196;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f197;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f198;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f199;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f200;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private int f201;

    public GoodsShareModel() {
    }

    public GoodsShareModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f197 = o.optString("title");
            this.f198 = o.optString("link");
            this.f199 = o.optString("img_url");
            this.f200 = o.optString("desc");
            this.f201 = o.optInt("img_width");
            this.f195 = o.optInt("img_height");
            this.f196 = o.optString("timeLineTitle");
        }
    }

    public String getDesc() {
        return this.f200 == null ? "" : this.f200;
    }

    public void setDesc(String desc) {
        this.f200 = desc;
    }

    public int getImgHeight() {
        return this.f195;
    }

    public void setImgHeight(int imgHeight) {
        this.f195 = imgHeight;
    }

    public String getImgUrl() {
        return this.f199 == null ? "" : this.f199;
    }

    public void setImgUrl(String imgUrl) {
        this.f199 = imgUrl;
    }

    public int getImgWidth() {
        return this.f201;
    }

    public void setImgWidth(int imgWidth) {
        this.f201 = imgWidth;
    }

    public String getLink() {
        return this.f198 == null ? "" : this.f198;
    }

    public void setLink(String link) {
        this.f198 = link;
    }

    public String getTimeLineTitle() {
        return this.f196 == null ? "" : this.f196;
    }

    public void setTimeLineTitle(String timeLineTitle) {
        this.f196 = timeLineTitle;
    }

    public String getTitle() {
        return this.f197 == null ? "" : this.f197;
    }

    public void setTitle(String title) {
        this.f197 = title;
    }

    public String toJson() {
        return "{\"title\":\"" + getTitle() + "\", \"link\":\"" + getLink() + "\", \"img_url\":\"" + getImgUrl() + "\", \"desc\":\"" + getDesc() + "\", \"img_width\":\"" + getImgWidth() + "\", \"img_height\":\"" + getImgHeight() + "\", \"timeLineTitle\":\"" + getTimeLineTitle() + "\"}";
    }
}
