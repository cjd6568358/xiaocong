package com.tencent.android.tpush.service.a;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.n;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.e.m;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a K = null;
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    public int H;
    public JSONArray I;
    public Map J;
    private Context L;
    public long a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public String u;
    public int v;
    public int w;
    public String x;
    public int y;
    public int z;

    private a(Context context) {
        this.L = null;
        this.x = null;
        this.y = 1;
        this.z = 1;
        this.A = 60000;
        this.B = 1;
        this.C = 1;
        this.D = 1;
        this.E = -1;
        this.F = -1;
        this.G = -1;
        this.H = -1;
        this.I = null;
        this.L = context.getApplicationContext();
        a();
    }

    public static a a(Context context) {
        if (K == null) {
            synchronized (a.class) {
                if (K == null) {
                    K = new a(context);
                }
            }
        }
        return K;
    }

    public String toString() {
        return "ConfigurationManager [context=" + this.L + ", configurationVersion=" + this.a + ", receiveTimeout=" + this.b + ", heartbeatInterval=" + this.c + ", httpHeartbeatInterval=" + this.d + ", speedTestInterval=" + this.e + ", channelMessageExpires=" + this.f + ", freqencySuccess=" + this.g + ", freqencyFailed=" + this.h + ", reportInterval=" + this.i + ", reportMaxCount=" + this.j + ", httpRetryCount=" + this.k + ", ackMaxCount=" + this.l + ", ackDuration=" + this.m + ", loadIpInerval=" + this.n + ", redirectConnectTimeOut=" + this.o + ", redirectSoTimeOut=" + this.p + ", strategyExpiredTime=" + this.q + ", logLevel=" + this.r + ", logFileSizeLimit=" + this.s + ", errCount=" + this.t + ", logUploadDomain=" + this.u + ", rptLive=" + this.v + ", rptLiveIntvl=" + this.w + ", disableXG=" + this.x + ", enableNewWd=" + this.y + ", enableMonitor=" + this.z + ", monitorFreg=" + this.A + ", enableReport=" + this.B + ", abTestVersion=" + this.C + ", isHttpDNSEnable=" + this.D + ", isLBSEnable=" + this.E + ", isAPPListEnable=" + this.F + ", isNotificatiobStatusEnable=" + this.G + ", isQgameEnable=" + this.H + ", pullup_Arr_ProviderAndActivty=" + this.I + ", pullup_packges_map=" + this.J + "]";
    }

    public void a() {
        if (this.a == 0) {
            this.a = b();
            this.b = a("recTo", BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            this.c = a("hbIntvl", 299980);
            this.d = a("httpHbIntvl", 600000);
            this.e = a("stIntvl", 54000000);
            this.f = a("cnMsgExp", 60000);
            this.g = a("fqcSuc", 10);
            this.h = a("fqcFal", 100);
            this.i = a("rptIntvl", 1200);
            this.j = a("rptMaxCnt", 5);
            this.k = a("httpRtCnt", 3);
            this.l = a("ackMaxCnt", 3);
            this.m = a("ackDuration", 180000);
            this.n = a("loadIpIntvl", 72000000);
            this.F = a("conf_applist", -1);
            this.E = a("conf_lbs", -1);
            this.G = a("conf_nt_status", -1);
            this.o = a("redirectConnectTime", BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            this.p = a("redirectSoTime", 20000);
            this.q = a("strategyExpiredTime", 1440);
            this.v = a("rptLive", 0);
            this.w = a("rptLiveIntvl", 3600);
            this.s = a("logFileSizeLimit", 262144);
            this.t = a("errCount", 5);
            this.u = a("logUploadDomain", "183.61.46.193");
            this.x = a("stopXG", Constants.MAIN_VERSION_TAG);
            String strA = a("pullup_packges", Constants.MAIN_VERSION_TAG);
            if (!t.c(strA)) {
                String strDecrypt = Rijndael.decrypt(strA);
                if (!t.c(strDecrypt)) {
                    this.J = b(strDecrypt);
                }
            }
            this.y = a("enableNewWd", 1);
            this.B = a("report", 1);
            this.C = a("ABT", 1);
            this.z = a("enable.monitor", 1);
            this.A = a("m.freq", 60000);
            this.D = a("httpdns", 1);
            this.H = a("conf_qgame", -1);
            try {
                String strA2 = a("conf_pull_arr", Constants.MAIN_VERSION_TAG);
                if (!t.c(strA2)) {
                    String strDecrypt2 = Rijndael.decrypt(strA2);
                    if (!t.c(strDecrypt2)) {
                        this.I = new JSONArray(strDecrypt2);
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "pullup_Arr_ProviderAndActivty.", th);
            }
        }
    }

    private a() {
        this.L = null;
        this.x = null;
        this.y = 1;
        this.z = 1;
        this.A = 60000;
        this.B = 1;
        this.C = 1;
        this.D = 1;
        this.E = -1;
        this.F = -1;
        this.G = -1;
        this.H = -1;
        this.I = null;
    }

    public void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.a = a("confVer", jSONObject);
            this.a = this.a == 0 ? 1L : this.a;
            this.b = a("recTo", jSONObject) * 1000;
            this.b = this.b == 0 ? BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH : this.b;
            this.c = a("hbIntvl", jSONObject) * 60 * 1000;
            this.c = this.c == 0 ? 299980 : this.c;
            this.d = a("httpHbIntvl", jSONObject) * 60 * 1000;
            this.d = this.d == 0 ? 600000 : this.d;
            this.e = a("stIntvl", jSONObject) * 60 * 1000;
            this.e = this.e == 0 ? 54000000 : this.e;
            this.f = a("cnMsgExp", jSONObject) * 1000;
            this.f = this.f == 0 ? 60000 : this.f;
            this.g = a("fqcSuc", jSONObject);
            this.g = this.g == 0 ? 10 : this.g;
            this.h = a("fqcFal", jSONObject);
            this.h = this.h == 0 ? 100 : this.h;
            this.i = a("rptIntvl", jSONObject);
            this.i = this.i == 0 ? 1200 : this.i;
            this.j = a("rptMaxCnt", jSONObject);
            this.j = this.j == 0 ? 5 : this.j;
            this.k = a("httpRtCnt", jSONObject);
            this.k = this.k == 0 ? 3 : this.k;
            this.l = a("ackMaxCnt", jSONObject);
            this.l = this.l != 0 ? this.l : 3;
            this.m = a("ackDuration", jSONObject) * 1000;
            this.m = this.m == 0 ? 180000 : this.m;
            this.n = a("loadIpIntvl", jSONObject) * 60 * 60 * 1000;
            this.n = this.n == 0 ? 72000000 : this.n;
            this.o = a("redirectConnectTime", jSONObject);
            this.o = this.o == 0 ? BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH : this.o;
            this.p = a("redirectSoTime", jSONObject);
            this.p = this.p == 0 ? 20000 : this.p;
            this.q = a("strategyExpiredTime", jSONObject);
            this.q = this.q == 0 ? 1440 : this.q;
            this.v = a("rptLive", jSONObject);
            this.v = this.v == 0 ? 0 : this.v;
            this.w = a("rptLiveIntvl", jSONObject);
            this.w = this.w != 3600 ? this.w : 3600;
            this.r = a("logLevel", jSONObject);
            this.s = a("logFileSizeLimit", jSONObject) * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
            this.s = this.s == 0 ? 262144 : this.s;
            this.t = a("errCount", jSONObject);
            this.t = this.t != 0 ? this.t : 5;
            this.u = b("logUploadDomain", jSONObject);
            this.u = TextUtils.isEmpty(this.u) ? "183.61.46.193" : this.u;
            this.y = jSONObject.optInt("enableNewWd", 1);
            this.B = jSONObject.optInt("report", 1);
            this.x = jSONObject.optString("stopXG", null);
            this.C = jSONObject.optInt("ABT", 1);
            this.z = jSONObject.optInt("enable.monitor", 1);
            this.A = jSONObject.optInt("m.freq", 60000);
            this.D = jSONObject.optInt("httpdns", 1);
            this.F = jSONObject.optInt("conf_applist", -1);
            this.E = jSONObject.optInt("conf_lbs", -1);
            this.G = jSONObject.optInt("conf_nt_status", -1);
            this.H = jSONObject.optInt("conf_qgame", -1);
            String strOptString = jSONObject.optString("st.kv", Constants.MAIN_VERSION_TAG);
            String strOptString2 = jSONObject.optString("sp.kv", Constants.MAIN_VERSION_TAG);
            String strOptString3 = jSONObject.optString("pullup_packges", Constants.MAIN_VERSION_TAG);
            if (!t.c(strOptString3)) {
                this.J = b(strOptString3);
                n.b(c(), c("pullup_packges"), Rijndael.encrypt(strOptString3));
            }
            n.b(c(), c("confVer"), this.a);
            n.b(c(), c("recTo"), this.b);
            n.b(c(), c("hbIntvl"), this.c);
            n.b(c(), c("httpHbIntvl"), this.d);
            n.b(c(), c("stIntvl"), this.e);
            n.b(c(), c("cnMsgExp"), this.f);
            n.b(c(), c("fqcSuc"), this.g);
            n.b(c(), c("fqcFal"), this.h);
            n.b(c(), c("rptIntvl"), this.i);
            n.b(c(), c("rptMaxCnt"), this.j);
            n.b(c(), c("httpRtCnt"), this.k);
            n.b(c(), c("ackMaxCnt"), this.l);
            n.b(c(), c("ackDuration"), this.m);
            n.b(c(), c("loadIpIntvl"), this.n);
            n.b(c(), c("redirectConnectTime"), this.o);
            n.b(c(), c("redirectSoTime"), this.p);
            n.b(c(), c("strategyExpiredTime"), this.q);
            n.b(c(), c("rptLive"), this.v);
            n.b(c(), c("rptLiveIntvl"), this.w);
            n.b(c(), c("logLevel"), this.r);
            n.b(c(), c("logFileSizeLimit"), this.s);
            n.b(c(), c("errCount"), this.t);
            n.b(c(), c("conf_applist"), this.F);
            n.b(c(), c("conf_lbs"), this.E);
            n.b(c(), c("conf_nt_status"), this.G);
            n.b(c(), c("conf_qgame"), this.H);
            if (!m.b(this.x)) {
                n.b(c(), c("stopXG"), Rijndael.encrypt(this.x));
            }
            n.b(c(), c("enableNewWd"), this.y);
            n.b(c(), c("report"), this.B);
            n.b(c(), c("enable.monitor"), this.z);
            n.b(c(), c("m.freq"), this.A);
            n.b(c(), c("httpdns"), this.D);
            if (!TextUtils.isEmpty(strOptString)) {
                b.b(c(), strOptString);
            }
            if (!TextUtils.isEmpty(strOptString2)) {
                b.a(c(), strOptString2);
            }
            try {
                this.I = jSONObject.optJSONArray("conf_pull_arr");
                if (this.I != null && this.I.length() > 0) {
                    n.b(c(), c("conf_pull_arr"), Rijndael.encrypt(this.I.toString()));
                } else if (this.I != null && this.I.length() == 0) {
                    System.err.println("pullup_Arr_ProviderAndActivty length is 0 remove the old arr");
                    this.I = null;
                    n.a(c(), c("conf_pull_arr"));
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "pullup_Arr_ProviderAndActivty.", th);
            }
        } catch (Throwable th2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "parseValue failed.", th2);
        }
    }

    public long b() {
        if (this.L != null) {
            return n.a(this.L, c("confVer"), 1L);
        }
        return 1L;
    }

    public void a(long j) {
        if (this.L != null && b() != j) {
            n.b(this.L, c("confVer"), j);
        }
    }

    public Map b(String str) {
        String[] strArrSplit;
        try {
            if (t.c(str) || (strArrSplit = str.split(",")) == null || strArrSplit.length <= 0) {
                return null;
            }
            HashMap map = new HashMap();
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split("/");
                if (strArrSplit2 != null && strArrSplit2.length >= 2) {
                    map.put(strArrSplit2[0], strArrSplit2[1]);
                }
            }
            return map;
        } catch (Throwable th) {
            return null;
        }
    }

    private Context c() {
        if (this.L != null) {
            return this.L;
        }
        if (com.tencent.android.tpush.service.n.f() != null) {
            this.L = com.tencent.android.tpush.service.n.f();
            return this.L;
        }
        if (this.L == null && XGPushManager.getContext() != null) {
            this.L = XGPushManager.getContext();
        }
        return this.L;
    }

    public int a(String str, int i) {
        return n.a(c(), c(str), i);
    }

    public String a(String str, String str2) {
        String strA = n.a(c(), c(str), str2);
        return TextUtils.isEmpty(strA) ? str2 : strA;
    }

    public int a(String str, JSONObject jSONObject) {
        if (jSONObject != null && !m.b(str)) {
            try {
                return jSONObject.getInt(str);
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getJsonInt", e);
            }
        }
        return 0;
    }

    public String b(String str, JSONObject jSONObject) {
        if (jSONObject != null && !m.b(str)) {
            try {
                return jSONObject.getString(str);
            } catch (JSONException e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getJsonStr", e);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public String c(String str) {
        return "com.tencent.tpus." + str;
    }
}
