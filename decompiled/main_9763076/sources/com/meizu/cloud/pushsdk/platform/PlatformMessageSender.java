package com.meizu.cloud.pushsdk.platform;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PlatformMessageSender {

    private interface a {
        String a();

        BasicPushStatus b();

        String c();
    }

    public static void a(Context context, final PushSwitchStatus pushSwitchStatus) {
        a(context, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.1
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return pushSwitchStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS;
            }
        });
    }

    public static void a(Context context, final RegisterStatus registerStatus) {
        a(context, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.2
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_REGISTER_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return registerStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_REGISTER_STATUS;
            }
        });
    }

    public static void a(Context context, final UnRegisterStatus unRegisterStatus) {
        a(context, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.3
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_UNREGISTER_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return unRegisterStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_UNREGISTER_STATUS;
            }
        });
    }

    public static void a(Context context, final SubTagsStatus subTagsStatus) {
        a(context, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.4
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBTAGS_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return subTagsStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SUBTAGS_STATUS;
            }
        });
    }

    public static void a(Context context, final SubAliasStatus subAliasStatus) {
        a(context, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.5
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBALIAS_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return subAliasStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SUBALIAS_STATUS;
            }
        });
    }

    private static void a(Context context, a aVar) {
        String packageName = context.getPackageName();
        Intent intent = new Intent();
        intent.addCategory(packageName);
        intent.setPackage(packageName);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, aVar.a());
        intent.putExtra(aVar.c(), aVar.b());
        a(context, intent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, context.getPackageName());
        a(context, new Intent("com.meizu.cloud.pushservice.action.PUSH_SERVICE_START"), (String) null, context.getPackageName());
    }

    public static void a(Context context, int i, boolean z, String str) {
        String strA = MzSystemUtils.a(context, "com.meizu.cloud");
        DebugLogger.i("PlatformMessageSender", context.getPackageName() + " switchPushMessageSetting cloudVersion_name " + strA);
        if (!TextUtils.isEmpty(strA) && strA.startsWith("6")) {
            Intent intent = new Intent(PushConstants.MZ_PUSH_ON_MESSAGE_SWITCH_SETTING);
            intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_TYPE, i);
            intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_STATUS, z);
            intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_PACKAGE_NAME, str);
            intent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            context.startService(intent);
        }
    }

    public static void launchStartActivity(Context context, String str, String str2, String str3) {
        MessageV3 messageV3 = MessageV3.parse(Constants.MAIN_VERSION_TAG, str, Constants.MAIN_VERSION_TAG, str3);
        messageV3.setUploadDataPackageName(str2);
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        if (!TextUtils.isEmpty(str2)) {
            intent.setPackage(str2);
            intent.setClassName(str2, "com.meizu.cloud.pushsdk.NotificationService");
        }
        intent.putExtra("command_type", "reflect_receiver");
        DebugLogger.i("PlatformMessageSender", "start notification service " + messageV3);
        try {
            context.startService(intent);
        } catch (Exception e) {
            DebugLogger.e("PlatformMessageSender", "launchStartActivity error " + e.getMessage());
        }
    }

    public static void a(Context context, Intent intent, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            intent.setAction(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            intent.setPackage(str2);
        }
        String strA = a(context, str, str2);
        if (!TextUtils.isEmpty(strA)) {
            intent.setClassName(str2, strA);
        }
        context.sendBroadcast(intent);
    }

    private static String a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str);
        intent.setPackage(str2);
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.size() <= 0) {
            return null;
        }
        return listQueryBroadcastReceivers.get(0).activityInfo.name;
    }
}
