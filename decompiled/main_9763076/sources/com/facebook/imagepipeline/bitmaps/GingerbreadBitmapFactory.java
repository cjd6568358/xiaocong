package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GingerbreadBitmapFactory extends PlatformBitmapFactory {
    @Override // com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
    public CloseableReference<Bitmap> createBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, bitmapConfig);
        return CloseableReference.of(bitmap, SimpleBitmapReleaser.getInstance());
    }
}
