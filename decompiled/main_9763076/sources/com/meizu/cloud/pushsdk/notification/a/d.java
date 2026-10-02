package com.meizu.cloud.pushsdk.notification.a;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MessageV4;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import java.io.File;
import org.apache.http.cookie.ClientCookie;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends c {
    public d(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification.Builder builder, MessageV3 messageV3) {
        if (com.meizu.cloud.pushsdk.util.b.a()) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(messageV3.getTitle());
            bigTextStyle.setSummaryText(messageV3.getContent());
            bigTextStyle.bigText(messageV3.getmNotificationStyle().getExpandableText());
            builder.setStyle(bigTextStyle);
        }
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification notification, MessageV3 messageV3) {
        super.a(notification, messageV3);
        MessageV4 messageV4 = MessageV4.parse(messageV3);
        if (messageV4.getActVideoSetting() != null && (!messageV4.getActVideoSetting().isWifiDisplay() || com.meizu.cloud.pushsdk.util.a.b(this.a))) {
            final String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdkAct/" + messageV3.getUploadDataPackageName();
            String strValueOf = String.valueOf(System.currentTimeMillis());
            String actUrl = messageV4.getActVideoSetting().getActUrl();
            if (!TextUtils.isEmpty(actUrl) && com.meizu.cloud.pushsdk.a.a.a(actUrl, str, strValueOf).a().b().b()) {
                DebugLogger.i("AbstractPushNotification", "down load " + actUrl + " success");
                String str2 = str + File.separator + "ACT-" + strValueOf;
                boolean zA = new com.meizu.cloud.pushsdk.notification.b.d(str + File.separator + strValueOf, str2).a();
                DebugLogger.i("AbstractPushNotification", "zip file " + zA);
                if (zA) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ClientCookie.PATH_ATTR, str2);
                    Bundle bundle2 = new Bundle();
                    bundle2.putBundle("big", bundle);
                    if (com.meizu.cloud.pushsdk.util.b.c()) {
                        notification.extras.putBundle("flyme.active", bundle2);
                    }
                }
            }
            com.meizu.cloud.pushsdk.b.b.a.b.a(new Runnable() { // from class: com.meizu.cloud.pushsdk.notification.a.d.1
                @Override // java.lang.Runnable
                public void run() {
                    for (File file : com.meizu.cloud.pushsdk.notification.b.a.b(str, String.valueOf(System.currentTimeMillis() - 86400000))) {
                        com.meizu.cloud.pushsdk.notification.b.a.b(file.getPath());
                        DebugLogger.i("AbstractPushNotification", "Delete file directory " + file.getName() + "\n");
                    }
                }
            });
            return;
        }
        DebugLogger.e("AbstractPushNotification", "only wifi can download act");
    }
}
