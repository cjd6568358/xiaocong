package com.youzan.androidsdk.model.shop;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ShopBasicModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f260;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f261;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f262;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private String f263;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private String f264;

    public ShopBasicModel(int certType, String name, String logo, String url, String sid) {
        this.f260 = certType;
        this.f261 = name;
        this.f262 = logo;
        this.f263 = url;
        this.f264 = sid;
    }

    public ShopBasicModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f260 = o.optInt("cert_type");
            this.f261 = o.optString("name");
            this.f262 = o.optString("logo");
            this.f263 = o.optString("url");
            this.f264 = o.optString("sid");
        }
    }

    public int getCertType() {
        return this.f260;
    }

    public String getName() {
        return this.f261;
    }

    public String getLogo() {
        return this.f262;
    }

    public String getUrl() {
        return this.f263;
    }

    public String getSid() {
        return this.f264;
    }
}
