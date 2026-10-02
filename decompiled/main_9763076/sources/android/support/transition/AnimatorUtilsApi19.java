package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class AnimatorUtilsApi19 implements AnimatorUtilsImpl {
    AnimatorUtilsApi19() {
    }

    @Override // android.support.transition.AnimatorUtilsImpl
    public void addPauseListener(Animator animator, AnimatorListenerAdapter listener) {
        animator.addPauseListener(listener);
    }

    @Override // android.support.transition.AnimatorUtilsImpl
    public void pause(Animator animator) {
        animator.pause();
    }

    @Override // android.support.transition.AnimatorUtilsImpl
    public void resume(Animator animator) {
        animator.resume();
    }
}
