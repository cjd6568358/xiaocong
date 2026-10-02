package com.bumptech.glide.request.animation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableCrossFadeViewAnimation<T extends Drawable> implements GlideAnimation<T> {
    private final GlideAnimation<T> defaultAnimation;
    private final int duration;

    public DrawableCrossFadeViewAnimation(GlideAnimation<T> defaultAnimation, int duration) {
        this.defaultAnimation = defaultAnimation;
        this.duration = duration;
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimation
    public boolean animate(T current, GlideAnimation.ViewAdapter adapter) {
        Drawable previous = adapter.getCurrentDrawable();
        if (previous != null) {
            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{previous, current});
            transitionDrawable.setCrossFadeEnabled(true);
            transitionDrawable.startTransition(this.duration);
            adapter.setDrawable(transitionDrawable);
            return true;
        }
        this.defaultAnimation.animate(current, adapter);
        return false;
    }
}
