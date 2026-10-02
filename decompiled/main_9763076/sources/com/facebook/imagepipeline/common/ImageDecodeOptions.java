package com.facebook.imagepipeline.common;

import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageDecodeOptions {
    private static final ImageDecodeOptions DEFAULTS = newBuilder().build();
    public final int backgroundColor;
    public final boolean decodeAllFrames;
    public final boolean decodePreviewFrame;
    public final boolean forceOldAnimationCode;
    public final boolean forceStaticImage;
    public final int minDecodeIntervalMs;
    public final boolean useLastFrameForPreview;

    public ImageDecodeOptions(ImageDecodeOptionsBuilder b) {
        this.minDecodeIntervalMs = b.getMinDecodeIntervalMs();
        this.backgroundColor = b.getBackgroundColor();
        this.forceOldAnimationCode = b.getForceOldAnimationCode();
        this.decodePreviewFrame = b.getDecodePreviewFrame();
        this.useLastFrameForPreview = b.getUseLastFrameForPreview();
        this.decodeAllFrames = b.getDecodeAllFrames();
        this.forceStaticImage = b.getForceStaticImage();
    }

    public static ImageDecodeOptions defaults() {
        return DEFAULTS;
    }

    public static ImageDecodeOptionsBuilder newBuilder() {
        return new ImageDecodeOptionsBuilder();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageDecodeOptions that = (ImageDecodeOptions) o;
        return this.backgroundColor == that.backgroundColor && this.forceOldAnimationCode == that.forceOldAnimationCode && this.decodePreviewFrame == that.decodePreviewFrame && this.useLastFrameForPreview == that.useLastFrameForPreview && this.decodeAllFrames == that.decodeAllFrames && this.forceStaticImage == that.forceStaticImage;
    }

    public int hashCode() {
        int result = this.backgroundColor;
        return (result * 31) + (this.forceOldAnimationCode ? 1 : 0);
    }

    public String toString() {
        return String.format((Locale) null, "%d-%d-%b-%b-%b-%b-%b", Integer.valueOf(this.minDecodeIntervalMs), Integer.valueOf(this.backgroundColor), Boolean.valueOf(this.forceOldAnimationCode), Boolean.valueOf(this.decodePreviewFrame), Boolean.valueOf(this.useLastFrameForPreview), Boolean.valueOf(this.decodeAllFrames), Boolean.valueOf(this.forceStaticImage));
    }
}
