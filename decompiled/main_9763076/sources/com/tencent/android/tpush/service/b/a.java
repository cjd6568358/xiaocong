package com.tencent.android.tpush.service.b;

import android.content.Context;
import com.tencent.android.tpush.common.n;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private String a;
    private Context b;

    public a(Context context, String str) {
        this.a = null;
        this.b = null;
        this.b = context;
        this.a = "com.tencent.xg.tpush.httpdns.cache." + str;
    }

    public void a(JSONObject jSONObject) {
        n.b(this.b, this.a, jSONObject.toString());
    }

    public String a() {
        return n.a(this.b, this.a, (String) null);
    }

    public String b() {
        String strA = a();
        d dVarA = (strA == null || strA.length() <= 7) ? null : d.a(strA);
        if (dVarA == null) {
            return null;
        }
        long jB = dVarA.b() - System.currentTimeMillis();
        com.tencent.android.tpush.a.a.c("httpDns", "cacheResult:" + dVarA + ",diff:" + jB);
        if (jB > 0) {
            return dVarA.a();
        }
        com.tencent.android.tpush.a.a.g("httpDns", "cacheResult Exp.");
        return null;
    }
}
