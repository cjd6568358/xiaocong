package com.tencent.android.tpush.stat;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static com.tencent.android.tpush.stat.a.f e = com.tencent.android.tpush.stat.a.e.b();
    static d a = new d(2);
    static d b = new d(1);
    private static StatReportStrategy f = StatReportStrategy.APP_LAUNCH;
    private static boolean g = false;
    private static boolean h = true;
    static String c = "__HIBERNATE__";
    static volatile String d = "pingma.qq.com:80";
    private static volatile String i = "http://pingma.qq.com:80/mstat/report";
    private static boolean j = false;
    private static short k = 6;
    private static int l = WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
    private static int m = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
    private static int n = 0;
    private static int o = 20;

    public static StatReportStrategy a() {
        return f;
    }

    public static void a(StatReportStrategy statReportStrategy) {
        f = statReportStrategy;
        if (b()) {
            e.h("Change to statSendStrategy: " + statReportStrategy);
        }
    }

    public static boolean b() {
        return g;
    }

    public static boolean c() {
        return h && com.tencent.android.tpush.service.a.a.a(h.a((Context) null)).B == 1;
    }

    public static void a(boolean z) {
        h = z;
        if (!z) {
            e.c("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    static void a(Context context, JSONObject jSONObject) {
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (next.equalsIgnoreCase(Integer.toString(b.a))) {
                    a(context, b, jSONObject.getJSONObject(next));
                } else if (next.equalsIgnoreCase(Integer.toString(a.a))) {
                    a(context, a, jSONObject.getJSONObject(next));
                }
            }
        } catch (JSONException e2) {
            e.b((Throwable) e2);
        }
    }

    static void a(Context context, d dVar, JSONObject jSONObject) {
        boolean z;
        boolean z2 = false;
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (next.equalsIgnoreCase(NotifyType.VIBRATE)) {
                    int i2 = jSONObject.getInt(next);
                    z = dVar.d != i2 ? true : z2;
                    dVar.d = i2;
                } else if (next.equalsIgnoreCase("c")) {
                    String string = jSONObject.getString("c");
                    if (string.length() > 0) {
                        dVar.b = new JSONObject(string);
                    }
                    z = z2;
                } else {
                    if (next.equalsIgnoreCase("m")) {
                        dVar.c = jSONObject.getString("m");
                    }
                    z = z2;
                }
                z2 = z;
            }
            if (z2 && dVar.a == b.a) {
                a(dVar.b);
                b(dVar.b);
            }
            a(context, dVar);
        } catch (JSONException e2) {
            e.b((Throwable) e2);
        } catch (Throwable th) {
            e.b(th);
        }
    }

    static void a(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategyA = StatReportStrategy.a(jSONObject.getInt("rs"));
            if (statReportStrategyA != null) {
                a(statReportStrategyA);
            }
        } catch (JSONException e2) {
            if (b()) {
                e.b("rs not found.");
            }
        }
    }

    static void a(Context context, d dVar) {
        if (dVar.a == b.a) {
            b = dVar;
            a(b.b);
        } else if (dVar.a == a.a) {
            a = dVar;
        }
    }

    static void b(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            try {
                String string = jSONObject.getString(c);
                if (b()) {
                    e.h("hibernateVer:" + string + ", current version:2.0.6");
                }
                long jA = com.tencent.android.tpush.stat.a.e.a(string);
                if (com.tencent.android.tpush.stat.a.e.a("2.0.6") <= jA) {
                    a(jA);
                }
            } catch (JSONException e2) {
                e.h("__HIBERNATE__ not found.");
            }
        }
    }

    static void a(long j2) {
        com.tencent.android.tpush.stat.a.g.b(f.a(), c, j2);
        a(false);
        e.c("MTA is disable for current SDK version");
    }

    public static String d() {
        return i;
    }

    public static boolean e() {
        return j;
    }

    public static void b(boolean z) {
        j = z;
    }

    public static short f() {
        return k;
    }

    public static int g() {
        return l;
    }
}
