package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultBitmapPoolParams {
    private static final SparseIntArray DEFAULT_BUCKETS = new SparseIntArray(0);

    private static int getMaxSizeHardCap() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        return maxMemory > 16777216 ? (maxMemory / 4) * 3 : maxMemory / 2;
    }

    public static PoolParams get() {
        return new PoolParams(0, getMaxSizeHardCap(), DEFAULT_BUCKETS);
    }
}
