package com.facebook.react.views.toolbar;

import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.imagepipeline.image.ImageInfo;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableWithIntrinsicSize extends ForwardingDrawable implements Drawable.Callback {
    private final ImageInfo mImageInfo;

    public DrawableWithIntrinsicSize(Drawable drawable, ImageInfo imageInfo) {
        super(drawable);
        this.mImageInfo = imageInfo;
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mImageInfo.getWidth();
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mImageInfo.getHeight();
    }
}
