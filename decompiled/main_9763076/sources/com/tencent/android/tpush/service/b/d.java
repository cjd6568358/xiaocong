package com.tencent.android.tpush.service.b;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private JSONObject a = new JSONObject();
    private List b = new ArrayList();

    public static d a(String str) {
        d dVar = new d();
        try {
            dVar.a = new JSONObject(str);
            String strOptString = dVar.a.optString("ips", null);
            if (strOptString != null && strOptString.trim().length() > 7) {
                if (strOptString.contains(";")) {
                    String[] strArrSplit = strOptString.split(";");
                    for (String str2 : strArrSplit) {
                        if (b.b(str2)) {
                            dVar.b.add(str2);
                        }
                    }
                } else if (b.b(strOptString)) {
                    dVar.b.add(strOptString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dVar;
    }

    public String a() {
        if (this.b == null) {
            return null;
        }
        int size = this.b.size();
        if (size == 1) {
            return (String) this.b.get(0);
        }
        if (size <= 1) {
            return null;
        }
        Collections.shuffle(this.b);
        return (String) this.b.get(0);
    }

    public String toString() {
        return this.a.toString();
    }

    public long b() {
        return this.a.optLong("exp", System.currentTimeMillis());
    }
}
