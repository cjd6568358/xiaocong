package com.bumptech.glide.module;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface GlideModule {
    void applyOptions(Context context, GlideBuilder glideBuilder);

    void registerComponents(Context context, Glide glide);
}
