package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultFlexByteArrayPoolParams {
    public static final int DEFAULT_MAX_NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static SparseIntArray generateBuckets(int min, int max, int numThreads) {
        SparseIntArray buckets = new SparseIntArray();
        for (int i = min; i <= max; i *= 2) {
            buckets.put(i, numThreads);
        }
        return buckets;
    }

    public static PoolParams get() {
        return new PoolParams(4194304, DEFAULT_MAX_NUM_THREADS * 4194304, generateBuckets(WXMediaMessage.MINI_PROGRAM__THUMB_LENGHT, 4194304, DEFAULT_MAX_NUM_THREADS), WXMediaMessage.MINI_PROGRAM__THUMB_LENGHT, 4194304, DEFAULT_MAX_NUM_THREADS);
    }
}
