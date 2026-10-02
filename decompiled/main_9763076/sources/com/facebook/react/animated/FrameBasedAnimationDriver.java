package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class FrameBasedAnimationDriver extends AnimationDriver {
    private final double[] mFrames;
    private double mFromValue;
    private long mStartFrameTimeNanos = -1;
    private final double mToValue;

    FrameBasedAnimationDriver(ReadableMap config) {
        ReadableArray frames = config.getArray("frames");
        int numberOfFrames = frames.size();
        this.mFrames = new double[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++) {
            this.mFrames[i] = frames.getDouble(i);
        }
        this.mToValue = config.getDouble("toValue");
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void runAnimationStep(long frameTimeNanos) {
        double nextValue;
        if (this.mStartFrameTimeNanos < 0) {
            this.mStartFrameTimeNanos = frameTimeNanos;
            this.mFromValue = this.mAnimatedValue.mValue;
        }
        long timeFromStartMillis = (frameTimeNanos - this.mStartFrameTimeNanos) / 1000000;
        int frameIndex = (int) (timeFromStartMillis / 16.666666666666668d);
        if (frameIndex < 0) {
            throw new IllegalStateException("Calculated frame index should never be lower than 0");
        }
        if (!this.mHasFinished) {
            if (frameIndex >= this.mFrames.length - 1) {
                this.mHasFinished = true;
                nextValue = this.mToValue;
            } else {
                nextValue = this.mFromValue + (this.mFrames[frameIndex] * (this.mToValue - this.mFromValue));
            }
            this.mAnimatedValue.mValue = nextValue;
        }
    }
}
