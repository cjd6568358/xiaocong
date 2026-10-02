package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.request.ImageRequest;
import com.tencent.bugly.Bugly;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EncodedMemoryCacheProducer implements Producer<EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;

    public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        String requestId = producerContext.getId();
        ProducerListener listener = producerContext.getListener();
        listener.onProducerStart(requestId, "EncodedMemoryCacheProducer");
        ImageRequest imageRequest = producerContext.getImageRequest();
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, producerContext.getCallerContext());
        CloseableReference<PooledByteBuffer> cachedReference = this.mMemoryCache.get(cacheKey);
        try {
            if (cachedReference != null) {
                EncodedImage cachedEncodedImage = new EncodedImage(cachedReference);
                try {
                    listener.onProducerFinishWithSuccess(requestId, "EncodedMemoryCacheProducer", listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", "true") : null);
                    consumer.onProgressUpdate(1.0f);
                    consumer.onNewResult(cachedEncodedImage, true);
                    EncodedImage.closeSafely(cachedEncodedImage);
                    CloseableReference.closeSafely(cachedReference);
                    return;
                } catch (Throwable th) {
                    EncodedImage.closeSafely(cachedEncodedImage);
                    throw th;
                }
            }
            if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
                listener.onProducerFinishWithSuccess(requestId, "EncodedMemoryCacheProducer", listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", Bugly.SDK_IS_DEV) : null);
                consumer.onNewResult(null, true);
                CloseableReference.closeSafely(cachedReference);
            } else {
                Consumer<EncodedImage> consumerOfInputProducer = new DelegatingConsumer<EncodedImage, EncodedImage>(consumer) { // from class: com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer.1
                    @Override // com.facebook.imagepipeline.producers.BaseConsumer
                    public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
                        if (!isLast || newResult == null) {
                            getConsumer().onNewResult(newResult, isLast);
                            return;
                        }
                        CloseableReference<PooledByteBuffer> ref = newResult.getByteBufferRef();
                        if (ref != null) {
                            try {
                                CloseableReference<PooledByteBuffer> cachedResult = EncodedMemoryCacheProducer.this.mMemoryCache.cache(cacheKey, ref);
                                CloseableReference.closeSafely(ref);
                                if (cachedResult != null) {
                                    try {
                                        EncodedImage cachedEncodedImage2 = new EncodedImage(cachedResult);
                                        cachedEncodedImage2.copyMetaDataFrom(newResult);
                                        CloseableReference.closeSafely(cachedResult);
                                        try {
                                            getConsumer().onProgressUpdate(1.0f);
                                            getConsumer().onNewResult(cachedEncodedImage2, true);
                                            return;
                                        } finally {
                                            EncodedImage.closeSafely(cachedEncodedImage2);
                                        }
                                    } catch (Throwable th2) {
                                        CloseableReference.closeSafely(cachedResult);
                                        throw th2;
                                    }
                                }
                            } catch (Throwable th3) {
                                CloseableReference.closeSafely(ref);
                                throw th3;
                            }
                        }
                        getConsumer().onNewResult(newResult, true);
                    }
                };
                listener.onProducerFinishWithSuccess(requestId, "EncodedMemoryCacheProducer", listener.requiresExtraMap(requestId) ? ImmutableMap.of("cached_value_found", Bugly.SDK_IS_DEV) : null);
                this.mInputProducer.produceResults(consumerOfInputProducer, producerContext);
                CloseableReference.closeSafely(cachedReference);
            }
        } catch (Throwable th2) {
            CloseableReference.closeSafely(cachedReference);
            throw th2;
        }
    }
}
