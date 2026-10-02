package com.youzan.androidsdk.model.action;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ServerParameterModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f118;

    public ServerParameterModel(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject o = new JSONObject(json);
                this.f118 = o.optString("detail_url");
            } catch (JSONException e) {
            }
        }
    }

    public String getDetailUrl() {
        return this.f118;
    }
}
