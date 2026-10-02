package com.facebook.yoga;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YogaMeasureOutput {
    public static long make(float width, float height) {
        int wBits = Float.floatToRawIntBits(width);
        int hBits = Float.floatToRawIntBits(height);
        return (((long) wBits) << 32) | ((long) hBits);
    }

    public static long make(int width, int height) {
        return make(width, height);
    }
}
