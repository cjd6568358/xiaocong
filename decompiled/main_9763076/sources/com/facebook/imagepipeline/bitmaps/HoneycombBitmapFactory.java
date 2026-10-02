package com.facebook.imagepipeline.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.platform.PlatformDecoder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(11)
public class HoneycombBitmapFactory extends PlatformBitmapFactory {
    private final EmptyJpegGenerator mJpegGenerator;
    private final PlatformDecoder mPurgeableDecoder;

    public HoneycombBitmapFactory(EmptyJpegGenerator jpegGenerator, PlatformDecoder purgeableDecoder) {
        this.mJpegGenerator = jpegGenerator;
        this.mPurgeableDecoder = purgeableDecoder;
    }

    @Override // com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
    public CloseableReference<Bitmap> createBitmap(int width, int height, Bitmap.Config bitmapConfig) throws Throwable {
        CloseableReference<PooledByteBuffer> jpgRef = this.mJpegGenerator.generate((short) width, (short) height);
        try {
            EncodedImage encodedImage = new EncodedImage(jpgRef);
            encodedImage.setImageFormat(ImageFormat.JPEG);
            try {
                CloseableReference<Bitmap> bitmapRef = this.mPurgeableDecoder.decodeJPEGFromEncodedImage(encodedImage, bitmapConfig, jpgRef.get().size());
                bitmapRef.get().eraseColor(0);
                EncodedImage.closeSafely(encodedImage);
                jpgRef.close();
                return bitmapRef;
            } catch (Throwable th) {
                EncodedImage.closeSafely(encodedImage);
                throw th;
            }
        } catch (Throwable th2) {
            jpgRef.close();
            throw th2;
        }
    }
}
