package com.tencent.mid.b;

import com.tencent.mid.util.Util;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static String a = "ts";
    public static String b = "times";
    public static String c = "mfreq";
    public static String d = "mdays";
    private static com.tencent.mid.util.f i = Util.getLogger();
    private long e;
    private int f;
    private int g;
    private int h;

    public a() {
        this.e = 0L;
        this.f = 1;
        this.g = WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        this.h = 3;
    }

    public a(String str) {
        this.e = 0L;
        this.f = 1;
        this.g = WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        this.h = 3;
        if (Util.isStringValid(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull(a)) {
                    this.e = jSONObject.getLong(a);
                }
                if (!jSONObject.isNull(c)) {
                    this.g = jSONObject.getInt(c);
                }
                if (!jSONObject.isNull(b)) {
                    this.f = jSONObject.getInt(b);
                }
                if (jSONObject.isNull(d)) {
                    return;
                }
                this.h = jSONObject.getInt(d);
            } catch (JSONException e) {
                i.d(e.toString());
            }
        }
    }

    public int a() {
        return this.h;
    }

    public void a(int i2) {
        this.h = i2;
    }

    public void a(long j) {
        this.e = j;
    }

    public long b() {
        return this.e;
    }

    public void b(int i2) {
        this.f = i2;
    }

    public int c() {
        return this.f;
    }

    public void c(int i2) {
        this.g = i2;
    }

    public int d() {
        return this.g;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(a, this.e);
            jSONObject.put(b, this.f);
            jSONObject.put(c, this.g);
            jSONObject.put(d, this.h);
        } catch (JSONException e) {
            i.d(e.toString());
        }
        return jSONObject.toString();
    }
}
