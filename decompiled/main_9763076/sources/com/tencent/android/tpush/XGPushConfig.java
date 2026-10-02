package com.tencent.android.tpush;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushConfig {
    public static final String TPUSH_ACCESS_ID = "XG_V2_ACCESS_ID";
    public static final String TPUSH_ACCESS_KEY = "XG_V2_ACCESS_KEY";
    public static final String TPUSH_IS_FOREIGINPUSH = "TPUSH_IS_FOREIGINPUSH";
    private static final String a = XGPushConfig.class.getSimpleName();
    private static String b = Constants.MAIN_VERSION_TAG;
    private static String c = Constants.MAIN_VERSION_TAG;
    private static long d = -1;
    private static String e = Constants.MAIN_VERSION_TAG;
    public static boolean enableDebug = false;
    public static Boolean enableLocation = null;
    public static Boolean enableApplist = null;
    public static Boolean enableNotification = null;
    public static Boolean isUsedOtherPush = null;
    public static Boolean isUsedFcmPush = null;
    private static Boolean f = null;
    public static boolean _isHuaweiDebug = false;
    private static Boolean g = null;

    public static synchronized long getAccessId(Context context) {
        long j;
        Object objA;
        String string;
        if (context == null || d != -1 || !TpnsSecurity.checkTpnsSecurityLibSo(context)) {
            j = d;
        } else {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (defaultSharedPreferences != null && (string = defaultSharedPreferences.getString(TPUSH_ACCESS_ID, null)) != null) {
                try {
                    d = Long.valueOf(Rijndael.decrypt(string)).longValue();
                } catch (Exception e2) {
                    d = -1L;
                    com.tencent.android.tpush.a.a.b(a, "get accessId error", e2);
                }
            }
            if (d == -1 && (objA = com.tencent.android.tpush.common.e.a(context, TPUSH_ACCESS_ID, (Object) null)) != null) {
                try {
                    d = Long.valueOf(objA.toString()).longValue();
                } catch (Exception e3) {
                    com.tencent.android.tpush.a.a.b(Constants.LogTag, "get accessId from getMetaData failed: ", e3);
                    d = -1L;
                }
            }
            if (d == -1) {
                com.tencent.android.tpush.a.a.i(Constants.LogTag, "accessId没有初始化");
            }
            j = d;
        }
        return j;
    }

    public static void setAccessId(Context context, long j) {
        if (context == null) {
            com.tencent.android.tpush.a.a.i(a, "null  context");
        } else {
            d = j;
            com.tencent.android.tpush.common.g.a().a(new r(context, j));
        }
    }

    public static synchronized String getAccessKey(Context context) {
        Object objA;
        String str = null;
        synchronized (XGPushConfig.class) {
            if (!com.tencent.android.tpush.service.e.m.b(e)) {
                str = e;
            } else if (TpnsSecurity.checkTpnsSecurityLibSo(context)) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (defaultSharedPreferences != null) {
                    String string = defaultSharedPreferences.getString(TPUSH_ACCESS_KEY, null);
                    if (com.tencent.android.tpush.service.e.m.b(string)) {
                        e = Rijndael.decrypt(string);
                    }
                }
                if (com.tencent.android.tpush.service.e.m.b(e) && (objA = com.tencent.android.tpush.common.e.a(context, TPUSH_ACCESS_KEY, (Object) null)) != null) {
                    e = objA.toString();
                }
                if (com.tencent.android.tpush.service.e.m.b(e)) {
                    com.tencent.android.tpush.a.a.i(a, "accessKey is null");
                }
                str = e;
            }
        }
        return str;
    }

    public static void setAccessKey(Context context, String str) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.i(Constants.LogTag, "null context or null accessKey");
        } else {
            e = str;
            com.tencent.android.tpush.common.g.a().a(new s(context, str));
        }
    }

    public static String getToken(Context context) {
        if (context != null) {
            return CacheManager.getToken(context);
        }
        com.tencent.android.tpush.a.a.i(Constants.LogTag, "null context");
        return null;
    }

    public static void enableDebug(Context context, boolean z) {
        if (context != null) {
            enableDebug = z;
            com.tencent.android.tpush.common.g.a().a(new t(z, context));
        }
    }

    public static boolean isEnableDebug(Context context) {
        return com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.debug,").append(context.getPackageName()).toString(), 0) != 0;
    }

    public static void setLocationEnable(Context context, boolean z) {
        if (enableLocation == null || enableLocation.booleanValue() != z) {
            enableLocation = Boolean.valueOf(z);
            com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.enable_location," + context.getPackageName(), z ? 1 : 0);
        }
    }

    public static boolean isLocationEnable(Context context) {
        if (enableLocation == null) {
            enableLocation = Boolean.valueOf(com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.enable_location,").append(context.getPackageName()).toString(), 1) != 0);
        }
        return enableLocation.booleanValue();
    }

    public static void setReportApplistEnable(Context context, boolean z) {
        if (enableApplist == null || enableApplist.booleanValue() != z) {
            enableApplist = Boolean.valueOf(z);
            com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.enable_applist," + context.getPackageName(), z ? 1 : 0);
        }
    }

    public static boolean isReportApplistEnable(Context context) {
        if (enableApplist == null) {
            enableApplist = Boolean.valueOf(com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.enable_applist,").append(context.getPackageName()).toString(), 1) != 0);
        }
        if (com.tencent.android.tpush.service.a.a.a(context).F == -1) {
            return enableApplist.booleanValue();
        }
        return com.tencent.android.tpush.service.a.a.a(context).F == 1;
    }

    public static void setReportNotificationStatusEnable(Context context, boolean z) {
        if (enableNotification == null || enableNotification.booleanValue() != z) {
            enableNotification = Boolean.valueOf(z);
            com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.enable_NOTIICATION," + context.getPackageName(), z ? 1 : 0);
        }
    }

    public static boolean isReportNotificationStatusEnable(Context context) {
        if (enableNotification == null) {
            enableNotification = Boolean.valueOf(com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.enable_NOTIICATION,").append(context.getPackageName()).toString(), 1) != 0);
        }
        if (com.tencent.android.tpush.service.a.a.a(context).G == -1) {
            return enableNotification.booleanValue();
        }
        return com.tencent.android.tpush.service.a.a.a(context).G == 1;
    }

    public static List getAccessidList(Context context) {
        ArrayList arrayList = new ArrayList(2);
        if (context != null) {
            long accessId = getAccessId(context);
            if (accessId > 0) {
                arrayList.add(Long.valueOf(accessId));
            }
            long qQAccessId = XGPush4Msdk.getQQAccessId(context);
            if (qQAccessId > 0) {
                arrayList.add(Long.valueOf(qQAccessId));
            }
            Object objA = com.tencent.android.tpush.common.e.a(context, TPUSH_ACCESS_ID, (Object) null);
            if (objA != null) {
                try {
                    long jLongValue = Long.valueOf(objA.toString()).longValue();
                    if (!arrayList.contains(Long.valueOf(jLongValue))) {
                        arrayList.add(Long.valueOf(jLongValue));
                    }
                } catch (Exception e2) {
                    com.tencent.android.tpush.a.a.b(a, "get accessId from getMetaData failed: ", e2);
                }
            }
        }
        return arrayList;
    }

    public static void setInstallChannel(Context context, String str) {
        if (context != null && str != null && str.trim().length() != 0) {
            b = str;
        }
    }

    public static String getInstallChannel(Context context) {
        return b;
    }

    public static void setGameServer(Context context, String str) {
        if (context != null && str != null && str.trim().length() != 0) {
            c = str;
        }
    }

    public static String getGameServer(Context context) {
        return c;
    }

    public static void setHeartbeatIntervalMs(Context context, int i) {
        if (context != null && i >= 5000 && i < 1800000) {
            try {
                com.tencent.android.tpush.service.e.h.b(context, "com.tencent.android.xg.wx.HeartbeatIntervalMs", i);
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(a, "setHeartbeatIntervalMs", e2);
            }
        }
    }

    public static boolean isUsedOtherPush(Context context) {
        if (context == null) {
            return false;
        }
        if (isUsedOtherPush == null) {
            isUsedOtherPush = Boolean.valueOf(com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.other.push,").append(context.getPackageName()).toString(), 0) != 0);
        }
        return isUsedOtherPush.booleanValue();
    }

    public static void enableOtherPush(Context context, boolean z) {
        if (context != null) {
            if (isUsedOtherPush == null || isUsedOtherPush.booleanValue() != z) {
                isUsedOtherPush = Boolean.valueOf(z);
                com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.other.push," + context.getPackageName(), z ? 1 : 0);
            }
        }
    }

    public static boolean isUsedFcmPush(Context context) {
        if (context == null) {
            return false;
        }
        if (isUsedFcmPush == null) {
            isUsedFcmPush = Boolean.valueOf(com.tencent.android.tpush.common.n.a(context, new StringBuilder().append("com.tencent.android.tpush.fcm,").append(context.getPackageName()).toString(), 0) != 0);
        }
        return isUsedFcmPush.booleanValue();
    }

    public static void enableFcmPush(Context context, boolean z) {
        if (context != null) {
            if (isUsedFcmPush == null || isUsedFcmPush.booleanValue() != z) {
                isUsedFcmPush = Boolean.valueOf(z);
                com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.fcm," + context.getPackageName(), z ? 1 : 0);
            }
        }
    }

    public static void setReportDebugMode(Context context, boolean z) {
        if (context != null) {
            com.tencent.android.tpush.common.n.b(context, context.getPackageName() + ".report.mode", z ? 1 : 0);
        }
    }

    public static boolean getReportDebugMode(Context context) {
        return com.tencent.android.tpush.common.n.a(context, new StringBuilder().append(context.getPackageName()).append(".report.mode").toString(), 0) != 0;
    }

    public static void setMiPushAppId(Context context, String str) {
        com.tencent.android.tpush.c.e.a(context, str);
    }

    public static void setMiPushAppKey(Context context, String str) {
        com.tencent.android.tpush.c.e.b(context, str);
    }

    public static void setMzPushAppId(Context context, String str) {
        com.tencent.android.tpush.c.e.c(context, str);
    }

    public static void setMzPushAppKey(Context context, String str) {
        com.tencent.android.tpush.c.e.d(context, str);
    }

    public static void setfcmSenderId(Context context, String str) {
        com.tencent.android.tpush.c.a.b(context, str);
    }

    public static void setForeiginPushEnable(Context context, boolean z) {
    }

    public static boolean isForeiginPush(Context context) {
        if (f == null) {
            try {
                Object objA = com.tencent.android.tpush.common.e.a(context, TPUSH_IS_FOREIGINPUSH, (Object) null);
                if (objA == null) {
                    f = false;
                    return f.booleanValue();
                }
                if ("true".equals(objA.toString())) {
                    f = true;
                } else {
                    f = false;
                }
            } catch (Throwable th) {
                f = false;
            }
        }
        return f.booleanValue();
    }

    public static void setHuaweiDebug(boolean z) {
        _isHuaweiDebug = z;
    }

    public static boolean isHuaweiDebug() {
        return _isHuaweiDebug;
    }

    public static boolean isForeignWeakAlarmMode(Context context) {
        Object objA;
        if (g != null) {
            return g.booleanValue();
        }
        int iA = PreferenceManager.getDefaultSharedPreferences(context) != null ? com.tencent.android.tpush.common.n.a(context, "com.tencent.android.tpush.enable_FOREIGIN_XG_WEAK_ALARM," + context.getPackageName(), -1) : -1;
        if (iA == -1 && (objA = com.tencent.android.tpush.common.e.a(context, Constants.META_STR_FOREIGIN_XG_WEAK_ALARM, (Object) null)) != null && objA.toString().equals("true")) {
            iA = 1;
        }
        g = Boolean.valueOf(iA == 1);
        return g.booleanValue();
    }

    public static void setForeignWeakAlarmMode(Context context, boolean z) {
        if (g == null || g.booleanValue() != z) {
            g = Boolean.valueOf(z);
            com.tencent.android.tpush.common.n.b(context, "com.tencent.android.tpush.enable_FOREIGIN_XG_WEAK_ALARM," + context.getPackageName(), z ? 1 : 0);
        }
    }
}
