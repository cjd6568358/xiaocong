package com.bumptech.glide.request;

import com.bumptech.glide.request.target.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RequestListener<T, R> {
    boolean onException(Exception exc, T t, Target<R> target, boolean z);

    boolean onResourceReady(R r, T t, Target<R> target, boolean z, boolean z2);
}
