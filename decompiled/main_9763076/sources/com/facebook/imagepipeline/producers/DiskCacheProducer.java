package com.facebook.imagepipeline.producers;

import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DiskCacheProducer implements Producer<EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final boolean mChooseCacheByImageSize;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final int mForceSmallCacheThresholdBytes;
    private final Producer<EncodedImage> mInputProducer;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;

    public DiskCacheProducer(BufferedDiskCache defaultBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer, int forceSmallCacheThresholdBytes) {
        this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
        this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
        this.mForceSmallCacheThresholdBytes = forceSmallCacheThresholdBytes;
        this.mChooseCacheByImageSize = forceSmallCacheThresholdBytes > 0;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        Task<EncodedImage> diskLookupTask;
        BufferedDiskCache firstCache;
        final BufferedDiskCache secondCache;
        ImageRequest imageRequest = producerContext.getImageRequest();
        if (!imageRequest.isDiskCacheEnabled()) {
            maybeStartInputProducer(consumer, consumer, producerContext);
            return;
        }
        producerContext.getListener().onProducerStart(producerContext.getId(), "DiskCacheProducer");
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, producerContext.getCallerContext());
        boolean isSmallRequest = imageRequest.getImageType() == ImageRequest.ImageType.SMALL;
        BufferedDiskCache preferredCache = isSmallRequest ? this.mSmallImageBufferedDiskCache : this.mDefaultBufferedDiskCache;
        final AtomicBoolean isCancelled = new AtomicBoolean(false);
        if (this.mChooseCacheByImageSize) {
            boolean alreadyInSmall = this.mSmallImageBufferedDiskCache.containsSync(cacheKey);
            boolean alreadyInMain = this.mDefaultBufferedDiskCache.containsSync(cacheKey);
            if (alreadyInSmall || !alreadyInMain) {
                firstCache = this.mSmallImageBufferedDiskCache;
                secondCache = this.mDefaultBufferedDiskCache;
            } else {
                firstCache = this.mDefaultBufferedDiskCache;
                secondCache = this.mSmallImageBufferedDiskCache;
            }
            Task<EncodedImage> diskLookupTask2 = firstCache.get(cacheKey, isCancelled);
            diskLookupTask = diskLookupTask2.continueWithTask(new Continuation<EncodedImage, Task<EncodedImage>>() { // from class: com.facebook.imagepipeline.producers.DiskCacheProducer.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // bolts.Continuation
                public Task<EncodedImage> then(Task<EncodedImage> task) throws Exception {
                    if (DiskCacheProducer.isTaskCancelled(task)) {
                        return task;
                    }
                    return (task.isFaulted() || task.getResult() == null) ? secondCache.get(cacheKey, isCancelled) : task;
                }
            });
        } else {
            diskLookupTask = preferredCache.get(cacheKey, isCancelled);
        }
        Continuation<EncodedImage, Void> continuation = onFinishDiskReads(consumer, preferredCache, cacheKey, producerContext);
        diskLookupTask.continueWith(continuation);
        subscribeTaskForRequestCancellation(isCancelled, producerContext);
    }

    private Continuation<EncodedImage, Void> onFinishDiskReads(final Consumer<EncodedImage> consumer, final BufferedDiskCache preferredCache, final CacheKey preferredCacheKey, final ProducerContext producerContext) {
        final String requestId = producerContext.getId();
        final ProducerListener listener = producerContext.getListener();
        return new Continuation<EncodedImage, Void>() { // from class: com.facebook.imagepipeline.producers.DiskCacheProducer.2
            @Override // bolts.Continuation
            public Void then(Task<EncodedImage> task) throws Exception {
                if (DiskCacheProducer.isTaskCancelled(task)) {
                    listener.onProducerFinishWithCancellation(requestId, "DiskCacheProducer", null);
                    consumer.onCancellation();
                } else if (task.isFaulted()) {
                    listener.onProducerFinishWithFailure(requestId, "DiskCacheProducer", task.getError(), null);
                    DiskCacheProducer.this.maybeStartInputProducer(consumer, new DiskCacheConsumer(consumer, preferredCache, preferredCacheKey), producerContext);
                } else {
                    EncodedImage cachedReference = task.getResult();
                    if (cachedReference != null) {
                        listener.onProducerFinishWithSuccess(requestId, "DiskCacheProducer", DiskCacheProducer.getExtraMap(listener, requestId, true));
                        consumer.onProgressUpdate(1.0f);
                        consumer.onNewResult(cachedReference, true);
                        cachedReference.close();
                    } else {
                        listener.onProducerFinishWithSuccess(requestId, "DiskCacheProducer", DiskCacheProducer.getExtraMap(listener, requestId, false));
                        DiskCacheProducer.this.maybeStartInputProducer(consumer, new DiskCacheConsumer(consumer, preferredCache, preferredCacheKey), producerContext);
                    }
                }
                return null;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTaskCancelled(Task<?> task) {
        return task.isCancelled() || (task.isFaulted() && (task.getError() instanceof CancellationException));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeStartInputProducer(Consumer<EncodedImage> consumerOfDiskCacheProducer, Consumer<EncodedImage> consumerOfInputProducer, ProducerContext producerContext) {
        if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.DISK_CACHE.getValue()) {
            consumerOfDiskCacheProducer.onNewResult(null, true);
        } else {
            this.mInputProducer.produceResults(consumerOfInputProducer, producerContext);
        }
    }

    static Map<String, String> getExtraMap(ProducerListener listener, String requestId, boolean valueFound) {
        if (listener.requiresExtraMap(requestId)) {
            return ImmutableMap.of("cached_value_found", String.valueOf(valueFound));
        }
        return null;
    }

    private void subscribeTaskForRequestCancellation(final AtomicBoolean isCancelled, ProducerContext producerContext) {
        producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.DiskCacheProducer.3
            @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                isCancelled.set(true);
            }
        });
    }

    private class DiskCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final BufferedDiskCache mCache;
        private final CacheKey mCacheKey;

        private DiskCacheConsumer(Consumer<EncodedImage> consumer, BufferedDiskCache cache, CacheKey cacheKey) {
            super(consumer);
            this.mCache = cache;
            this.mCacheKey = cacheKey;
        }

        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (newResult != null && isLast) {
                if (DiskCacheProducer.this.mChooseCacheByImageSize) {
                    int size = newResult.getSize();
                    if (size <= 0 || size >= DiskCacheProducer.this.mForceSmallCacheThresholdBytes) {
                        DiskCacheProducer.this.mDefaultBufferedDiskCache.put(this.mCacheKey, newResult);
                    } else {
                        DiskCacheProducer.this.mSmallImageBufferedDiskCache.put(this.mCacheKey, newResult);
                    }
                } else {
                    this.mCache.put(this.mCacheKey, newResult);
                }
            }
            getConsumer().onNewResult(newResult, isLast);
        }
    }
}
