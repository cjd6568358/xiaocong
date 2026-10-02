package com.facebook.react.animation;

import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class Animation {
    private View mAnimatedView;
    private final int mAnimationID;
    private AnimationListener mAnimationListener;
    private volatile boolean mCancelled;
    private volatile boolean mIsFinished;
    private final AnimationPropertyUpdater mPropertyUpdater;

    public abstract void run();

    public void setAnimationListener(AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public final void start(View view) {
        this.mAnimatedView = view;
        this.mPropertyUpdater.prepare(view);
        run();
    }

    public final void cancel() {
        if (!this.mIsFinished && !this.mCancelled) {
            this.mCancelled = true;
            if (this.mAnimationListener != null) {
                this.mAnimationListener.onCancel();
            }
        }
    }

    public int getAnimationID() {
        return this.mAnimationID;
    }
}
