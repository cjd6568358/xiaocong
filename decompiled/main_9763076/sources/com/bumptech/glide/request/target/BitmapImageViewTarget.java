package com.bumptech.glide.request.target;

import android.graphics.Bitmap;
import android.widget.ImageView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapImageViewTarget extends ImageViewTarget<Bitmap> {
    public BitmapImageViewTarget(ImageView view) {
        super(view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.request.target.ImageViewTarget
    public void setResource(Bitmap resource) {
        ((ImageView) this.view).setImageBitmap(resource);
    }
}
