package com.tencent.android.tpush;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPush4Msdk {
    private static long a = 0;
    private static long b = 0;
    private static String c = Constants.MAIN_VERSION_TAG;

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(Context context) {
        return context.getPackageName() + ":XG_DEBUG_SERVER_INFO";
    }

    public static void setDebugServerInfo(Context context, String str, int i) {
        if (!com.tencent.android.tpush.service.e.m.b(str)) {
            com.tencent.android.tpush.common.n.b(context, b(context), str + "," + i);
        } else {
            com.tencent.android.tpush.common.g.a().a(new d(context));
        }
    }

    public static String getDebugServerInfo(Context context) {
        return com.tencent.android.tpush.common.n.a(context, b(context), (String) null);
    }

    private static boolean a(long j, long j2, long j3) {
        return j >= j2 && j < j3;
    }

    public static void setQQAppId(Context context, long j) {
        long j2;
        if (a(j, 0L, 200000L)) {
            j2 = 90000000;
        } else if (a(j, 99000000L, 100000000L)) {
            j2 = 0;
        } else if (a(j, 100200000L, 100600000L)) {
            j2 = -10000000;
        } else if (a(j, 101000000L, 101400000L)) {
            j2 = -10400000;
        } else if (a(j, 900000000L, 900100000L)) {
            j2 = -809000000;
        } else if (a(j, 1000000000L, 1000100000L)) {
            j2 = -908900000;
        } else if (a(j, 1101000000L, 1104500000L)) {
            j2 = -1009800000;
        } else if (a(j, 1150000000L, 1150100000L)) {
            j2 = -1055300000;
        } else if (a(j, 100600000L, 101000000L)) {
            j2 = -5800000;
        } else if (a(j, 1104500000L, 1109300000L)) {
            j2 = -1009300000;
        } else if (a(j, 1109300000L, 1119300000L)) {
            j2 = -1029300000;
        } else if (a(j, 1119300000L, 1120000000L)) {
            j2 = -1049300000;
        } else {
            Log.e(Constants.MSDK_TAG, "手Q的appid：" + j + " 不在固定的范围，请联系msdk和信鸽的同事解决之。");
            j2 = 0;
        }
        a = j;
        b = j2 + 2100000000 + j;
        com.tencent.android.tpush.common.n.b(context, "TPUSH_QQ_ACCESS_ID", b);
        com.tencent.android.tpush.common.n.a(context, "TPUSH_QQ_APP_ID");
        c = "MSDK_" + j;
        com.tencent.android.tpush.common.n.b(context, "__en__TPUSH_QQ_ACCESS_KEY", Rijndael.encrypt(c));
        com.tencent.android.tpush.common.n.a(context, "TPUSH_QQ_ACCESS_KEY");
    }

    public static long getQQAccessId(Context context) {
        if (b <= 0) {
            b = com.tencent.android.tpush.common.n.a(context, "TPUSH_QQ_ACCESS_ID", b);
        }
        return b;
    }

    public static void setQQAppKey(Context context, String str) {
    }

    public static String getQQAppKey(Context context) {
        if (!TextUtils.isEmpty(c)) {
            return c;
        }
        String strA = com.tencent.android.tpush.common.n.a(context, "__en__TPUSH_QQ_ACCESS_KEY", c);
        if (!TextUtils.isEmpty(strA)) {
            c = Rijndael.decrypt(strA);
        } else {
            c = com.tencent.android.tpush.common.n.a(context, "TPUSH_QQ_ACCESS_KEY", Constants.MAIN_VERSION_TAG);
            com.tencent.android.tpush.common.n.b(context, "TPUSH_QQ_ACCESS_KEY", Constants.MAIN_VERSION_TAG);
        }
        return c;
    }

    public static void setTag(Context context, String str) {
        com.tencent.android.tpush.a.a.c(Constants.MSDK_TAG, "setTag: tagName=" + str + ",qqAppid=" + a + ",xg_accessid=" + getQQAccessId(context));
        XGPushManager.a(context, str, 1, getQQAccessId(context));
    }

    public static void deleteTag(Context context, String str) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c(Constants.MSDK_TAG, "deleteTag: tagName=" + str + ",qqAppid=" + a + ",xg_accessid=" + getQQAccessId(context));
        }
        XGPushManager.a(context, str, 2, getQQAccessId(context));
    }

    public static void registerPush(Context context, String str, XGIOperateCallback xGIOperateCallback) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.e(Constants.MSDK_TAG, "registerPush: account=" + str + ",qqAppid=" + a + ",xg_accessid=" + getQQAccessId(context));
        }
        XGIOperateCallback eVar = xGIOperateCallback == null ? new e() : xGIOperateCallback;
        if (!com.tencent.android.tpush.service.e.m.b(str)) {
            XGPushManager.a(context, str, PushConstants.PUSH_TYPE_NOTIFY, 0, null, eVar, getQQAccessId(context), getQQAppKey(context));
        } else {
            XGPushManager.a(context, null, null, -1, null, eVar, getQQAccessId(context), getQQAppKey(context));
        }
    }

    public static void unregisterPush(Context context, XGIOperateCallback xGIOperateCallback) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.e(Constants.MSDK_TAG, "unregisterPush,qqAppid=" + a + ",xg_accessid=" + getQQAccessId(context));
        }
        if (xGIOperateCallback == null) {
            xGIOperateCallback = new f();
        }
        XGPushManager.a(context, xGIOperateCallback, getQQAccessId(context), getQQAppKey(context));
    }

    public static long addLocalNotification(Context context, XGLocalMessage xGLocalMessage) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.e(Constants.MSDK_TAG, "addLocalNotification:msg=" + xGLocalMessage.toString() + ",qqAppid=" + a + ",xg_accessid=" + getQQAccessId(context));
        }
        return XGPushManager.a(context, xGLocalMessage, getQQAccessId(context));
    }

    public static void setPushNotificationBuilder(Context context, int i, XGPushNotificationBuilder xGPushNotificationBuilder) {
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }
        if (i < 5000 || i > 6000) {
            throw new IllegalArgumentException("notificationBulderId超过范围[5000, 6000].");
        }
        if (xGPushNotificationBuilder != null) {
            com.tencent.android.tpush.b.b.a(context, i, xGPushNotificationBuilder);
        }
    }

    public static void setDefaultNotificationBuilder(Context context, XGPushNotificationBuilder xGPushNotificationBuilder) {
        XGPushManager.setDefaultNotificationBuilder(context, xGPushNotificationBuilder);
    }
}
