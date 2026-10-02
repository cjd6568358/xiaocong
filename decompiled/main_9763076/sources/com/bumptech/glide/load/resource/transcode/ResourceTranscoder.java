package com.bumptech.glide.load.resource.transcode;

import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ResourceTranscoder<Z, R> {
    String getId();

    Resource<R> transcode(Resource<Z> resource);
}
