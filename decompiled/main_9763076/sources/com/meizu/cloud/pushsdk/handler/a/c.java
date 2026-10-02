package com.meizu.cloud.pushsdk.handler.a;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.NotificationService;
import com.meizu.cloud.pushsdk.common.b.h;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.g;
import com.meizu.cloud.pushsdk.notification.model.styleenum.BaseStyleModel;
import com.meizu.cloud.pushsdk.notification.model.styleenum.InnerStyleLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends a<MessageV3> {
    public c(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public MessageV3 c(Intent intent) {
        String stringExtra;
        if (PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW_V3.equals(i(intent))) {
            stringExtra = intent.getStringExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
        } else {
            stringExtra = intent.getStringExtra("message");
        }
        return MessageV3.parse(c().getPackageName(), g(intent), h(intent), d(intent), e(intent), f(intent), stringExtra);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(MessageV3 messageV3, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (eVar != null) {
            eVar.c(messageV3);
            b().b(c(), messageV3.getTitle(), messageV3.getContent(), a(messageV3.getWebUrl(), messageV3.getParamsMap()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: c, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public com.meizu.cloud.pushsdk.notification.e a(MessageV3 messageV3) {
        PushNotificationBuilder pushNotificationBuilder = new PushNotificationBuilder();
        b().a(pushNotificationBuilder);
        com.meizu.cloud.pushsdk.notification.e dVar = null;
        if (messageV3.getmNotificationStyle() != null) {
            int baseStyle = messageV3.getmNotificationStyle().getBaseStyle();
            if (BaseStyleModel.FLYME.getCode() == baseStyle) {
                int innerStyle = messageV3.getmNotificationStyle().getInnerStyle();
                if (InnerStyleLayout.EXPANDABLE_STANDARD.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable disable");
                    dVar = new com.meizu.cloud.pushsdk.notification.f(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_TEXT.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable Text");
                    dVar = new com.meizu.cloud.pushsdk.notification.c(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_PIC.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable Picture");
                    dVar = new com.meizu.cloud.pushsdk.notification.b(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_VIDEO.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Flyme Video notification");
                    dVar = new com.meizu.cloud.pushsdk.notification.a.d(c(), pushNotificationBuilder);
                }
            } else if (BaseStyleModel.PURE_PICTURE.getCode() == baseStyle) {
                dVar = new com.meizu.cloud.pushsdk.notification.d(c(), pushNotificationBuilder);
                DebugLogger.i("AbstractMessageHandler", "show Pure Picture Notification");
            } else if (BaseStyleModel.ANDROID.getCode() == baseStyle) {
                int innerStyle2 = messageV3.getmNotificationStyle().getInnerStyle();
                if (InnerStyleLayout.EXPANDABLE_STANDARD.getCode() == innerStyle2) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable disable");
                    dVar = new com.meizu.cloud.pushsdk.notification.a.c(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_TEXT.getCode() == innerStyle2) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable Text");
                    dVar = new com.meizu.cloud.pushsdk.notification.a.b(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_PIC.getCode() == innerStyle2) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable Picture");
                    dVar = new com.meizu.cloud.pushsdk.notification.a.a(c(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_VIDEO.getCode() == innerStyle2) {
                    DebugLogger.i("AbstractMessageHandler", "show Flyme Video notification");
                    dVar = new com.meizu.cloud.pushsdk.notification.a.d(c(), pushNotificationBuilder);
                }
            }
        }
        if (dVar == null) {
            DebugLogger.e("AbstractMessageHandler", "use standard v2 notification");
            return new g(c(), pushNotificationBuilder);
        }
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public int d(MessageV3 messageV3) {
        if (messageV3.getmTimeDisplaySetting() == null || !messageV3.getmTimeDisplaySetting().isTimeDisplay()) {
            return 0;
        }
        if (System.currentTimeMillis() > Long.valueOf(messageV3.getmTimeDisplaySetting().getEndShowTime()).longValue()) {
            com.meizu.cloud.pushsdk.util.d.a(c(), "schedule notification expire", PushConstants.EXPIRE_NOTIFICATION, messageV3.getTaskId(), messageV3.getDeviceId());
            return 1;
        }
        if (System.currentTimeMillis() > Long.valueOf(messageV3.getmTimeDisplaySetting().getStartShowTime()).longValue()) {
            com.meizu.cloud.pushsdk.util.d.a(c(), "schedule notification on time", PushConstants.ONTIME_NOTIFICATION, messageV3.getTaskId(), messageV3.getDeviceId());
            return 2;
        }
        com.meizu.cloud.pushsdk.util.d.a(c(), "schedule notification delay", PushConstants.DELAY_NOTIFICATION, messageV3.getTaskId(), messageV3.getDeviceId());
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void e(MessageV3 messageV3) {
        Context contextC = c();
        c();
        AlarmManager alarmManager = (AlarmManager) contextC.getSystemService("alarm");
        Intent intent = new Intent(c(), (Class<?>) NotificationService.class);
        intent.setPackage(messageV3.getPackageName());
        intent.addCategory(messageV3.getPackageName());
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra("command_type", "reflect_receiver");
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SCHEDULE_NOTIFICATION_MESSAGE, messageV3);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SCHEDULE_NOTIFICATION);
        PendingIntent service = PendingIntent.getService(c(), 0, intent, 1073741824);
        String startShowTime = messageV3.getmTimeDisplaySetting().getStartShowTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = null;
        if (!TextUtils.isEmpty(startShowTime)) {
            str = simpleDateFormat.format(new Date(Long.valueOf(startShowTime).longValue()));
        }
        long jLongValue = Long.valueOf(startShowTime).longValue() - System.currentTimeMillis();
        DebugLogger.i("AbstractMessageHandler", "after " + (jLongValue / 1000) + " seconds Notification AlarmManager execute At " + str);
        if (h.a(19)) {
            DebugLogger.i("AbstractMessageHandler", "setAlarmManager setWindow ELAPSED_REALTIME_WAKEUP");
            alarmManager.setExact(2, jLongValue + SystemClock.elapsedRealtime(), service);
        } else {
            alarmManager.set(2, jLongValue + SystemClock.elapsedRealtime(), service);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start MessageV3Handler match");
        if (!a(0, g(intent))) {
            return false;
        }
        if (PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW_V3.equals(i(intent))) {
            return true;
        }
        if (!TextUtils.isEmpty(i(intent))) {
            return false;
        }
        String stringExtra = intent.getStringExtra("message");
        if (TextUtils.isEmpty(stringExtra) || !a(stringExtra)) {
            return false;
        }
        DebugLogger.e("AbstractMessageHandler", "old cloud notification message");
        return true;
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.c(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void c(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.a(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }
}
