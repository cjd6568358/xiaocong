package com.facebook.imagepipeline.decoder;

import android.graphics.Bitmap;
import com.facebook.common.internal.Closeables;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.GifFormatChecker;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageDecoder {
    private final AnimatedImageFactory mAnimatedImageFactory;
    private final Bitmap.Config mBitmapConfig;
    private final PlatformDecoder mPlatformDecoder;

    public ImageDecoder(AnimatedImageFactory animatedImageFactory, PlatformDecoder platformDecoder, Bitmap.Config bitmapConfig) {
        this.mAnimatedImageFactory = animatedImageFactory;
        this.mBitmapConfig = bitmapConfig;
        this.mPlatformDecoder = platformDecoder;
    }

    public CloseableImage decodeImage(EncodedImage encodedImage, int length, QualityInfo qualityInfo, ImageDecodeOptions options) {
        ImageFormat imageFormat = encodedImage.getImageFormat();
        if (imageFormat == null || imageFormat == ImageFormat.UNKNOWN) {
            imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(encodedImage.getInputStream());
        }
        switch (imageFormat) {
            case UNKNOWN:
                throw new IllegalArgumentException("unknown image format");
            case JPEG:
                return decodeJpeg(encodedImage, length, qualityInfo);
            case GIF:
                return decodeGif(encodedImage, options);
            case WEBP_ANIMATED:
                return decodeAnimatedWebp(encodedImage, options);
            default:
                return decodeStaticImage(encodedImage);
        }
    }

    public CloseableImage decodeGif(EncodedImage encodedImage, ImageDecodeOptions options) {
        CloseableImage closeableImageDecodeStaticImage;
        InputStream is = encodedImage.getInputStream();
        if (is == null) {
            return null;
        }
        try {
            if (!options.forceStaticImage && GifFormatChecker.isAnimated(is)) {
                closeableImageDecodeStaticImage = this.mAnimatedImageFactory.decodeGif(encodedImage, options, this.mBitmapConfig);
            } else {
                closeableImageDecodeStaticImage = decodeStaticImage(encodedImage);
            }
            return closeableImageDecodeStaticImage;
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    public CloseableStaticBitmap decodeStaticImage(EncodedImage encodedImage) {
        CloseableReference<Bitmap> bitmapReference = this.mPlatformDecoder.decodeFromEncodedImage(encodedImage, this.mBitmapConfig);
        try {
            return new CloseableStaticBitmap(bitmapReference, ImmutableQualityInfo.FULL_QUALITY, encodedImage.getRotationAngle());
        } finally {
            bitmapReference.close();
        }
    }

    public CloseableStaticBitmap decodeJpeg(EncodedImage encodedImage, int length, QualityInfo qualityInfo) {
        CloseableReference<Bitmap> bitmapReference = this.mPlatformDecoder.decodeJPEGFromEncodedImage(encodedImage, this.mBitmapConfig, length);
        try {
            return new CloseableStaticBitmap(bitmapReference, qualityInfo, encodedImage.getRotationAngle());
        } finally {
            bitmapReference.close();
        }
    }

    public CloseableImage decodeAnimatedWebp(EncodedImage encodedImage, ImageDecodeOptions options) {
        return this.mAnimatedImageFactory.decodeWebP(encodedImage, options, this.mBitmapConfig);
    }
}
