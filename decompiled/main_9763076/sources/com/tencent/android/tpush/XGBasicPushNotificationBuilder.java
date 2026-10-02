package com.tencent.android.tpush;

import android.app.Notification;
import android.content.Context;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGBasicPushNotificationBuilder extends XGPushNotificationBuilder {
    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    public Notification buildNotification(Context context) {
        return a(context);
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    protected void a(JSONObject jSONObject) {
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    protected void b(JSONObject jSONObject) {
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    public String getType() {
        return XGPushNotificationBuilder.BASIC_NOTIFICATION_BUILDER_TYPE;
    }
}
