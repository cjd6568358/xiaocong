package com.tencent.android.tpush.service.e;

import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k {
    private int a;
    private int b;
    private int c;
    private int d;
    private String e;
    private double f;
    private double g;
    private int h;

    private k() {
    }

    /* synthetic */ k(j jVar) {
        this();
    }

    public String toString() {
        try {
            return String.format("{\"cellId\":%d,\"mcc\":%d,\"lac\":%d,\"mnc\":%d,\"type\":%s,\"lat\":%f,\"lng\":%f}", Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.d), Integer.valueOf(this.c), this.e, Double.valueOf(this.f), Double.valueOf(this.g));
        } catch (Exception e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public JSONObject a() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cid", this.a);
            jSONObject.put("mcc", this.b);
            jSONObject.put("lac", this.d);
            jSONObject.put("mnc", this.c);
            jSONObject.put("type", this.e);
            jSONObject.put("nt", this.h);
            if (this.f != 0.0d) {
                jSONObject.put("lat", this.f);
            }
            if (this.g != 0.0d) {
                jSONObject.put("lng", this.g);
                return jSONObject;
            }
            return jSONObject;
        } catch (JSONException e) {
            com.tencent.android.tpush.a.a.i(k.class.getSimpleName(), e.getMessage());
            return null;
        }
    }
}
