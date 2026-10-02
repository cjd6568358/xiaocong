package com.xiaocong.smarthome.timerRuler.utils;

import android.content.res.Resources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CUtils {
    public static int dip2px(float dipValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) ((dipValue * scale) + 0.5f);
    }
}
