package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultByteArrayPoolParams {
    public static PoolParams get() {
        SparseIntArray defaultBuckets = new SparseIntArray();
        defaultBuckets.put(16384, 5);
        return new PoolParams(81920, 1048576, defaultBuckets);
    }
}
