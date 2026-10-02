package com.baidu.mobstat;

import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class be {
    public boolean a;
    public String b;
    public boolean c;

    public be() {
        this.a = false;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = false;
    }

    public be(JSONObject jSONObject) {
        this.a = false;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = false;
        try {
            this.a = jSONObject.getBoolean("SDK_BPLUS_SERVICE");
        } catch (Exception e) {
            bd.b(e);
        }
        try {
            this.b = jSONObject.getString("SDK_PRODUCT_LY");
        } catch (Exception e2) {
            bd.b(e2);
        }
        try {
            this.c = jSONObject.getBoolean("SDK_LOCAL_SERVER");
        } catch (Exception e3) {
            bd.b(e3);
        }
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("SDK_BPLUS_SERVICE", this.a);
        } catch (JSONException e) {
            bd.b(e);
        }
        try {
            jSONObject.put("SDK_PRODUCT_LY", this.b);
        } catch (JSONException e2) {
            bd.b(e2);
        }
        try {
            jSONObject.put("SDK_LOCAL_SERVER", this.c);
        } catch (JSONException e3) {
            bd.b(e3);
        }
        return jSONObject;
    }
}
