package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ICUCompatIcs {
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        try {
            Class<?> clazz = Class.forName("libcore.icu.ICU");
            if (clazz != null) {
                sGetScriptMethod = clazz.getMethod("getScript", String.class);
                sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", String.class);
            }
        } catch (Exception e) {
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            Log.w("ICUCompatIcs", e);
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        String localeWithSubtags = addLikelySubtags(locale);
        if (localeWithSubtags != null) {
            return getScript(localeWithSubtags);
        }
        return null;
    }

    private static String getScript(String localeStr) {
        try {
            if (sGetScriptMethod != null) {
                Object[] args = {localeStr};
                return (String) sGetScriptMethod.invoke(null, args);
            }
        } catch (IllegalAccessException e) {
            Log.w("ICUCompatIcs", e);
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompatIcs", e2);
        }
        return null;
    }

    private static String addLikelySubtags(Locale locale) {
        String localeStr = locale.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                Object[] args = {localeStr};
                return (String) sAddLikelySubtagsMethod.invoke(null, args);
            }
        } catch (IllegalAccessException e) {
            Log.w("ICUCompatIcs", e);
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompatIcs", e2);
        }
        return localeStr;
    }
}
