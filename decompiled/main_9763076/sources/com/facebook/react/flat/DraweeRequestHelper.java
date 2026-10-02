package com.facebook.react.flat;

import android.graphics.drawable.Drawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DraweeRequestHelper {
    private int mAttachCounter;
    private final DraweeController mDraweeController;

    void attach(FlatViewGroup.InvalidateCallback callback) {
        this.mAttachCounter++;
        if (this.mAttachCounter == 1) {
            getDrawable().setCallback((Drawable.Callback) callback.get());
            this.mDraweeController.onAttach();
        }
    }

    void detach() {
        this.mAttachCounter--;
        if (this.mAttachCounter == 0) {
            this.mDraweeController.onDetach();
        }
    }

    GenericDraweeHierarchy getHierarchy() {
        return (GenericDraweeHierarchy) Assertions.assumeNotNull(this.mDraweeController.getHierarchy());
    }

    Drawable getDrawable() {
        return getHierarchy().getTopLevelDrawable();
    }
}
