package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class LayoutUpdateAnimation extends AbstractLayoutAnimation {
    LayoutUpdateAnimation() {
    }

    @Override // com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation
    boolean isValid() {
        return this.mDurationMs > 0;
    }

    @Override // com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation
    Animation createAnimationImpl(View view, int x, int y, int width, int height) {
        boolean animateLocation = (view.getX() == ((float) x) && view.getY() == ((float) y)) ? false : true;
        boolean animateSize = (view.getWidth() == width && view.getHeight() == height) ? false : true;
        if (!animateLocation && !animateSize) {
            return null;
        }
        if (!animateLocation || !animateSize) {
        }
        return new PositionAndSizeAnimation(view, x, y, width, height);
    }
}
