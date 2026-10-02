package com.youzan.androidsdk.model.reviews;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ReviewsRateModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f257;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f258;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f259;

    public ReviewsRateModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f257 = o.optInt("badNum");
            this.f258 = o.optInt("bestNum");
            this.f259 = o.optInt("commonNum");
        }
    }

    public int getBadNum() {
        return this.f257;
    }

    public int getBestNum() {
        return this.f258;
    }

    public int getCommonNum() {
        return this.f259;
    }
}
