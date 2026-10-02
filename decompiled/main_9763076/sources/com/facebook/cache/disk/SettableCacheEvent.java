package com.facebook.cache.disk;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SettableCacheEvent implements CacheEvent {
    private CacheKey mCacheKey;
    private long mCacheLimit;
    private long mCacheSize;
    private CacheEventListener.EvictionReason mEvictionReason;
    private IOException mException;
    private long mItemSize;
    private String mResourceId;

    public SettableCacheEvent setCacheKey(CacheKey cacheKey) {
        this.mCacheKey = cacheKey;
        return this;
    }

    public SettableCacheEvent setResourceId(String resourceId) {
        this.mResourceId = resourceId;
        return this;
    }

    public SettableCacheEvent setItemSize(long itemSize) {
        this.mItemSize = itemSize;
        return this;
    }

    public SettableCacheEvent setCacheSize(long cacheSize) {
        this.mCacheSize = cacheSize;
        return this;
    }

    public SettableCacheEvent setCacheLimit(long cacheLimit) {
        this.mCacheLimit = cacheLimit;
        return this;
    }

    public SettableCacheEvent setException(IOException exception) {
        this.mException = exception;
        return this;
    }

    public SettableCacheEvent setEvictionReason(CacheEventListener.EvictionReason evictionReason) {
        this.mEvictionReason = evictionReason;
        return this;
    }
}
