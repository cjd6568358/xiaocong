package com.xiaocong.smarthome.httplib.swipeBack;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xiaocong.smarthome.httplib.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SwipeBackLayout extends FrameLayout {
    private static final int[] EDGE_FLAGS = {1, 2, 8, 11};
    private Activity mActivity;
    private int mContentLeft;
    private int mContentTop;
    private View mContentView;
    private ViewDragHelper mDragHelper;
    private int mEdgeFlag;
    private boolean mEnable;
    private boolean mInLayout;
    private List<SwipeListener> mListeners;
    private int mScrimColor;
    private float mScrimOpacity;
    private float mScrollPercent;
    private float mScrollThreshold;
    private Drawable mShadowBottom;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private Rect mTmpRect;
    private int mTrackingEdge;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeBackLayoutStyle);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mScrollThreshold = 0.3f;
        this.mEnable = true;
        this.mScrimColor = -1728053248;
        this.mTmpRect = new Rect();
        this.mDragHelper = ViewDragHelper.create(this, new ViewDragCallback(this, (1) null));
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout, defStyle, R.style.SwipeBackLayout);
        int edgeSize = a.getDimensionPixelSize(R.styleable.SwipeBackLayout_edge_size, -1);
        if (edgeSize > 0) {
            setEdgeSize(edgeSize);
        }
        int mode = EDGE_FLAGS[a.getInt(R.styleable.SwipeBackLayout_edge_flag, 0)];
        setEdgeTrackingEnabled(mode);
        int shadowLeft = a.getResourceId(R.styleable.SwipeBackLayout_shadow_left, R.drawable.shadow_left);
        int shadowRight = a.getResourceId(R.styleable.SwipeBackLayout_shadow_right, R.drawable.shadow_right);
        int shadowBottom = a.getResourceId(R.styleable.SwipeBackLayout_shadow_bottom, R.drawable.shadow_bottom);
        setShadow(shadowLeft, 1);
        setShadow(shadowRight, 2);
        setShadow(shadowBottom, 8);
        a.recycle();
        float density = getResources().getDisplayMetrics().density;
        float minVel = 400.0f * density;
        this.mDragHelper.setMinVelocity(minVel);
        this.mDragHelper.setMaxVelocity(2.0f * minVel);
    }

    private void setContentView(View view) {
        this.mContentView = view;
    }

    public void setEnableGesture(boolean enable) {
        this.mEnable = enable;
    }

    public void setEdgeTrackingEnabled(int edgeFlags) {
        this.mEdgeFlag = edgeFlags;
        this.mDragHelper.setEdgeTrackingEnabled(this.mEdgeFlag);
    }

    public void setScrimColor(int color) {
        this.mScrimColor = color;
        invalidate();
    }

    public void setEdgeSize(int size) {
        this.mDragHelper.setEdgeSize(size);
    }

    @Deprecated
    public void setSwipeListener(SwipeListener listener) {
        addSwipeListener(listener);
    }

    public void addSwipeListener(SwipeListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(listener);
    }

    public void setScrollThresHold(float threshold) {
        if (threshold >= 1.0f || threshold <= 0.0f) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        this.mScrollThreshold = threshold;
    }

    public void setShadow(Drawable shadow, int edgeFlag) {
        if ((edgeFlag & 1) != 0) {
            this.mShadowLeft = shadow;
        } else if ((edgeFlag & 2) != 0) {
            this.mShadowRight = shadow;
        } else if ((edgeFlag & 8) != 0) {
            this.mShadowBottom = shadow;
        }
        invalidate();
    }

    public void setShadow(int resId, int edgeFlag) {
        setShadow(getResources().getDrawable(resId), edgeFlag);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mEnable) {
            return false;
        }
        try {
            return this.mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mEnable) {
            return false;
        }
        this.mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mInLayout = true;
        if (this.mContentView != null) {
            this.mContentView.layout(this.mContentLeft, this.mContentTop, this.mContentLeft + this.mContentView.getMeasuredWidth(), this.mContentTop + this.mContentView.getMeasuredHeight());
        }
        this.mInLayout = false;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean drawContent = child == this.mContentView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (this.mScrimOpacity > 0.0f && drawContent && this.mDragHelper.getViewDragState() != 0) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        int baseAlpha = (this.mScrimColor & (-16777216)) >>> 24;
        int alpha = (int) (baseAlpha * this.mScrimOpacity);
        int color = (alpha << 24) | (this.mScrimColor & 16777215);
        if ((this.mTrackingEdge & 1) != 0) {
            canvas.clipRect(0, 0, child.getLeft(), getHeight());
        } else if ((this.mTrackingEdge & 2) != 0) {
            canvas.clipRect(child.getRight(), 0, getRight(), getHeight());
        } else if ((this.mTrackingEdge & 8) != 0) {
            canvas.clipRect(child.getLeft(), child.getBottom(), getRight(), getHeight());
        }
        canvas.drawColor(color);
    }

    private void drawShadow(Canvas canvas, View child) {
        Rect childRect = this.mTmpRect;
        child.getHitRect(childRect);
        if ((this.mEdgeFlag & 1) != 0) {
            this.mShadowLeft.setBounds(childRect.left - this.mShadowLeft.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
            this.mShadowLeft.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowLeft.draw(canvas);
        }
        if ((this.mEdgeFlag & 2) != 0) {
            this.mShadowRight.setBounds(childRect.right, childRect.top, childRect.right + this.mShadowRight.getIntrinsicWidth(), childRect.bottom);
            this.mShadowRight.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowRight.draw(canvas);
        }
        if ((this.mEdgeFlag & 8) != 0) {
            this.mShadowBottom.setBounds(childRect.left, childRect.bottom, childRect.right, childRect.bottom + this.mShadowBottom.getIntrinsicHeight());
            this.mShadowBottom.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowBottom.draw(canvas);
        }
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mScrimOpacity = 1.0f - this.mScrollPercent;
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
