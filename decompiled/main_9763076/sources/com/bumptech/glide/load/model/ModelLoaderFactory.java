package com.bumptech.glide.load.model;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ModelLoaderFactory<T, Y> {
    ModelLoader<T, Y> build(Context context, GenericLoaderFactory genericLoaderFactory);

    void teardown();
}
