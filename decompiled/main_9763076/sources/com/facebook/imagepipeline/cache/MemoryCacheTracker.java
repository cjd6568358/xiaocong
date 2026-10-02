package com.facebook.imagepipeline.cache;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface MemoryCacheTracker {
    void onCacheHit();

    void onCacheMiss();

    void onCachePut();
}
