package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.b.b.f;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    public static void a(Context context, String str, int i, String str2, String str3) {
        if (!TextUtils.isEmpty(str2)) {
            a(context, context.getPackageName(), str3, str2, PushManager.TAG, str, i);
        }
    }

    public static void a(Context context, Intent intent, String str, int i) {
        a(context, intent, PushManager.TAG, str, i);
    }

    public static void a(Context context, Intent intent, String str, String str2, int i) {
        if (!TextUtils.isEmpty(a(intent))) {
            a(context, context.getPackageName(), intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), a(intent), str, str2, i);
        }
    }

    public static String a(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
        if (TextUtils.isEmpty(stringExtra)) {
            try {
                MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
                if (mPushMessage != null) {
                    return mPushMessage.getTaskId();
                }
            } catch (Exception e) {
                DebugLogger.e("UxIPUtils", "paese MessageV2 error " + e.getMessage());
                return "no push platform task";
            }
        }
        return stringExtra;
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, int i) {
        HashMap map = new HashMap();
        map.put("taskId", str3);
        map.put(Constants.FLAG_DEVICE_ID, str2);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("package_name", str);
        map.put("pushsdk_version", str4);
        map.put("push_info", str5);
        map.put("push_info_type", String.valueOf(i));
        a(context, "notification_service_message", map);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, str, str2, str3, str4, "show_push_message", str5);
    }

    public static void b(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, str, str2, str3, str4, "delete_push_message", str5);
    }

    public static void c(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, str, str2, str3, str4, "receive_push_event", str5);
    }

    public static void d(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, str, str2, str3, str4, "receive_push_event", str5);
    }

    public static void e(Context context, String str, String str2, String str3, String str4, String str5) {
        a(context, str, str2, str3, str4, "click_push_message", str5);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        HashMap map = new HashMap();
        map.put("taskId", str3);
        map.put(Constants.FLAG_DEVICE_ID, str2);
        if (TextUtils.isEmpty(str6)) {
            str6 = String.valueOf(System.currentTimeMillis() / 1000);
        }
        map.put("timestamp", str6);
        map.put("package_name", str);
        map.put("pushsdk_version", PushManager.TAG);
        if (!TextUtils.isEmpty(str4)) {
            map.put("seq_id", str4);
        }
        a(context, str5, map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Context context, String str, Map<String, String> map) {
        DebugLogger.e("UxIPUtils", "onLogEvent eventName [" + str + "] properties = " + map);
        if (!"notification_service_message".equals(str)) {
            com.meizu.cloud.pushsdk.b.a.a(context, (f) null).a(((com.meizu.cloud.pushsdk.b.c.b.a) com.meizu.cloud.pushsdk.b.c.b.d().a(str).a(Long.valueOf(map.get("timestamp")).longValue())).c(map.get(Constants.FLAG_DEVICE_ID)).e(map.get("package_name")).d(map.get("pushsdk_version")).b(map.get("taskId")).f(TextUtils.isEmpty(map.get("seq_id")) ? "null" : map.get("seq_id")).g(String.valueOf(c.g(context, map.get("package_name")))).b());
        }
    }
}
