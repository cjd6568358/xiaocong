package com.xiaocong.smarthome.httplib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SpUtils {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void saveToLocal(Context context, String name, String key, T t) {
        SharedPreferences sp;
        if (context != null) {
            if (name == null) {
                sp = getDefaultSharedPreferences(context);
            } else {
                sp = context.getSharedPreferences(name, 0);
            }
            if (sp != null) {
                if (t instanceof Boolean) {
                    sp.edit().putBoolean(key, ((Boolean) t).booleanValue()).commit();
                    return;
                }
                if (t instanceof String) {
                    sp.edit().putString(key, (String) t).commit();
                    return;
                }
                if (t instanceof Integer) {
                    sp.edit().putInt(key, ((Integer) t).intValue()).commit();
                } else if (t instanceof Float) {
                    sp.edit().putFloat(key, ((Float) t).floatValue()).commit();
                } else if (t instanceof Long) {
                    sp.edit().putLong(key, ((Long) t).longValue()).commit();
                }
            }
        }
    }

    public static <T> T getFromLocal(Context context, String str, String str2, T t) {
        SharedPreferences sharedPreferences;
        Map<String, ?> all;
        if (context != null) {
            if (str == null) {
                sharedPreferences = getDefaultSharedPreferences(context);
            } else {
                sharedPreferences = context.getSharedPreferences(str, 0);
            }
            return (sharedPreferences == null || (all = sharedPreferences.getAll()) == null || all.get(str2) == null) ? t : (T) all.get(str2);
        }
        return t;
    }

    public static boolean clearSp(String name, Context context) {
        SharedPreferences sp;
        if (context == null) {
            return false;
        }
        if (name == null) {
            sp = getDefaultSharedPreferences(context);
        } else {
            sp = context.getSharedPreferences(name, 0);
        }
        if (sp != null) {
            return sp.edit().clear().commit();
        }
        return false;
    }

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
