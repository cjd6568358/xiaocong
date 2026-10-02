package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class j extends e {
    Long a;
    String m;
    String n;

    public j(Context context, String str, String str2, int i, Long l, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = null;
        this.n = str;
        this.m = str2;
        this.a = l;
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.PAGE_VIEW;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        r.a(jSONObject, "pi", this.m);
        r.a(jSONObject, "rf", this.n);
        if (this.a == null) {
            return true;
        }
        jSONObject.put("du", this.a);
        return true;
    }
}
