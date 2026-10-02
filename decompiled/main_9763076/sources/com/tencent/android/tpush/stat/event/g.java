package com.tencent.android.tpush.stat.event;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends d {
    private com.tencent.android.tpush.stat.a.a a;
    private JSONObject b;

    public g(Context context, int i, JSONObject jSONObject, long j) {
        super(context, i, j);
        this.b = null;
        this.a = new com.tencent.android.tpush.stat.a.a(context, j);
        this.b = jSONObject;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public EventType b() {
        return EventType.SESSION_ENV;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public boolean a(JSONObject jSONObject) throws JSONException {
        jSONObject.put("ut", 1);
        if (this.b != null) {
            jSONObject.put("cfg", this.b);
        }
        if (com.tencent.android.tpush.stat.a.e.k(this.l)) {
            jSONObject.put("ncts", 1);
        }
        this.a.a(jSONObject, (Thread) null);
        return true;
    }
}
