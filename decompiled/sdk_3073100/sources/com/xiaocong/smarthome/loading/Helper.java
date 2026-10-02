package com.xiaocong.smarthome.loading;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class Helper {
    private static float scale;

    public static int dpToPixel(float dp, Context context) {
        if (scale == 0.0f) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (scale * dp);
    }
}
