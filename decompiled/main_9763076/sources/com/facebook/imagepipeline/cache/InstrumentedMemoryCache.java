package com.facebook.imagepipeline.cache;

import com.android.internal.util.Predicate;
import com.facebook.common.references.CloseableReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class InstrumentedMemoryCache<K, V> implements MemoryCache<K, V> {
    private final MemoryCache<K, V> mDelegate;
    private final MemoryCacheTracker mTracker;

    public InstrumentedMemoryCache(MemoryCache<K, V> delegate, MemoryCacheTracker tracker) {
        this.mDelegate = delegate;
        this.mTracker = tracker;
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public CloseableReference<V> get(K key) {
        CloseableReference<V> result = this.mDelegate.get(key);
        if (result == null) {
            this.mTracker.onCacheMiss();
        } else {
            this.mTracker.onCacheHit();
        }
        return result;
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public CloseableReference<V> cache(K key, CloseableReference<V> value) {
        this.mTracker.onCachePut();
        return this.mDelegate.cache(key, value);
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public int removeAll(Predicate<K> predicate) {
        return this.mDelegate.removeAll(predicate);
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public boolean contains(Predicate<K> predicate) {
        return this.mDelegate.contains(predicate);
    }
}
