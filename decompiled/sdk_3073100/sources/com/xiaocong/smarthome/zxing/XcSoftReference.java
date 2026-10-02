package com.xiaocong.smarthome.zxing;

import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XcSoftReference<T> extends SoftReference<T> {
    private final String TAG;

    public XcSoftReference(T r) {
        super(r);
        this.TAG = getClass().getSimpleName();
    }
}
