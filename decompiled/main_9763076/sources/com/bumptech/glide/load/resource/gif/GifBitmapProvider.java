package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class GifBitmapProvider implements GifDecoder.BitmapProvider {
    private final BitmapPool bitmapPool;

    public GifBitmapProvider(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider
    public Bitmap obtain(int width, int height, Bitmap.Config config) {
        return this.bitmapPool.getDirty(width, height, config);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider
    public void release(Bitmap bitmap) {
        if (!this.bitmapPool.put(bitmap)) {
            bitmap.recycle();
        }
    }
}
