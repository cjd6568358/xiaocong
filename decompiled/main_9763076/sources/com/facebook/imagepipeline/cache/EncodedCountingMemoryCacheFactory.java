package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.PooledByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EncodedCountingMemoryCacheFactory {
    public static CountingMemoryCache<CacheKey, PooledByteBuffer> get(Supplier<MemoryCacheParams> encodedMemoryCacheParamsSupplier, MemoryTrimmableRegistry memoryTrimmableRegistry) {
        ValueDescriptor<PooledByteBuffer> valueDescriptor = new ValueDescriptor<PooledByteBuffer>() { // from class: com.facebook.imagepipeline.cache.EncodedCountingMemoryCacheFactory.1
            @Override // com.facebook.imagepipeline.cache.ValueDescriptor
            public int getSizeInBytes(PooledByteBuffer value) {
                return value.size();
            }
        };
        CountingMemoryCache.CacheTrimStrategy trimStrategy = new NativeMemoryCacheTrimStrategy();
        CountingMemoryCache<CacheKey, PooledByteBuffer> countingCache = new CountingMemoryCache<>(valueDescriptor, trimStrategy, encodedMemoryCacheParamsSupplier);
        memoryTrimmableRegistry.registerMemoryTrimmable(countingCache);
        return countingCache;
    }
}
