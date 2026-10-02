package com.youzan.androidsdk;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YouzanToken {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private String f131;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private String f132;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private String f133;

    public YouzanToken(JSONObject o) throws JSONException {
        if (o != null) {
            JSONObject data = o.has("data") ? o.optJSONObject("data") : o;
            this.f131 = data.optString("access_token");
            this.f132 = data.optString("cookie_key");
            this.f133 = data.optString("cookie_value");
        }
    }

    public YouzanToken() {
    }

    public String getAccessToken() {
        return this.f131;
    }

    public void setAccessToken(String token) {
        this.f131 = token;
    }

    public String getCookieKey() {
        return this.f132;
    }

    public void setCookieKey(String cookieKey) {
        this.f132 = cookieKey;
    }

    public String getCookieValue() {
        return this.f133;
    }

    public void setCookieValue(String cookieValue) {
        this.f133 = cookieValue;
    }
}
