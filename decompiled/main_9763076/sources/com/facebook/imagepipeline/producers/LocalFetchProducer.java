package com.facebook.imagepipeline.producers;

import android.os.Build;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class LocalFetchProducer implements Producer<EncodedImage> {
    private final boolean mDecodeFileDescriptorEnabledForKitKat;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    protected abstract EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException;

    protected abstract String getProducerName();

    protected LocalFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean fileDescriptorEnabled) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mDecodeFileDescriptorEnabledForKitKat = fileDescriptorEnabled && Build.VERSION.SDK_INT == 19;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final StatefulProducerRunnable<EncodedImage> statefulProducerRunnable = new StatefulProducerRunnable<EncodedImage>(consumer, listener, getProducerName(), requestId) { // from class: com.facebook.imagepipeline.producers.LocalFetchProducer.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.common.executors.StatefulRunnable
            public EncodedImage getResult() throws Exception {
                EncodedImage encodedImage = LocalFetchProducer.this.getEncodedImage(imageRequest);
                if (encodedImage == null) {
                    return null;
                }
                encodedImage.parseMetaData();
                return encodedImage;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.LocalFetchProducer.2
            @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                statefulProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(statefulProducerRunnable);
    }

    protected EncodedImage getByteBufferBackedEncodedImage(InputStream inputStream, int length) throws IOException {
        CloseableReference<PooledByteBuffer> ref = null;
        try {
            if (length < 0) {
                ref = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(inputStream));
            } else {
                ref = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(inputStream, length));
            }
            EncodedImage encodedImage = new EncodedImage(ref);
            Closeables.closeQuietly(inputStream);
            CloseableReference.closeSafely(ref);
            return encodedImage;
        } catch (Throwable th) {
            Closeables.closeQuietly(inputStream);
            CloseableReference.closeSafely(ref);
            throw th;
        }
    }

    protected EncodedImage getEncodedImage(InputStream inputStream, int length) throws IOException {
        Runtime runTime = Runtime.getRuntime();
        long javaMax = runTime.maxMemory();
        long javaUsed = runTime.totalMemory() - runTime.freeMemory();
        long javaFree = Math.min(javaMax - javaUsed, 8388608L);
        return (this.mDecodeFileDescriptorEnabledForKitKat && (inputStream instanceof FileInputStream) && javaMax >= 64 * javaFree) ? getInputStreamBackedEncodedImage(new File(inputStream.toString()), length) : getByteBufferBackedEncodedImage(inputStream, length);
    }

    protected EncodedImage getInputStreamBackedEncodedImage(final File file, int length) throws IOException {
        Supplier<FileInputStream> sup = new Supplier<FileInputStream>() { // from class: com.facebook.imagepipeline.producers.LocalFetchProducer.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.facebook.common.internal.Supplier
            public FileInputStream get() {
                try {
                    return new FileInputStream(file);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        };
        return new EncodedImage(sup, length);
    }
}
