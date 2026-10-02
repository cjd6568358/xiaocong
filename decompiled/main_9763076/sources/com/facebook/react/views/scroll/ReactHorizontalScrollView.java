package com.facebook.react.views.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactHorizontalScrollView extends HorizontalScrollView implements ReactClippingViewGroup {
    private boolean mActivelyScrolling;
    private Rect mClippingRect;
    private boolean mDragging;
    private Drawable mEndBackground;
    private int mEndFillColor;
    private FpsListener mFpsListener;
    private final OnScrollDispatchHelper mOnScrollDispatchHelper;
    private boolean mPagingEnabled;
    private Runnable mPostTouchRunnable;
    private boolean mRemoveClippedSubviews;
    private boolean mScrollEnabled;
    private String mScrollPerfTag;
    private boolean mSendMomentumEvents;

    public ReactHorizontalScrollView(Context context) {
        this(context, null);
    }

    public ReactHorizontalScrollView(Context context, FpsListener fpsListener) {
        super(context);
        this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
        this.mPagingEnabled = false;
        this.mScrollEnabled = true;
        this.mFpsListener = null;
        this.mEndFillColor = 0;
        this.mFpsListener = fpsListener;
    }

    public void setScrollPerfTag(String scrollPerfTag) {
        this.mScrollPerfTag = scrollPerfTag;
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

    public void setSendMomentumEvents(boolean sendMomentumEvents) {
        this.mSendMomentumEvents = sendMomentumEvents;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.mPagingEnabled = pagingEnabled;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        scrollTo(getScrollX(), getScrollY());
    }

    @Override // android.view.View
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (this.mOnScrollDispatchHelper.onScrollChanged(x, y)) {
            if (this.mRemoveClippedSubviews) {
                updateClippingRect();
            }
            this.mActivelyScrolling = true;
            ReactScrollViewHelper.emitScrollEvent(this);
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
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

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mScrollEnabled) {
            return false;
        }
        int action = ev.getAction() & 255;
        if (action == 1 && this.mDragging) {
            ReactScrollViewHelper.emitScrollEndDragEvent(this);
            this.mDragging = false;
            handlePostTouchScrolling();
        }
        return super.onTouchEvent(ev);
    }

    @Override // android.widget.HorizontalScrollView
    public void fling(int velocityX) {
        if (this.mPagingEnabled) {
            smoothScrollToPage(velocityX);
        } else {
            super.fling(velocityX);
        }
        handlePostTouchScrolling();
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
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

    public void setEndFillColor(int color) {
        if (color != this.mEndFillColor) {
            this.mEndFillColor = color;
            this.mEndBackground = new ColorDrawable(this.mEndFillColor);
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

    @Override // android.widget.HorizontalScrollView, android.view.View
    public void draw(Canvas canvas) {
        if (this.mEndFillColor != 0) {
            View content = getChildAt(0);
            if (this.mEndBackground != null && content != null && content.getRight() < getWidth()) {
                this.mEndBackground.setBounds(content.getRight(), 0, getWidth(), getHeight());
                this.mEndBackground.draw(canvas);
            }
        }
        super.draw(canvas);
    }

    @TargetApi(16)
    private void handlePostTouchScrolling() {
        if ((this.mSendMomentumEvents || this.mPagingEnabled || isScrollPerfLoggingEnabled()) && this.mPostTouchRunnable == null) {
            if (this.mSendMomentumEvents) {
                ReactScrollViewHelper.emitScrollMomentumBeginEvent(this);
            }
            this.mActivelyScrolling = false;
            this.mPostTouchRunnable = new Runnable() { // from class: com.facebook.react.views.scroll.ReactHorizontalScrollView.1
                private boolean mSnappingToPage = false;

                @Override // java.lang.Runnable
                public void run() {
                    if (ReactHorizontalScrollView.this.mActivelyScrolling) {
                        ReactHorizontalScrollView.this.mActivelyScrolling = false;
                        ReactHorizontalScrollView.this.postOnAnimationDelayed(this, 20L);
                        return;
                    }
                    boolean doneWithAllScrolling = true;
                    if (ReactHorizontalScrollView.this.mPagingEnabled && !this.mSnappingToPage) {
                        this.mSnappingToPage = true;
                        ReactHorizontalScrollView.this.smoothScrollToPage(0);
                        doneWithAllScrolling = false;
                    }
                    if (doneWithAllScrolling) {
                        if (ReactHorizontalScrollView.this.mSendMomentumEvents) {
                            ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactHorizontalScrollView.this);
                        }
                        ReactHorizontalScrollView.this.mPostTouchRunnable = null;
                        ReactHorizontalScrollView.this.disableFpsListener();
                        return;
                    }
                    ReactHorizontalScrollView.this.postOnAnimationDelayed(this, 20L);
                }
            };
            postOnAnimationDelayed(this.mPostTouchRunnable, 20L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothScrollToPage(int velocity) {
        int width = getWidth();
        int currentX = getScrollX();
        int predictedX = currentX + velocity;
        int page = currentX / width;
        if (predictedX > (page * width) + (width / 2)) {
            page++;
        }
        smoothScrollTo(page * width, getScrollY());
    }
}
