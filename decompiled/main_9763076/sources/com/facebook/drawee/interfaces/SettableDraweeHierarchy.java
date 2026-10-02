package com.facebook.drawee.interfaces;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface SettableDraweeHierarchy extends DraweeHierarchy {
    void reset();

    void setControllerOverlay(Drawable drawable);

    void setFailure(Throwable th);

    void setImage(Drawable drawable, float f, boolean z);

    void setProgress(float f, boolean z);

    void setRetry(Throwable th);
}
