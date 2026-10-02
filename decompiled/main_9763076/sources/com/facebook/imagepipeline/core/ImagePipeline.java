package com.facebook.imagepipeline.core;

import android.net.Uri;
import com.android.internal.util.Predicate;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.datasource.CloseableProducerToDataSourceAdapter;
import com.facebook.imagepipeline.datasource.ProducerToDataSourceAdapter;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.ForwardingRequestListener;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImagePipeline {
    private static final CancellationException PREFETCH_EXCEPTION = new CancellationException("Prefetching is not enabled");
    private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
    private final CacheKeyFactory mCacheKeyFactory;
    private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
    private AtomicLong mIdCounter = new AtomicLong();
    private final Supplier<Boolean> mIsPrefetchEnabledSupplier;
    private final BufferedDiskCache mMainBufferedDiskCache;
    private final ProducerSequenceFactory mProducerSequenceFactory;
    private final RequestListener mRequestListener;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ImagePipeline(ProducerSequenceFactory producerSequenceFactory, Set<RequestListener> requestListeners, Supplier<Boolean> isPrefetchEnabledSupplier, MemoryCache<CacheKey, CloseableImage> bitmapMemoryCache, MemoryCache<CacheKey, PooledByteBuffer> encodedMemoryCache, BufferedDiskCache mainBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        this.mProducerSequenceFactory = producerSequenceFactory;
        this.mRequestListener = new ForwardingRequestListener(requestListeners);
        this.mIsPrefetchEnabledSupplier = isPrefetchEnabledSupplier;
        this.mBitmapMemoryCache = bitmapMemoryCache;
        this.mEncodedMemoryCache = encodedMemoryCache;
        this.mMainBufferedDiskCache = mainBufferedDiskCache;
        this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
    }

    private String generateUniqueFutureId() {
        return String.valueOf(this.mIdCounter.getAndIncrement());
    }

    public DataSource<CloseableReference<CloseableImage>> fetchImageFromBitmapCache(ImageRequest imageRequest, Object callerContext) {
        try {
            Producer<CloseableReference<CloseableImage>> producerSequence = this.mProducerSequenceFactory.getDecodedImageProducerSequence(imageRequest);
            return submitFetchRequest(producerSequence, imageRequest, ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE, callerContext);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext) {
        try {
            Producer<CloseableReference<CloseableImage>> producerSequence = this.mProducerSequenceFactory.getDecodedImageProducerSequence(imageRequest);
            return submitFetchRequest(producerSequence, imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<Void> prefetchToDiskCache(ImageRequest imageRequest, Object callerContext) {
        return prefetchToDiskCache(imageRequest, callerContext, Priority.LOW);
    }

    public DataSource<Void> prefetchToDiskCache(ImageRequest imageRequest, Object callerContext, Priority priority) {
        if (!this.mIsPrefetchEnabledSupplier.get().booleanValue()) {
            return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
        }
        try {
            Producer<Void> producerSequence = this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(imageRequest);
            return submitPrefetchRequest(producerSequence, imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext, priority);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public void clearMemoryCaches() {
        Predicate<CacheKey> allPredicate = new Predicate<CacheKey>() { // from class: com.facebook.imagepipeline.core.ImagePipeline.3
            public boolean apply(CacheKey key) {
                return true;
            }
        };
        this.mBitmapMemoryCache.removeAll(allPredicate);
        this.mEncodedMemoryCache.removeAll(allPredicate);
    }

    public void clearDiskCaches() {
        this.mMainBufferedDiskCache.clearAll();
        this.mSmallImageBufferedDiskCache.clearAll();
    }

    public void clearCaches() {
        clearMemoryCaches();
        clearDiskCaches();
    }

    public boolean isInBitmapMemoryCache(Uri uri) {
        if (uri == null) {
            return false;
        }
        Predicate<CacheKey> bitmapCachePredicate = predicateForUri(uri);
        return this.mBitmapMemoryCache.contains(bitmapCachePredicate);
    }

    public boolean isInDiskCacheSync(Uri uri) {
        return isInDiskCacheSync(ImageRequest.fromUri(uri));
    }

    public boolean isInDiskCacheSync(ImageRequest imageRequest) {
        CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, null);
        return this.mMainBufferedDiskCache.diskCheckSync(cacheKey);
    }

    private <T> DataSource<CloseableReference<T>> submitFetchRequest(Producer<CloseableReference<T>> producerSequence, ImageRequest imageRequest, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit, Object callerContext) {
        try {
            ImageRequest.RequestLevel lowestPermittedRequestLevel = ImageRequest.RequestLevel.getMax(imageRequest.getLowestPermittedRequestLevel(), lowestPermittedRequestLevelOnSubmit);
            SettableProducerContext settableProducerContext = new SettableProducerContext(imageRequest, generateUniqueFutureId(), this.mRequestListener, callerContext, lowestPermittedRequestLevel, false, imageRequest.getProgressiveRenderingEnabled() || !UriUtil.isNetworkUri(imageRequest.getSourceUri()), imageRequest.getPriority());
            return CloseableProducerToDataSourceAdapter.create(producerSequence, settableProducerContext, this.mRequestListener);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    private DataSource<Void> submitPrefetchRequest(Producer<Void> producerSequence, ImageRequest imageRequest, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit, Object callerContext, Priority priority) {
        try {
            ImageRequest.RequestLevel lowestPermittedRequestLevel = ImageRequest.RequestLevel.getMax(imageRequest.getLowestPermittedRequestLevel(), lowestPermittedRequestLevelOnSubmit);
            SettableProducerContext settableProducerContext = new SettableProducerContext(imageRequest, generateUniqueFutureId(), this.mRequestListener, callerContext, lowestPermittedRequestLevel, true, false, priority);
            return ProducerToDataSourceAdapter.create(producerSequence, settableProducerContext, this.mRequestListener);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    private Predicate<CacheKey> predicateForUri(final Uri uri) {
        return new Predicate<CacheKey>() { // from class: com.facebook.imagepipeline.core.ImagePipeline.6
            public boolean apply(CacheKey key) {
                return key.containsUri(uri);
            }
        };
    }
}
