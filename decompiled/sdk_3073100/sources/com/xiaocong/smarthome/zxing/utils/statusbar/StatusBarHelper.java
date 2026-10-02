package com.xiaocong.smarthome.zxing.utils.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class StatusBarHelper {
    public static float sVirtualDensity = -1.0f;
    public static float sVirtualDensityDpi = -1.0f;
    private static int sStatusbarHeight = -1;
    private static int mStatuBarType = 0;

    public static void translucent(Activity activity) {
        translucent(activity, 1073741824);
    }

    @TargetApi(19)
    public static void translucent(Activity activity, int colorOn5x) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (DeviceHelper.isMeizu() || DeviceHelper.isMIUI()) {
                activity.getWindow().setFlags(67108864, 67108864);
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.getDecorView().setSystemUiVisibility(1280);
                if (Build.VERSION.SDK_INT >= 23 && supportTransclentStatusBar6()) {
                    window.clearFlags(67108864);
                    window.addFlags(Integer.MIN_VALUE);
                    window.setStatusBarColor(0);
                } else {
                    window.clearFlags(67108864);
                    window.addFlags(Integer.MIN_VALUE);
                    window.setStatusBarColor(colorOn5x);
                }
            }
        }
    }

    public static boolean setStatusBarLightMode(Activity activity) {
        if (DeviceHelper.isZTKC2016()) {
            return false;
        }
        if (mStatuBarType != 0) {
            return setStatusBarLightMode(activity, mStatuBarType);
        }
        if (Build.VERSION.SDK_INT < 19) {
            return false;
        }
        if (isMIUICustomStatusBarLightModeImpl() && MIUISetStatusBarLightMode(activity.getWindow(), true)) {
            mStatuBarType = 1;
            return true;
        }
        if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
            mStatuBarType = 2;
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        Android6SetStatusBarLightMode(activity.getWindow(), true);
        mStatuBarType = 3;
        return true;
    }

    private static boolean setStatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            return MIUISetStatusBarLightMode(activity.getWindow(), true);
        }
        if (type == 2) {
            return FlymeSetStatusBarLightMode(activity.getWindow(), true);
        }
        if (type == 3) {
            return Android6SetStatusBarLightMode(activity.getWindow(), true);
        }
        return false;
    }

    public static boolean setStatusBarDarkMode(Activity activity) {
        if (mStatuBarType == 0) {
            return true;
        }
        if (mStatuBarType == 1) {
            return MIUISetStatusBarLightMode(activity.getWindow(), false);
        }
        if (mStatuBarType == 2) {
            return FlymeSetStatusBarLightMode(activity.getWindow(), false);
        }
        if (mStatuBarType == 3) {
            return Android6SetStatusBarLightMode(activity.getWindow(), false);
        }
        return true;
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window, int out) {
        return retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, out, 1024), 4), 2), 4096), 1024), 512);
    }

    public static int retainSystemUiFlag(Window window, int out, int type) {
        int now = window.getDecorView().getSystemUiVisibility();
        if ((now & type) == type) {
            return out | type;
        }
        return out;
    }

    @TargetApi(23)
    private static boolean Android6SetStatusBarLightMode(Window window, boolean light) {
        View decorView = window.getDecorView();
        int systemUi = light ? AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE : 256;
        decorView.setSystemUiVisibility(changeStatusBarModeRetainFlag(window, systemUi));
        return true;
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        Class<?> cls = window.getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(cls2);
            Method extraFlagField = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
            if (dark) {
                extraFlagField.invoke(window, Integer.valueOf(darkModeFlag), Integer.valueOf(darkModeFlag));
            } else {
                extraFlagField.invoke(window, 0, Integer.valueOf(darkModeFlag));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isMIUICustomStatusBarLightModeImpl() {
        return DeviceHelper.isMIUIV5() || DeviceHelper.isMIUIV6() || DeviceHelper.isMIUIV7() || DeviceHelper.isMIUIV8();
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        int value;
        if (window == null) {
            return false;
        }
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value2 = meizuFlags.getInt(lp);
            if (dark) {
                value = value2 | bit;
            } else {
                value = value2 & (bit ^ (-1));
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean supportTransclentStatusBar6() {
        return (DeviceHelper.isZUKZ1() || DeviceHelper.isZTKC2016()) ? false : true;
    }
}
