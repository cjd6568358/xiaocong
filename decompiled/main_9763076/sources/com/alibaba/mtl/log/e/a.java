package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ApiResponseParse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static C0001a a(String str) {
        C0001a c0001a = new C0001a();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("success")) {
                String string = jSONObject.getString("success");
                if (!TextUtils.isEmpty(string) && string.equals("success")) {
                    c0001a.H = true;
                }
            }
            if (jSONObject.has("ret")) {
                c0001a.ad = jSONObject.getString("ret");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c0001a;
    }

    /* JADX INFO: renamed from: com.alibaba.mtl.log.e.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: ApiResponseParse.java */
    public static class C0001a {
        public static C0001a a = new C0001a();
        public boolean H = false;
        public String ad = null;

        public boolean g() {
            return "E0102".equalsIgnoreCase(this.ad);
        }

        public boolean h() {
            return "E0111".equalsIgnoreCase(this.ad) || "E0112".equalsIgnoreCase(this.ad);
        }
    }
}
