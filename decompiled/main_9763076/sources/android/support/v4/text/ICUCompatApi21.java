package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ICUCompatApi21 {
    private static Method sAddLikelySubtagsMethod;

    static {
        try {
            Class<?> clazz = Class.forName("libcore.icu.ICU");
            sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", Locale.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        try {
            Object[] args = {locale};
            return ((Locale) sAddLikelySubtagsMethod.invoke(null, args)).getScript();
        } catch (IllegalAccessException e) {
            Log.w("ICUCompatApi21", e);
            return locale.getScript();
        } catch (InvocationTargetException e2) {
            Log.w("ICUCompatApi21", e2);
            return locale.getScript();
        }
    }
}
