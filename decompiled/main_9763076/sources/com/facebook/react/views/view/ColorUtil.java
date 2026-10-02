package com.facebook.react.views.view;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ColorUtil {
    public static int multiplyColorAlpha(int color, int alpha) {
        if (alpha != 255) {
            if (alpha == 0) {
                return color & 16777215;
            }
            int colorAlpha = color >>> 24;
            int multipliedAlpha = (colorAlpha * (alpha + (alpha >> 7))) >> 8;
            return (multipliedAlpha << 24) | (16777215 & color);
        }
        return color;
    }

    public static int getOpacityFromColor(int color) {
        int colorAlpha = color >>> 24;
        if (colorAlpha == 255) {
            return -1;
        }
        if (colorAlpha == 0) {
            return -2;
        }
        return -3;
    }
}
