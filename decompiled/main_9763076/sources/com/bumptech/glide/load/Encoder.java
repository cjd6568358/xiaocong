package com.bumptech.glide.load;

import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Encoder<T> {
    boolean encode(T t, OutputStream outputStream);

    String getId();
}
