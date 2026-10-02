package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class GifFrameResourceDecoder implements ResourceDecoder<GifDecoder, Bitmap> {
    private final BitmapPool bitmapPool;

    public GifFrameResourceDecoder(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<Bitmap> decode(GifDecoder source, int width, int height) {
        Bitmap bitmap = source.getNextFrame();
        return BitmapResource.obtain(bitmap, this.bitmapPool);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return "GifFrameResourceDecoder.com.bumptech.glide.load.resource.gif";
    }
}
