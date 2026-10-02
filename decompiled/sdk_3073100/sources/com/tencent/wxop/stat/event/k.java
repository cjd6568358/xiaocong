package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.l;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class k extends e {
    private com.tencent.wxop.stat.common.b a;
    private JSONObject m;

    public k(Context context, int i, JSONObject jSONObject, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.m = null;
        this.a = new com.tencent.wxop.stat.common.b(context);
        this.m = jSONObject;
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.SESSION_ENV;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        if (this.e != null) {
            jSONObject.put("ut", this.e.d());
        }
        if (this.m != null) {
            jSONObject.put("cfg", this.m);
        }
        if (l.w(this.l)) {
            jSONObject.put("ncts", 1);
        }
        this.a.a(jSONObject, (Thread) null);
        return true;
    }
}
