package com.tencent.android.tpush.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.tencent.android.tpush.XGPushManager;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l {
    private static final String[] a = {"android.permission.INTERNET", "android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"};
    private static Map b = new HashMap(8);

    private static Context b() {
        return XGPushManager.getContext() != null ? XGPushManager.getContext() : com.tencent.android.tpush.service.n.f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(String str) {
        boolean zBooleanValue;
        Throwable th;
        Map map = null;
        try {
            if (b.containsKey(str)) {
                zBooleanValue = ((Boolean) b.get(str)).booleanValue();
            } else {
                Context contextB = b();
                zBooleanValue = contextB.getPackageManager().checkPermission(str, contextB.getPackageName()) == 0 ? 1 : 0;
                try {
                    map = b;
                    map.put(str, Boolean.valueOf(zBooleanValue));
                    zBooleanValue = zBooleanValue;
                } catch (Throwable th2) {
                    th = th2;
                    Log.e("XgStat", "checkPermission error", th);
                }
            }
        } catch (Throwable th3) {
            zBooleanValue = map;
            th = th3;
        }
        return zBooleanValue;
    }

    public static boolean a() {
        Context contextB = b();
        if (contextB == null) {
            throw new IllegalArgumentException("The context parameter can not be null!");
        }
        try {
            PackageManager packageManager = contextB.getPackageManager();
            if (packageManager != null) {
                String[] strArr = packageManager.getPackageInfo(contextB.getPackageName(), 4096).requestedPermissions;
                if (strArr == null) {
                    return false;
                }
                for (String str : a) {
                    boolean zA = a(strArr, str);
                    b.put(str, Boolean.valueOf(zA));
                    if (!zA) {
                        com.tencent.android.tpush.a.a.j(Constants.LogTag, "The required permission of <" + str + "> does not found!");
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "check required permissins exception.", e);
            return false;
        }
    }

    private static boolean a(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }
}
