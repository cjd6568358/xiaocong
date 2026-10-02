package com.huawei.hms.update.f;

import android.content.Context;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/* JADX INFO: compiled from: UpdateUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    public static String a(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (configuration.locale != null) {
            String language = configuration.locale.getLanguage();
            String country = configuration.locale.getCountry();
            if (language != null && country != null) {
                return language.toLowerCase(Locale.getDefault()) + '_' + country.toUpperCase(Locale.getDefault());
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static String b(Context context) {
        String strA = a("ro.product.locale.region");
        if (TextUtils.isEmpty(strA)) {
            String strD = d(context);
            return TextUtils.isEmpty(strD) ? Constants.MAIN_VERSION_TAG : strD;
        }
        return strA;
    }

    private static String d(Context context) {
        String country;
        Configuration configuration = context.getResources().getConfiguration();
        return (configuration.locale == null || (country = configuration.locale.getCountry()) == null) ? Constants.MAIN_VERSION_TAG : country;
    }

    private static String a(String str) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) cls.getDeclaredMethod("get", String.class).invoke(cls, str);
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.d("UpdateUtils", "An exception occurred while reading: " + str);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static boolean c(Context context) {
        String strA = a("ro.product.locale.region");
        if (!TextUtils.isEmpty(strA)) {
            return AdvanceSetting.CLEAR_NOTIFICATION.equalsIgnoreCase(strA);
        }
        String strD = d(context);
        if (!TextUtils.isEmpty(strD)) {
            return AdvanceSetting.CLEAR_NOTIFICATION.equalsIgnoreCase(strD);
        }
        if (e(context).startsWith("460")) {
        }
        return true;
    }

    private static String e(Context context) {
        String strA;
        String simOperator = Constants.MAIN_VERSION_TAG;
        com.huawei.hms.update.f.a.a aVarA = com.huawei.hms.update.f.a.a.a();
        if (aVarA != null) {
            int iB = aVarA.b();
            if (iB != -1 && 5 != aVarA.b(iB)) {
                strA = Constants.MAIN_VERSION_TAG;
            } else {
                strA = aVarA.a(iB);
                if (TextUtils.isEmpty(strA)) {
                    strA = aVarA.c(iB);
                }
            }
            simOperator = strA;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null && 5 == telephonyManager.getSimState()) {
                simOperator = telephonyManager.getSimOperator();
                if (TextUtils.isEmpty(simOperator)) {
                    simOperator = telephonyManager.getSubscriberId();
                }
            }
        }
        if (TextUtils.isEmpty(simOperator)) {
            return "00000";
        }
        return simOperator.substring(0, 5);
    }
}
