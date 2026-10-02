package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imageutils.BitmapUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapCounter {
    private int mCount;
    private final int mMaxCount;
    private final int mMaxSize;
    private long mSize;
    private final ResourceReleaser<Bitmap> mUnpooledBitmapsReleaser;

    public BitmapCounter(int maxCount, int maxSize) {
        Preconditions.checkArgument(maxCount > 0);
        Preconditions.checkArgument(maxSize > 0);
        this.mMaxCount = maxCount;
        this.mMaxSize = maxSize;
        this.mUnpooledBitmapsReleaser = new ResourceReleaser<Bitmap>() { // from class: com.facebook.imagepipeline.memory.BitmapCounter.1
            @Override // com.facebook.common.references.ResourceReleaser
            public void release(Bitmap value) {
                try {
                    BitmapCounter.this.decrease(value);
                } finally {
                    value.recycle();
                }
            }
        };
    }

    public synchronized boolean increase(Bitmap bitmap) {
        boolean z;
        int bitmapSize = BitmapUtil.getSizeInBytes(bitmap);
        if (this.mCount >= this.mMaxCount || this.mSize + ((long) bitmapSize) > this.mMaxSize) {
            z = false;
        } else {
            this.mCount++;
            this.mSize += (long) bitmapSize;
            z = true;
        }
        return z;
    }

    public synchronized void decrease(Bitmap bitmap) {
        synchronized (this) {
            int bitmapSize = BitmapUtil.getSizeInBytes(bitmap);
            Preconditions.checkArgument(this.mCount > 0, "No bitmaps registered.");
            Preconditions.checkArgument(((long) bitmapSize) <= this.mSize, "Bitmap size bigger than the total registered size: %d, %d", Integer.valueOf(bitmapSize), Long.valueOf(this.mSize));
            this.mSize -= (long) bitmapSize;
            this.mCount--;
        }
    }

    public ResourceReleaser<Bitmap> getReleaser() {
        return this.mUnpooledBitmapsReleaser;
    }
}
