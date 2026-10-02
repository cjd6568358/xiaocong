package com.facebook.react.uimanager;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.touch.ReactHitSlopView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TouchTargetHelper {
    private static final float[] mEventCoords = new float[2];
    private static final PointF mTempPoint = new PointF();
    private static final float[] mMatrixTransformCoords = new float[2];
    private static final Matrix mInverseMatrix = new Matrix();

    public static int findTargetTagForTouch(float eventX, float eventY, ViewGroup viewGroup) {
        return findTargetTagAndCoordinatesForTouch(eventX, eventY, viewGroup, mEventCoords, null);
    }

    public static int findTargetTagAndCoordinatesForTouch(float eventX, float eventY, ViewGroup viewGroup, float[] viewCoords, int[] nativeViewTag) {
        View reactTargetView;
        UiThreadUtil.assertOnUiThread();
        int targetTag = viewGroup.getId();
        viewCoords[0] = eventX;
        viewCoords[1] = eventY;
        View nativeTargetView = findTouchTargetView(viewCoords, viewGroup);
        if (nativeTargetView != null && (reactTargetView = findClosestReactAncestor(nativeTargetView)) != null) {
            if (nativeViewTag != null) {
                nativeViewTag[0] = reactTargetView.getId();
            }
            return getTouchTargetForView(reactTargetView, viewCoords[0], viewCoords[1]);
        }
        return targetTag;
    }

    private static View findClosestReactAncestor(View view) {
        while (view != null && view.getId() <= 0) {
            view = (View) view.getParent();
        }
        return view;
    }

    private static View findTouchTargetView(float[] eventCoords, ViewGroup viewGroup) {
        int childrenCount = viewGroup.getChildCount();
        for (int i = childrenCount - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            PointF childPoint = mTempPoint;
            if (isTransformedTouchPointInView(eventCoords[0], eventCoords[1], viewGroup, child, childPoint)) {
                float restoreX = eventCoords[0];
                float restoreY = eventCoords[1];
                eventCoords[0] = childPoint.x;
                eventCoords[1] = childPoint.y;
                View targetView = findTouchTargetViewWithPointerEvents(eventCoords, child);
                if (targetView == null) {
                    eventCoords[0] = restoreX;
                    eventCoords[1] = restoreY;
                } else {
                    return targetView;
                }
            }
        }
        return viewGroup;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isTransformedTouchPointInView(float x, float y, ViewGroup parent, View view, PointF outLocalPoint) {
        float localX = (parent.getScrollX() + x) - view.getLeft();
        float localY = (parent.getScrollY() + y) - view.getTop();
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            float[] localXY = mMatrixTransformCoords;
            localXY[0] = localX;
            localXY[1] = localY;
            Matrix inverseMatrix = mInverseMatrix;
            matrix.invert(inverseMatrix);
            inverseMatrix.mapPoints(localXY);
            localX = localXY[0];
            localY = localXY[1];
        }
        if (!(view instanceof ReactHitSlopView) || ((ReactHitSlopView) view).getHitSlopRect() == null) {
            if (localX < 0.0f || localX >= view.getRight() - view.getLeft() || localY < 0.0f || localY >= view.getBottom() - view.getTop()) {
                return false;
            }
            outLocalPoint.set(localX, localY);
            return true;
        }
        Rect hitSlopRect = ((ReactHitSlopView) view).getHitSlopRect();
        if (localX < (-hitSlopRect.left) || localX >= (view.getRight() - view.getLeft()) + hitSlopRect.right || localY < (-hitSlopRect.top) || localY >= (view.getBottom() - view.getTop()) + hitSlopRect.bottom) {
            return false;
        }
        outLocalPoint.set(localX, localY);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static View findTouchTargetViewWithPointerEvents(float[] eventCoords, View view) {
        PointerEvents pointerEvents = view instanceof ReactPointerEventsView ? ((ReactPointerEventsView) view).getPointerEvents() : PointerEvents.AUTO;
        if (!view.isEnabled()) {
            if (pointerEvents == PointerEvents.AUTO) {
                pointerEvents = PointerEvents.BOX_NONE;
            } else if (pointerEvents == PointerEvents.BOX_ONLY) {
                pointerEvents = PointerEvents.NONE;
            }
        }
        if (pointerEvents == PointerEvents.NONE) {
            return null;
        }
        if (pointerEvents != PointerEvents.BOX_ONLY) {
            if (pointerEvents == PointerEvents.BOX_NONE) {
                if (view instanceof ViewGroup) {
                    View targetView = findTouchTargetView(eventCoords, (ViewGroup) view);
                    if (targetView != view) {
                        return targetView;
                    }
                    if (view instanceof ReactCompoundView) {
                        int reactTag = ((ReactCompoundView) view).reactTagForTouch(eventCoords[0], eventCoords[1]);
                        if (reactTag != view.getId()) {
                            return view;
                        }
                    }
                }
                return null;
            }
            if (pointerEvents == PointerEvents.AUTO) {
                if ((!(view instanceof ReactCompoundViewGroup) || !((ReactCompoundViewGroup) view).interceptsTouchEvent(eventCoords[0], eventCoords[1])) && (view instanceof ViewGroup)) {
                    View view2 = findTouchTargetView(eventCoords, (ViewGroup) view);
                    return view2;
                }
                return view;
            }
            throw new JSApplicationIllegalArgumentException("Unknown pointer event type: " + pointerEvents.toString());
        }
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int getTouchTargetForView(View view, float eventX, float eventY) {
        return view instanceof ReactCompoundView ? ((ReactCompoundView) view).reactTagForTouch(eventX, eventY) : view.getId();
    }
}
