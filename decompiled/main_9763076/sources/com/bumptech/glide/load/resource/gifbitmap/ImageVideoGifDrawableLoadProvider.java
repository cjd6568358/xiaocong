package com.bumptech.glide.load.resource.gifbitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.provider.DataLoadProvider;
import java.io.File;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageVideoGifDrawableLoadProvider implements DataLoadProvider<ImageVideoWrapper, GifBitmapWrapper> {
    private final ResourceDecoder<File, GifBitmapWrapper> cacheDecoder;
    private final ResourceEncoder<GifBitmapWrapper> encoder;
    private final ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> sourceDecoder;
    private final Encoder<ImageVideoWrapper> sourceEncoder;

    public ImageVideoGifDrawableLoadProvider(DataLoadProvider<ImageVideoWrapper, Bitmap> bitmapProvider, DataLoadProvider<InputStream, GifDrawable> gifProvider, BitmapPool bitmapPool) {
        GifBitmapWrapperResourceDecoder decoder = new GifBitmapWrapperResourceDecoder(bitmapProvider.getSourceDecoder(), gifProvider.getSourceDecoder(), bitmapPool);
        this.cacheDecoder = new FileToStreamDecoder(new GifBitmapWrapperStreamResourceDecoder(decoder));
        this.sourceDecoder = decoder;
        this.encoder = new GifBitmapWrapperResourceEncoder(bitmapProvider.getEncoder(), gifProvider.getEncoder());
        this.sourceEncoder = bitmapProvider.getSourceEncoder();
    }

    @Override // com.bumptech.glide.provider.DataLoadProvider
    public ResourceDecoder<File, GifBitmapWrapper> getCacheDecoder() {
        return this.cacheDecoder;
    }

    @Override // com.bumptech.glide.provider.DataLoadProvider
    public ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> getSourceDecoder() {
        return this.sourceDecoder;
    }

    @Override // com.bumptech.glide.provider.DataLoadProvider
    public Encoder<ImageVideoWrapper> getSourceEncoder() {
        return this.sourceEncoder;
    }

    @Override // com.bumptech.glide.provider.DataLoadProvider
    public ResourceEncoder<GifBitmapWrapper> getEncoder() {
        return this.encoder;
    }
}
