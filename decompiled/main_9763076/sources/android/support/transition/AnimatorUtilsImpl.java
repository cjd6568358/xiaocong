package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface AnimatorUtilsImpl {
    void addPauseListener(Animator animator, AnimatorListenerAdapter animatorListenerAdapter);

    void pause(Animator animator);

    void resume(Animator animator);
}
