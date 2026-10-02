package com.ixiaocong.smarthome.phone.android.common.utils;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScreenUtils {
    public static int getStatusHeight(Context context) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            int statusHeight = context.getResources().getDimensionPixelSize(height);
            return statusHeight;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
