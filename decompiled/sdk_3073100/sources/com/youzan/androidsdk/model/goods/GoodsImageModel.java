package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsImageModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private String f170;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f171;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f172;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f173;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f174;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f175;

    public GoodsImageModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f171 = o.optInt("id");
            this.f172 = o.optString("created");
            this.f173 = o.optString("url");
            this.f174 = o.optString("thumbnail");
            this.f175 = o.optString("medium");
            this.f170 = o.optString("combine");
        }
    }

    public int getId() {
        return this.f171;
    }

    public void setId(int id) {
        this.f171 = id;
    }

    public String getCreated() {
        return this.f172;
    }

    public void setCreated(String created) {
        this.f172 = created;
    }

    public String getUrl() {
        return this.f173;
    }

    public void setUrl(String url) {
        this.f173 = url;
    }

    public String getThumbnail() {
        return this.f174;
    }

    public void setThumbnail(String thumbnail) {
        this.f174 = thumbnail;
    }

    public String getMedium() {
        return this.f175;
    }

    public void setMedium(String medium) {
        this.f175 = medium;
    }

    public String getCombine() {
        return this.f170;
    }

    public void setCombine(String combine) {
        this.f170 = combine;
    }
}
