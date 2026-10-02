package com.xiaocong.smarthome.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DisplayHelper {
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    public static float sDensity = 0.0f;
    private static Boolean sHasCamera = null;

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getDensity(Context context) {
        if (sDensity == 0.0f) {
            sDensity = getDisplayMetrics(context).density;
        }
        return sDensity;
    }

    public static int dp2px(Context context, int dp) {
        return (int) (((double) (getDensity(context) * dp)) + 0.5d);
    }
}
