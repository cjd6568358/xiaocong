package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class GlideDrawable extends Drawable implements Animatable {
    public abstract boolean isAnimated();

    public abstract void setLoopCount(int i);
}
