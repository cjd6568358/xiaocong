package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsMessageModel {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private boolean f176;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f177;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private boolean f178;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f179;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f180;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f181;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private int f182;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private boolean f183;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private boolean f184;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private boolean f185;

    public GoodsMessageModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f179 = o.optString("name");
            this.f180 = o.optString("type");
            this.f181 = o.optInt("multiple");
            this.f182 = o.optInt("required");
            this.f185 = o.optBoolean("disable");
            this.f176 = o.optBoolean("disableDelete");
            this.f177 = o.optBoolean("disableEditName");
            this.f178 = o.optBoolean("disableType");
            this.f183 = o.optBoolean("disableRequired");
            this.f184 = o.optBoolean("disableMultiple");
        }
    }

    public boolean isDisable() {
        return this.f185;
    }

    public boolean isDisableDelete() {
        return this.f176;
    }

    public boolean isDisableEditName() {
        return this.f177;
    }

    public boolean isDisableMultiple() {
        return this.f184;
    }

    public boolean isDisableRequired() {
        return this.f183;
    }

    public boolean isDisableType() {
        return this.f178;
    }

    public int getMultiple() {
        return this.f181;
    }

    public String getName() {
        return this.f179;
    }

    public int getRequired() {
        return this.f182;
    }

    public String getType() {
        return this.f180;
    }
}
