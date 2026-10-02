package com.facebook.drawee.drawable;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableUtils {
    public static void copyProperties(Drawable to, Drawable from) {
        if (from != null && to != null && to != from) {
            to.setBounds(from.getBounds());
            to.setChangingConfigurations(from.getChangingConfigurations());
            to.setLevel(from.getLevel());
            to.setVisible(from.isVisible(), false);
            to.setState(from.getState());
        }
    }

    public static void setDrawableProperties(Drawable drawable, DrawableProperties properties) {
        if (drawable != null && properties != null) {
            properties.applyTo(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setCallbacks(Drawable drawable, Drawable.Callback callback, TransformCallback transformCallback) {
        if (drawable != 0) {
            drawable.setCallback(callback);
            if (drawable instanceof TransformAwareDrawable) {
                ((TransformAwareDrawable) drawable).setTransformCallback(transformCallback);
            }
        }
    }

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
