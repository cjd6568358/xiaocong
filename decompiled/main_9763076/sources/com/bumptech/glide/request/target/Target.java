package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Target<R> extends LifecycleListener {
    Request getRequest();

    void getSize(SizeReadyCallback sizeReadyCallback);

    void onLoadCleared(Drawable drawable);

    void onLoadFailed(Exception exc, Drawable drawable);

    void onLoadStarted(Drawable drawable);

    void onResourceReady(R r, GlideAnimation<? super R> glideAnimation);

    void setRequest(Request request);
}
