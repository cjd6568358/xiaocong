package com.alibaba.sdk.android.httpdns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class p {
    private boolean enabled;
    private String[] f;

    p(String str) {
        int i = 0;
        this.enabled = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            h.d("Schedule center response:" + jSONObject.toString());
            if (jSONObject.has("service_status")) {
                this.enabled = jSONObject.getString("service_status").equals("disable") ? false : true;
            }
            if (!jSONObject.has("service_ip")) {
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("service_ip");
            this.f = new String[jSONArray.length()];
            while (true) {
                int i2 = i;
                if (i2 >= jSONArray.length()) {
                    return;
                }
                this.f[i2] = (String) jSONArray.get(i2);
                i = i2 + 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] c() {
        return this.f;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
