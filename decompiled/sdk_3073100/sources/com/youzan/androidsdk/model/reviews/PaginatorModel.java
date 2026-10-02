package com.youzan.androidsdk.model.reviews;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PaginatorModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f227;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f228;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private int f229;

    public PaginatorModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f227 = o.optInt("pageSize");
            this.f228 = o.optInt("page");
            this.f229 = o.optInt("totalCount");
        }
    }

    public int getPage() {
        return this.f228;
    }

    public int getPageSize() {
        return this.f227;
    }

    public int getTotalCount() {
        return this.f229;
    }
}
