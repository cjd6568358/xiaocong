package com.bumptech.glide.load;

import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Transformation<T> {
    String getId();

    Resource<T> transform(Resource<T> resource, int i, int i2);
}
