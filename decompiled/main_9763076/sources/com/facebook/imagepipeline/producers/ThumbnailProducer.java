package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ThumbnailProducer<T> extends Producer<T> {
    boolean canProvideImageForSize(ResizeOptions resizeOptions);
}
