package com.scwang.smartrefresh.layout.util;

import android.content.res.Resources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DensityUtil {
    public float density = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(float dpValue) {
        return (int) (0.5f + (Resources.getSystem().getDisplayMetrics().density * dpValue));
    }

    public static float px2dp(int pxValue) {
        return pxValue / Resources.getSystem().getDisplayMetrics().density;
    }

    public int dip2px(float dpValue) {
        return (int) (0.5f + (this.density * dpValue));
    }
}
