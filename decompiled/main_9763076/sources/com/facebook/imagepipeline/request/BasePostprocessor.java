package com.facebook.imagepipeline.request;

import android.graphics.Bitmap;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.nativecode.Bitmaps;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BasePostprocessor implements Postprocessor {
    @Override // com.facebook.imagepipeline.request.Postprocessor
    public String getName() {
        return "Unknown postprocessor";
    }

    @Override // com.facebook.imagepipeline.request.Postprocessor
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        CloseableReference<Bitmap> destBitmapRef = bitmapFactory.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
        try {
            process(destBitmapRef.get(), sourceBitmap);
            return CloseableReference.cloneOrNull(destBitmapRef);
        } finally {
            CloseableReference.closeSafely(destBitmapRef);
        }
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        Bitmaps.copyBitmap(destBitmap, sourceBitmap);
        process(destBitmap);
    }

    public void process(Bitmap bitmap) {
    }

    @Override // com.facebook.imagepipeline.request.Postprocessor
    public CacheKey getPostprocessorCacheKey() {
        return null;
    }
}
