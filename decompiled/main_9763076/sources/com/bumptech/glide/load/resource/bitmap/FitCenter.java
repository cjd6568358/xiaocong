package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FitCenter extends BitmapTransformation {
    public FitCenter(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return TransformationUtils.fitCenter(toTransform, pool, outWidth, outHeight);
    }

    @Override // com.bumptech.glide.load.Transformation
    public String getId() {
        return "FitCenter.com.bumptech.glide.load.resource.bitmap";
    }
}
