package com.facebook.imagepipeline.memory;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(21)
public class BitmapPool extends BasePool<Bitmap> {
    public BitmapPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        initialize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public Bitmap alloc(int size) {
        return Bitmap.createBitmap(1, (int) Math.ceil(((double) size) / 2.0d), Bitmap.Config.RGB_565);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public void free(Bitmap value) {
        Preconditions.checkNotNull(value);
        value.recycle();
    }

    @Override // com.facebook.imagepipeline.memory.BasePool
    protected int getBucketedSize(int requestSize) {
        return requestSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public int getBucketedSizeForValue(Bitmap value) {
        Preconditions.checkNotNull(value);
        return value.getAllocationByteCount();
    }

    @Override // com.facebook.imagepipeline.memory.BasePool
    protected int getSizeInBytes(int bucketedSize) {
        return bucketedSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.memory.BasePool
    public boolean isReusable(Bitmap value) {
        Preconditions.checkNotNull(value);
        return !value.isRecycled() && value.isMutable();
    }
}
