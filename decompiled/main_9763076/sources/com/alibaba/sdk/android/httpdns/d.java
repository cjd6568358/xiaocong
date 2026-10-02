package com.alibaba.sdk.android.httpdns;

import com.tencent.android.tpush.common.MessageKey;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d {
    private String[] a;
    private long b;
    private long c;
    private String hostName;

    d(com.alibaba.sdk.android.httpdns.b.e eVar) {
        int size;
        this.hostName = eVar.h;
        this.c = com.alibaba.sdk.android.httpdns.b.c.a(eVar.j);
        if (eVar.a == null || eVar.a.size() <= 0 || (size = eVar.a.size()) <= 0) {
            return;
        }
        this.b = com.alibaba.sdk.android.httpdns.b.c.a(eVar.a.get(0).l);
        this.a = new String[size];
        for (int i = 0; i < size; i++) {
            this.a[i] = eVar.a.get(i).k;
        }
    }

    d(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        this.hostName = jSONObject.getString("host");
        JSONArray jSONArray = jSONObject.getJSONArray("ips");
        int length = jSONArray.length();
        this.a = new String[length];
        for (int i = 0; i < length; i++) {
            this.a[i] = jSONArray.getString(i);
        }
        this.b = jSONObject.getLong(MessageKey.MSG_TTL);
        this.c = System.currentTimeMillis() / 1000;
    }

    d(String str, String[] strArr, long j, long j2) {
        this.hostName = str;
        this.a = strArr;
        this.b = j;
        this.c = j2;
    }

    long a() {
        return this.b;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    com.alibaba.sdk.android.httpdns.b.e m45a() {
        com.alibaba.sdk.android.httpdns.b.e eVar = new com.alibaba.sdk.android.httpdns.b.e();
        eVar.h = this.hostName;
        eVar.j = String.valueOf(this.c);
        eVar.i = com.alibaba.sdk.android.httpdns.b.b.g();
        if (this.a != null && this.a.length > 0) {
            eVar.a = new ArrayList<>();
            for (String str : this.a) {
                com.alibaba.sdk.android.httpdns.b.g gVar = new com.alibaba.sdk.android.httpdns.b.g();
                gVar.k = str;
                gVar.l = String.valueOf(this.b);
                eVar.a.add(gVar);
            }
        }
        return eVar;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    String[] m46a() {
        return this.a;
    }

    long b() {
        return this.c;
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    boolean m47b() {
        return b() + a() < System.currentTimeMillis() / 1000;
    }

    public String toString() {
        String str = "host: " + this.hostName + " ip cnt: " + this.a.length + " ttl: " + this.b;
        for (int i = 0; i < this.a.length; i++) {
            str = str + "\n ip: " + this.a[i];
        }
        return str;
    }
}
