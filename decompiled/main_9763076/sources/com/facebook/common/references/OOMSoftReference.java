package com.facebook.common.references;

import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OOMSoftReference<T> {
    SoftReference<T> softRef1 = null;
    SoftReference<T> softRef2 = null;
    SoftReference<T> softRef3 = null;

    public void set(T hardReference) {
        this.softRef1 = new SoftReference<>(hardReference);
        this.softRef2 = new SoftReference<>(hardReference);
        this.softRef3 = new SoftReference<>(hardReference);
    }

    public T get() {
        if (this.softRef1 == null) {
            return null;
        }
        return this.softRef1.get();
    }

    public void clear() {
        if (this.softRef1 != null) {
            this.softRef1.clear();
            this.softRef1 = null;
        }
        if (this.softRef2 != null) {
            this.softRef2.clear();
            this.softRef2 = null;
        }
        if (this.softRef3 != null) {
            this.softRef3.clear();
            this.softRef3 = null;
        }
    }
}
