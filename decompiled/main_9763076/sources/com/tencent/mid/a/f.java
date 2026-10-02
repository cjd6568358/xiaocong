package com.tencent.mid.a;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class f {
    protected Context a;

    protected f(Context context) {
        this.a = null;
        this.a = context.getApplicationContext();
    }

    protected abstract int a();

    public JSONObject a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        jSONObject.put("et", a());
        b(jSONObject);
        return jSONObject;
    }

    protected abstract void b(JSONObject jSONObject);
}
