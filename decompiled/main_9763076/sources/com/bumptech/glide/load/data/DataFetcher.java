package com.bumptech.glide.load.data;

import com.bumptech.glide.Priority;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DataFetcher<T> {
    void cancel();

    void cleanup();

    String getId();

    T loadData(Priority priority) throws Exception;
}
