package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class SpringAnimation extends AnimationDriver {
    private final PhysicsState mCurrentState;
    private double mDisplacementFromRestThreshold;
    private double mEndValue;
    private long mLastTime;
    private boolean mOvershootClampingEnabled;
    private final PhysicsState mPreviousState;
    private double mRestSpeedThreshold;
    private double mSpringFriction;
    private boolean mSpringStarted;
    private double mSpringTension;
    private double mStartValue;
    private final PhysicsState mTempState;
    private double mTimeAccumulator = 0.0d;

    private static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    SpringAnimation(ReadableMap config) {
        this.mCurrentState = new PhysicsState();
        this.mPreviousState = new PhysicsState();
        this.mTempState = new PhysicsState();
        this.mSpringFriction = config.getDouble("friction");
        this.mSpringTension = config.getDouble("tension");
        this.mCurrentState.velocity = config.getDouble("initialVelocity");
        this.mEndValue = config.getDouble("toValue");
        this.mRestSpeedThreshold = config.getDouble("restSpeedThreshold");
        this.mDisplacementFromRestThreshold = config.getDouble("restDisplacementThreshold");
        this.mOvershootClampingEnabled = config.getBoolean("overshootClamping");
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void runAnimationStep(long frameTimeNanos) {
        long frameTimeMillis = frameTimeNanos / 1000000;
        if (!this.mSpringStarted) {
            PhysicsState physicsState = this.mCurrentState;
            double d = this.mAnimatedValue.mValue;
            physicsState.position = d;
            this.mStartValue = d;
            this.mLastTime = frameTimeMillis;
            this.mSpringStarted = true;
        }
        advance((frameTimeMillis - this.mLastTime) / 1000.0d);
        this.mLastTime = frameTimeMillis;
        this.mAnimatedValue.mValue = this.mCurrentState.position;
        this.mHasFinished = isAtRest();
    }

    private double getDisplacementDistanceForState(PhysicsState state) {
        return Math.abs(this.mEndValue - state.position);
    }

    private boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringTension == 0.0d);
    }

    private boolean isOvershooting() {
        return this.mSpringTension > 0.0d && ((this.mStartValue < this.mEndValue && this.mCurrentState.position > this.mEndValue) || (this.mStartValue > this.mEndValue && this.mCurrentState.position < this.mEndValue));
    }

    private void interpolate(double alpha) {
        this.mCurrentState.position = (this.mCurrentState.position * alpha) + (this.mPreviousState.position * (1.0d - alpha));
        this.mCurrentState.velocity = (this.mCurrentState.velocity * alpha) + (this.mPreviousState.velocity * (1.0d - alpha));
    }

    private void advance(double realDeltaTime) {
        if (!isAtRest()) {
            double adjustedDeltaTime = realDeltaTime;
            if (realDeltaTime > 0.064d) {
                adjustedDeltaTime = 0.064d;
            }
            this.mTimeAccumulator += adjustedDeltaTime;
            double tension = this.mSpringTension;
            double friction = this.mSpringFriction;
            double position = this.mCurrentState.position;
            double velocity = this.mCurrentState.velocity;
            double tempPosition = this.mTempState.position;
            double tempVelocity = this.mTempState.velocity;
            while (this.mTimeAccumulator >= 0.001d) {
                this.mTimeAccumulator -= 0.001d;
                if (this.mTimeAccumulator < 0.001d) {
                    this.mPreviousState.position = position;
                    this.mPreviousState.velocity = velocity;
                }
                double aVelocity = velocity;
                double aAcceleration = ((this.mEndValue - tempPosition) * tension) - (friction * velocity);
                double tempPosition2 = position + (0.001d * aVelocity * 0.5d);
                double tempVelocity2 = velocity + (0.001d * aAcceleration * 0.5d);
                double bAcceleration = ((this.mEndValue - tempPosition2) * tension) - (friction * tempVelocity2);
                double tempPosition3 = position + (0.001d * tempVelocity2 * 0.5d);
                double tempVelocity3 = velocity + (0.001d * bAcceleration * 0.5d);
                double cAcceleration = ((this.mEndValue - tempPosition3) * tension) - (friction * tempVelocity3);
                tempPosition = position + (0.001d * tempVelocity3);
                tempVelocity = velocity + (0.001d * cAcceleration);
                double dAcceleration = ((this.mEndValue - tempPosition) * tension) - (friction * tempVelocity);
                double dxdt = 0.16666666666666666d * ((2.0d * (tempVelocity2 + tempVelocity3)) + aVelocity + tempVelocity);
                double dvdt = 0.16666666666666666d * ((2.0d * (bAcceleration + cAcceleration)) + aAcceleration + dAcceleration);
                position += 0.001d * dxdt;
                velocity += 0.001d * dvdt;
            }
            this.mTempState.position = tempPosition;
            this.mTempState.velocity = tempVelocity;
            this.mCurrentState.position = position;
            this.mCurrentState.velocity = velocity;
            if (this.mTimeAccumulator > 0.0d) {
                interpolate(this.mTimeAccumulator / 0.001d);
            }
            if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
                if (tension > 0.0d) {
                    this.mStartValue = this.mEndValue;
                    this.mCurrentState.position = this.mEndValue;
                } else {
                    this.mEndValue = this.mCurrentState.position;
                    this.mStartValue = this.mEndValue;
                }
                this.mCurrentState.velocity = 0.0d;
            }
        }
    }
}
