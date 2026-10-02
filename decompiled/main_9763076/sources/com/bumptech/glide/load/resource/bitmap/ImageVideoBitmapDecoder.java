package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageVideoBitmapDecoder implements ResourceDecoder<ImageVideoWrapper, Bitmap> {
    private final ResourceDecoder<ParcelFileDescriptor, Bitmap> fileDescriptorDecoder;
    private final ResourceDecoder<InputStream, Bitmap> streamDecoder;

    public ImageVideoBitmapDecoder(ResourceDecoder<InputStream, Bitmap> streamDecoder, ResourceDecoder<ParcelFileDescriptor, Bitmap> fileDescriptorDecoder) {
        this.streamDecoder = streamDecoder;
        this.fileDescriptorDecoder = fileDescriptorDecoder;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<Bitmap> decode(ImageVideoWrapper source, int width, int height) throws IOException {
        ParcelFileDescriptor fileDescriptor;
        Resource<Bitmap> result = null;
        InputStream is = source.getStream();
        if (is != null) {
            try {
                result = this.streamDecoder.decode(is, width, height);
            } catch (IOException e) {
                if (Log.isLoggable("ImageVideoDecoder", 2)) {
                    Log.v("ImageVideoDecoder", "Failed to load image from stream, trying FileDescriptor", e);
                }
            }
        }
        if (result == null && (fileDescriptor = source.getFileDescriptor()) != null) {
            return this.fileDescriptorDecoder.decode(fileDescriptor, width, height);
        }
        return result;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return "ImageVideoBitmapDecoder.com.bumptech.glide.load.resource.bitmap";
    }
}
