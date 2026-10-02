package com.bumptech.glide.load.resource.transcode;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifBitmapWrapperDrawableTranscoder implements ResourceTranscoder<GifBitmapWrapper, GlideDrawable> {
    private final ResourceTranscoder<Bitmap, GlideBitmapDrawable> bitmapDrawableResourceTranscoder;

    public GifBitmapWrapperDrawableTranscoder(ResourceTranscoder<Bitmap, GlideBitmapDrawable> bitmapDrawableResourceTranscoder) {
        this.bitmapDrawableResourceTranscoder = bitmapDrawableResourceTranscoder;
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    public Resource<GlideDrawable> transcode(Resource<GifBitmapWrapper> toTranscode) {
        GifBitmapWrapper gifBitmap = toTranscode.get();
        Resource<Bitmap> bitmapResource = gifBitmap.getBitmapResource();
        if (bitmapResource != null) {
            return this.bitmapDrawableResourceTranscoder.transcode(bitmapResource);
        }
        return gifBitmap.getGifResource();
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    public String getId() {
        return "GifBitmapWrapperDrawableTranscoder.com.bumptech.glide.load.resource.transcode";
    }
}
