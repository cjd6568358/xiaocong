package com.huawei.hms.support.api.push.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import bsh.ParserConstants;
import java.io.File;
import java.util.List;

/* JADX INFO: compiled from: CommFun.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    private static int a = -1;
    private static final Object b = new Object();

    private static boolean c(Context context) {
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("CommFun", "existFrameworkPush:" + a);
        }
        try {
            if (!new File("/system/framework/hwpush.jar").isFile()) {
                return false;
            }
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("CommFun", "push jarFile is exist");
            }
            List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(new Intent().setClassName("android", "com.huawei.android.pushagentproxy.PushService"), ParserConstants.LSHIFTASSIGN);
            if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
                if (!com.huawei.hms.support.log.a.b()) {
                    return false;
                }
                com.huawei.hms.support.log.a.b("CommFun", "framework push not exist, need vote apk or sdk to support pushservice");
                return false;
            }
            if (com.huawei.hms.support.log.a.b()) {
                com.huawei.hms.support.log.a.b("CommFun", "framework push exist, use framework push first");
            }
            return true;
        } catch (Exception e) {
            if (!com.huawei.hms.support.log.a.a()) {
                return false;
            }
            com.huawei.hms.support.log.a.d("CommFun", "get Apk version faild ,Exception e= " + e.toString());
            return false;
        }
    }

    public static boolean a(Context context) {
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("CommFun", "existFrameworkPush:" + a);
        }
        synchronized (b) {
            if (-1 != a) {
                return 1 == a;
            }
            if (c(context)) {
                a = 1;
            } else {
                a = 0;
            }
            return 1 == a;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) == null) {
                return false;
            }
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("CommFun", str + " is installed");
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            if (!com.huawei.hms.support.log.a.a()) {
                return "0.0";
            }
            com.huawei.hms.support.log.a.a("CommFun", "package not exist");
            return "0.0";
        } catch (Exception e2) {
            if (!com.huawei.hms.support.log.a.a()) {
                return "0.0";
            }
            com.huawei.hms.support.log.a.d("CommFun", "getApkVersionName error" + e2.getMessage());
            return "0.0";
        }
    }
}
