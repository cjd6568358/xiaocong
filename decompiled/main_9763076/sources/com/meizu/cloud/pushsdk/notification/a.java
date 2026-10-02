package com.meizu.cloud.pushsdk.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a implements e {
    protected Context a;
    protected PushNotificationBuilder b;
    protected Handler c;
    private NotificationManager d;

    protected a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.b = pushNotificationBuilder;
        this.a = context;
        this.c = new Handler(context.getMainLooper());
        this.d = (NotificationManager) context.getSystemService("notification");
    }

    protected void a(Notification.Builder builder, MessageV3 messageV3) {
    }

    protected void a(Notification notification, MessageV3 messageV3) {
    }

    protected void b(Notification notification, MessageV3 messageV3) {
    }

    protected void b(Notification.Builder builder, MessageV3 messageV3) {
    }

    protected Notification a(MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        Notification notification;
        Notification.Builder builder = new Notification.Builder(this.a);
        a(builder, messageV3, pendingIntent, pendingIntent2);
        c(builder, messageV3);
        b(builder, messageV3);
        a(builder, messageV3);
        if (com.meizu.cloud.pushsdk.util.b.a()) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        a(notification, messageV3);
        b(notification, messageV3);
        return notification;
    }

    protected PendingIntent a(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        intent.setClassName(this.a.getPackageName(), MzSystemUtils.a(this.a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, this.a.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.a, 0, intent, 1073741824);
    }

    protected PendingIntent b(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE);
        intent.setClassName(this.a.getPackageName(), MzSystemUtils.a(this.a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, this.a.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.a, 0, intent, 1073741824);
    }

    protected void a(Notification.Builder builder, MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        builder.setContentTitle(messageV3.getTitle());
        builder.setContentText(messageV3.getContent());
        builder.setTicker(messageV3.getContent());
        builder.setAutoCancel(true);
        if (com.meizu.cloud.pushsdk.util.b.b()) {
            builder.setVisibility(1);
        }
        builder.setSmallIcon((this.b == null || this.b.getmStatusbarIcon() == 0) ? this.a.getApplicationInfo().icon : this.b.getmStatusbarIcon());
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(pendingIntent2);
    }

    protected void c(Notification.Builder builder, MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            if (advanceSetting.getNotifyType() != null) {
                boolean zIsVibrate = advanceSetting.getNotifyType().isVibrate();
                boolean zIsLights = advanceSetting.getNotifyType().isLights();
                boolean zIsSound = advanceSetting.getNotifyType().isSound();
                if (zIsVibrate || zIsLights || zIsSound) {
                    int i = zIsVibrate ? 2 : 0;
                    if (zIsLights) {
                        i |= 4;
                    }
                    if (zIsSound) {
                        i |= 1;
                    }
                    DebugLogger.e("AbstractPushNotification", "current notification type is " + i);
                    builder.setDefaults(i);
                }
            }
            builder.setOngoing(!advanceSetting.isClearNotification());
            if (advanceSetting.isHeadUpNotification() && com.meizu.cloud.pushsdk.util.b.a()) {
                builder.setPriority(2);
            }
        }
    }

    public Bitmap a(String str) {
        com.meizu.cloud.pushsdk.a.a.c cVarA = com.meizu.cloud.pushsdk.a.a.a(str).a().a();
        if (cVarA.b() && cVarA.a() != null) {
            DebugLogger.i("AbstractPushNotification", "ANRequest On other Thread down load largeIcon " + str + "image " + (cVarA.a() != null ? "success" : "fail"));
            return (Bitmap) cVarA.a();
        }
        DebugLogger.i("AbstractPushNotification", "ANRequest On other Thread down load largeIcon " + str + "image fail");
        return null;
    }

    public Bitmap a(Context context, String str) {
        try {
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(str)).getBitmap();
        } catch (PackageManager.NameNotFoundException e) {
            DebugLogger.i("AbstractPushNotification", "getappicon error " + e.getMessage());
            return ((BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager())).getBitmap();
        }
    }

    protected boolean a() {
        return Thread.currentThread() == this.a.getMainLooper().getThread();
    }

    @Override // com.meizu.cloud.pushsdk.notification.e
    public void c(MessageV3 messageV3) {
        Notification notificationA = a(messageV3, a(messageV3), b(messageV3));
        int iCurrentTimeMillis = (int) System.currentTimeMillis();
        if (messageV3.isDiscard()) {
            if (com.meizu.cloud.pushsdk.util.c.c(this.a, messageV3.getPackageName()) == 0) {
                com.meizu.cloud.pushsdk.util.c.a(this.a, messageV3.getPackageName(), iCurrentTimeMillis);
                DebugLogger.i("AbstractPushNotification", "no notification show so put notification id " + iCurrentTimeMillis);
            }
            if (!TextUtils.isEmpty(messageV3.getTaskId())) {
                if (com.meizu.cloud.pushsdk.util.c.d(this.a, messageV3.getPackageName()) == 0) {
                    com.meizu.cloud.pushsdk.util.c.b(this.a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                } else if (Integer.valueOf(messageV3.getTaskId()).intValue() < com.meizu.cloud.pushsdk.util.c.d(this.a, messageV3.getPackageName())) {
                    DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " taskid " + messageV3.getTaskId() + " dont show notification");
                    return;
                } else {
                    com.meizu.cloud.pushsdk.util.c.b(this.a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                    iCurrentTimeMillis = com.meizu.cloud.pushsdk.util.c.c(this.a, messageV3.getPackageName());
                }
            }
            DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " notificationId=" + iCurrentTimeMillis + " taskId=" + messageV3.getTaskId());
        }
        this.d.notify(iCurrentTimeMillis, notificationA);
        a(this.d, iCurrentTimeMillis, messageV3);
    }

    protected void a(final NotificationManager notificationManager, final int i, MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            boolean zIsHeadUpNotification = advanceSetting.isHeadUpNotification();
            boolean zIsClearNotification = advanceSetting.isClearNotification();
            if (zIsHeadUpNotification && !zIsClearNotification) {
                messageV3.getmAdvanceSetting().setHeadUpNotification(false);
                messageV3.getmAdvanceSetting().getNotifyType().setSound(false);
                messageV3.getmAdvanceSetting().getNotifyType().setVibrate(false);
                final Notification notificationA = a(messageV3, a(messageV3), b(messageV3));
                this.c.postDelayed(new Runnable() { // from class: com.meizu.cloud.pushsdk.notification.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        notificationManager.notify(i, notificationA);
                    }
                }, 5000L);
            }
        }
    }
}
