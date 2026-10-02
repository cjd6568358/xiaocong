package com.facebook.drawee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DraweeHolder<DH extends DraweeHierarchy> implements VisibilityCallback {
    private DH mHierarchy;
    private boolean mIsControllerAttached = false;
    private boolean mIsHolderAttached = false;
    private boolean mIsVisible = true;
    private boolean mIsActivityStarted = true;
    private DraweeController mController = null;
    private final DraweeEventTracker mEventTracker = new DraweeEventTracker();

    public static <DH extends DraweeHierarchy> DraweeHolder<DH> create(DH hierarchy, Context context) {
        DraweeHolder<DH> holder = new DraweeHolder<>(hierarchy);
        holder.registerWithContext(context);
        return holder;
    }

    public void registerWithContext(Context context) {
    }

    public DraweeHolder(DH hierarchy) {
        if (hierarchy != null) {
            setHierarchy(hierarchy);
        }
    }

    public void onAttach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_ATTACH);
        this.mIsHolderAttached = true;
        attachOrDetachController();
    }

    public void onDetach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_DETACH);
        this.mIsHolderAttached = false;
        attachOrDetachController();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mController == null) {
            return false;
        }
        return this.mController.onTouchEvent(event);
    }

    @Override // com.facebook.drawee.drawable.VisibilityCallback
    public void onVisibilityChange(boolean isVisible) {
        if (this.mIsVisible != isVisible) {
            this.mEventTracker.recordEvent(isVisible ? DraweeEventTracker.Event.ON_DRAWABLE_SHOW : DraweeEventTracker.Event.ON_DRAWABLE_HIDE);
            this.mIsVisible = isVisible;
            attachOrDetachController();
        }
    }

    @Override // com.facebook.drawee.drawable.VisibilityCallback
    public void onDraw() {
        if (!this.mIsControllerAttached) {
            FLog.wtf((Class<?>) DraweeEventTracker.class, "%x: Draw requested for a non-attached controller %x. %s", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.mController)), toString());
            this.mIsHolderAttached = true;
            this.mIsVisible = true;
            this.mIsActivityStarted = true;
            attachOrDetachController();
        }
    }

    private void setVisibilityCallback(VisibilityCallback visibilityCallback) {
        Object topLevelDrawable = getTopLevelDrawable();
        if (topLevelDrawable instanceof VisibilityAwareDrawable) {
            ((VisibilityAwareDrawable) topLevelDrawable).setVisibilityCallback(visibilityCallback);
        }
    }

    public void setController(DraweeController draweeController) {
        boolean wasAttached = this.mIsControllerAttached;
        if (wasAttached) {
            detachController();
        }
        if (this.mController != null) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_OLD_CONTROLLER);
            this.mController.setHierarchy(null);
        }
        this.mController = draweeController;
        if (this.mController != null) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_CONTROLLER);
            this.mController.setHierarchy(this.mHierarchy);
        } else {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_CONTROLLER);
        }
        if (wasAttached) {
            attachController();
        }
    }

    public DraweeController getController() {
        return this.mController;
    }

    public void setHierarchy(DH hierarchy) {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_HIERARCHY);
        setVisibilityCallback(null);
        this.mHierarchy = (DH) Preconditions.checkNotNull(hierarchy);
        Drawable drawable = this.mHierarchy.getTopLevelDrawable();
        onVisibilityChange(drawable == null || drawable.isVisible());
        setVisibilityCallback(this);
        if (this.mController != null) {
            this.mController.setHierarchy(hierarchy);
        }
    }

    public DH getHierarchy() {
        return (DH) Preconditions.checkNotNull(this.mHierarchy);
    }

    public Drawable getTopLevelDrawable() {
        if (this.mHierarchy == null) {
            return null;
        }
        return this.mHierarchy.getTopLevelDrawable();
    }

    private void attachController() {
        if (!this.mIsControllerAttached) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
            this.mIsControllerAttached = true;
            if (this.mController != null && this.mController.getHierarchy() != null) {
                this.mController.onAttach();
            }
        }
    }

    private void detachController() {
        if (this.mIsControllerAttached) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
            this.mIsControllerAttached = false;
            if (this.mController != null) {
                this.mController.onDetach();
            }
        }
    }

    private void attachOrDetachController() {
        if (this.mIsHolderAttached && this.mIsVisible && this.mIsActivityStarted) {
            attachController();
        } else {
            detachController();
        }
    }

    public String toString() {
        return Objects.toStringHelper(this).add("controllerAttached", this.mIsControllerAttached).add("holderAttached", this.mIsHolderAttached).add("drawableVisible", this.mIsVisible).add("activityStarted", this.mIsActivityStarted).add("events", this.mEventTracker.toString()).toString();
    }
}
