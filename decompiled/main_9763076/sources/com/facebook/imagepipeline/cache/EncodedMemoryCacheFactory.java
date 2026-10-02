package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.memory.PooledByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EncodedMemoryCacheFactory {
    public static MemoryCache<CacheKey, PooledByteBuffer> get(CountingMemoryCache<CacheKey, PooledByteBuffer> encodedCountingMemoryCache, final ImageCacheStatsTracker imageCacheStatsTracker) {
        imageCacheStatsTracker.registerEncodedMemoryCache(encodedCountingMemoryCache);
        MemoryCacheTracker memoryCacheTracker = new MemoryCacheTracker() { // from class: com.facebook.imagepipeline.cache.EncodedMemoryCacheFactory.1
            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCacheHit() {
                imageCacheStatsTracker.onMemoryCacheHit();
            }

            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCacheMiss() {
                imageCacheStatsTracker.onMemoryCacheMiss();
            }

            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCachePut() {
                imageCacheStatsTracker.onMemoryCachePut();
            }
        };
        return new InstrumentedMemoryCache(encodedCountingMemoryCache, memoryCacheTracker);
    }
}
