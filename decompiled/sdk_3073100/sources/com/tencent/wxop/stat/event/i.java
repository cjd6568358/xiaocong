package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.l;
import com.tencent.wxop.stat.common.r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class i extends e {
    private static String a = null;
    private String m;
    private String n;

    public i(Context context, int i, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.m = null;
        this.n = null;
        this.m = com.tencent.wxop.stat.a.a(context).b();
        if (a == null) {
            a = l.g(context);
        }
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.NETWORK_MONITOR;
    }

    public void a(String str) {
        this.n = str;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        r.a(jSONObject, "op", a);
        r.a(jSONObject, "cn", this.m);
        jSONObject.put("sp", this.n);
        return true;
    }
}
