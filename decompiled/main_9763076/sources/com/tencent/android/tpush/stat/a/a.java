package com.tencent.android.tpush.stat.a;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    static c a;
    private static f d = e.b();
    private static JSONObject e = new JSONObject();
    Integer b;
    String c;

    static synchronized c a(Context context, long j) {
        if (a == null) {
            a = new c(context.getApplicationContext(), j);
        }
        return a;
    }

    public a(Context context, long j) {
        this.b = null;
        this.c = null;
        try {
            a(context, j);
            this.b = e.g(context.getApplicationContext());
            this.c = com.tencent.android.tpush.stat.a.a(context).a();
        } catch (Throwable th) {
            d.b(th);
        }
    }

    public void a(JSONObject jSONObject, Thread thread) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (a != null) {
                a.a(jSONObject2, thread);
            }
            h.a(jSONObject2, AdvanceSetting.CLEAR_NOTIFICATION, this.c);
            if (this.b != null) {
                jSONObject2.put("tn", this.b);
            }
            if (thread == null) {
                jSONObject.put("ev", jSONObject2);
            } else {
                jSONObject.put("errkv", jSONObject2.toString());
            }
            if (e != null && e.length() > 0) {
                jSONObject.put("eva", e);
            }
        } catch (Throwable th) {
            d.b(th);
        }
    }
}
