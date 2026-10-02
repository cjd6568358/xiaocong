package com.bumptech.glide.request.animation;

import android.graphics.drawable.Drawable;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface GlideAnimation<R> {

    public interface ViewAdapter {
        Drawable getCurrentDrawable();

        View getView();

        void setDrawable(Drawable drawable);
    }

    boolean animate(R r, ViewAdapter viewAdapter);
}
