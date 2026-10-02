package com.bumptech.glide.load.resource;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.tencent.android.tpush.common.Constants;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NullResourceEncoder<T> implements ResourceEncoder<T> {
    private static final NullResourceEncoder<?> NULL_ENCODER = new NullResourceEncoder<>();

    public static <T> NullResourceEncoder<T> get() {
        return (NullResourceEncoder<T>) NULL_ENCODER;
    }

    @Override // com.bumptech.glide.load.Encoder
    public boolean encode(Resource<T> data, OutputStream os) {
        return false;
    }

    @Override // com.bumptech.glide.load.Encoder
    public String getId() {
        return Constants.MAIN_VERSION_TAG;
    }
}
