package com.facebook.react.uimanager.layoutanimation;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class SimpleSpringInterpolator implements Interpolator {
    SimpleSpringInterpolator() {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        return (float) (1.0d + (Math.pow(2.0d, (-10.0f) * input) * Math.sin(((((double) (input - 0.125f)) * 3.141592653589793d) * 2.0d) / 0.5d)));
    }
}
