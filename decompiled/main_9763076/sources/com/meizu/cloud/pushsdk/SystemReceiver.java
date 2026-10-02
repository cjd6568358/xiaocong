package com.meizu.cloud.pushsdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.common.base.WorkReceiver;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.a.b;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.d;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemReceiver extends WorkReceiver {
    @Override // com.meizu.cloud.pushsdk.common.base.WorkReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context, intent);
        } catch (Exception e) {
            DebugLogger.e("SystemReceiver", "Event core error " + e.getMessage());
            d.a(context, context.getPackageName(), (String) null, (String) null, PushManager.TAG, "SystemReceiver " + e.getMessage(), PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
        }
    }

    @Override // com.meizu.cloud.pushsdk.common.base.WorkReceiver
    public void onHandleIntent(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || "com.meizu.cloud.pushservice.action.PUSH_SERVICE_START".equals(intent.getAction())) {
                    a(context);
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                    DebugLogger.e("SystemReceiver", encodedSchemeSpecificPart + " has been uninstall");
                    if (!TextUtils.isEmpty(encodedSchemeSpecificPart) && !MzSystemUtils.b(context, encodedSchemeSpecificPart)) {
                        DebugLogger.e("SystemReceiver", context.getPackageName() + " SystemReceiver start unregister packageName " + encodedSchemeSpecificPart);
                        MzSystemUtils.a(context, encodedSchemeSpecificPart, true);
                        b.a(context).a(encodedSchemeSpecificPart);
                    }
                } else if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                    String encodedSchemeSpecificPart2 = intent.getData().getEncodedSchemeSpecificPart();
                    DebugLogger.e("SystemReceiver", encodedSchemeSpecificPart2 + " has been install");
                    MzSystemUtils.a(context, encodedSchemeSpecificPart2, false);
                }
            } catch (Exception e) {
                DebugLogger.e("SystemReceiver", "onHandleIntent Exception " + e.getMessage());
            }
        }
    }

    public void a(Context context, Intent intent) {
        try {
            context.startService(intent);
        } catch (SecurityException e) {
            DebugLogger.e("SystemReceiver", "start service error " + e.getMessage());
        }
    }

    public void a(Context context) {
        String strA = MzSystemUtils.a(context, "com.meizu.cloud");
        DebugLogger.i("SystemReceiver", context.getPackageName() + " start register cloudVersion_name " + strA);
        Intent intent = new Intent();
        if ("com.meizu.cloud".equals(MzSystemUtils.a(context))) {
            DebugLogger.e("SystemReceiver", "cloud pushService start");
            intent.setAction("com.meizu.pushservice.action.START");
            intent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
        } else if (!TextUtils.isEmpty(strA) && MzSystemUtils.a(strA, "4.5.7")) {
            DebugLogger.e("SystemReceiver", "flyme 4.x start register cloud versionName " + strA);
            intent.setPackage("com.meizu.cloud");
            intent.setAction(PushConstants.MZ_PUSH_ON_START_PUSH_REGISTER);
        } else if (!TextUtils.isEmpty(strA) && strA.startsWith("3")) {
            DebugLogger.e("SystemReceiver", "flyme 3.x start register cloud versionName " + strA);
            intent.setAction(PushConstants.REQUEST_REGISTRATION_INTENT);
            intent.setPackage("com.meizu.cloud");
        } else {
            DebugLogger.e("SystemReceiver", context.getPackageName() + " start register ");
            intent.setClassName(context.getPackageName(), "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            intent.setAction("com.meizu.pushservice.action.START");
        }
        a(context, intent);
    }
}
