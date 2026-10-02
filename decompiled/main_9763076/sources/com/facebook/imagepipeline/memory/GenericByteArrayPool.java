package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GenericByteArrayPool extends BasePool<byte[]> implements ByteArrayPool {
    private final int[] mBucketSizes;

    public GenericByteArrayPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        SparseIntArray bucketSizes = poolParams.bucketSizes;
        this.mBucketSizes = new int[bucketSizes.size()];
        for (int i = 0; i < bucketSizes.size(); i++) {
            this.mBucketSizes[i] = bucketSizes.keyAt(i);
        }
        initialize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public byte[] alloc(int bucketedSize) {
        return new byte[bucketedSize];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public void free(byte[] value) {
        Preconditions.checkNotNull(value);
    }

    @Override // com.facebook.imagepipeline.memory.BasePool
    protected int getSizeInBytes(int bucketedSize) {
        return bucketedSize;
    }

    @Override // com.facebook.imagepipeline.memory.BasePool
    protected int getBucketedSize(int requestSize) {
        if (requestSize <= 0) {
            throw new BasePool.InvalidSizeException(Integer.valueOf(requestSize));
        }
        for (int bucketedSize : this.mBucketSizes) {
            if (bucketedSize >= requestSize) {
                return bucketedSize;
            }
        }
        return requestSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public int getBucketedSizeForValue(byte[] value) {
        Preconditions.checkNotNull(value);
        return value.length;
    }
}
