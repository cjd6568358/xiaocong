package com.huawei.hms.c;

import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import bsh.ParserConstants;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* JADX INFO: compiled from: Util.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    public static boolean a() {
        String str;
        String str2;
        String str3 = Constants.MAIN_VERSION_TAG;
        try {
            Object objA = a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Class[]{String.class}, new Object[]{"ro.product.locale.language"});
            Object objA2 = a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Class[]{String.class}, new Object[]{"ro.product.locale.region"});
            if (objA != null) {
                str3 = (String) objA;
            }
            if (objA2 == null) {
                str2 = Constants.MAIN_VERSION_TAG;
            } else {
                str2 = (String) objA2;
            }
            str = str3;
        } catch (Exception e) {
            com.huawei.hms.support.log.a.d("Util", "can not get language and region:" + e.getMessage());
            str = str3;
            str2 = Constants.MAIN_VERSION_TAG;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return b();
        }
        return "zh".equalsIgnoreCase(str) && AdvanceSetting.CLEAR_NOTIFICATION.equalsIgnoreCase(str2);
    }

    public static boolean b() {
        return AdvanceSetting.CLEAR_NOTIFICATION.equalsIgnoreCase(Locale.getDefault().getCountry());
    }

    public static Object a(String str, String str2, Class<?>[] clsArr, Object[] objArr) {
        Class<?> cls;
        Method method;
        if (clsArr == null || objArr == null || clsArr.length != objArr.length) {
            com.huawei.hms.support.log.a.a("Util", "invokeFun params invalid");
            return null;
        }
        Object objA = a(str);
        if (objA == null) {
            return null;
        }
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            com.huawei.hms.support.log.a.d("Util", "can not find class:" + str);
            cls = null;
        }
        if (cls != null) {
            try {
                method = cls.getMethod(str2, clsArr);
            } catch (NoSuchMethodException e2) {
                com.huawei.hms.support.log.a.d("Util", "can not find method:" + str2);
                method = null;
            }
        } else {
            method = null;
        }
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(objA, objArr);
        } catch (IllegalAccessException e3) {
            com.huawei.hms.support.log.a.d("Util", "method can not invoke:" + e3.getMessage());
            return null;
        } catch (IllegalArgumentException e4) {
            com.huawei.hms.support.log.a.d("Util", "method can not invoke:" + e4.getMessage());
            return null;
        } catch (InvocationTargetException e5) {
            com.huawei.hms.support.log.a.d("Util", "method can not invoke:" + e5.getMessage());
            return null;
        }
    }

    public static Object a(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            com.huawei.hms.support.log.a.d("Util", "can not find class:" + str);
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e2) {
            com.huawei.hms.support.log.a.d("Util", "class creat instance error :" + e2.getMessage());
            return null;
        } catch (InstantiationException e3) {
            com.huawei.hms.support.log.a.d("Util", "class creat instance error :" + e3.getMessage());
            return null;
        }
    }

    public static String a(Context context) {
        String strValueOf;
        Object obj;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            com.huawei.hms.support.log.a.d("Util", "In getMetaDataAppId, Failed to get 'PackageManager' instance.");
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            if (applicationInfo != null && applicationInfo.metaData != null && (obj = applicationInfo.metaData.get("com.huawei.hms.client.appid")) != null) {
                strValueOf = String.valueOf(obj);
            } else {
                com.huawei.hms.support.log.a.d("Util", "In getMetaDataAppId, Failed to read meta data for the AppID.");
                strValueOf = Constants.MAIN_VERSION_TAG;
            }
            return strValueOf;
        } catch (PackageManager.NameNotFoundException e) {
            com.huawei.hms.support.log.a.d("Util", "In getMetaDataAppId, Failed to read meta data for the AppID.");
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String a(Context context, String str) {
        if (context == null) {
            com.huawei.hms.support.log.a.d("Util", "In getAppName, context is null.");
            return Constants.MAIN_VERSION_TAG;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            com.huawei.hms.support.log.a.d("Util", "In getAppName, Failed to get 'PackageManager' instance.");
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            if (TextUtils.isEmpty(str)) {
                str = context.getPackageName();
            }
            CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0));
            return applicationLabel == null ? Constants.MAIN_VERSION_TAG : applicationLabel.toString();
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            com.huawei.hms.support.log.a.d("Util", "In getAppName, Failed to get app name.");
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static void a(Context context, ServiceConnection serviceConnection) {
        try {
            context.unbindService(serviceConnection);
        } catch (Exception e) {
            com.huawei.hms.support.log.a.d("Util", "On unBindServiceException:" + e.getMessage());
        }
    }
}
