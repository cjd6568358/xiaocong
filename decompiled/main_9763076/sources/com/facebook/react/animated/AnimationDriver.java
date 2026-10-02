package com.facebook.react.animated;

import com.facebook.react.bridge.Callback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class AnimationDriver {
    ValueAnimatedNode mAnimatedValue;
    Callback mEndCallback;
    boolean mHasFinished = false;
    int mId;

    public abstract void runAnimationStep(long j);

    AnimationDriver() {
    }
}
