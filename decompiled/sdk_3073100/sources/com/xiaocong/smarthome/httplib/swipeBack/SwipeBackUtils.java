package com.xiaocong.smarthome.httplib.swipeBack;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwipeBackUtils {
    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    public static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class<?> cls : classes) {
                if (cls.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = cls;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, null);
        } catch (Throwable th) {
        }
    }

    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions", new Class[0]);
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity, new Object[0]);
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class<?> cls : classes) {
                if (cls.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = cls;
                }
            }
            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable th) {
        }
    }
}
