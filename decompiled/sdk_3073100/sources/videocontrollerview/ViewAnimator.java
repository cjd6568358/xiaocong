package videocontrollerview;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ViewAnimator {
    View view;

    public static class Listeners {

        public interface Cancel {
            void onCancel();
        }

        public interface End {
            void onEnd();
        }

        public interface Size {
            void onSize(ViewAnimator viewAnimator);
        }

        public interface Start {
            void onStart();
        }
    }

    public ViewAnimator(View view) {
        this.view = view;
    }

    public static ViewAnimator putOn(View view) {
        return new ViewAnimator(view);
    }

    public void waitForSize(final Listeners.Size sizeListener) {
        this.view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: videocontrollerview.ViewAnimator.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (ViewAnimator.this.view != null) {
                    ViewAnimator.this.view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (sizeListener != null) {
                        sizeListener.onSize(ViewAnimator.this);
                        return false;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    public ViewAnimator translationY(float translation) {
        if (this.view != null) {
            ViewCompat.setTranslationY(this.view, translation);
        }
        return this;
    }

    public AnimatorExecutor animate() {
        return new AnimatorExecutor(this);
    }

    static class AnimatorListener implements ViewPropertyAnimatorListener {
        AnimatorExecutor animatorExecutor;

        public AnimatorListener(AnimatorExecutor animatorExecutor) {
            this.animatorExecutor = animatorExecutor;
        }

        public void onAnimationStart(View view) {
            AnimatorExecutor animatorExecutor = this.animatorExecutor;
            if (animatorExecutor != null && animatorExecutor.startListener != null) {
                Listeners.Start startListener = animatorExecutor.startListener;
                startListener.onStart();
            }
        }

        public void onAnimationEnd(View view) {
            AnimatorExecutor animatorExecutor = this.animatorExecutor;
            if (animatorExecutor != null && animatorExecutor.endListener != null) {
                Listeners.End endListener = animatorExecutor.endListener;
                endListener.onEnd();
            }
        }

        public void onAnimationCancel(View view) {
            AnimatorExecutor animatorExecutor = this.animatorExecutor;
            if (animatorExecutor != null && animatorExecutor.cancelListener != null) {
                Listeners.Cancel cancelListener = animatorExecutor.cancelListener;
                cancelListener.onCancel();
            }
        }
    }

    public static class AnimatorExecutor {
        final ViewPropertyAnimatorCompat animator;
        Listeners.Cancel cancelListener;
        Listeners.End endListener;
        Listeners.Start startListener;
        final ViewAnimator viewAnimator;

        AnimatorExecutor(ViewAnimator viewAnimator) {
            this.animator = ViewCompat.animate(viewAnimator.view);
            this.viewAnimator = viewAnimator;
            this.animator.setListener(new AnimatorListener(this));
        }

        public AnimatorExecutor translationY(float translation) {
            this.animator.translationY(translation);
            return this;
        }

        public AnimatorExecutor translationY(float from, float to) {
            this.viewAnimator.translationY(from);
            return translationY(to);
        }

        public AnimatorExecutor duration(long duration) {
            this.animator.setDuration(duration);
            return this;
        }

        public AnimatorExecutor startDelay(long duration) {
            this.animator.setStartDelay(duration);
            return this;
        }

        public AnimatorExecutor start(Listeners.Start listener) {
            this.startListener = listener;
            return this;
        }

        public AnimatorExecutor andAnimate(View view) {
            ViewAnimator viewAnimator = new ViewAnimator(view);
            AnimatorExecutor animatorExecutor = viewAnimator.animate();
            animatorExecutor.startDelay(this.animator.getStartDelay());
            return viewAnimator.animate();
        }
    }
}
