package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileDescriptorBitmapDecoder implements ResourceDecoder<ParcelFileDescriptor, Bitmap> {
    private final VideoBitmapDecoder bitmapDecoder;
    private final BitmapPool bitmapPool;
    private DecodeFormat decodeFormat;

    public FileDescriptorBitmapDecoder(BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this(new VideoBitmapDecoder(), bitmapPool, decodeFormat);
    }

    public FileDescriptorBitmapDecoder(VideoBitmapDecoder bitmapDecoder, BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this.bitmapDecoder = bitmapDecoder;
        this.bitmapPool = bitmapPool;
        this.decodeFormat = decodeFormat;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<Bitmap> decode(ParcelFileDescriptor source, int width, int height) throws IOException {
        Bitmap bitmap = this.bitmapDecoder.decode(source, this.bitmapPool, width, height, this.decodeFormat);
        return BitmapResource.obtain(bitmap, this.bitmapPool);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return "FileDescriptorBitmapDecoder.com.bumptech.glide.load.data.bitmap";
    }
}
