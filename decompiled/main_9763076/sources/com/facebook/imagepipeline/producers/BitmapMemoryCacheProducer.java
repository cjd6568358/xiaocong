package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.tencent.bugly.Bugly;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public BitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        listener.onProducerStart(requestId, getProducerName());
        ImageRequest imageRequest = producerContext.getImageRequest();
        Object callerContext = producerContext.getCallerContext();
        CacheKey cacheKey = this.mCacheKeyFactory.getBitmapCacheKey(imageRequest, callerContext);
        CloseableReference<CloseableImage> cachedReference = this.mMemoryCache.get(cacheKey);
        if (cachedReference != null) {
            boolean isFinal = cachedReference.get().getQualityInfo().isOfFullQuality();
            if (isFinal) {
                listener.onProducerFinishWithSuccess(requestId, getProducerName(), listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", "true") : null);
                consumer.onProgressUpdate(1.0f);
            }
            consumer.onNewResult(cachedReference, isFinal);
            cachedReference.close();
            if (isFinal) {
                return;
            }
        }
        if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
            listener.onProducerFinishWithSuccess(requestId, getProducerName(), listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", Bugly.SDK_IS_DEV) : null);
            consumer.onNewResult(null, true);
        } else {
            Consumer<CloseableReference<CloseableImage>> wrappedConsumer = wrapConsumer(consumer, cacheKey);
            listener.onProducerFinishWithSuccess(requestId, getProducerName(), listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", Bugly.SDK_IS_DEV) : null);
            this.mInputProducer.produceResults(wrappedConsumer, producerContext);
        }
    }

    protected Consumer<CloseableReference<CloseableImage>> wrapConsumer(Consumer<CloseableReference<CloseableImage>> consumer, final CacheKey cacheKey) {
        return new DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>>(consumer) { // from class: com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer.1
            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
                CloseableReference<CloseableImage> currentCachedResult;
                if (newResult == null) {
                    if (isLast) {
                        getConsumer().onNewResult(null, true);
                        return;
                    }
                    return;
                }
                if (newResult.get().isStateful()) {
                    getConsumer().onNewResult(newResult, isLast);
                    return;
                }
                if (!isLast && (currentCachedResult = BitmapMemoryCacheProducer.this.mMemoryCache.get(cacheKey)) != null) {
                    try {
                        QualityInfo newInfo = newResult.get().getQualityInfo();
                        QualityInfo cachedInfo = currentCachedResult.get().getQualityInfo();
                        if (cachedInfo.isOfFullQuality() || cachedInfo.getQuality() >= newInfo.getQuality()) {
                            getConsumer().onNewResult(currentCachedResult, false);
                            CloseableReference.closeSafely(currentCachedResult);
                            return;
                        }
                        CloseableReference.closeSafely(currentCachedResult);
                    } catch (Throwable th) {
                        CloseableReference.closeSafely(currentCachedResult);
                        throw th;
                    }
                }
                CloseableReference<CloseableImage> newCachedResult = BitmapMemoryCacheProducer.this.mMemoryCache.cache(cacheKey, newResult);
                if (isLast) {
                    try {
                        getConsumer().onProgressUpdate(1.0f);
                    } finally {
                        CloseableReference.closeSafely(newCachedResult);
                    }
                }
                Consumer<CloseableReference<CloseableImage>> consumer2 = getConsumer();
                if (newCachedResult != null) {
                    newResult = newCachedResult;
                }
                consumer2.onNewResult(newResult, isLast);
            }
        };
    }

    protected String getProducerName() {
        return "BitmapMemoryCacheProducer";
    }
}
