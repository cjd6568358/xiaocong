package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class AbstractLayoutAnimation {
    private static final Map<InterpolatorType, Interpolator> INTERPOLATOR = MapBuilder.of(InterpolatorType.LINEAR, new LinearInterpolator(), InterpolatorType.EASE_IN, new AccelerateInterpolator(), InterpolatorType.EASE_OUT, new DecelerateInterpolator(), InterpolatorType.EASE_IN_EASE_OUT, new AccelerateDecelerateInterpolator(), InterpolatorType.SPRING, new SimpleSpringInterpolator());
    protected AnimatedPropertyType mAnimatedProperty;
    private int mDelayMs;
    protected int mDurationMs;
    private Interpolator mInterpolator;

    abstract Animation createAnimationImpl(View view, int i, int i2, int i3, int i4);

    abstract boolean isValid();

    AbstractLayoutAnimation() {
    }

    public void reset() {
        this.mAnimatedProperty = null;
        this.mDurationMs = 0;
        this.mDelayMs = 0;
        this.mInterpolator = null;
    }

    public void initializeFromConfig(ReadableMap data, int globalDuration) {
        this.mAnimatedProperty = data.hasKey("property") ? AnimatedPropertyType.fromString(data.getString("property")) : null;
        if (data.hasKey("duration")) {
            globalDuration = data.getInt("duration");
        }
        this.mDurationMs = globalDuration;
        this.mDelayMs = data.hasKey("delay") ? data.getInt("delay") : 0;
        if (!data.hasKey("type")) {
            throw new IllegalArgumentException("Missing interpolation type.");
        }
        this.mInterpolator = getInterpolator(InterpolatorType.fromString(data.getString("type")));
        if (!isValid()) {
            throw new IllegalViewOperationException("Invalid layout animation : " + data);
        }
    }

    public final Animation createAnimation(View view, int x, int y, int width, int height) {
        if (!isValid()) {
            return null;
        }
        Animation animation = createAnimationImpl(view, x, y, width, height);
        if (animation != null) {
            animation.setDuration(this.mDurationMs * 1);
            animation.setStartOffset(this.mDelayMs * 1);
            animation.setInterpolator(this.mInterpolator);
            return animation;
        }
        return animation;
    }

    private static Interpolator getInterpolator(InterpolatorType type) {
        Interpolator interpolator = INTERPOLATOR.get(type);
        if (interpolator == null) {
            throw new IllegalArgumentException("Missing interpolator for type : " + type);
        }
        return interpolator;
    }
}
