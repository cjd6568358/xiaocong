package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatServiceImpl;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import java.util.Map;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b extends e {
    protected c a;
    private long m;

    public b(Context context, int i, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = new c();
        this.m = -1L;
        this.a.a = str;
    }

    private void h() {
        Properties commonKeyValueForKVEvent;
        if (this.a.a == null || (commonKeyValueForKVEvent = StatServiceImpl.getCommonKeyValueForKVEvent(this.a.a)) == null || commonKeyValueForKVEvent.size() <= 0) {
            return;
        }
        if (this.a.c == null || this.a.c.length() == 0) {
            this.a.c = new JSONObject(commonKeyValueForKVEvent);
            return;
        }
        for (Map.Entry entry : commonKeyValueForKVEvent.entrySet()) {
            try {
                this.a.c.put(entry.getKey().toString(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.CUSTOM;
    }

    public void a(long j) {
        this.m = j;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        jSONObject.put("ei", this.a.a);
        if (this.m > 0) {
            jSONObject.put("du", this.m);
        }
        if (this.a.b != null) {
            jSONObject.put("ar", this.a.b);
            return true;
        }
        h();
        jSONObject.put("kv", this.a.c);
        return true;
    }

    public c b() {
        return this.a;
    }
}
