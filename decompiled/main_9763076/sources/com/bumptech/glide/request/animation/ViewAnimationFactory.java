package com.bumptech.glide.request.animation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViewAnimationFactory<R> implements GlideAnimationFactory<R> {
    private final ViewAnimation.AnimationFactory animationFactory;
    private GlideAnimation<R> glideAnimation;

    ViewAnimationFactory(ViewAnimation.AnimationFactory animationFactory) {
        this.animationFactory = animationFactory;
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimationFactory
    public GlideAnimation<R> build(boolean isFromMemoryCache, boolean isFirstResource) {
        if (isFromMemoryCache || !isFirstResource) {
            return NoAnimation.get();
        }
        if (this.glideAnimation == null) {
            this.glideAnimation = new ViewAnimation(this.animationFactory);
        }
        return this.glideAnimation;
    }
}
