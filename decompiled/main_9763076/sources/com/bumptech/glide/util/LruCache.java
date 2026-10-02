package com.bumptech.glide.util;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LruCache<T, Y> {
    private final LinkedHashMap<T, Y> cache = new LinkedHashMap<>(100, 0.75f, true);
    private int currentSize = 0;
    private final int initialMaxSize;
    private int maxSize;

    public LruCache(int size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    protected int getSize(Y item) {
        return 1;
    }

    protected void onItemEvicted(T key, Y item) {
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    public Y get(T key) {
        return this.cache.get(key);
    }

    public Y put(T key, Y item) {
        int itemSize = getSize(item);
        if (itemSize >= this.maxSize) {
            onItemEvicted(key, item);
            return null;
        }
        Y result = this.cache.put(key, item);
        if (item != null) {
            this.currentSize += getSize(item);
        }
        if (result != null) {
            this.currentSize -= getSize(result);
        }
        evict();
        return result;
    }

    public Y remove(T key) {
        Y value = this.cache.remove(key);
        if (value != null) {
            this.currentSize -= getSize(value);
        }
        return value;
    }

    public void clearMemory() {
        trimToSize(0);
    }

    protected void trimToSize(int size) {
        while (this.currentSize > size) {
            Map.Entry<T, Y> last = this.cache.entrySet().iterator().next();
            Y toRemove = last.getValue();
            this.currentSize -= getSize(toRemove);
            T key = last.getKey();
            this.cache.remove(key);
            onItemEvicted(key, toRemove);
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }
}
