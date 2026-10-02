package com.facebook.imagepipeline.cache;

import com.facebook.common.internal.Supplier;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultEncodedMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.common.internal.Supplier
    public MemoryCacheParams get() {
        int maxCacheSize = getMaxCacheSize();
        int maxCacheEntrySize = maxCacheSize / 8;
        return new MemoryCacheParams(maxCacheSize, Integer.MAX_VALUE, maxCacheSize, Integer.MAX_VALUE, maxCacheEntrySize);
    }

    private int getMaxCacheSize() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return 1048576;
        }
        if (maxMemory < 33554432) {
            return 2097152;
        }
        return 4194304;
    }
}
