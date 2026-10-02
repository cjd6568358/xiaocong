package com.tencent.android.tpush.stat.event;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.stat.a.h;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class d {
    public static String g = "xgsdk";
    protected static String i = null;
    protected static long j = 0;
    protected String c;
    protected long e;
    protected int f;
    protected Context l;
    protected long d = 0;
    protected String h = null;
    protected long k = 0;

    public abstract boolean a(JSONObject jSONObject);

    public abstract EventType b();

    d(Context context, int i2, long j2) {
        this.c = null;
        this.c = "Axg" + j2;
        a(context, i2, j2);
    }

    public d(Context context, String str) {
        this.c = null;
        this.c = str;
        a(context, 0, this.d);
    }

    private void a(Context context, int i2, long j2) {
        this.l = context;
        this.d = j2;
        this.e = System.currentTimeMillis() / 1000;
        this.f = i2;
        this.h = com.tencent.android.tpush.stat.a.e.b(context, j2);
        if (i == null || i.trim().length() < 40) {
            i = XGPushConfig.getToken(context);
            if (!com.tencent.android.tpush.stat.a.e.b(i)) {
                i = PushConstants.PUSH_TYPE_NOTIFY;
            }
        }
        if (j == 0) {
            j = CacheManager.getGuid(c());
        }
    }

    public Context c() {
        return this.l;
    }

    public boolean b(JSONObject jSONObject) {
        boolean zA = false;
        try {
            h.a(jSONObject, "ky", this.c);
            jSONObject.put("et", b().a());
            jSONObject.put("ui", h.e(this.l));
            h.a(jSONObject, "mc", h.f(this.l));
            jSONObject.put("ut", 1);
            if (b() != EventType.SESSION_ENV) {
                h.a(jSONObject, "av", this.h);
                h.a(jSONObject, "ch", g);
            }
            h.a(jSONObject, "mid", i);
            jSONObject.put("si", this.f);
            if (b() == EventType.CUSTOM) {
                jSONObject.put("cts", this.e);
                if (this.k == 0 && this.e != 0) {
                    jSONObject.put("ts", this.e);
                } else {
                    jSONObject.put("ts", this.k);
                }
            } else {
                jSONObject.put("ts", this.e);
            }
            if (PushConstants.PUSH_TYPE_NOTIFY.equals(com.tencent.android.tpush.stat.a.e.a(this.l, this.d))) {
                jSONObject.put("sv", com.tencent.android.tpush.stat.a.e.a(this.l));
            } else {
                jSONObject.put("sv", com.tencent.android.tpush.stat.a.e.a(this.l, this.d));
            }
            jSONObject.put("guid", j);
            jSONObject.put("dts", com.tencent.android.tpush.stat.a.e.a(this.l, false));
            zA = a(jSONObject);
            return zA;
        } catch (Throwable th) {
            return zA;
        }
    }

    public String d() {
        try {
            JSONObject jSONObject = new JSONObject();
            b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public String toString() {
        return d();
    }
}
