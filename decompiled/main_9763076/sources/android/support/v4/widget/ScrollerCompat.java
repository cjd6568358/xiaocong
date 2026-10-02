package android.support.v4.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public final class ScrollerCompat {
    OverScroller mScroller;

    @Deprecated
    public static ScrollerCompat create(Context context, Interpolator interpolator) {
        return new ScrollerCompat(context, interpolator);
    }

    ScrollerCompat(Context context, Interpolator interpolator) {
        this.mScroller = interpolator != null ? new OverScroller(context, interpolator) : new OverScroller(context);
    }

    @Deprecated
    public boolean isFinished() {
        return this.mScroller.isFinished();
    }

    @Deprecated
    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    @Deprecated
    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    @Deprecated
    public int getFinalX() {
        return this.mScroller.getFinalX();
    }

    @Deprecated
    public int getFinalY() {
        return this.mScroller.getFinalY();
    }

    @Deprecated
    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    @Deprecated
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mScroller.startScroll(startX, startY, dx, dy, duration);
    }

    @Deprecated
    public void abortAnimation() {
        this.mScroller.abortAnimation();
    }
}
