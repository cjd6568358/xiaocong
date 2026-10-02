package android.support.design.widget;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private int mActivePointerId;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    OverScroller mScroller;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public HeaderBehavior() {
        this.mActivePointerId = -1;
        this.mTouchSlop = -1;
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mActivePointerId = -1;
        this.mTouchSlop = -1;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        int pointerIndex;
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        int action = ev.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (ev.getActionMasked()) {
            case 0:
                this.mIsBeingDragged = false;
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                }
                break;
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                break;
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != -1 && (pointerIndex = ev.findPointerIndex(activePointerId)) != -1) {
                    int y2 = (int) ev.getY(pointerIndex);
                    int yDiff = Math.abs(y2 - this.mLastMotionY);
                    if (yDiff > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        this.mLastMotionY = y2;
                    }
                }
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return this.mIsBeingDragged;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:9:0x001d  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        switch (ev.getActionMasked()) {
            case 0:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, x, y) && canDragView(child)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.addMovement(ev);
                    }
                    return true;
                }
                return false;
            case 1:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float yvel = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                    fling(parent, child, -getScrollRangeForDragFling(child), 0, yvel);
                }
                break;
            case 2:
                int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
                if (activePointerIndex == -1) {
                    return false;
                }
                int y2 = (int) ev.getY(activePointerIndex);
                int dy = this.mLastMotionY - y2;
                if (!this.mIsBeingDragged && Math.abs(dy) > this.mTouchSlop) {
                    this.mIsBeingDragged = true;
                    dy = dy > 0 ? dy - this.mTouchSlop : dy + this.mTouchSlop;
                }
                if (this.mIsBeingDragged) {
                    this.mLastMotionY = y2;
                    scroll(parent, child, dy, getMaxDragOffset(child), 0);
                }
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                }
                return true;
            case 3:
                break;
            default:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                }
                return true;
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return true;
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset, int minOffset, int maxOffset) {
        int newOffset2;
        int curOffset = getTopAndBottomOffset();
        if (minOffset == 0 || curOffset < minOffset || curOffset > maxOffset || curOffset == (newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset))) {
            return 0;
        }
        setTopAndBottomOffset(newOffset2);
        int consumed = curOffset - newOffset2;
        return consumed;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout, header, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V layout, int minOffset, int maxOffset, float velocityY) {
        if (this.mFlingRunnable != null) {
            layout.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = new OverScroller(layout.getContext());
        }
        this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingRunnable = new FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, this.mFlingRunnable);
            return true;
        }
        onFlingFinished(coordinatorLayout, layout);
        return false;
    }

    void onFlingFinished(CoordinatorLayout parent, V layout) {
    }

    boolean canDragView(V view) {
        return false;
    }

    int getMaxDragOffset(V view) {
        return -view.getHeight();
    }

    int getScrollRangeForDragFling(V view) {
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private class FlingRunnable implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;

        FlingRunnable(CoordinatorLayout parent, V layout) {
            this.mParent = parent;
            this.mLayout = layout;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mLayout != null && HeaderBehavior.this.mScroller != null) {
                if (HeaderBehavior.this.mScroller.computeScrollOffset()) {
                    HeaderBehavior.this.setHeaderTopBottomOffset(this.mParent, this.mLayout, HeaderBehavior.this.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                } else {
                    HeaderBehavior.this.onFlingFinished(this.mParent, this.mLayout);
                }
            }
        }
    }
}
