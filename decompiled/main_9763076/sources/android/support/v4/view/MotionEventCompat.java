package android.support.v4.view;

import android.view.MotionEvent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MotionEventCompat {
    @Deprecated
    public static int getActionMasked(MotionEvent event) {
        return event.getActionMasked();
    }

    @Deprecated
    public static int getActionIndex(MotionEvent event) {
        return event.getActionIndex();
    }

    @Deprecated
    public static int findPointerIndex(MotionEvent event, int pointerId) {
        return event.findPointerIndex(pointerId);
    }

    @Deprecated
    public static int getPointerId(MotionEvent event, int pointerIndex) {
        return event.getPointerId(pointerIndex);
    }

    @Deprecated
    public static float getX(MotionEvent event, int pointerIndex) {
        return event.getX(pointerIndex);
    }

    @Deprecated
    public static float getY(MotionEvent event, int pointerIndex) {
        return event.getY(pointerIndex);
    }

    @Deprecated
    public static int getPointerCount(MotionEvent event) {
        return event.getPointerCount();
    }

    public static boolean isFromSource(MotionEvent event, int source) {
        return (event.getSource() & source) == source;
    }
}
