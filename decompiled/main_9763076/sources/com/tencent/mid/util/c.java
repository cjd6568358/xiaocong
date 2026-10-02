package com.tencent.mid.util;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    static e a;
    private static f d = Util.getLogger();
    private static JSONObject e = null;
    Integer b;
    String c;

    public c(Context context) {
        this.b = null;
        this.c = null;
        try {
            a(context);
            this.b = j.h(context.getApplicationContext());
            this.c = j.g(context);
        } catch (Throwable th) {
            d.f(th);
        }
    }

    static synchronized e a(Context context) {
        if (a == null) {
            a = new e(context.getApplicationContext());
        }
        return a;
    }

    public void a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (a != null) {
                a.a(jSONObject2);
            }
            Util.jsonPut(jSONObject2, AdvanceSetting.CLEAR_NOTIFICATION, this.c);
            if (this.b != null) {
                jSONObject2.put("tn", this.b);
            }
            jSONObject.put("ev", jSONObject2);
            if (e == null || e.length() <= 0) {
                return;
            }
            jSONObject.put("eva", e);
        } catch (Throwable th) {
            d.f(th);
        }
    }
}
