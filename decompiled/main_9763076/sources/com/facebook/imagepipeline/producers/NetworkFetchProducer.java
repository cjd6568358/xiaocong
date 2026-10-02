package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import java.io.InputStream;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NetworkFetchProducer implements Producer<EncodedImage> {
    private final ByteArrayPool mByteArrayPool;
    private final NetworkFetcher mNetworkFetcher;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public NetworkFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, NetworkFetcher networkFetcher) {
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mNetworkFetcher = networkFetcher;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        context.getListener().onProducerStart(context.getId(), "NetworkFetchProducer");
        final FetchState fetchState = this.mNetworkFetcher.createFetchState(consumer, context);
        this.mNetworkFetcher.fetch(fetchState, new NetworkFetcher.Callback() { // from class: com.facebook.imagepipeline.producers.NetworkFetchProducer.1
            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onResponse(InputStream response, int responseLength) throws Throwable {
                NetworkFetchProducer.this.onResponse(fetchState, response, responseLength);
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onFailure(Throwable throwable) {
                NetworkFetchProducer.this.onFailure(fetchState, throwable);
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onCancellation() {
                NetworkFetchProducer.this.onCancellation(fetchState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onResponse(FetchState fetchState, InputStream responseData, int responseContentLength) throws Throwable {
        PooledByteBufferOutputStream pooledOutputStream;
        if (responseContentLength > 0) {
            pooledOutputStream = this.mPooledByteBufferFactory.newOutputStream(responseContentLength);
        } else {
            pooledOutputStream = this.mPooledByteBufferFactory.newOutputStream();
        }
        byte[] ioArray = this.mByteArrayPool.get(16384);
        while (true) {
            try {
                int length = responseData.read(ioArray);
                if (length >= 0) {
                    if (length > 0) {
                        pooledOutputStream.write(ioArray, 0, length);
                        maybeHandleIntermediateResult(pooledOutputStream, fetchState);
                        float progress = calculateProgress(pooledOutputStream.size(), responseContentLength);
                        fetchState.getConsumer().onProgressUpdate(progress);
                    }
                } else {
                    this.mNetworkFetcher.onFetchCompletion(fetchState, pooledOutputStream.size());
                    handleFinalResult(pooledOutputStream, fetchState);
                    this.mByteArrayPool.release(ioArray);
                    pooledOutputStream.close();
                    return;
                }
            } catch (Throwable th) {
                this.mByteArrayPool.release(ioArray);
                pooledOutputStream.close();
                throw th;
            }
        }
    }

    private static float calculateProgress(int downloaded, int total) {
        return total > 0 ? downloaded / total : 1.0f - ((float) Math.exp(((double) (-downloaded)) / 50000.0d));
    }

    private void maybeHandleIntermediateResult(PooledByteBufferOutputStream pooledOutputStream, FetchState fetchState) throws Throwable {
        long nowMs = SystemClock.uptimeMillis();
        if (shouldPropagateIntermediateResults(fetchState) && nowMs - fetchState.getLastIntermediateResultTimeMs() >= 100) {
            fetchState.setLastIntermediateResultTimeMs(nowMs);
            fetchState.getListener().onProducerEvent(fetchState.getId(), "NetworkFetchProducer", "intermediate_result");
            notifyConsumer(pooledOutputStream, false, fetchState.getConsumer());
        }
    }

    private void handleFinalResult(PooledByteBufferOutputStream pooledOutputStream, FetchState fetchState) throws Throwable {
        Map<String, String> extraMap = getExtraMap(fetchState, pooledOutputStream.size());
        fetchState.getListener().onProducerFinishWithSuccess(fetchState.getId(), "NetworkFetchProducer", extraMap);
        notifyConsumer(pooledOutputStream, true, fetchState.getConsumer());
    }

    private void notifyConsumer(PooledByteBufferOutputStream pooledOutputStream, boolean isFinal, Consumer<EncodedImage> consumer) throws Throwable {
        CloseableReference<PooledByteBuffer> result = CloseableReference.of(pooledOutputStream.toByteBuffer());
        EncodedImage encodedImage = null;
        try {
            EncodedImage encodedImage2 = new EncodedImage(result);
            try {
                encodedImage2.parseMetaData();
                consumer.onNewResult(encodedImage2, isFinal);
                EncodedImage.closeSafely(encodedImage2);
                CloseableReference.closeSafely(result);
            } catch (Throwable th) {
                th = th;
                encodedImage = encodedImage2;
                EncodedImage.closeSafely(encodedImage);
                CloseableReference.closeSafely(result);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFailure(FetchState fetchState, Throwable e) {
        fetchState.getListener().onProducerFinishWithFailure(fetchState.getId(), "NetworkFetchProducer", e, null);
        fetchState.getConsumer().onFailure(e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCancellation(FetchState fetchState) {
        fetchState.getListener().onProducerFinishWithCancellation(fetchState.getId(), "NetworkFetchProducer", null);
        fetchState.getConsumer().onCancellation();
    }

    private boolean shouldPropagateIntermediateResults(FetchState fetchState) {
        if (fetchState.getContext().getImageRequest().getProgressiveRenderingEnabled()) {
            return this.mNetworkFetcher.shouldPropagate(fetchState);
        }
        return false;
    }

    private Map<String, String> getExtraMap(FetchState fetchState, int byteSize) {
        if (fetchState.getListener().requiresExtraMap(fetchState.getId())) {
            return this.mNetworkFetcher.getExtraMap(fetchState, byteSize);
        }
        return null;
    }
}
