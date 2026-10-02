package com.xiaocong.smarthome.zxing.utils;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DPIUtil {
    private static float mDensity = 160.0f;

    public static int dip2px(float dipValue) {
        return (int) ((mDensity * dipValue) + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }
}
