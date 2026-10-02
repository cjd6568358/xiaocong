package com.bumptech.glide.load.engine.cache;

import android.annotation.SuppressLint;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.LruCache;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LruResourceCache extends LruCache<Key, Resource<?>> implements MemoryCache {
    private MemoryCache.ResourceRemovedListener listener;

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public /* bridge */ /* synthetic */ Resource put(Key x0, Resource x1) {
        return (Resource) super.put(x0, x1);
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public /* bridge */ /* synthetic */ Resource remove(Key x0) {
        return (Resource) super.remove(x0);
    }

    public LruResourceCache(int size) {
        super(size);
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void setResourceRemovedListener(MemoryCache.ResourceRemovedListener listener) {
        this.listener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.util.LruCache
    public void onItemEvicted(Key key, Resource<?> item) {
        if (this.listener != null) {
            this.listener.onResourceRemoved(item);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.util.LruCache
    public int getSize(Resource<?> item) {
        return item.getSize();
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int level) {
        if (level >= 60) {
            clearMemory();
        } else if (level >= 40) {
            trimToSize(getCurrentSize() / 2);
        }
    }
}
