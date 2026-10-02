package com.facebook.react.views.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import com.facebook.react.touch.ReactHitSlopView;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.ReactPointerEventsView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactViewGroup extends ViewGroup implements ReactHitSlopView, ReactInterceptingViewGroup, ReactClippingViewGroup, ReactPointerEventsView {
    private static final ViewGroup.LayoutParams sDefaultLayoutParam = new ViewGroup.LayoutParams(0, 0);
    private static final Rect sHelperRect = new Rect();
    private View[] mAllChildren;
    private int mAllChildrenCount;
    private ChildrenLayoutChangeListener mChildrenLayoutChangeListener;
    private Rect mClippingRect;
    private Rect mHitSlopRect;
    private boolean mNeedsOffscreenAlphaCompositing;
    private OnInterceptTouchEventListener mOnInterceptTouchEventListener;
    private PointerEvents mPointerEvents;
    private ReactViewBackgroundDrawable mReactBackgroundDrawable;
    private boolean mRemoveClippedSubviews;

    private static final class ChildrenLayoutChangeListener implements View.OnLayoutChangeListener {
        private final ReactViewGroup mParent;

        private ChildrenLayoutChangeListener(ReactViewGroup parent) {
            this.mParent = parent;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (this.mParent.getRemoveClippedSubviews()) {
                this.mParent.updateSubviewClipStatus(v);
            }
        }
    }

    public ReactViewGroup(Context context) {
        super(context);
        this.mRemoveClippedSubviews = false;
        this.mAllChildren = null;
        this.mPointerEvents = PointerEvents.AUTO;
        this.mNeedsOffscreenAlphaCompositing = false;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
    }

    @Override // android.view.View
    public void setBackgroundColor(int color) {
        if (color != 0 || this.mReactBackgroundDrawable != null) {
            getOrCreateReactViewBackground().setColor(color);
        }
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        throw new UnsupportedOperationException("This method is not supported for ReactViewGroup instances");
    }

    public void setTranslucentBackgroundDrawable(Drawable background) {
        super.setBackground(null);
        if (this.mReactBackgroundDrawable != null && background != null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.mReactBackgroundDrawable, background});
            super.setBackground(layerDrawable);
        } else if (background != null) {
            super.setBackground(background);
        }
    }

    @Override // com.facebook.react.touch.ReactInterceptingViewGroup
    public void setOnInterceptTouchEventListener(OnInterceptTouchEventListener listener) {
        this.mOnInterceptTouchEventListener = listener;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if ((this.mOnInterceptTouchEventListener != null && this.mOnInterceptTouchEventListener.onInterceptTouchEvent(this, ev)) || this.mPointerEvents == PointerEvents.NONE || this.mPointerEvents == PointerEvents.BOX_ONLY) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return (this.mPointerEvents == PointerEvents.NONE || this.mPointerEvents == PointerEvents.BOX_NONE) ? false : true;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return this.mNeedsOffscreenAlphaCompositing;
    }

    public void setNeedsOffscreenAlphaCompositing(boolean needsOffscreenAlphaCompositing) {
        this.mNeedsOffscreenAlphaCompositing = needsOffscreenAlphaCompositing;
    }

    public void setBorderWidth(int position, float width) {
        getOrCreateReactViewBackground().setBorderWidth(position, width);
    }

    public void setBorderColor(int position, float rgb, float alpha) {
        getOrCreateReactViewBackground().setBorderColor(position, rgb, alpha);
    }

    public void setBorderRadius(float borderRadius) {
        getOrCreateReactViewBackground().setRadius(borderRadius);
    }

    public void setBorderRadius(float borderRadius, int position) {
        getOrCreateReactViewBackground().setRadius(borderRadius, position);
    }

    public void setBorderStyle(String style) {
        getOrCreateReactViewBackground().setBorderStyle(style);
    }

    public void setRemoveClippedSubviews(boolean removeClippedSubviews) {
        if (removeClippedSubviews != this.mRemoveClippedSubviews) {
            this.mRemoveClippedSubviews = removeClippedSubviews;
            if (removeClippedSubviews) {
                this.mClippingRect = new Rect();
                ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
                this.mAllChildrenCount = getChildCount();
                int initialSize = Math.max(12, this.mAllChildrenCount);
                this.mAllChildren = new View[initialSize];
                this.mChildrenLayoutChangeListener = new ChildrenLayoutChangeListener();
                for (int i = 0; i < this.mAllChildrenCount; i++) {
                    View child = getChildAt(i);
                    this.mAllChildren[i] = child;
                    child.addOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
                }
                updateClippingRect();
                return;
            }
            Assertions.assertNotNull(this.mClippingRect);
            Assertions.assertNotNull(this.mAllChildren);
            Assertions.assertNotNull(this.mChildrenLayoutChangeListener);
            for (int i2 = 0; i2 < this.mAllChildrenCount; i2++) {
                this.mAllChildren[i2].removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
            }
            getDrawingRect(this.mClippingRect);
            updateClippingToRect(this.mClippingRect);
            this.mAllChildren = null;
            this.mClippingRect = null;
            this.mAllChildrenCount = 0;
            this.mChildrenLayoutChangeListener = null;
        }
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public boolean getRemoveClippedSubviews() {
        return this.mRemoveClippedSubviews;
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void getClippingRect(Rect outClippingRect) {
        outClippingRect.set(this.mClippingRect);
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void updateClippingRect() {
        if (this.mRemoveClippedSubviews) {
            Assertions.assertNotNull(this.mClippingRect);
            Assertions.assertNotNull(this.mAllChildren);
            ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
            updateClippingToRect(this.mClippingRect);
        }
    }

    private void updateClippingToRect(Rect clippingRect) {
        Assertions.assertNotNull(this.mAllChildren);
        int clippedSoFar = 0;
        for (int i = 0; i < this.mAllChildrenCount; i++) {
            updateSubviewClipStatus(clippingRect, i, clippedSoFar);
            if (this.mAllChildren[i].getParent() == null) {
                clippedSoFar++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateSubviewClipStatus(Rect clippingRect, int idx, int clippedSoFar) {
        ConstraintLayout constraintLayout = ((View[]) Assertions.assertNotNull(this.mAllChildren))[idx];
        sHelperRect.set(constraintLayout.getLeft(), constraintLayout.getTop(), constraintLayout.getRight(), constraintLayout.getBottom());
        boolean intersects = clippingRect.intersects(sHelperRect.left, sHelperRect.top, sHelperRect.right, sHelperRect.bottom);
        boolean needUpdateClippingRecursive = false;
        Animation animation = constraintLayout.getAnimation();
        boolean isAnimating = (animation == null || animation.hasEnded()) ? false : true;
        if (!intersects && constraintLayout.getParent() != null && !isAnimating) {
            super.removeViewsInLayout(idx - clippedSoFar, 1);
            needUpdateClippingRecursive = true;
        } else if (intersects && constraintLayout.getParent() == null) {
            super.addViewInLayout(constraintLayout, idx - clippedSoFar, sDefaultLayoutParam, true);
            invalidate();
            needUpdateClippingRecursive = true;
        } else if (intersects) {
            needUpdateClippingRecursive = true;
        }
        if (needUpdateClippingRecursive && (constraintLayout instanceof ReactClippingViewGroup)) {
            ReactClippingViewGroup clippingChild = (ReactClippingViewGroup) constraintLayout;
            if (clippingChild.getRemoveClippedSubviews()) {
                clippingChild.updateClippingRect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSubviewClipStatus(View subview) {
        if (this.mRemoveClippedSubviews && getParent() != null) {
            Assertions.assertNotNull(this.mClippingRect);
            Assertions.assertNotNull(this.mAllChildren);
            sHelperRect.set(subview.getLeft(), subview.getTop(), subview.getRight(), subview.getBottom());
            boolean intersects = this.mClippingRect.intersects(sHelperRect.left, sHelperRect.top, sHelperRect.right, sHelperRect.bottom);
            boolean oldIntersects = subview.getParent() != null;
            if (intersects != oldIntersects) {
                int clippedSoFar = 0;
                for (int i = 0; i < this.mAllChildrenCount; i++) {
                    if (this.mAllChildren[i] == subview) {
                        updateSubviewClipStatus(this.mClippingRect, i, clippedSoFar);
                        return;
                    } else {
                        if (this.mAllChildren[i].getParent() == null) {
                            clippedSoFar++;
                        }
                    }
                }
            }
        }
    }

    @Override // android.view.View
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

    @Override // com.facebook.react.uimanager.ReactPointerEventsView
    public PointerEvents getPointerEvents() {
        return this.mPointerEvents;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean pressed) {
    }

    void setPointerEvents(PointerEvents pointerEvents) {
        this.mPointerEvents = pointerEvents;
    }

    int getAllChildrenCount() {
        return this.mAllChildrenCount;
    }

    View getChildAtWithSubviewClippingEnabled(int index) {
        return ((View[]) Assertions.assertNotNull(this.mAllChildren))[index];
    }

    void addViewWithSubviewClippingEnabled(View child, int index) {
        addViewWithSubviewClippingEnabled(child, index, sDefaultLayoutParam);
    }

    void addViewWithSubviewClippingEnabled(View child, int index, ViewGroup.LayoutParams params) {
        Assertions.assertCondition(this.mRemoveClippedSubviews);
        Assertions.assertNotNull(this.mClippingRect);
        Assertions.assertNotNull(this.mAllChildren);
        addInArray(child, index);
        int clippedSoFar = 0;
        for (int i = 0; i < index; i++) {
            if (this.mAllChildren[i].getParent() == null) {
                clippedSoFar++;
            }
        }
        updateSubviewClipStatus(this.mClippingRect, index, clippedSoFar);
        child.addOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
    }

    void removeViewWithSubviewClippingEnabled(View view) {
        Assertions.assertCondition(this.mRemoveClippedSubviews);
        Assertions.assertNotNull(this.mClippingRect);
        Assertions.assertNotNull(this.mAllChildren);
        view.removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
        int index = indexOfChildInAllChildren(view);
        if (this.mAllChildren[index].getParent() != null) {
            int clippedSoFar = 0;
            for (int i = 0; i < index; i++) {
                if (this.mAllChildren[i].getParent() == null) {
                    clippedSoFar++;
                }
            }
            super.removeViewsInLayout(index - clippedSoFar, 1);
        }
        removeFromArray(index);
    }

    void removeAllViewsWithSubviewClippingEnabled() {
        Assertions.assertCondition(this.mRemoveClippedSubviews);
        Assertions.assertNotNull(this.mAllChildren);
        for (int i = 0; i < this.mAllChildrenCount; i++) {
            this.mAllChildren[i].removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
        }
        removeAllViewsInLayout();
        this.mAllChildrenCount = 0;
    }

    private int indexOfChildInAllChildren(View child) {
        int count = this.mAllChildrenCount;
        View[] children = (View[]) Assertions.assertNotNull(this.mAllChildren);
        for (int i = 0; i < count; i++) {
            if (children[i] == child) {
                return i;
            }
        }
        return -1;
    }

    private void addInArray(View child, int index) {
        View[] children = (View[]) Assertions.assertNotNull(this.mAllChildren);
        int count = this.mAllChildrenCount;
        int size = children.length;
        if (index == count) {
            if (size == count) {
                this.mAllChildren = new View[size + 12];
                System.arraycopy(children, 0, this.mAllChildren, 0, size);
                children = this.mAllChildren;
            }
            int i = this.mAllChildrenCount;
            this.mAllChildrenCount = i + 1;
            children[i] = child;
            return;
        }
        if (index < count) {
            if (size == count) {
                this.mAllChildren = new View[size + 12];
                System.arraycopy(children, 0, this.mAllChildren, 0, index);
                System.arraycopy(children, index, this.mAllChildren, index + 1, count - index);
                children = this.mAllChildren;
            } else {
                System.arraycopy(children, index, children, index + 1, count - index);
            }
            children[index] = child;
            this.mAllChildrenCount++;
            return;
        }
        throw new IndexOutOfBoundsException("index=" + index + " count=" + count);
    }

    private void removeFromArray(int index) {
        View[] children = (View[]) Assertions.assertNotNull(this.mAllChildren);
        int count = this.mAllChildrenCount;
        if (index == count - 1) {
            int i = this.mAllChildrenCount - 1;
            this.mAllChildrenCount = i;
            children[i] = null;
        } else {
            if (index >= 0 && index < count) {
                System.arraycopy(children, index + 1, children, index, (count - index) - 1);
                int i2 = this.mAllChildrenCount - 1;
                this.mAllChildrenCount = i2;
                children[i2] = null;
                return;
            }
            throw new IndexOutOfBoundsException();
        }
    }

    public int getBackgroundColor() {
        if (getBackground() != null) {
            return ((ReactViewBackgroundDrawable) getBackground()).getColor();
        }
        return 0;
    }

    private ReactViewBackgroundDrawable getOrCreateReactViewBackground() {
        if (this.mReactBackgroundDrawable == null) {
            this.mReactBackgroundDrawable = new ReactViewBackgroundDrawable();
            Drawable backgroundDrawable = getBackground();
            super.setBackground(null);
            if (backgroundDrawable == null) {
                super.setBackground(this.mReactBackgroundDrawable);
            } else {
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.mReactBackgroundDrawable, backgroundDrawable});
                super.setBackground(layerDrawable);
            }
        }
        return this.mReactBackgroundDrawable;
    }

    @Override // com.facebook.react.touch.ReactHitSlopView
    public Rect getHitSlopRect() {
        return this.mHitSlopRect;
    }

    public void setHitSlopRect(Rect rect) {
        this.mHitSlopRect = rect;
    }
}
