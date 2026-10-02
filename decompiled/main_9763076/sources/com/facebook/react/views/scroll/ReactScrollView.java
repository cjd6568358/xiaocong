package com.facebook.react.views.scroll;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactScrollView extends ScrollView implements View.OnLayoutChangeListener, ViewGroup.OnHierarchyChangeListener, ReactClippingViewGroup {
    private static Field sScrollerField;
    private static boolean sTriedToGetScrollerField = false;
    private Rect mClippingRect;
    private View mContentView;
    private boolean mDoneFlinging;
    private boolean mDragging;
    private Drawable mEndBackground;
    private int mEndFillColor;
    private boolean mFlinging;
    private FpsListener mFpsListener;
    private final OnScrollDispatchHelper mOnScrollDispatchHelper;
    private boolean mRemoveClippedSubviews;
    private boolean mScrollEnabled;
    private String mScrollPerfTag;
    private final OverScroller mScroller;
    private boolean mSendMomentumEvents;

    public ReactScrollView(ReactContext context, FpsListener fpsListener) {
        super(context);
        this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
        this.mScrollEnabled = true;
        this.mFpsListener = null;
        this.mEndFillColor = 0;
        this.mFpsListener = fpsListener;
        if (!sTriedToGetScrollerField) {
            sTriedToGetScrollerField = true;
            try {
                sScrollerField = ScrollView.class.getDeclaredField("mScroller");
                sScrollerField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.w("React", "Failed to get mScroller field for ScrollView! This app will exhibit the bounce-back scrolling bug :(");
            }
        }
        if (sScrollerField != null) {
            try {
                Object scroller = sScrollerField.get(this);
                if (scroller instanceof OverScroller) {
                    this.mScroller = (OverScroller) scroller;
                } else {
                    Log.w("React", "Failed to cast mScroller field in ScrollView (probably due to OEM changes to AOSP)! This app will exhibit the bounce-back scrolling bug :(");
                    this.mScroller = null;
                }
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Failed to get mScroller from ScrollView!", e2);
            }
        } else {
            this.mScroller = null;
        }
        setOnHierarchyChangeListener(this);
        setScrollBarStyle(33554432);
    }

    public void setSendMomentumEvents(boolean sendMomentumEvents) {
        this.mSendMomentumEvents = sendMomentumEvents;
    }

    public void setScrollPerfTag(String scrollPerfTag) {
        this.mScrollPerfTag = scrollPerfTag;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        scrollTo(getScrollX(), getScrollY());
    }

    @Override // android.widget.ScrollView, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    @Override // android.view.View
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (this.mOnScrollDispatchHelper.onScrollChanged(x, y)) {
            if (this.mRemoveClippedSubviews) {
                updateClippingRect();
            }
            if (this.mFlinging) {
                this.mDoneFlinging = false;
            }
            ReactScrollViewHelper.emitScrollEvent(this);
        }
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mScrollEnabled || !super.onInterceptTouchEvent(ev)) {
            return false;
        }
        NativeGestureUtil.notifyNativeGestureStarted(this, ev);
        ReactScrollViewHelper.emitScrollBeginDragEvent(this);
        this.mDragging = true;
        enableFpsListener();
        return true;
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mScrollEnabled) {
            return false;
        }
        int action = ev.getAction() & 255;
        if (action == 1 && this.mDragging) {
            ReactScrollViewHelper.emitScrollEndDragEvent(this);
            this.mDragging = false;
            disableFpsListener();
        }
        return super.onTouchEvent(ev);
    }

    public void setRemoveClippedSubviews(boolean removeClippedSubviews) {
        if (removeClippedSubviews && this.mClippingRect == null) {
            this.mClippingRect = new Rect();
        }
        this.mRemoveClippedSubviews = removeClippedSubviews;
        updateClippingRect();
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public boolean getRemoveClippedSubviews() {
        return this.mRemoveClippedSubviews;
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void updateClippingRect() {
        if (this.mRemoveClippedSubviews) {
            Assertions.assertNotNull(this.mClippingRect);
            ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
            KeyEvent.Callback childAt = getChildAt(0);
            if (childAt instanceof ReactClippingViewGroup) {
                ((ReactClippingViewGroup) childAt).updateClippingRect();
            }
        }
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void getClippingRect(Rect outClippingRect) {
        outClippingRect.set((Rect) Assertions.assertNotNull(this.mClippingRect));
    }

    @Override // android.widget.ScrollView
    public void fling(int velocityY) {
        if (this.mScroller != null) {
            int scrollWindowHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
            this.mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0, Integer.MAX_VALUE, 0, scrollWindowHeight / 2);
            postInvalidateOnAnimation();
        } else {
            super.fling(velocityY);
        }
        if (this.mSendMomentumEvents || isScrollPerfLoggingEnabled()) {
            this.mFlinging = true;
            enableFpsListener();
            ReactScrollViewHelper.emitScrollMomentumBeginEvent(this);
            Runnable r = new Runnable() { // from class: com.facebook.react.views.scroll.ReactScrollView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ReactScrollView.this.mDoneFlinging) {
                        ReactScrollView.this.mFlinging = false;
                        ReactScrollView.this.disableFpsListener();
                        ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactScrollView.this);
                    } else {
                        ReactScrollView.this.mDoneFlinging = true;
                        ReactScrollView.this.postOnAnimationDelayed(this, 20L);
                    }
                }
            };
            postOnAnimationDelayed(r, 20L);
        }
    }

    private void enableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.enable(this.mScrollPerfTag);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.disable(this.mScrollPerfTag);
        }
    }

    private boolean isScrollPerfLoggingEnabled() {
        return (this.mFpsListener == null || this.mScrollPerfTag == null || this.mScrollPerfTag.isEmpty()) ? false : true;
    }

    private int getMaxScrollY() {
        int contentHeight = this.mContentView.getHeight();
        int viewportHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
        return Math.max(0, contentHeight - viewportHeight);
    }

    @Override // android.widget.ScrollView, android.view.View
    public void draw(Canvas canvas) {
        if (this.mEndFillColor != 0) {
            View content = getChildAt(0);
            if (this.mEndBackground != null && content != null && content.getBottom() < getHeight()) {
                this.mEndBackground.setBounds(0, content.getBottom(), getWidth(), getHeight());
                this.mEndBackground.draw(canvas);
            }
        }
        super.draw(canvas);
    }

    public void setEndFillColor(int color) {
        if (color != this.mEndFillColor) {
            this.mEndFillColor = color;
            this.mEndBackground = new ColorDrawable(this.mEndFillColor);
        }
    }

    @Override // android.widget.ScrollView, android.view.View
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        int scrollRange;
        if (this.mScroller != null && !this.mScroller.isFinished() && this.mScroller.getCurrY() != this.mScroller.getFinalY() && scrollY >= (scrollRange = getMaxScrollY())) {
            this.mScroller.abortAnimation();
            scrollY = scrollRange;
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public void onChildViewAdded(View parent, View child) {
        this.mContentView = child;
        this.mContentView.addOnLayoutChangeListener(this);
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public void onChildViewRemoved(View parent, View child) {
        this.mContentView.removeOnLayoutChangeListener(this);
        this.mContentView = null;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (this.mContentView != null) {
            int currentScrollY = getScrollY();
            int maxScrollY = getMaxScrollY();
            if (currentScrollY > maxScrollY) {
                scrollTo(getScrollX(), maxScrollY);
            }
        }
    }
}
