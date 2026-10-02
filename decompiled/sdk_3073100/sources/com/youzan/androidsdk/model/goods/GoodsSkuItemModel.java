package com.youzan.androidsdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsSkuItemModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public int f202;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    public int f203;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    public String f204;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    public String f205;

    public GoodsSkuItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f202 = o.optInt("kid", 0);
            this.f203 = o.optInt("vid", 0);
            this.f204 = o.optString("k");
            this.f205 = o.optString("v");
        }
    }

    public String getkDesc() {
        return this.f204;
    }

    public int getKid() {
        return this.f202;
    }

    public String getvDesc() {
        return this.f205;
    }

    public int getVid() {
        return this.f203;
    }
}
