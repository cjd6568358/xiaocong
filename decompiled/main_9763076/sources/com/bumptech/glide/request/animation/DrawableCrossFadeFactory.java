package com.bumptech.glide.request.animation;

import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableCrossFadeFactory<T extends Drawable> implements GlideAnimationFactory<T> {
    private final ViewAnimationFactory<T> animationFactory;
    private final int duration;
    private DrawableCrossFadeViewAnimation<T> firstResourceAnimation;
    private DrawableCrossFadeViewAnimation<T> secondResourceAnimation;

    public DrawableCrossFadeFactory() {
        this(300);
    }

    public DrawableCrossFadeFactory(int duration) {
        this(new ViewAnimationFactory(new DefaultAnimationFactory(duration)), duration);
    }

    DrawableCrossFadeFactory(ViewAnimationFactory<T> animationFactory, int duration) {
        this.animationFactory = animationFactory;
        this.duration = duration;
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimationFactory
    public GlideAnimation<T> build(boolean isFromMemoryCache, boolean isFirstResource) {
        if (isFromMemoryCache) {
            return NoAnimation.get();
        }
        if (isFirstResource) {
            return getFirstResourceAnimation();
        }
        return getSecondResourceAnimation();
    }

    private GlideAnimation<T> getFirstResourceAnimation() {
        if (this.firstResourceAnimation == null) {
            GlideAnimation<T> defaultAnimation = this.animationFactory.build(false, true);
            this.firstResourceAnimation = new DrawableCrossFadeViewAnimation<>(defaultAnimation, this.duration);
        }
        return this.firstResourceAnimation;
    }

    private GlideAnimation<T> getSecondResourceAnimation() {
        if (this.secondResourceAnimation == null) {
            GlideAnimation<T> defaultAnimation = this.animationFactory.build(false, false);
            this.secondResourceAnimation = new DrawableCrossFadeViewAnimation<>(defaultAnimation, this.duration);
        }
        return this.secondResourceAnimation;
    }

    private static class DefaultAnimationFactory implements ViewAnimation.AnimationFactory {
        private final int duration;

        DefaultAnimationFactory(int duration) {
            this.duration = duration;
        }

        @Override // com.bumptech.glide.request.animation.ViewAnimation.AnimationFactory
        public Animation build() {
            AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(this.duration);
            return animation;
        }
    }
}
