package com.huawei.hms.support.b;

import android.content.Context;
import android.text.TextUtils;
import com.hianalytics.android.v1.HiAnalytics;
import com.huawei.hms.c.g;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: HiAnalyticsUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a;
    private static final Object b = new Object();

    public static a a() {
        a aVar;
        synchronized (b) {
            if (a == null) {
                a = new a();
            }
            aVar = a;
        }
        return aVar;
    }

    public void a(Context context, String str, Map<String, String> map) {
        if (!b()) {
            String strA = a(map);
            if (!TextUtils.isEmpty(strA)) {
                HiAnalytics.onEvent(context, str, strA);
            }
        }
    }

    private String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jSONObject.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            com.huawei.hms.support.log.a.d("HiAnalyticsUtil", "AnalyticsHelper create json exception" + e.getMessage());
        }
        return jSONObject.toString();
    }

    public boolean b() {
        if (g.a()) {
            return false;
        }
        com.huawei.hms.support.log.a.a("HiAnalyticsUtil", "not ChinaROM ");
        return true;
    }
}
