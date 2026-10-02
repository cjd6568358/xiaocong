package com.tencent.android.tpush.stat.event;

import android.content.Context;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e extends a {
    public e(Context context, String str, JSONObject jSONObject, String str2, boolean z) {
        super(context, str, jSONObject, str2, z);
    }

    @Override // com.tencent.android.tpush.stat.event.a, com.tencent.android.tpush.stat.event.d
    public EventType b() {
        return EventType.LBS;
    }

    @Override // com.tencent.android.tpush.stat.event.a, com.tencent.android.tpush.stat.event.d
    public boolean a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(NotificationStyle.EXPANDABLE_IMAGE_URL, this.a.a);
        if (this.b > 0) {
            jSONObject.put("du", this.b);
        }
        if (this.a.b == null) {
            jSONObject.put("lbs", this.a.c);
            return true;
        }
        jSONObject.put("ar", this.a.b);
        return true;
    }
}
