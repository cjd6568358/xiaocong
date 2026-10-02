package com.bumptech.glide.load.resource.gif;

import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifDrawableResource extends DrawableResource<GifDrawable> {
    public GifDrawableResource(GifDrawable drawable) {
        super(drawable);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return Util.getBitmapByteSize(((GifDrawable) this.drawable).getFirstFrame()) + ((GifDrawable) this.drawable).getData().length;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
        ((GifDrawable) this.drawable).stop();
        ((GifDrawable) this.drawable).recycle();
    }
}
