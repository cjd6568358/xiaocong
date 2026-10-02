package com.facebook.imagepipeline.cache;

import com.android.internal.util.Predicate;
import com.facebook.common.references.CloseableReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface MemoryCache<K, V> {
    CloseableReference<V> cache(K k, CloseableReference<V> closeableReference);

    boolean contains(Predicate<K> predicate);

    CloseableReference<V> get(K k);

    int removeAll(Predicate<K> predicate);
}
