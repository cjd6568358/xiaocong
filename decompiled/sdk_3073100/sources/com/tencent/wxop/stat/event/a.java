package com.tencent.wxop.stat.event;

import android.content.Context;
import com.tencent.wxop.stat.StatAccount;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a extends e {
    private StatAccount a;

    public a(Context context, int i, StatAccount statAccount, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = null;
        this.a = statAccount;
    }

    @Override // com.tencent.wxop.stat.event.e
    public EventType a() {
        return EventType.ADDITION;
    }

    @Override // com.tencent.wxop.stat.event.e
    public boolean a(JSONObject jSONObject) throws JSONException {
        r.a(jSONObject, "qq", this.a.getAccount());
        jSONObject.put("acc", this.a.toJsonString());
        return true;
    }
}
