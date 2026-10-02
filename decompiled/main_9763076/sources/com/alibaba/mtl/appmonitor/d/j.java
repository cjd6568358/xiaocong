package com.alibaba.mtl.appmonitor.d;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

/* JADX INFO: compiled from: SampleRules.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class j {
    private static final String TAG = null;
    private static j a;
    private String A;
    private int r;
    private Map<com.alibaba.mtl.appmonitor.a.f, g> t = new HashMap();

    private j() {
        for (com.alibaba.mtl.appmonitor.a.f fVar : com.alibaba.mtl.appmonitor.a.f.values()) {
            if (fVar == com.alibaba.mtl.appmonitor.a.f.ALARM) {
                this.t.put(fVar, new f(fVar, fVar.e()));
            } else {
                this.t.put(fVar, new g(fVar, fVar.e()));
            }
        }
    }

    public static j a() {
        if (a == null) {
            synchronized (j.class) {
                if (a == null) {
                    a = new j();
                }
            }
        }
        return a;
    }

    public void init(Context context) {
        k();
    }

    public static boolean a(com.alibaba.mtl.appmonitor.a.f fVar, String str, String str2) {
        return a().b(fVar, str, str2, (Map<String, String>) null);
    }

    public static boolean a(com.alibaba.mtl.appmonitor.a.f fVar, String str, String str2, Map<String, String> map) {
        return a().b(fVar, str, str2, map);
    }

    public static boolean a(String str, String str2, Boolean bool, Map<String, String> map) {
        return a().b(str, str2, bool, map);
    }

    public boolean b(com.alibaba.mtl.appmonitor.a.f fVar, String str, String str2, Map<String, String> map) {
        g gVar = this.t.get(fVar);
        if (gVar != null) {
            return gVar.a(this.r, str, str2, map);
        }
        return false;
    }

    public boolean b(String str, String str2, Boolean bool, Map<String, String> map) {
        g gVar = this.t.get(com.alibaba.mtl.appmonitor.a.f.ALARM);
        if (gVar == null || !(gVar instanceof f)) {
            return false;
        }
        return ((f) gVar).a(this.r, str, str2, bool, map);
    }

    public void k() {
        this.r = new Random(System.currentTimeMillis()).nextInt(Constants.ERRORCODE_UNKNOWN);
    }

    public void b(String str) {
        com.alibaba.mtl.log.e.i.a("SampleRules", "config:", str);
        synchronized (this) {
            if (!com.alibaba.mtl.appmonitor.f.b.isBlank(str) && (this.A == null || !this.A.equals(str))) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    for (com.alibaba.mtl.appmonitor.a.f fVar : com.alibaba.mtl.appmonitor.a.f.values()) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(fVar.toString());
                        g gVar = this.t.get(fVar);
                        if (jSONObjectOptJSONObject != null && gVar != null) {
                            com.alibaba.mtl.log.e.i.a(TAG, fVar, jSONObjectOptJSONObject);
                            gVar.b(jSONObjectOptJSONObject);
                        }
                    }
                    this.A = str;
                } catch (Throwable th) {
                }
            }
        }
    }

    public void a(com.alibaba.mtl.appmonitor.a.f fVar, int i) {
        g gVar = this.t.get(fVar);
        if (gVar != null) {
            gVar.setSampling(i);
        }
    }
}
