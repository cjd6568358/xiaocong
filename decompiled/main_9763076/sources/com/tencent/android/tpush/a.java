package com.tencent.android.tpush;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static ReentrantLock a = new ReentrantLock();
    private static Map b = new HashMap();

    public static synchronized Map a(Context context) {
        HashMap map;
        map = new HashMap();
        try {
            for (ProviderInfo providerInfo : context.getPackageManager().queryContentProviders((String) null, 0, 0)) {
                if (providerInfo.name.equals(XGPushProvider.class.getName()) && providerInfo.authority.equals(a(providerInfo.packageName))) {
                    map.put(providerInfo.packageName, providerInfo);
                    com.tencent.android.tpush.a.a.c(Constants.LogTag, providerInfo.authority + "," + providerInfo.packageName + "," + providerInfo.name);
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("ProviderUtil", "Package manager has died", th);
        }
        return map;
    }

    public static String a(String str) {
        return str + XGPushProvider.AUTH_PRIX;
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SettingsContentProvider.KEY, str2);
            context.getContentResolver().insert(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/msg"), contentValues);
            return true;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("ProviderUtil", "sendMsgByPkgName", th);
            return false;
        }
    }

    public static void b(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("mid", str2);
            context.getContentResolver().insert(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/insert_mid_new"), contentValues);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th);
        }
    }

    public static void c(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("mid", str2);
            context.getContentResolver().insert(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/insert_mid_old"), contentValues);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th);
        }
    }

    public static boolean a(Context context, String str, Intent intent) {
        return a(context, str, intent.toURI());
    }

    public static void d(Context context, String str, String str2) {
        Uri uri = Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/feedback");
        ContentValues contentValues = new ContentValues();
        contentValues.put("feedback", Rijndael.encrypt(str2));
        try {
            context.getContentResolver().update(uri, contentValues, null, null);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SettingsContentProvider", "error : ", th);
        }
    }

    public static void b(Context context) {
        Map mapA = a(context);
        if (mapA != null && mapA.size() != 0) {
            try {
                if (Build.MODEL.contains(Constants.VIVO_STR)) {
                    a.lock();
                    for (String str : mapA.keySet()) {
                        com.tencent.android.tpush.a.a.e(Constants.LogTag, "heartbeat to " + str);
                        String strDecrypt = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/heart")));
                        com.tencent.android.tpush.a.a.e(Constants.LogTag, "heartbeat " + str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + strDecrypt);
                        if (com.tencent.android.tpush.stat.a.e.b(strDecrypt)) {
                            new JSONObject(strDecrypt).optInt("cnt", 0);
                            b.put(str, strDecrypt);
                        }
                    }
                    try {
                        return;
                    } catch (Throwable th) {
                        return;
                    }
                }
                for (String str2 : mapA.keySet()) {
                    try {
                        com.tencent.android.tpush.a.a.e(Constants.LogTag, "heartbeat to " + str2);
                        String strDecrypt2 = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str2 + XGPushProvider.AUTH_PRIX + "/heart")));
                        com.tencent.android.tpush.a.a.e(Constants.LogTag, "heartbeat " + str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + strDecrypt2);
                        if (com.tencent.android.tpush.stat.a.e.b(strDecrypt2)) {
                            new JSONObject(strDecrypt2).optInt("cnt", 0);
                            b.put(str2, strDecrypt2);
                        }
                    } catch (Throwable th2) {
                        com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th2);
                    }
                }
            } catch (Throwable th3) {
                com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th3);
            } finally {
                try {
                    a.unlock();
                } catch (Throwable th4) {
                }
            }
        }
    }

    public static String a(Context context, String str) {
        try {
            if (Build.MODEL.contains(Constants.VIVO_STR)) {
                try {
                    a.lock();
                    String strDecrypt = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/" + Constants.FLAG_TOKEN)));
                    com.tencent.android.tpush.a.a.e(Constants.LogTag, "get token from pkg:" + str + ", token:" + strDecrypt);
                    if (strDecrypt != null && strDecrypt.trim().length() == 40) {
                        try {
                            a.unlock();
                            return strDecrypt;
                        } catch (Throwable th) {
                            return strDecrypt;
                        }
                    }
                } catch (Throwable th2) {
                    com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th2);
                }
            } else {
                try {
                    String strDecrypt2 = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/" + Constants.FLAG_TOKEN)));
                    com.tencent.android.tpush.a.a.e(Constants.LogTag, "get token from pkg:" + str + ", token:" + strDecrypt2);
                    if (strDecrypt2 != null && strDecrypt2.trim().length() == 40) {
                        return strDecrypt2;
                    }
                } catch (Throwable th3) {
                    com.tencent.android.tpush.a.a.c("ProviderUtil", Constants.MAIN_VERSION_TAG, th3);
                }
            }
            return null;
        } finally {
            try {
                a.unlock();
            } catch (Throwable th4) {
            }
        }
    }

    public static Map c(Context context) {
        Map mapA = a(context);
        HashMap map = new HashMap();
        if (mapA == null || mapA.size() == 0) {
            return map;
        }
        Iterator it = mapA.keySet().iterator();
        while (it.hasNext()) {
            String strA = a(context, (String) it.next());
            if (com.tencent.android.tpush.stat.b.c.a(strA)) {
                Integer num = (Integer) map.get(strA);
                if (num == null) {
                    map.put(strA, 1);
                } else {
                    map.put(strA, Integer.valueOf(num.intValue() + 1));
                }
            }
        }
        return map;
    }

    public static String d(Context context) {
        String str;
        int iIntValue;
        String str2 = null;
        Map mapC = c(context);
        if (mapC != null && mapC.size() > 0) {
            int i = 0;
            for (Map.Entry entry : mapC.entrySet()) {
                if (((Integer) entry.getValue()).intValue() > i) {
                    iIntValue = ((Integer) entry.getValue()).intValue();
                    str = (String) entry.getKey();
                } else {
                    str = str2;
                    iIntValue = i;
                }
                str2 = str;
                i = iIntValue;
            }
        }
        return str2;
    }

    public static RegisterEntity b(Context context, String str) {
        try {
            if (Build.MODEL.contains(Constants.VIVO_STR)) {
                String strDecrypt = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/register")));
                if (strDecrypt != null) {
                    RegisterEntity registerEntityA = RegisterEntity.a(strDecrypt);
                    try {
                        return registerEntityA;
                    } catch (Throwable th) {
                        return registerEntityA;
                    }
                }
            } else {
                try {
                    String strDecrypt2 = Rijndael.decrypt(context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/register")));
                    if (strDecrypt2 != null) {
                        return RegisterEntity.a(strDecrypt2);
                    }
                } catch (Throwable th2) {
                    com.tencent.android.tpush.a.a.c("ProviderUtil", "getRegisterInfo", th2);
                }
            }
        } catch (Throwable th3) {
            com.tencent.android.tpush.a.a.c("ProviderUtil", "getRegisterInfo", th3);
        } finally {
            try {
                a.unlock();
            } catch (Throwable th4) {
            }
        }
        return null;
    }

    public static Map e(Context context) {
        RegisterEntity registerEntityB;
        Map mapA = a(context);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        if (mapA == null || mapA.size() == 0) {
            return concurrentHashMap;
        }
        for (String str : mapA.keySet()) {
            if (str != null && str.equals(context.getPackageName())) {
                registerEntityB = CacheManager.getCurrentAppRegisterEntity(context);
            } else {
                registerEntityB = b(context, str);
            }
            if (registerEntityB != null && registerEntityB.accessId > 0) {
                concurrentHashMap.put(Long.valueOf(registerEntityB.accessId), registerEntityB);
            }
        }
        return concurrentHashMap;
    }
}
