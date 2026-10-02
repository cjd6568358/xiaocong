package com.huawei.hms.update.a;

import android.content.Context;
import android.os.Build;
import com.huawei.hms.api.HuaweiApiAvailability;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CheckParams.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    private final Context a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;
    private final String i;
    private final String j;
    private final int k;
    private final String l;
    private final String m;
    private final String n;

    public a(Context context) {
        com.huawei.hms.c.a.a(context, "context must not be null.");
        this.a = context;
        com.huawei.hms.c.e eVar = new com.huawei.hms.c.e(context);
        this.b = Build.MODEL;
        this.c = Build.DISPLAY;
        this.d = Build.HARDWARE;
        this.e = Build.FINGERPRINT;
        this.f = "Android " + Build.VERSION.RELEASE;
        this.g = com.huawei.hms.update.f.a.a(this.a);
        this.h = String.valueOf(com.huawei.hms.a.a.C0014a.a);
        this.i = "full";
        this.j = HuaweiApiAvailability.SERVICES_PACKAGE;
        this.m = eVar.d(HuaweiApiAvailability.SERVICES_PACKAGE);
        this.n = com.huawei.hms.update.f.a.b(context);
        o oVar = new o(this.a);
        this.k = oVar.a();
        this.l = oVar.b();
    }

    public String toString() {
        try {
            return a().toString(2);
        } catch (JSONException e) {
            return super.toString();
        }
    }

    public JSONObject a() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("DeviceName", this.b);
            jSONObject.put("Firmware", this.c);
            jSONObject.put("Hardware", this.d);
            jSONObject.put("FingerPrint", this.e);
            jSONObject.put("Language", this.g);
            jSONObject.put("OS", this.f);
            jSONObject.put("EmotionUI", this.h);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("PackageType", this.i);
            jSONObject2.put("PackageName", this.j);
            jSONObject2.put("PackageVersionCode", String.valueOf(this.k));
            jSONObject2.put("PackageVersionName", this.l);
            jSONObject2.put("PackageFingerprint", this.m);
            jSONObject2.put("SystemRegion", this.n);
            return new JSONObject().put("rules", jSONObject).put("components", new JSONArray().put(jSONObject2));
        } catch (JSONException e) {
            com.huawei.hms.support.log.a.d("CheckParams", "In toJSON, Failed to build json for check-update request." + e.getMessage());
            return new JSONObject();
        }
    }
}
