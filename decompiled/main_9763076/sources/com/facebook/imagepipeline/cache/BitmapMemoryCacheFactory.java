package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.image.CloseableImage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapMemoryCacheFactory {
    public static MemoryCache<CacheKey, CloseableImage> get(CountingMemoryCache<CacheKey, CloseableImage> bitmapCountingMemoryCache, final ImageCacheStatsTracker imageCacheStatsTracker) {
        imageCacheStatsTracker.registerBitmapMemoryCache(bitmapCountingMemoryCache);
        MemoryCacheTracker memoryCacheTracker = new MemoryCacheTracker() { // from class: com.facebook.imagepipeline.cache.BitmapMemoryCacheFactory.1
            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCacheHit() {
                imageCacheStatsTracker.onBitmapCacheHit();
            }

            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCacheMiss() {
                imageCacheStatsTracker.onBitmapCacheMiss();
            }

            @Override // com.facebook.imagepipeline.cache.MemoryCacheTracker
            public void onCachePut() {
                imageCacheStatsTracker.onBitmapCachePut();
            }
        };
        return new InstrumentedMemoryCache(bitmapCountingMemoryCache, memoryCacheTracker);
    }
}
