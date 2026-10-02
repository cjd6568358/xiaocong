package com.tencent.wxop.stat.event;

import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    public String a;
    public JSONArray b;
    public JSONObject c;

    public c() {
        this.c = null;
    }

    public c(String str, String[] strArr, Properties properties) {
        this.c = null;
        this.a = str;
        if (properties != null) {
            this.c = new JSONObject(properties);
            return;
        }
        if (strArr == null) {
            this.c = new JSONObject();
            return;
        }
        this.b = new JSONArray();
        for (String str2 : strArr) {
            this.b.put(str2);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof c) {
            return toString().equals(((c) obj).toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.a).append(",");
        if (this.b != null) {
            sb.append(this.b.toString());
        }
        if (this.c != null) {
            sb.append(this.c.toString());
        }
        return sb.toString();
    }
}
