package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.request.ImageRequest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface CacheKeyFactory {
    CacheKey getBitmapCacheKey(ImageRequest imageRequest, Object obj);

    CacheKey getEncodedCacheKey(ImageRequest imageRequest, Object obj);

    CacheKey getPostprocessedBitmapCacheKey(ImageRequest imageRequest, Object obj);
}
