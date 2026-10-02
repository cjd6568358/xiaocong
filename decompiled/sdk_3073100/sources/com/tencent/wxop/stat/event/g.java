package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatAppMonitor;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.l;
import com.tencent.wxop.stat.common.r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g extends e {
    private static String m = null;
    private static String n = null;
    private StatAppMonitor a;

    public g(Context context, int i, StatAppMonitor statAppMonitor, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = null;
        this.a = statAppMonitor.m0clone();
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.MONITOR_STAT;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        if (this.a == null) {
            return false;
        }
        jSONObject.put("na", this.a.getInterfaceName());
        jSONObject.put("rq", this.a.getReqSize());
        jSONObject.put("rp", this.a.getRespSize());
        jSONObject.put("rt", this.a.getResultType());
        jSONObject.put("tm", this.a.getMillisecondsConsume());
        jSONObject.put("rc", this.a.getReturnCode());
        jSONObject.put("sp", this.a.getSampling());
        if (n == null) {
            n = l.l(this.l);
        }
        r.a(jSONObject, "av", n);
        if (m == null) {
            m = l.g(this.l);
        }
        r.a(jSONObject, "op", m);
        jSONObject.put("cn", com.tencent.wxop.stat.a.a(this.l).b());
        return true;
    }
}
