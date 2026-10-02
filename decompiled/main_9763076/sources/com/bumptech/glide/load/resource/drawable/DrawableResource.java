package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DrawableResource<T extends Drawable> implements Resource<T> {
    protected final T drawable;

    public DrawableResource(T drawable) {
        if (drawable == null) {
            throw new NullPointerException("Drawable must not be null!");
        }
        this.drawable = drawable;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public final T get() {
        return (T) this.drawable.getConstantState().newDrawable();
    }
}
