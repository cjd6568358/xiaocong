package com.bumptech.glide.load;

import com.bumptech.glide.load.engine.Resource;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ResourceDecoder<T, Z> {
    Resource<Z> decode(T t, int i, int i2) throws IOException;

    String getId();
}
