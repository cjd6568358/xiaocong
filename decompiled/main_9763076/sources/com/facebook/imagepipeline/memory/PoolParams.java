package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PoolParams {
    public final SparseIntArray bucketSizes;
    public final int maxBucketSize;
    public final int maxNumThreads;
    public final int maxSizeHardCap;
    public final int maxSizeSoftCap;
    public final int minBucketSize;

    public PoolParams(int maxSizeSoftCap, int maxSizeHardCap, SparseIntArray bucketSizes) {
        this(maxSizeSoftCap, maxSizeHardCap, bucketSizes, 0, Integer.MAX_VALUE, -1);
    }

    public PoolParams(int maxSizeSoftCap, int maxSizeHardCap, SparseIntArray bucketSizes, int minBucketSize, int maxBucketSize, int maxNumThreads) {
        Preconditions.checkState(maxSizeSoftCap >= 0 && maxSizeHardCap >= maxSizeSoftCap);
        this.maxSizeSoftCap = maxSizeSoftCap;
        this.maxSizeHardCap = maxSizeHardCap;
        this.bucketSizes = bucketSizes;
        this.minBucketSize = minBucketSize;
        this.maxBucketSize = maxBucketSize;
        this.maxNumThreads = maxNumThreads;
    }
}
