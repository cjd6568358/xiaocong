package com.facebook.imagepipeline.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageDecodeOptionsBuilder {
    private boolean mDecodeAllFrames;
    private boolean mDecodePreviewFrame;
    private boolean mForceOldAnimationCode;
    private boolean mForceStaticImage;
    private boolean mUseLastFrameForPreview;
    private int mMinDecodeIntervalMs = 100;
    private int mBackgroundColor = -1;

    public int getMinDecodeIntervalMs() {
        return this.mMinDecodeIntervalMs;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public boolean getForceOldAnimationCode() {
        return this.mForceOldAnimationCode;
    }

    public boolean getDecodePreviewFrame() {
        return this.mDecodePreviewFrame;
    }

    public boolean getUseLastFrameForPreview() {
        return this.mUseLastFrameForPreview;
    }

    public boolean getDecodeAllFrames() {
        return this.mDecodeAllFrames;
    }

    public boolean getForceStaticImage() {
        return this.mForceStaticImage;
    }

    public ImageDecodeOptions build() {
        return new ImageDecodeOptions(this);
    }
}
