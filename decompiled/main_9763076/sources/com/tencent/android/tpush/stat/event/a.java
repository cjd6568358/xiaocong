package com.tencent.android.tpush.stat.event;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends d {
    protected b a;
    protected long b;

    public b a() {
        return this.a;
    }

    public a(Context context, String str, JSONObject jSONObject, String str2, boolean z) {
        super(context, str2);
        this.a = new b();
        this.b = -1L;
        this.a.a = str;
        this.a.c = jSONObject;
        this.a.d = z;
    }

    public a(Context context, int i, String str, long j, long j2) {
        super(context, i, j);
        this.a = new b();
        this.b = -1L;
        this.a.a = str;
        this.k = j2;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public EventType b() {
        return EventType.CUSTOM;
    }

    @Override // com.tencent.android.tpush.stat.event.d
    public boolean a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(NotificationStyle.EXPANDABLE_IMAGE_URL, this.a.a);
        if (this.b > 0) {
            jSONObject.put("du", this.b);
        }
        if (this.a.b == null) {
            if (this.a.d) {
                jSONObject.put("kv2", this.a.c);
                return true;
            }
            jSONObject.put("kv", this.a.c);
            return true;
        }
        jSONObject.put("ar", this.a.b);
        return true;
    }
}
