package com.facebook.react.uimanager;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.infer.annotation.Assertions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DisplayMetricsHolder {
    private static DisplayMetrics sScreenDisplayMetrics;
    private static DisplayMetrics sWindowDisplayMetrics;

    public static void setWindowDisplayMetrics(DisplayMetrics displayMetrics) {
        sWindowDisplayMetrics = displayMetrics;
    }

    public static void initDisplayMetricsIfNotInitialized(Context context) {
        if (getScreenDisplayMetrics() == null) {
            initDisplayMetrics(context);
        }
    }

    public static void initDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowDisplayMetrics(displayMetrics);
        DisplayMetrics screenDisplayMetrics = new DisplayMetrics();
        screenDisplayMetrics.setTo(displayMetrics);
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Assertions.assertNotNull(wm, "WindowManager is null!");
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(screenDisplayMetrics);
        } else {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight", new Class[0]);
                Method mGetRawW = Display.class.getMethod("getRawWidth", new Class[0]);
                screenDisplayMetrics.widthPixels = ((Integer) mGetRawW.invoke(display, new Object[0])).intValue();
                screenDisplayMetrics.heightPixels = ((Integer) mGetRawH.invoke(display, new Object[0])).intValue();
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Error getting real dimensions for API level < 17", e);
            }
        }
        setScreenDisplayMetrics(screenDisplayMetrics);
    }

    @Deprecated
    public static DisplayMetrics getWindowDisplayMetrics() {
        return sWindowDisplayMetrics;
    }

    public static void setScreenDisplayMetrics(DisplayMetrics screenDisplayMetrics) {
        sScreenDisplayMetrics = screenDisplayMetrics;
    }

    public static DisplayMetrics getScreenDisplayMetrics() {
        return sScreenDisplayMetrics;
    }
}
