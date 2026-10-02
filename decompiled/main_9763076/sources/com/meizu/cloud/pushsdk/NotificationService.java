package com.meizu.cloud.pushsdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.util.d;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NotificationService extends IntentService {
    public NotificationService(String str) {
        super(str);
    }

    public NotificationService() {
        super("NotificationService");
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        DebugLogger.i("NotificationService", "NotificationService destroy");
        super.onDestroy();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                DebugLogger.i("NotificationService", "onHandleIntentaction " + intent.getAction());
                String stringExtra = intent.getStringExtra("command_type");
                DebugLogger.d("NotificationService", "-- command_type -- " + stringExtra);
                if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver")) {
                    a(intent);
                }
            } catch (Exception e) {
                DebugLogger.e("NotificationService", "onHandleIntent error " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        List<ResolveInfo> listQueryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.size() <= 0) {
            return null;
        }
        return listQueryBroadcastReceivers.get(0).activityInfo.name;
    }

    public void a(Intent intent) {
        String strA = a(getPackageName(), intent.getAction());
        if (TextUtils.isEmpty(strA)) {
            d.a(this, intent, "reflectReceiver sendbroadcast", PushConstants.NOTIFICATIONSERVICE_SEND_MESSAGE_BROADCAST);
            DebugLogger.i("NotificationService", " reflectReceiver error: receiver for: " + intent.getAction() + " not found, package: " + getPackageName());
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
            return;
        }
        try {
            d.a(this, intent, "reflectReceiver startservice", PushConstants.NOTIFICATIONSERVICE_SEND_MESSAGE);
            Class<?> cls = Class.forName(strA);
            Object objNewInstance = cls.getConstructor((Class[]) null).newInstance((Object[]) null);
            Method method = cls.getMethod("onReceive", Context.class, Intent.class);
            intent.setClassName(getPackageName(), strA);
            method.invoke(objNewInstance, getApplicationContext(), intent);
        } catch (Exception e) {
            DebugLogger.i("NotificationService", "reflect e: " + e);
            d.a(this, intent, e.getMessage(), PushConstants.NOTIFICATIONSERVICE_SEND_MESSAGE_ERROR);
        }
    }
}
