package android.support.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ViewGroupOverlayApi18 implements ViewGroupOverlayImpl {
    private final ViewGroupOverlay mViewGroupOverlay;

    ViewGroupOverlayApi18(ViewGroup group) {
        this.mViewGroupOverlay = group.getOverlay();
    }

    @Override // android.support.transition.ViewOverlayImpl
    public void add(Drawable drawable) {
        this.mViewGroupOverlay.add(drawable);
    }

    @Override // android.support.transition.ViewOverlayImpl
    public void remove(Drawable drawable) {
        this.mViewGroupOverlay.remove(drawable);
    }

    @Override // android.support.transition.ViewGroupOverlayImpl
    public void add(View view) {
        this.mViewGroupOverlay.add(view);
    }

    @Override // android.support.transition.ViewGroupOverlayImpl
    public void remove(View view) {
        this.mViewGroupOverlay.remove(view);
    }
}
