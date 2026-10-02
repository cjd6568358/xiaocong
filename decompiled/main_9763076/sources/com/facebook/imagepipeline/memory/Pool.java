package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.ResourceReleaser;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Pool<V> extends MemoryTrimmable, ResourceReleaser<V> {
    V get(int i);

    void release(V v);
}
