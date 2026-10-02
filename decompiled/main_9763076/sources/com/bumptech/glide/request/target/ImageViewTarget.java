package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.request.animation.GlideAnimation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ImageViewTarget<Z> extends ViewTarget<ImageView, Z> implements GlideAnimation.ViewAdapter {
    protected abstract void setResource(Z z);

    public ImageViewTarget(ImageView view) {
        super(view);
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter
    public Drawable getCurrentDrawable() {
        return ((ImageView) this.view).getDrawable();
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter
    public void setDrawable(Drawable drawable) {
        ((ImageView) this.view).setImageDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadStarted(Drawable placeholder) {
        ((ImageView) this.view).setImageDrawable(placeholder);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        ((ImageView) this.view).setImageDrawable(errorDrawable);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadCleared(Drawable placeholder) {
        ((ImageView) this.view).setImageDrawable(placeholder);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onResourceReady(Z resource, GlideAnimation<? super Z> glideAnimation) {
        if (glideAnimation == null || !glideAnimation.animate(resource, this)) {
            setResource(resource);
        }
    }
}
