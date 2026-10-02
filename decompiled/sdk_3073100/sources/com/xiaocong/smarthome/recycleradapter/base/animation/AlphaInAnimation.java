package com.xiaocong.smarthome.recycleradapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import com.xiaocong.smarthome.recycleradapter.base.BaseAnimation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AlphaInAnimation implements BaseAnimation {
    private final float mFrom;

    public AlphaInAnimation() {
        this(0.0f);
    }

    public AlphaInAnimation(float from) {
        this.mFrom = from;
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.BaseAnimation
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", this.mFrom, 1.0f)};
    }
}
