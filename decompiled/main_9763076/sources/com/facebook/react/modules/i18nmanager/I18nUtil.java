package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.text.TextUtilsCompat;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class I18nUtil {
    private static I18nUtil sharedI18nUtilInstance = null;

    private I18nUtil() {
    }

    public static I18nUtil getInstance() {
        if (sharedI18nUtilInstance == null) {
            sharedI18nUtilInstance = new I18nUtil();
        }
        return sharedI18nUtilInstance;
    }

    public boolean isRTL(Context context) {
        if (isRTLForced(context)) {
            return true;
        }
        return isRTLAllowed(context) && isDevicePreferredLanguageRTL();
    }

    private boolean isRTLAllowed(Context context) {
        return isPrefSet(context, "RCTI18nUtil_allowRTL", true);
    }

    public void allowRTL(Context context, boolean allowRTL) {
        setPref(context, "RCTI18nUtil_allowRTL", allowRTL);
    }

    private boolean isRTLForced(Context context) {
        return isPrefSet(context, "RCTI18nUtil_forceRTL", false);
    }

    public void forceRTL(Context context, boolean forceRTL) {
        setPref(context, "RCTI18nUtil_forceRTL", forceRTL);
    }

    private boolean isDevicePreferredLanguageRTL() {
        int directionality = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        return directionality == 1;
    }

    private boolean isPrefSet(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences("com.facebook.react.modules.i18nmanager.I18nUtil", 0);
        return prefs.getBoolean(key, defaultValue);
    }

    private void setPref(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("com.facebook.react.modules.i18nmanager.I18nUtil", 0).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
