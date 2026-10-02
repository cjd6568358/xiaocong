package com.bumptech.glide.request;

import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ResourceCallback {
    void onException(Exception exc);

    void onResourceReady(Resource<?> resource);
}
