package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LayoutAnimationController {
    private boolean mShouldAnimateLayout;
    private final AbstractLayoutAnimation mLayoutCreateAnimation = new LayoutCreateAnimation();
    private final AbstractLayoutAnimation mLayoutUpdateAnimation = new LayoutUpdateAnimation();
    private final AbstractLayoutAnimation mLayoutDeleteAnimation = new LayoutDeleteAnimation();

    public void initializeFromConfig(ReadableMap config) {
        if (config == null) {
            reset();
            return;
        }
        this.mShouldAnimateLayout = false;
        int globalDuration = config.hasKey("duration") ? config.getInt("duration") : 0;
        if (config.hasKey(LayoutAnimationType.CREATE.toString())) {
            this.mLayoutCreateAnimation.initializeFromConfig(config.getMap(LayoutAnimationType.CREATE.toString()), globalDuration);
            this.mShouldAnimateLayout = true;
        }
        if (config.hasKey(LayoutAnimationType.UPDATE.toString())) {
            this.mLayoutUpdateAnimation.initializeFromConfig(config.getMap(LayoutAnimationType.UPDATE.toString()), globalDuration);
            this.mShouldAnimateLayout = true;
        }
        if (config.hasKey(LayoutAnimationType.DELETE.toString())) {
            this.mLayoutDeleteAnimation.initializeFromConfig(config.getMap(LayoutAnimationType.DELETE.toString()), globalDuration);
            this.mShouldAnimateLayout = true;
        }
    }

    public void reset() {
        this.mLayoutCreateAnimation.reset();
        this.mLayoutUpdateAnimation.reset();
        this.mLayoutDeleteAnimation.reset();
        this.mShouldAnimateLayout = false;
    }

    public boolean shouldAnimateLayout(View viewToAnimate) {
        return this.mShouldAnimateLayout && viewToAnimate.getParent() != null;
    }

    public void applyLayoutUpdate(View view, int x, int y, int width, int height) {
        UiThreadUtil.assertOnUiThread();
        AbstractLayoutAnimation layoutAnimation = (view.getWidth() == 0 || view.getHeight() == 0) ? this.mLayoutCreateAnimation : this.mLayoutUpdateAnimation;
        Animation animation = layoutAnimation.createAnimation(view, x, y, width, height);
        if (animation == null || !(animation instanceof HandleLayout)) {
            view.layout(x, y, x + width, y + height);
        }
        if (animation != null) {
            view.startAnimation(animation);
        }
    }

    public void deleteView(View view, final LayoutAnimationListener listener) {
        UiThreadUtil.assertOnUiThread();
        AbstractLayoutAnimation layoutAnimation = this.mLayoutDeleteAnimation;
        Animation animation = layoutAnimation.createAnimation(view, view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
        if (animation != null) {
            disableUserInteractions(view);
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.facebook.react.uimanager.layoutanimation.LayoutAnimationController.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation anim) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation anim) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation anim) {
                    listener.onAnimationEnd();
                }
            });
            view.startAnimation(animation);
            return;
        }
        listener.onAnimationEnd();
    }

    private void disableUserInteractions(View view) {
        view.setClickable(false);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                disableUserInteractions(viewGroup.getChildAt(i));
            }
        }
    }
}
