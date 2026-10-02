package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeMemoryChunkPool extends BasePool<NativeMemoryChunk> {
    private final int[] mBucketSizes;

    public NativeMemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker nativeMemoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, nativeMemoryChunkPoolStatsTracker);
        SparseIntArray bucketSizes = poolParams.bucketSizes;
        this.mBucketSizes = new int[bucketSizes.size()];
        for (int i = 0; i < this.mBucketSizes.length; i++) {
            this.mBucketSizes[i] = bucketSizes.keyAt(i);
        }
        initialize();
    }

    public int getMinBufferSize() {
        return this.mBucketSizes[0];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public NativeMemoryChunk alloc(int bucketedSize) {
        return new NativeMemoryChunk(bucketedSize);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public void free(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        value.close();
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
    public int getBucketedSizeForValue(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        return value.getSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public boolean isReusable(NativeMemoryChunk value) {
        Preconditions.checkNotNull(value);
        return !value.isClosed();
    }
}
