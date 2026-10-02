package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.image.CloseableImage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapCountingMemoryCacheFactory {
    public static CountingMemoryCache<CacheKey, CloseableImage> get(Supplier<MemoryCacheParams> bitmapMemoryCacheParamsSupplier, MemoryTrimmableRegistry memoryTrimmableRegistry) {
        ValueDescriptor<CloseableImage> valueDescriptor = new ValueDescriptor<CloseableImage>() { // from class: com.facebook.imagepipeline.cache.BitmapCountingMemoryCacheFactory.1
            @Override // com.facebook.imagepipeline.cache.ValueDescriptor
            public int getSizeInBytes(CloseableImage value) {
                return value.getSizeInBytes();
            }
        };
        CountingMemoryCache.CacheTrimStrategy trimStrategy = new BitmapMemoryCacheTrimStrategy();
        CountingMemoryCache<CacheKey, CloseableImage> countingCache = new CountingMemoryCache<>(valueDescriptor, trimStrategy, bitmapMemoryCacheParamsSupplier);
        memoryTrimmableRegistry.registerMemoryTrimmable(countingCache);
        return countingCache;
    }
}
