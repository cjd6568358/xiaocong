package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.tencent.bugly.Bugly;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PostprocessedBitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public PostprocessedBitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        ImageRequest imageRequest = producerContext.getImageRequest();
        Object callerContext = producerContext.getCallerContext();
        Postprocessor postprocessor = imageRequest.getPostprocessor();
        if (postprocessor == null || postprocessor.getPostprocessorCacheKey() == null) {
            this.mInputProducer.produceResults(consumer, producerContext);
            return;
        }
        listener.onProducerStart(requestId, getProducerName());
        CacheKey cacheKey = this.mCacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest, callerContext);
        CloseableReference<CloseableImage> cachedReference = this.mMemoryCache.get(cacheKey);
        if (cachedReference != null) {
            listener.onProducerFinishWithSuccess(requestId, getProducerName(), listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", "true") : null);
            consumer.onProgressUpdate(1.0f);
            consumer.onNewResult(cachedReference, true);
            cachedReference.close();
            return;
        }
        boolean isRepeatedProcessor = postprocessor instanceof RepeatedPostprocessor;
        Consumer<CloseableReference<CloseableImage>> cachedConsumer = new CachedPostprocessorConsumer(consumer, cacheKey, isRepeatedProcessor, this.mMemoryCache);
        listener.onProducerFinishWithSuccess(requestId, getProducerName(), listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", Bugly.SDK_IS_DEV) : null);
        this.mInputProducer.produceResults(cachedConsumer, producerContext);
    }

    public static class CachedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private final CacheKey mCacheKey;
        private final boolean mIsRepeatedProcessor;
        private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

        public CachedPostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, CacheKey cacheKey, boolean isRepeatedProcessor, MemoryCache<CacheKey, CloseableImage> memoryCache) {
            super(consumer);
            this.mCacheKey = cacheKey;
            this.mIsRepeatedProcessor = isRepeatedProcessor;
            this.mMemoryCache = memoryCache;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (newResult == null) {
                if (isLast) {
                    getConsumer().onNewResult(null, true);
                }
            } else if (isLast || this.mIsRepeatedProcessor) {
                CloseableReference<CloseableImage> newCachedResult = this.mMemoryCache.cache(this.mCacheKey, newResult);
                try {
                    getConsumer().onProgressUpdate(1.0f);
                    Consumer<CloseableReference<CloseableImage>> consumer = getConsumer();
                    if (newCachedResult != null) {
                        newResult = newCachedResult;
                    }
                    consumer.onNewResult(newResult, isLast);
                } finally {
                    CloseableReference.closeSafely(newCachedResult);
                }
            }
        }
    }

    protected String getProducerName() {
        return "PostprocessedBitmapMemoryCacheProducer";
    }
}
