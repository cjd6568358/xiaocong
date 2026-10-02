package com.bumptech.glide.load.resource;

import com.bumptech.glide.load.Encoder;
import com.tencent.android.tpush.common.Constants;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NullEncoder<T> implements Encoder<T> {
    private static final NullEncoder<?> NULL_ENCODER = new NullEncoder<>();

    public static <T> Encoder<T> get() {
        return NULL_ENCODER;
    }

    @Override // com.bumptech.glide.load.Encoder
    public boolean encode(T data, OutputStream os) {
        return false;
    }

    @Override // com.bumptech.glide.load.Encoder
    public String getId() {
        return Constants.MAIN_VERSION_TAG;
    }
}
