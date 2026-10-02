package com.tencent.android.tpush.stat.event;

import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public String a;
    public JSONArray b;
    public JSONObject c;
    public boolean d;

    public b(String str, String[] strArr, Properties properties) {
        this.c = null;
        this.d = false;
        this.a = str;
        if (properties != null) {
            this.c = new JSONObject(properties);
            return;
        }
        if (strArr != null) {
            this.b = new JSONArray();
            for (String str2 : strArr) {
                this.b.put(str2);
            }
            return;
        }
        this.c = new JSONObject();
    }

    public b() {
        this.c = null;
        this.d = false;
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

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof b) {
            return toString().equals(((b) obj).toString());
        }
        return false;
    }
}
