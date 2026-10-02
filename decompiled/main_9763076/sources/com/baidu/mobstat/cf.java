package com.baidu.mobstat;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cf {
    private volatile long a;
    private volatile long e;
    private volatile long b = 0;
    private volatile long c = 0;
    private volatile long d = 0;
    private volatile int f = 0;
    private List<cg> g = new ArrayList();

    public cf() {
        this.a = 0L;
        this.e = 0L;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.a = jCurrentTimeMillis;
        this.e = jCurrentTimeMillis;
    }

    public void a() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        c(jCurrentTimeMillis);
        this.b = 0L;
        this.c = 0L;
        this.d = 0L;
        this.e = jCurrentTimeMillis;
        this.f = 0;
        this.f = 0;
        this.g.clear();
    }

    public void a(long j) {
        this.c = j;
    }

    public void b(long j) {
        this.d = j;
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(cg cgVar) {
        a(this.g, cgVar);
    }

    private void a(List<cg> list, cg cgVar) {
        if (list != null && cgVar != null) {
            int size = list.size();
            if (size == 0) {
                list.add(cgVar);
                return;
            }
            cg cgVar2 = list.get(size - 1);
            if (TextUtils.isEmpty(cgVar2.a) || TextUtils.isEmpty(cgVar.a)) {
                list.add(cgVar);
                return;
            }
            if (!cgVar2.a.equals(cgVar.a) || cgVar2.f == cgVar.f) {
                list.add(cgVar);
            } else if (cgVar2.f) {
                cgVar2.a(cgVar);
            }
        }
    }

    public void c(long j) {
        this.a = j;
    }

    public long b() {
        return this.a;
    }

    public long c() {
        return this.b;
    }

    public void d(long j) {
        this.b = j;
    }

    public JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotifyType.SOUND, this.a);
            jSONObject.put("e", this.b);
            jSONObject.put("i", this.e);
            jSONObject.put("c", 1);
            jSONObject.put("s2", this.c);
            jSONObject.put("e2", this.d);
            jSONObject.put("pc", this.f);
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.g.size(); i++) {
                jSONArray.put(a(this.g.get(i), this.a));
            }
            jSONObject.put("p", jSONArray);
        } catch (JSONException e) {
            db.a("StatSession.constructJSONObject() failed");
        }
        return jSONObject;
    }

    public static JSONObject a(cg cgVar, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", cgVar.a());
            jSONObject.put("d", cgVar.c());
            long jD = cgVar.d() - j;
            jSONObject.put("ps", jD >= 0 ? jD : 0L);
            jSONObject.put("t", cgVar.b());
            jSONObject.put("at", cgVar.f() ? 1 : 0);
            JSONObject jSONObjectE = cgVar.e();
            if (jSONObjectE != null && jSONObjectE.length() != 0) {
                jSONObject.put("ext", jSONObjectE);
            }
        } catch (JSONException e) {
            db.b(e);
        }
        return jSONObject;
    }
}
