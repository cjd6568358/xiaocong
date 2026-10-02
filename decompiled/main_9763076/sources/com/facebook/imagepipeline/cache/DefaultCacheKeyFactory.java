package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultCacheKeyFactory implements CacheKeyFactory {
    private static DefaultCacheKeyFactory sInstance = null;

    protected DefaultCacheKeyFactory() {
    }

    public static synchronized DefaultCacheKeyFactory getInstance() {
        if (sInstance == null) {
            sInstance = new DefaultCacheKeyFactory();
        }
        return sInstance;
    }

    @Override // com.facebook.imagepipeline.cache.CacheKeyFactory
    public CacheKey getBitmapCacheKey(ImageRequest request, Object callerContext) {
        return new BitmapMemoryCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString(), request.getResizeOptions(), request.getAutoRotateEnabled(), request.getImageDecodeOptions(), null, null, callerContext);
    }

    @Override // com.facebook.imagepipeline.cache.CacheKeyFactory
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request, Object callerContext) {
        CacheKey postprocessorCacheKey;
        String postprocessorName;
        Postprocessor postprocessor = request.getPostprocessor();
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        } else {
            postprocessorCacheKey = null;
            postprocessorName = null;
        }
        return new BitmapMemoryCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString(), request.getResizeOptions(), request.getAutoRotateEnabled(), request.getImageDecodeOptions(), postprocessorCacheKey, postprocessorName, callerContext);
    }

    @Override // com.facebook.imagepipeline.cache.CacheKeyFactory
    public CacheKey getEncodedCacheKey(ImageRequest request, Object callerContext) {
        return new SimpleCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString());
    }

    protected Uri getCacheKeySourceUri(Uri sourceUri) {
        return sourceUri;
    }
}
