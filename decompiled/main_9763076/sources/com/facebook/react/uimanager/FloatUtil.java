package com.facebook.react.uimanager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FloatUtil {
    public static boolean floatsEqual(float f1, float f2) {
        if (Float.isNaN(f1) || Float.isNaN(f2)) {
            return Float.isNaN(f1) && Float.isNaN(f2);
        }
        return Math.abs(f2 - f1) < 1.0E-5f;
    }
}
