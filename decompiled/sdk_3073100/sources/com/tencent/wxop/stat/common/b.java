package com.tencent.wxop.stat.common;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    static d a;
    private static StatLogger d = l.b();
    private static JSONObject e = new JSONObject();
    Integer b;
    String c;

    public b(Context context) {
        this.b = null;
        this.c = null;
        try {
            a(context);
            this.b = l.k(context.getApplicationContext());
            this.c = com.tencent.wxop.stat.a.a(context).b();
        } catch (Throwable th) {
            d.e(th);
        }
    }

    static synchronized d a(Context context) {
        if (a == null) {
            a = new d(context.getApplicationContext());
        }
        return a;
    }

    public static void a(Context context, Map<String, String> map) throws JSONException {
        if (map == null) {
            return;
        }
        for (Map.Entry entry : new HashMap(map).entrySet()) {
            e.put((String) entry.getKey(), entry.getValue());
        }
    }

    public void a(JSONObject jSONObject, Thread thread) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (a != null) {
                a.a(jSONObject2, thread);
            }
            r.a(jSONObject2, "cn", this.c);
            if (this.b != null) {
                jSONObject2.put("tn", this.b);
            }
            if (thread == null) {
                jSONObject.put("ev", jSONObject2);
            } else {
                jSONObject.put("errkv", jSONObject2.toString());
            }
            if (e == null || e.length() <= 0) {
                return;
            }
            jSONObject.put("eva", e);
        } catch (Throwable th) {
            d.e(th);
        }
    }
}
