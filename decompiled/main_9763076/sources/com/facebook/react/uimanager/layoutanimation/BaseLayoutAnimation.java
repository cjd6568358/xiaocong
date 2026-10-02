package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.facebook.react.uimanager.IllegalViewOperationException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class BaseLayoutAnimation extends AbstractLayoutAnimation {
    abstract boolean isReverse();

    BaseLayoutAnimation() {
    }

    @Override // com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation
    boolean isValid() {
        return this.mDurationMs > 0 && this.mAnimatedProperty != null;
    }

    @Override // com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation
    Animation createAnimationImpl(View view, int x, int y, int width, int height) {
        if (this.mAnimatedProperty != null) {
            switch (this.mAnimatedProperty) {
                case OPACITY:
                    return new OpacityAnimation(view, isReverse() ? view.getAlpha() : 0.0f, isReverse() ? 0.0f : view.getAlpha());
                case SCALE_XY:
                    float fromValue = isReverse() ? 1.0f : 0.0f;
                    float toValue = isReverse() ? 0.0f : 1.0f;
                    return new ScaleAnimation(fromValue, toValue, fromValue, toValue, 1, 0.5f, 1, 0.5f);
                default:
                    throw new IllegalViewOperationException("Missing animation for property : " + this.mAnimatedProperty);
            }
        }
        throw new IllegalViewOperationException("Missing animated property from animation config");
    }
}
