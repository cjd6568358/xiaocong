package com.facebook.react.flat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import bsh.ParserConstants;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import com.facebook.react.touch.ReactHitSlopView;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactCompoundViewGroup;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.image.ImageLoadEvent;
import com.tencent.android.tpush.common.Constants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class FlatViewGroup extends ViewGroup implements ReactHitSlopView, ReactInterceptingViewGroup, ReactClippingViewGroup, ReactCompoundViewGroup, ReactPointerEventsView {
    private static Paint sDebugCornerPaint;
    private static Rect sDebugRect;
    private static Paint sDebugRectPaint;
    private static Paint sDebugTextBackgroundPaint;
    private static Paint sDebugTextPaint;
    private boolean mAndroidDebugDraw;
    private AttachDetachListener[] mAttachDetachListeners;
    private int mDrawChildIndex;
    private DrawCommandManager mDrawCommandManager;
    private DrawCommand[] mDrawCommands;
    private Rect mHitSlopRect;
    private Drawable mHotspot;
    private InvalidateCallback mInvalidateCallback;
    private boolean mIsAttached;
    private boolean mIsLayoutRequested;
    private long mLastTouchDownTime;
    private boolean mNeedsOffscreenAlphaCompositing;
    private NodeRegion[] mNodeRegions;
    private OnInterceptTouchEventListener mOnInterceptTouchEventListener;
    private PointerEvents mPointerEvents;
    private static final ArrayList<FlatViewGroup> LAYOUT_REQUESTS = new ArrayList<>();
    private static final Rect VIEW_BOUNDS = new Rect();
    private static final SparseArray<View> EMPTY_DETACHED_VIEWS = new SparseArray<>(0);

    static final class InvalidateCallback extends WeakReference<FlatViewGroup> {
        private InvalidateCallback(FlatViewGroup view) {
            super(view);
        }

        public void invalidate() {
            FlatViewGroup view = (FlatViewGroup) get();
            if (view != null) {
                view.invalidate();
            }
        }

        public void dispatchImageLoadEvent(int reactTag, int imageLoadEvent) {
            FlatViewGroup view = (FlatViewGroup) get();
            if (view != null) {
                ReactContext reactContext = (ReactContext) view.getContext();
                UIManagerModule uiManagerModule = (UIManagerModule) reactContext.getNativeModule(UIManagerModule.class);
                uiManagerModule.getEventDispatcher().dispatchEvent(new ImageLoadEvent(reactTag, imageLoadEvent));
            }
        }
    }

    FlatViewGroup(Context context) {
        super(context);
        this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
        this.mAttachDetachListeners = AttachDetachListener.EMPTY_ARRAY;
        this.mNodeRegions = NodeRegion.EMPTY_ARRAY;
        this.mDrawChildIndex = 0;
        this.mIsAttached = false;
        this.mIsLayoutRequested = false;
        this.mNeedsOffscreenAlphaCompositing = false;
        this.mPointerEvents = PointerEvents.AUTO;
        setClipChildren(false);
    }

    @Override // android.view.ViewGroup
    protected void detachAllViewsFromParent() {
        super.detachAllViewsFromParent();
    }

    @Override // android.view.View, android.view.ViewParent
    @SuppressLint({"MissingSuperCall"})
    public void requestLayout() {
        if (!this.mIsLayoutRequested) {
            this.mIsLayoutRequested = true;
            LAYOUT_REQUESTS.add(this);
        }
    }

    @Override // com.facebook.react.uimanager.ReactCompoundView
    public int reactTagForTouch(float touchX, float touchY) {
        NodeRegion nodeRegion;
        SoftAssertions.assertCondition(this.mPointerEvents != PointerEvents.NONE, "TouchTargetHelper should not allow calling this method when pointer events are NONE");
        if (this.mPointerEvents != PointerEvents.BOX_ONLY && (nodeRegion = virtualNodeRegionWithinBounds(touchX, touchY)) != null) {
            return nodeRegion.getReactTag(touchX, touchY);
        }
        return getId();
    }

    @Override // com.facebook.react.uimanager.ReactCompoundViewGroup
    public boolean interceptsTouchEvent(float touchX, float touchY) {
        NodeRegion nodeRegion = anyNodeRegionWithinBounds(touchX, touchY);
        return nodeRegion != null && nodeRegion.mIsVirtual;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        this.mAndroidDebugDraw = false;
        super.dispatchDraw(canvas);
        if (this.mDrawCommandManager != null) {
            this.mDrawCommandManager.draw(canvas);
        } else {
            for (DrawCommand drawCommand : this.mDrawCommands) {
                drawCommand.draw(this, canvas);
            }
        }
        if (this.mDrawChildIndex != getChildCount()) {
            throw new RuntimeException("Did not draw all children: " + this.mDrawChildIndex + " / " + getChildCount());
        }
        this.mDrawChildIndex = 0;
        if (this.mAndroidDebugDraw) {
            initDebugDrawResources();
            debugDraw(canvas);
        }
        if (this.mHotspot != null) {
            this.mHotspot.draw(canvas);
        }
    }

    private void debugDraw(Canvas canvas) {
        if (this.mDrawCommandManager != null) {
            this.mDrawCommandManager.debugDraw(canvas);
        } else {
            for (DrawCommand drawCommand : this.mDrawCommands) {
                drawCommand.debugDraw(this, canvas);
            }
        }
        this.mDrawChildIndex = 0;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return false;
    }

    void debugDrawNextChild(Canvas canvas) {
        View child = getChildAt(this.mDrawChildIndex);
        int color = child instanceof FlatViewGroup ? -12303292 : -65536;
        debugDrawRect(canvas, color, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        this.mDrawChildIndex++;
    }

    int dipsToPixels(int dips) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) ((dips * scale) + 0.5f);
    }

    private static void fillRect(Canvas canvas, Paint paint, float x1, float y1, float x2, float y2) {
        if (x1 != x2 && y1 != y2) {
            if (x1 > x2) {
                x1 = x2;
                x2 = x1;
            }
            if (y1 > y2) {
                y1 = y2;
                y2 = y1;
            }
            canvas.drawRect(x1, y1, x2, y2, paint);
        }
    }

    private static int sign(float x) {
        return x >= 0.0f ? 1 : -1;
    }

    private static void drawCorner(Canvas c, Paint paint, float x1, float y1, float dx, float dy, float lw) {
        fillRect(c, paint, x1, y1, x1 + dx, y1 + (sign(dy) * lw));
        fillRect(c, paint, x1, y1, x1 + (sign(dx) * lw), y1 + dy);
    }

    private static void drawRectCorners(Canvas canvas, float x1, float y1, float x2, float y2, Paint paint, int lineLength, int lineWidth) {
        drawCorner(canvas, paint, x1, y1, lineLength, lineLength, lineWidth);
        drawCorner(canvas, paint, x1, y2, lineLength, -lineLength, lineWidth);
        drawCorner(canvas, paint, x2, y1, -lineLength, lineLength, lineWidth);
        drawCorner(canvas, paint, x2, y2, -lineLength, -lineLength, lineWidth);
    }

    private void initDebugDrawResources() {
        if (sDebugTextPaint == null) {
            sDebugTextPaint = new Paint();
            sDebugTextPaint.setTextAlign(Paint.Align.RIGHT);
            sDebugTextPaint.setTextSize(dipsToPixels(9));
            sDebugTextPaint.setTypeface(Typeface.MONOSPACE);
            sDebugTextPaint.setAntiAlias(true);
            sDebugTextPaint.setColor(-65536);
        }
        if (sDebugTextBackgroundPaint == null) {
            sDebugTextBackgroundPaint = new Paint();
            sDebugTextBackgroundPaint.setColor(-1);
            sDebugTextBackgroundPaint.setAlpha(200);
            sDebugTextBackgroundPaint.setStyle(Paint.Style.FILL);
        }
        if (sDebugRectPaint == null) {
            sDebugRectPaint = new Paint();
            sDebugRectPaint.setAlpha(100);
            sDebugRectPaint.setStyle(Paint.Style.STROKE);
        }
        if (sDebugCornerPaint == null) {
            sDebugCornerPaint = new Paint();
            sDebugCornerPaint.setAlpha(200);
            sDebugCornerPaint.setColor(Color.rgb(63, ParserConstants.MODASSIGN, 255));
            sDebugCornerPaint.setStyle(Paint.Style.FILL);
        }
        if (sDebugRect == null) {
            sDebugRect = new Rect();
        }
    }

    private void debugDrawRect(Canvas canvas, int color, float left, float top, float right, float bottom) {
        debugDrawNamedRect(canvas, color, Constants.MAIN_VERSION_TAG, left, top, right, bottom);
    }

    void debugDrawNamedRect(Canvas canvas, int color, String name, float left, float top, float right, float bottom) {
        sDebugRectPaint.setColor((sDebugRectPaint.getColor() & (-16777216)) | (16777215 & color));
        sDebugRectPaint.setAlpha(100);
        canvas.drawRect(left, top, right - 1.0f, bottom - 1.0f, sDebugRectPaint);
        drawRectCorners(canvas, left, top, right, bottom, sDebugCornerPaint, dipsToPixels(8), dipsToPixels(1));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override // android.view.View
    @SuppressLint({"MissingSuperCall"})
    protected boolean verifyDrawable(Drawable who) {
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        if (!this.mIsAttached) {
            this.mIsAttached = true;
            super.onAttachedToWindow();
            dispatchOnAttached(this.mAttachDetachListeners);
            updateClippingRect();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        if (!this.mIsAttached) {
            throw new RuntimeException("Double detach");
        }
        this.mIsAttached = false;
        super.onDetachedFromWindow();
        dispatchOnDetached(this.mAttachDetachListeners);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.mHotspot != null) {
            this.mHotspot.setBounds(0, 0, w, h);
            invalidate();
        }
        updateClippingRect();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDrawableHotspotChanged(float x, float y) {
        if (this.mHotspot != null) {
            this.mHotspot.setHotspot(x, y);
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mHotspot != null && this.mHotspot.isStateful()) {
            this.mHotspot.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mHotspot != null) {
            this.mHotspot.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void invalidate() {
        invalidate(0, 0, getWidth() + 1, getHeight() + 1);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return this.mNeedsOffscreenAlphaCompositing;
    }

    @Override // com.facebook.react.touch.ReactInterceptingViewGroup
    public void setOnInterceptTouchEventListener(OnInterceptTouchEventListener listener) {
        this.mOnInterceptTouchEventListener = listener;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        long downTime = ev.getDownTime();
        if (downTime != this.mLastTouchDownTime) {
            this.mLastTouchDownTime = downTime;
            if (interceptsTouchEvent(ev.getX(), ev.getY())) {
                return true;
            }
        }
        if ((this.mOnInterceptTouchEventListener != null && this.mOnInterceptTouchEventListener.onInterceptTouchEvent(this, ev)) || this.mPointerEvents == PointerEvents.NONE || this.mPointerEvents == PointerEvents.BOX_ONLY) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mPointerEvents == PointerEvents.NONE) {
            return false;
        }
        if (this.mPointerEvents == PointerEvents.BOX_NONE) {
            NodeRegion nodeRegion = virtualNodeRegionWithinBounds(ev.getX(), ev.getY());
            if (nodeRegion == null) {
                return false;
            }
        }
        return true;
    }

    @Override // com.facebook.react.uimanager.ReactPointerEventsView
    public PointerEvents getPointerEvents() {
        return this.mPointerEvents;
    }

    void setPointerEvents(PointerEvents pointerEvents) {
        this.mPointerEvents = pointerEvents;
    }

    void setNeedsOffscreenAlphaCompositing(boolean needsOffscreenAlphaCompositing) {
        this.mNeedsOffscreenAlphaCompositing = needsOffscreenAlphaCompositing;
    }

    void setHotspot(Drawable hotspot) {
        if (this.mHotspot != null) {
            this.mHotspot.setCallback(null);
            unscheduleDrawable(this.mHotspot);
        }
        if (hotspot != null) {
            hotspot.setCallback(this);
            if (hotspot.isStateful()) {
                hotspot.setState(getDrawableState());
            }
        }
        this.mHotspot = hotspot;
        invalidate();
    }

    void drawNextChild(Canvas canvas) {
        View child = getChildAt(this.mDrawChildIndex);
        if (child instanceof FlatViewGroup) {
            super.drawChild(canvas, child, getDrawingTime());
        } else {
            canvas.save(2);
            child.getHitRect(VIEW_BOUNDS);
            canvas.clipRect(VIEW_BOUNDS);
            super.drawChild(canvas, child, getDrawingTime());
            canvas.restore();
        }
        this.mDrawChildIndex++;
    }

    void removeDetachedView(View view) {
        removeDetachedView(view, false);
    }

    @Override // android.view.ViewGroup
    public void removeAllViewsInLayout() {
        this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
        super.removeAllViewsInLayout();
    }

    void addViewInLayout(View view, int index) {
        addViewInLayout(view, index, ensureLayoutParams(view.getLayoutParams()), true);
    }

    void attachViewToParent(View view, int index) {
        attachViewToParent(view, index, ensureLayoutParams(view.getLayoutParams()));
    }

    private NodeRegion virtualNodeRegionWithinBounds(float touchX, float touchY) {
        if (this.mDrawCommandManager != null) {
            return this.mDrawCommandManager.virtualNodeRegionWithinBounds(touchX, touchY);
        }
        for (int i = this.mNodeRegions.length - 1; i >= 0; i--) {
            NodeRegion nodeRegion = this.mNodeRegions[i];
            if (nodeRegion.mIsVirtual && nodeRegion.withinBounds(touchX, touchY)) {
                return nodeRegion;
            }
        }
        return null;
    }

    private NodeRegion anyNodeRegionWithinBounds(float touchX, float touchY) {
        if (this.mDrawCommandManager != null) {
            return this.mDrawCommandManager.anyNodeRegionWithinBounds(touchX, touchY);
        }
        for (int i = this.mNodeRegions.length - 1; i >= 0; i--) {
            NodeRegion nodeRegion = this.mNodeRegions[i];
            if (nodeRegion.withinBounds(touchX, touchY)) {
                return nodeRegion;
            }
        }
        return null;
    }

    private void dispatchOnAttached(AttachDetachListener[] listeners) {
        int numListeners = listeners.length;
        if (numListeners != 0) {
            InvalidateCallback callback = getInvalidateCallback();
            for (AttachDetachListener listener : listeners) {
                listener.onAttached(callback);
            }
        }
    }

    private InvalidateCallback getInvalidateCallback() {
        if (this.mInvalidateCallback == null) {
            this.mInvalidateCallback = new InvalidateCallback();
        }
        return this.mInvalidateCallback;
    }

    private static void dispatchOnDetached(AttachDetachListener[] listeners) {
        for (AttachDetachListener listener : listeners) {
            listener.onDetached();
        }
    }

    private ViewGroup.LayoutParams ensureLayoutParams(ViewGroup.LayoutParams lp) {
        return checkLayoutParams(lp) ? lp : generateDefaultLayoutParams();
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void updateClippingRect() {
        if (this.mDrawCommandManager != null && this.mDrawCommandManager.updateClippingRect()) {
            invalidate();
        }
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public void getClippingRect(Rect outClippingRect) {
        if (this.mDrawCommandManager == null) {
            throw new RuntimeException("Trying to get the clipping rect for a non-clipping FlatViewGroup");
        }
        this.mDrawCommandManager.getClippingRect(outClippingRect);
    }

    public void setRemoveClippedSubviews(boolean removeClippedSubviews) {
        boolean currentlyClipping = getRemoveClippedSubviews();
        if (removeClippedSubviews != currentlyClipping) {
            if (currentlyClipping) {
                throw new RuntimeException("Trying to transition FlatViewGroup from clipping to non-clipping state");
            }
            this.mDrawCommandManager = DrawCommandManager.getVerticalClippingInstance(this, this.mDrawCommands);
            this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
        }
    }

    @Override // com.facebook.react.uimanager.ReactClippingViewGroup
    public boolean getRemoveClippedSubviews() {
        return this.mDrawCommandManager != null;
    }

    @Override // com.facebook.react.touch.ReactHitSlopView
    public Rect getHitSlopRect() {
        return this.mHitSlopRect;
    }

    void setHitSlopRect(Rect rect) {
        this.mHitSlopRect = rect;
    }
}
