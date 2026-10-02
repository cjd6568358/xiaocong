package com.tencent.android.tpush.stat.event;

import android.content.Context;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends d {
    Long a;
    String b;
    String m;
    public long n;
    public long o;

    public f(Context context, String str, String str2, int i, Long l, long j) {
        super(context, i, j);
        this.a = null;
        this.n = 0L;
        this.o = 0L;
        this.m = str;
        this.b = str2;
        this.a = l;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public EventType b() {
        return EventType.PAGE_VIEW;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public boolean a(JSONObject jSONObject) throws JSONException {
        t.a(jSONObject, "pi", this.b);
        t.a(jSONObject, "rf", this.m);
        if (this.a != null) {
            jSONObject.put("du", this.a);
        }
        if (this.n > 0) {
            t.a(jSONObject, MessageKey.MSG_ID, this.n);
        }
        if (this.o > 0) {
            t.a(jSONObject, MessageKey.MSG_BUSI_MSG_ID, this.o);
            return true;
        }
        return true;
    }
}
