package com.facebook.imagepipeline.memory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapCounterProvider {
    public static final int MAX_BITMAP_TOTAL_SIZE = getMaxSizeHardCap();
    private static BitmapCounter sBitmapCounter;

    private static int getMaxSizeHardCap() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        return ((long) maxMemory) > 16777216 ? (maxMemory / 4) * 3 : maxMemory / 2;
    }

    public static BitmapCounter get() {
        if (sBitmapCounter == null) {
            sBitmapCounter = new BitmapCounter(384, MAX_BITMAP_TOTAL_SIZE);
        }
        return sBitmapCounter;
    }
}
