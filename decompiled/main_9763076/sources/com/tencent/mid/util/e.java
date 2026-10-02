package com.tencent.mid.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e {
    String a;
    String b;
    DisplayMetrics c;
    int d;
    String e;
    String f;
    String g;
    String h;
    String i;
    String j;
    String k;
    int l;
    String m;
    Context n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;

    private e(Context context) {
        this.b = String.valueOf(4.06f);
        this.d = Build.VERSION.SDK_INT;
        this.e = Build.MODEL;
        this.f = Build.MANUFACTURER;
        this.g = Locale.getDefault().getLanguage();
        this.l = 0;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.n = context;
        this.c = j.c(context);
        this.a = j.e(context);
        this.i = j.d(context);
        this.j = TimeZone.getDefault().getID();
        this.l = j.i(context);
        this.k = j.j(context);
        this.m = context.getPackageName();
        if (this.d >= 14) {
            this.o = j.n(context);
        }
        this.p = j.m(context).toString();
        this.q = j.k(context);
        this.r = j.a();
        this.s = j.a(context);
    }

    void a(JSONObject jSONObject) throws JSONException {
        jSONObject.put("sr", this.c.widthPixels + "*" + this.c.heightPixels);
        Util.jsonPut(jSONObject, "av", this.a);
        Util.jsonPut(jSONObject, "ch", this.h);
        Util.jsonPut(jSONObject, "mf", this.f);
        Util.jsonPut(jSONObject, "sv", this.b);
        Util.jsonPut(jSONObject, "ov", Integer.toString(this.d));
        jSONObject.put("os", 1);
        Util.jsonPut(jSONObject, "op", this.i);
        Util.jsonPut(jSONObject, "lg", this.g);
        Util.jsonPut(jSONObject, "md", this.e);
        Util.jsonPut(jSONObject, "tz", this.j);
        if (this.l != 0) {
            jSONObject.put("jb", this.l);
        }
        Util.jsonPut(jSONObject, "sd", this.k);
        Util.jsonPut(jSONObject, "apn", this.m);
        if (Util.isNetworkAvailable(this.n) && Util.isWifiNet(this.n)) {
            JSONObject jSONObject2 = new JSONObject();
            Util.jsonPut(jSONObject2, NotificationStyle.BASE_STYLE, Util.getWiFiBBSID(this.n));
            Util.jsonPut(jSONObject2, "ss", Util.getWiFiSSID(this.n));
            if (jSONObject2.length() > 0) {
                Util.jsonPut(jSONObject, "wf", jSONObject2.toString());
            }
        }
        JSONArray wifiTopN = Util.getWifiTopN(this.n, 10);
        if (wifiTopN != null && wifiTopN.length() > 0) {
            Util.jsonPut(jSONObject, "wflist", wifiTopN.toString());
        }
        JSONArray sensors = Util.getSensors(this.n);
        if (sensors != null && sensors.length() > 0) {
            Util.jsonPut(jSONObject, "sslist", sensors.toString());
        }
        Util.jsonPut(jSONObject, "sen", this.o);
        Util.jsonPut(jSONObject, "cpu", this.p);
        Util.jsonPut(jSONObject, "ram", this.q);
        Util.jsonPut(jSONObject, "rom", this.r);
        Util.jsonPut(jSONObject, "ciip", this.s);
    }
}
