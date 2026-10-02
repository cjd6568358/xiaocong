package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class VideoBitmapDecoder implements BitmapDecoder<ParcelFileDescriptor> {
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();
    private MediaMetadataRetrieverFactory factory;
    private int frame;

    public VideoBitmapDecoder() {
        this(DEFAULT_FACTORY, -1);
    }

    VideoBitmapDecoder(MediaMetadataRetrieverFactory factory, int frame) {
        this.factory = factory;
        this.frame = frame;
    }

    public Bitmap decode(ParcelFileDescriptor resource, BitmapPool bitmapPool, int outWidth, int outHeight, DecodeFormat decodeFormat) throws IOException {
        Bitmap result;
        MediaMetadataRetriever mediaMetadataRetriever = this.factory.build();
        mediaMetadataRetriever.setDataSource(resource.getFileDescriptor());
        if (this.frame >= 0) {
            result = mediaMetadataRetriever.getFrameAtTime(this.frame);
        } else {
            result = mediaMetadataRetriever.getFrameAtTime();
        }
        mediaMetadataRetriever.release();
        resource.close();
        return result;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapDecoder
    public String getId() {
        return "VideoBitmapDecoder.com.bumptech.glide.load.resource.bitmap";
    }

    static class MediaMetadataRetrieverFactory {
        MediaMetadataRetrieverFactory() {
        }

        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }
}
