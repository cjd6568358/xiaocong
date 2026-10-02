package com.meizu.cloud.pushsdk.notification.a;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends com.meizu.cloud.pushsdk.notification.a {
    public c(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void b(Notification.Builder builder, MessageV3 messageV3) {
        AppIconSetting appIconSetting = messageV3.getmAppIconSetting();
        if (appIconSetting != null) {
            if (appIconSetting.isDefaultLargeIcon()) {
                builder.setLargeIcon((this.b == null || this.b.getmLargIcon() == 0) ? a(this.a, messageV3.getPackageName()) : BitmapFactory.decodeResource(this.a.getResources(), this.b.getmLargIcon()));
                return;
            }
            if (Thread.currentThread() != this.a.getMainLooper().getThread()) {
                Bitmap bitmapA = a(appIconSetting.getLargeIconUrl());
                if (bitmapA != null) {
                    DebugLogger.i("AbstractPushNotification", "On other Thread down load largeIcon image success");
                    builder.setLargeIcon(bitmapA);
                } else {
                    builder.setLargeIcon(a(this.a, messageV3.getPackageName()));
                }
            }
        }
    }
}
