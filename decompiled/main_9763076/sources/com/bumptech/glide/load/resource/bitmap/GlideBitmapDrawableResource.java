package com.bumptech.glide.load.resource.bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GlideBitmapDrawableResource extends DrawableResource<GlideBitmapDrawable> {
    private final BitmapPool bitmapPool;

    public GlideBitmapDrawableResource(GlideBitmapDrawable drawable, BitmapPool bitmapPool) {
        super(drawable);
        this.bitmapPool = bitmapPool;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return Util.getBitmapByteSize(((GlideBitmapDrawable) this.drawable).getBitmap());
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
        this.bitmapPool.put(((GlideBitmapDrawable) this.drawable).getBitmap());
    }
}
