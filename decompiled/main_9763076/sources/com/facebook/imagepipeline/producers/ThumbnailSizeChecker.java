package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ThumbnailSizeChecker {
    public static boolean isImageBigEnough(int width, int height, ResizeOptions resizeOptions) {
        if (resizeOptions == null) {
            return ((float) getAcceptableSize(width)) >= 2048.0f && getAcceptableSize(height) >= 2048;
        }
        return getAcceptableSize(width) >= resizeOptions.width && getAcceptableSize(height) >= resizeOptions.height;
    }

    public static boolean isImageBigEnough(EncodedImage encodedImage, ResizeOptions resizeOptions) {
        if (encodedImage == null) {
            return false;
        }
        switch (encodedImage.getRotationAngle()) {
            case 90:
            case 270:
                return isImageBigEnough(encodedImage.getHeight(), encodedImage.getWidth(), resizeOptions);
            default:
                return isImageBigEnough(encodedImage.getWidth(), encodedImage.getHeight(), resizeOptions);
        }
    }

    public static int getAcceptableSize(int size) {
        return (int) (size * 1.3333334f);
    }
}
