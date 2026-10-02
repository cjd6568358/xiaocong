package com.bumptech.glide.request.animation;

import android.view.View;
import android.view.animation.Animation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViewAnimation<R> implements GlideAnimation<R> {
    private final AnimationFactory animationFactory;

    interface AnimationFactory {
        Animation build();
    }

    ViewAnimation(AnimationFactory animationFactory) {
        this.animationFactory = animationFactory;
    }

    @Override // com.bumptech.glide.request.animation.GlideAnimation
    public boolean animate(R current, GlideAnimation.ViewAdapter adapter) {
        View view = adapter.getView();
        if (view != null) {
            view.clearAnimation();
            Animation animation = this.animationFactory.build();
            view.startAnimation(animation);
            return false;
        }
        return false;
    }
}
