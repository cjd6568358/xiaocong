package com.baidu.mobstat;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ba {
    public static final ba a = new ba();

    public void a(Context context, JSONObject jSONObject) throws Throwable {
        bd.a("startDataAnynalyzed start");
        a(jSONObject);
        az azVarA = az.a(context);
        boolean zA = azVarA.a();
        bd.a("is data collect closed:" + zA);
        if (!zA) {
            if (!y.a.b(Constants.ERRORCODE_UNKNOWN)) {
                c(context);
            }
            if (!y.b.b(Constants.ERRORCODE_UNKNOWN)) {
                d(context);
            }
            if (!y.c.b(Constants.ERRORCODE_UNKNOWN)) {
                e(context);
            }
            if (bc.e && !y.e.b(Constants.ERRORCODE_UNKNOWN)) {
                f(context);
            }
            boolean zN = de.n(context);
            if (zN && azVarA.l()) {
                bd.a("sendLog");
                g(context);
            } else if (!zN) {
                bd.a("isWifiAvailable = false, will not sendLog");
            } else {
                bd.a("can not sendLog due to time stratergy");
            }
        }
        bd.a("startDataAnynalyzed finished");
    }

    private void a(JSONObject jSONObject) {
        be beVar = new be(jSONObject);
        bc.b = beVar.a;
        bc.c = beVar.b;
        bc.d = beVar.c;
    }

    private void c(Context context) throws Throwable {
        bd.a("collectAPWithStretegy 1");
        az azVarA = az.a(context);
        long jA = azVarA.a(u.AP_LIST);
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jE = azVarA.e();
        bd.a("now time: " + jCurrentTimeMillis + ": last time: " + jA + "; time interval: " + jE);
        if (jA == 0 || jCurrentTimeMillis - jA > jE) {
            bd.a("collectAPWithStretegy 2");
            n.a(context);
        }
    }

    private void d(Context context) throws Throwable {
        bd.a("collectAPPListWithStretegy 1");
        long jCurrentTimeMillis = System.currentTimeMillis();
        az azVarA = az.a(context);
        long jA = azVarA.a(u.APP_USER_LIST);
        long jF = azVarA.f();
        bd.a("now time: " + jCurrentTimeMillis + ": last time: " + jA + "; userInterval : " + jF);
        if (jA == 0 || jCurrentTimeMillis - jA > jF || !azVarA.a(jA)) {
            bd.a("collectUserAPPListWithStretegy 2");
            n.a(context, false);
        }
        long jA2 = azVarA.a(u.APP_SYS_LIST);
        long jG = azVarA.g();
        bd.a("now time: " + jCurrentTimeMillis + ": last time: " + jA2 + "; sysInterval : " + jG);
        if (jA2 == 0 || jCurrentTimeMillis - jA2 > jG) {
            bd.a("collectSysAPPListWithStretegy 2");
            n.a(context, true);
        }
    }

    private void e(Context context) throws Throwable {
        bd.a("collectAPPTraceWithStretegy 1");
        long jCurrentTimeMillis = System.currentTimeMillis();
        az azVarA = az.a(context);
        long jA = azVarA.a(u.APP_TRACE_HIS);
        long jI = azVarA.i();
        bd.a("now time: " + jCurrentTimeMillis + ": last time: " + jA + "; time interval: " + jI);
        if (jA == 0 || jCurrentTimeMillis - jA > jI) {
            bd.a("collectAPPTraceWithStretegy 2");
            n.b(context, false);
        }
    }

    private void f(Context context) throws Throwable {
        bd.a("collectAPKWithStretegy 1");
        long jCurrentTimeMillis = System.currentTimeMillis();
        az azVarA = az.a(context);
        long jA = azVarA.a(u.APP_APK);
        long jH = azVarA.h();
        bd.a("now time: " + jCurrentTimeMillis + ": last time: " + jA + "; interval : " + jH);
        if (jA == 0 || jCurrentTimeMillis - jA > jH) {
            bd.a("collectAPKWithStretegy 2");
            n.b(context);
        }
    }

    public void a(Context context, String str) throws Throwable {
        az.a(context).a(str);
    }

    public void b(Context context, String str) throws Throwable {
        az.a(context).b(str);
    }

    public void a(Context context, long j) throws Throwable {
        az.a(context).a(u.LAST_UPDATE, j);
    }

    private void g(Context context) throws Throwable {
        az.a(context).a(u.LAST_SEND, System.currentTimeMillis());
        JSONObject jSONObjectA = v.a(context);
        bd.a("header: " + jSONObjectA);
        int i = 0;
        while (a()) {
            int i2 = i + 1;
            if (i > 0) {
                v.c(jSONObjectA);
            }
            b(context, jSONObjectA);
            i = i2;
        }
    }

    private boolean a() {
        return (y.a.b() && y.b.b() && y.c.b() && y.d.b() && y.e.b()) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0079  */
    private void b(Context context, JSONObject jSONObject) {
        int length;
        int length2 = 0;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("he", jSONObject);
            length2 = 0 + jSONObject.toString().length();
        } catch (JSONException e) {
            bd.a(e);
        }
        bd.a("APP_MEM");
        if (az.a(context).b()) {
            length = length2;
        } else {
            String strT = de.t(context);
            JSONArray jSONArray = new JSONArray();
            bd.a(strT);
            jSONArray.put(strT);
            if (jSONArray.length() > 0) {
                try {
                    jSONObject2.put("app_mem3", jSONArray);
                    length = length2 + jSONArray.toString().length();
                } catch (JSONException e2) {
                    bd.a(e2);
                    length = length2;
                }
            } else {
                length = length2;
            }
        }
        bd.a("APP_APK");
        List<String> listA = y.e.a(20480);
        JSONArray jSONArray2 = new JSONArray();
        for (String str : listA) {
            bd.a(str);
            jSONArray2.put(str);
        }
        if (jSONArray2.length() > 0) {
            try {
                jSONObject2.put("app_apk3", jSONArray2);
                length += jSONArray2.toString().length();
            } catch (JSONException e3) {
                bd.a(e3);
            }
        }
        bd.a("APP_CHANGE");
        List<String> listA2 = y.d.a(10240);
        JSONArray jSONArray3 = new JSONArray();
        for (String str2 : listA2) {
            bd.a(str2);
            jSONArray3.put(str2);
        }
        if (jSONArray3.length() > 0) {
            try {
                jSONObject2.put("app_change3", jSONArray3);
                length += jSONArray3.toString().length();
            } catch (JSONException e4) {
                bd.a(e4);
            }
        }
        bd.a("APP_TRACE");
        List<String> listA3 = y.c.a(15360);
        JSONArray jSONArray4 = new JSONArray();
        for (String str3 : listA3) {
            bd.a(str3);
            jSONArray4.put(str3);
        }
        if (jSONArray4.length() > 0) {
            try {
                jSONObject2.put("app_trace3", jSONArray4);
                length += jSONArray4.toString().length();
            } catch (JSONException e5) {
                bd.a(e5);
            }
        }
        bd.a("APP_LIST");
        List<String> listA4 = y.b.a(46080);
        JSONArray jSONArray5 = new JSONArray();
        for (String str4 : listA4) {
            bd.a(str4);
            jSONArray5.put(str4);
        }
        if (jSONArray5.length() > 0) {
            try {
                jSONObject2.put("app_list3", jSONArray5);
                length += jSONArray5.toString().length();
            } catch (JSONException e6) {
                bd.a(e6);
            }
        }
        bd.a("AP_LIST");
        List<String> listA5 = y.a.a(184320 - length);
        JSONArray jSONArray6 = new JSONArray();
        for (String str5 : listA5) {
            bd.a(str5);
            jSONArray6.put(str5);
        }
        if (jSONArray6.length() > 0) {
            try {
                jSONObject2.put("ap_list3", jSONArray6);
                length += jSONArray6.toString().length();
            } catch (JSONException e7) {
                bd.a(e7);
            }
        }
        bd.a("log in bytes is almost :" + length);
        JSONArray jSONArray7 = new JSONArray();
        jSONArray7.put(jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("payload", jSONArray7);
            al.a().a(context, jSONObject3.toString());
        } catch (Exception e8) {
            bd.a(e8);
        }
    }

    public boolean a(Context context) {
        az azVarA = az.a(context);
        long jA = azVarA.a(u.LAST_UPDATE);
        long jC = azVarA.c();
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - jA > jC) {
            bd.a("need to update, checkWithLastUpdateTime lastUpdateTime =" + jA + "nowTime=" + jCurrentTimeMillis + ";timeInteveral=" + jC);
            return true;
        }
        bd.a("no need to update, checkWithLastUpdateTime lastUpdateTime =" + jA + "nowTime=" + jCurrentTimeMillis + ";timeInteveral=" + jC);
        return false;
    }

    public boolean b(Context context) {
        return !az.a(context).a() || a(context);
    }
}
