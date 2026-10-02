package com.facebook.cache.common;

import android.net.Uri;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MultiCacheKey implements CacheKey {
    final List<CacheKey> mCacheKeys;

    public List<CacheKey> getCacheKeys() {
        return this.mCacheKeys;
    }

    @Override // com.facebook.cache.common.CacheKey
    public String toString() {
        return "MultiCacheKey:" + this.mCacheKeys.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MultiCacheKey) {
            MultiCacheKey otherKey = (MultiCacheKey) o;
            return this.mCacheKeys.equals(otherKey.mCacheKeys);
        }
        return false;
    }

    public int hashCode() {
        return this.mCacheKeys.hashCode();
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean containsUri(Uri uri) {
        for (int i = 0; i < this.mCacheKeys.size(); i++) {
            if (this.mCacheKeys.get(i).containsUri(uri)) {
                return true;
            }
        }
        return false;
    }
}
