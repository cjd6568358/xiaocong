package com.youzan.jsbridge.method;

import android.support.annotation.Keep;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
@Deprecated
public class JsMethodCompat implements Method {
    public String callback;
    public String name;
    public String params;

    public JsMethodCompat(String name, String param) {
        this.name = name;
        this.params = param;
        parseCallback(param);
    }

    private void parseCallback(String param) {
        if (!TextUtils.isEmpty(param)) {
            try {
                JSONObject obj = new JSONObject(param);
                this.callback = obj.optString("callback", null);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.callback = null;
    }

    public String getName() {
        return this.name;
    }

    public String getCallback() {
        return this.callback;
    }

    public String getParams() {
        return this.params;
    }
}
