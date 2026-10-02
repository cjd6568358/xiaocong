package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    private static final boolean USE_OFFSET_API;
    static final Handler sHandler;
    private final AccessibilityManager mAccessibilityManager;
    private List<BaseCallback<B>> mCallbacks;
    private final ContentViewCallback mContentViewCallback;
    final SnackbarManager.Callback mManagerCallback;
    private final ViewGroup mTargetParent;
    final SnackbarBaseLayout mView;

    public interface ContentViewCallback {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    public static abstract class BaseCallback<B> {
        public void onDismissed(B transientBottomBar, int event) {
        }

        public void onShown(B transientBottomBar) {
        }
    }

    static {
        USE_OFFSET_API = Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19;
        sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: android.support.design.widget.BaseTransientBottomBar.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        ((BaseTransientBottomBar) message.obj).showView();
                        return true;
                    case 1:
                        ((BaseTransientBottomBar) message.obj).hideView(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    void dispatchDismiss(int event) {
        SnackbarManager.getInstance().dismiss(this.mManagerCallback, event);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback);
    }

    final void showView() {
        if (this.mView.getParent() == null) {
            ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
                BaseTransientBottomBar<B>.Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(0);
                behavior.setListener(new SwipeDismissBehavior.OnDismissListener() { // from class: android.support.design.widget.BaseTransientBottomBar.4
                    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
                    public void onDismiss(View view) {
                        view.setVisibility(8);
                        BaseTransientBottomBar.this.dispatchDismiss(0);
                    }

                    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case 0:
                                SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
                                break;
                            case 1:
                            case 2:
                                SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                                break;
                        }
                    }
                });
                clp.setBehavior(behavior);
                clp.insetEdge = 80;
            }
            this.mTargetParent.addView(this.mView);
        }
        this.mView.setOnAttachStateChangeListener(new OnAttachStateChangeListener() { // from class: android.support.design.widget.BaseTransientBottomBar.5
            @Override // android.support.design.widget.BaseTransientBottomBar.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
            }

            @Override // android.support.design.widget.BaseTransientBottomBar.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
                if (BaseTransientBottomBar.this.isShownOrQueued()) {
                    BaseTransientBottomBar.sHandler.post(new Runnable() { // from class: android.support.design.widget.BaseTransientBottomBar.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            BaseTransientBottomBar.this.onViewHidden(3);
                        }
                    });
                }
            }
        });
        if (ViewCompat.isLaidOut(this.mView)) {
            if (shouldAnimate()) {
                animateViewIn();
                return;
            } else {
                onViewShown();
                return;
            }
        }
        this.mView.setOnLayoutChangeListener(new OnLayoutChangeListener() { // from class: android.support.design.widget.BaseTransientBottomBar.6
            @Override // android.support.design.widget.BaseTransientBottomBar.OnLayoutChangeListener
            public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                BaseTransientBottomBar.this.mView.setOnLayoutChangeListener(null);
                if (BaseTransientBottomBar.this.shouldAnimate()) {
                    BaseTransientBottomBar.this.animateViewIn();
                } else {
                    BaseTransientBottomBar.this.onViewShown();
                }
            }
        });
    }

    void animateViewIn() {
        if (Build.VERSION.SDK_INT >= 12) {
            final int viewHeight = this.mView.getHeight();
            if (USE_OFFSET_API) {
                ViewCompat.offsetTopAndBottom(this.mView, viewHeight);
            } else {
                this.mView.setTranslationY(viewHeight);
            }
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(viewHeight, 0);
            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            animator.setDuration(250L);
            animator.addListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.BaseTransientBottomBar.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    BaseTransientBottomBar.this.mContentViewCallback.animateContentIn(70, 180);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    BaseTransientBottomBar.this.onViewShown();
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.support.design.widget.BaseTransientBottomBar.8
                private int mPreviousAnimatedIntValue;

                {
                    this.mPreviousAnimatedIntValue = viewHeight;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animator2) {
                    int currentAnimatedIntValue = ((Integer) animator2.getAnimatedValue()).intValue();
                    if (BaseTransientBottomBar.USE_OFFSET_API) {
                        ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, currentAnimatedIntValue - this.mPreviousAnimatedIntValue);
                    } else {
                        BaseTransientBottomBar.this.mView.setTranslationY(currentAnimatedIntValue);
                    }
                    this.mPreviousAnimatedIntValue = currentAnimatedIntValue;
                }
            });
            animator.start();
            return;
        }
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_in);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250L);
        anim.setAnimationListener(new Animation.AnimationListener() { // from class: android.support.design.widget.BaseTransientBottomBar.9
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                BaseTransientBottomBar.this.onViewShown();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mView.startAnimation(anim);
    }

    private void animateViewOut(final int event) {
        if (Build.VERSION.SDK_INT >= 12) {
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(0, this.mView.getHeight());
            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            animator.setDuration(250L);
            animator.addListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.BaseTransientBottomBar.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    BaseTransientBottomBar.this.mContentViewCallback.animateContentOut(0, 180);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    BaseTransientBottomBar.this.onViewHidden(event);
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.support.design.widget.BaseTransientBottomBar.11
                private int mPreviousAnimatedIntValue = 0;

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animator2) {
                    int currentAnimatedIntValue = ((Integer) animator2.getAnimatedValue()).intValue();
                    if (BaseTransientBottomBar.USE_OFFSET_API) {
                        ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, currentAnimatedIntValue - this.mPreviousAnimatedIntValue);
                    } else {
                        BaseTransientBottomBar.this.mView.setTranslationY(currentAnimatedIntValue);
                    }
                    this.mPreviousAnimatedIntValue = currentAnimatedIntValue;
                }
            });
            animator.start();
            return;
        }
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_out);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250L);
        anim.setAnimationListener(new Animation.AnimationListener() { // from class: android.support.design.widget.BaseTransientBottomBar.12
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                BaseTransientBottomBar.this.onViewHidden(event);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mView.startAnimation(anim);
    }

    final void hideView(int event) {
        if (shouldAnimate() && this.mView.getVisibility() == 0) {
            animateViewOut(event);
        } else {
            onViewHidden(event);
        }
    }

    void onViewShown() {
        SnackbarManager.getInstance().onShown(this.mManagerCallback);
        if (this.mCallbacks != null) {
            int callbackCount = this.mCallbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                this.mCallbacks.get(i).onShown(this);
            }
        }
    }

    void onViewHidden(int event) {
        SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
        if (this.mCallbacks != null) {
            int callbackCount = this.mCallbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                this.mCallbacks.get(i).onDismissed(this, event);
            }
        }
        if (Build.VERSION.SDK_INT < 11) {
            this.mView.setVisibility(8);
        }
        ViewParent parent = this.mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.mView);
        }
    }

    boolean shouldAnimate() {
        return !this.mAccessibilityManager.isEnabled();
    }

    static class SnackbarBaseLayout extends FrameLayout {
        private OnAttachStateChangeListener mOnAttachStateChangeListener;
        private OnLayoutChangeListener mOnLayoutChangeListener;

        SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        SnackbarBaseLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            a.recycle();
            setClickable(true);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (this.mOnLayoutChangeListener != null) {
                this.mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewAttachedToWindow(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.mOnLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
            this.mOnAttachStateChangeListener = listener;
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
        Behavior() {
        }

        @Override // android.support.design.widget.SwipeDismissBehavior
        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        @Override // android.support.design.widget.SwipeDismissBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, SnackbarBaseLayout child, MotionEvent event) {
            switch (event.getActionMasked()) {
                case 0:
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                    }
                    break;
                case 1:
                case 3:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
                    break;
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}
