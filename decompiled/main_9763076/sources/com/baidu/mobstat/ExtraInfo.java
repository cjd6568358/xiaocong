package com.baidu.mobstat;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ExtraInfo {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;
    String i;
    String j;

    public JSONObject dumpToJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.a)) {
                jSONObject.put("v1", this.a);
            }
            if (!TextUtils.isEmpty(this.b)) {
                jSONObject.put("v2", this.b);
            }
            if (!TextUtils.isEmpty(this.c)) {
                jSONObject.put("v3", this.c);
            }
            if (!TextUtils.isEmpty(this.d)) {
                jSONObject.put("v4", this.d);
            }
            if (!TextUtils.isEmpty(this.e)) {
                jSONObject.put("v5", this.e);
            }
            if (!TextUtils.isEmpty(this.f)) {
                jSONObject.put("v6", this.f);
            }
            if (!TextUtils.isEmpty(this.g)) {
                jSONObject.put("v7", this.g);
            }
            if (!TextUtils.isEmpty(this.h)) {
                jSONObject.put("v8", this.h);
            }
            if (!TextUtils.isEmpty(this.i)) {
                jSONObject.put("v9", this.i);
            }
            if (!TextUtils.isEmpty(this.j)) {
                jSONObject.put("v10", this.j);
            }
        } catch (JSONException e) {
            db.c(e);
        }
        return jSONObject;
    }
}
