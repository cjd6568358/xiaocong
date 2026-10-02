package com.bumptech.glide.load.model;

import com.bumptech.glide.load.data.DataFetcher;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ModelLoader<T, Y> {
    DataFetcher<Y> getResourceFetcher(T t, int i, int i2);
}
