package com.facebook.imagepipeline.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imageutils.BitmapUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(21)
public class ArtBitmapFactory extends PlatformBitmapFactory {
    private final BitmapPool mBitmapPool;

    public ArtBitmapFactory(BitmapPool bitmapPool) {
        this.mBitmapPool = bitmapPool;
    }

    @Override // com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
    public CloseableReference<Bitmap> createBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        int sizeInBytes = BitmapUtil.getSizeInByteForBitmap(width, height, bitmapConfig);
        Bitmap bitmap = this.mBitmapPool.get(sizeInBytes);
        Bitmaps.reconfigureBitmap(bitmap, width, height, bitmapConfig);
        return CloseableReference.of(bitmap, this.mBitmapPool);
    }
}
