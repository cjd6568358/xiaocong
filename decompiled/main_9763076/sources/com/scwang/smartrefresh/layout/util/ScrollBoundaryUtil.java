package com.scwang.smartrefresh.layout.util;

import android.graphics.PointF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScrollBoundaryUtil {
    public static boolean canRefresh(View targetView, MotionEvent event) {
        if (canScrollUp(targetView) && targetView.getVisibility() == 0) {
            return false;
        }
        if ((targetView instanceof ViewGroup) && event != null) {
            ViewGroup viewGroup = (ViewGroup) targetView;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            for (int i = childCount; i > 0; i--) {
                View child = viewGroup.getChildAt(i - 1);
                if (isTransformedTouchPointInView(viewGroup, child, event.getX(), event.getY(), point)) {
                    MotionEvent event2 = MotionEvent.obtain(event);
                    event2.offsetLocation(point.x, point.y);
                    return canRefresh(child, event2);
                }
            }
        }
        return true;
    }

    public static boolean canLoadMore(View targetView, MotionEvent event) {
        if (!canScrollDown(targetView) && canScrollUp(targetView) && targetView.getVisibility() == 0) {
            return true;
        }
        if ((targetView instanceof ViewGroup) && event != null) {
            ViewGroup viewGroup = (ViewGroup) targetView;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                if (isTransformedTouchPointInView(viewGroup, child, event.getX(), event.getY(), point)) {
                    MotionEvent event2 = MotionEvent.obtain(event);
                    event2.offsetLocation(point.x, point.y);
                    return canLoadMore(child, event2);
                }
            }
        }
        return false;
    }

    public static boolean canScrollDown(View targetView, MotionEvent event) {
        if (canScrollDown(targetView) && targetView.getVisibility() == 0) {
            return true;
        }
        if ((targetView instanceof ViewGroup) && event != null) {
            ViewGroup viewGroup = (ViewGroup) targetView;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                if (isTransformedTouchPointInView(viewGroup, child, event.getX(), event.getY(), point)) {
                    MotionEvent event2 = MotionEvent.obtain(event);
                    event2.offsetLocation(point.x, point.y);
                    return canScrollDown(child, event2);
                }
            }
        }
        return false;
    }

    public static boolean canScrollUp(View targetView) {
        if (Build.VERSION.SDK_INT < 14) {
            if (!(targetView instanceof AbsListView)) {
                return targetView.getScrollY() > 0;
            }
            AbsListView absListView = (AbsListView) targetView;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        }
        return targetView.canScrollVertically(-1);
    }

    public static boolean canScrollDown(View targetView) {
        if (Build.VERSION.SDK_INT < 14) {
            if (!(targetView instanceof AbsListView)) {
                return targetView.getScrollY() < 0;
            }
            AbsListView absListView = (AbsListView) targetView;
            return absListView.getChildCount() > 0 && (absListView.getLastVisiblePosition() < absListView.getChildCount() + (-1) || absListView.getChildAt(absListView.getChildCount() + (-1)).getBottom() > absListView.getPaddingBottom());
        }
        return targetView.canScrollVertically(1);
    }

    public static boolean isTransformedTouchPointInView(ViewGroup group, View child, float x, float y, PointF outLocalPoint) {
        if (child.getVisibility() != 0) {
            return false;
        }
        float[] point = {x, y};
        transformPointToViewLocal(group, child, point);
        boolean isInView = pointInView(child, point[0], point[1], 0.0f);
        if (isInView && outLocalPoint != null) {
            outLocalPoint.set(point[0] - x, point[1] - y);
            return isInView;
        }
        return isInView;
    }

    public static boolean pointInView(View view, float localX, float localY, float slop) {
        float left = -slop;
        float top = -slop;
        float width = view.getWidth();
        float height = view.getHeight();
        return localX >= left && localY >= top && localX < width + slop && localY < height + slop;
    }

    public static void transformPointToViewLocal(ViewGroup group, View child, float[] point) {
        point[0] = point[0] + (group.getScrollX() - child.getLeft());
        point[1] = point[1] + (group.getScrollY() - child.getTop());
    }
}
