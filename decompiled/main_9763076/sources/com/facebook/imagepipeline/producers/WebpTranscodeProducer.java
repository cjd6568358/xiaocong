package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.nativecode.WebpTranscoder;
import com.facebook.imagepipeline.nativecode.WebpTranscoderFactory;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WebpTranscodeProducer implements Producer<EncodedImage> {
    private final Executor mExecutor;
    private final Producer<EncodedImage> mInputProducer;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public WebpTranscodeProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Producer<EncodedImage> inputProducer) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mPooledByteBufferFactory = (PooledByteBufferFactory) Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        this.mInputProducer.produceResults(new WebpTranscodeConsumer(consumer, context), context);
    }

    private class WebpTranscodeConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ProducerContext mContext;
        private TriState mShouldTranscodeWhenFinished;

        public WebpTranscodeConsumer(Consumer<EncodedImage> consumer, ProducerContext context) {
            super(consumer);
            this.mContext = context;
            this.mShouldTranscodeWhenFinished = TriState.UNSET;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (this.mShouldTranscodeWhenFinished == TriState.UNSET && newResult != null) {
                this.mShouldTranscodeWhenFinished = WebpTranscodeProducer.shouldTranscode(newResult);
            }
            if (this.mShouldTranscodeWhenFinished == TriState.NO) {
                getConsumer().onNewResult(newResult, isLast);
                return;
            }
            if (isLast) {
                if (this.mShouldTranscodeWhenFinished == TriState.YES && newResult != null) {
                    WebpTranscodeProducer.this.transcodeLastResult(newResult, getConsumer(), this.mContext);
                } else {
                    getConsumer().onNewResult(newResult, isLast);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void transcodeLastResult(EncodedImage originalResult, Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        Preconditions.checkNotNull(originalResult);
        final EncodedImage encodedImageCopy = EncodedImage.cloneOrNull(originalResult);
        StatefulProducerRunnable<EncodedImage> runnable = new StatefulProducerRunnable<EncodedImage>(consumer, producerContext.getListener(), "WebpTranscodeProducer", producerContext.getId()) { // from class: com.facebook.imagepipeline.producers.WebpTranscodeProducer.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.common.executors.StatefulRunnable
            public EncodedImage getResult() throws Exception {
                PooledByteBufferOutputStream outputStream = WebpTranscodeProducer.this.mPooledByteBufferFactory.newOutputStream();
                try {
                    WebpTranscodeProducer.doTranscode(encodedImageCopy, outputStream);
                    CloseableReference<PooledByteBuffer> ref = CloseableReference.of(outputStream.toByteBuffer());
                    try {
                        EncodedImage encodedImage = new EncodedImage(ref);
                        encodedImage.copyMetaDataFrom(encodedImageCopy);
                        CloseableReference.closeSafely(ref);
                        outputStream.close();
                        return encodedImage;
                    } catch (Throwable th) {
                        CloseableReference.closeSafely(ref);
                        throw th;
                    }
                } catch (Throwable th2) {
                    outputStream.close();
                    throw th2;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            public void onSuccess(EncodedImage result) {
                EncodedImage.closeSafely(encodedImageCopy);
                super.onSuccess(result);
            }

            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            protected void onFailure(Exception e) {
                EncodedImage.closeSafely(encodedImageCopy);
                super.onFailure(e);
            }

            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            protected void onCancellation() {
                EncodedImage.closeSafely(encodedImageCopy);
                super.onCancellation();
            }
        };
        this.mExecutor.execute(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TriState shouldTranscode(EncodedImage encodedImage) {
        Preconditions.checkNotNull(encodedImage);
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(encodedImage.getInputStream());
        switch (imageFormat) {
            case WEBP_SIMPLE:
            case WEBP_LOSSLESS:
            case WEBP_EXTENDED:
            case WEBP_EXTENDED_WITH_ALPHA:
                WebpTranscoder webpTranscoder = WebpTranscoderFactory.getWebpTranscoder();
                if (webpTranscoder == null) {
                    return TriState.NO;
                }
                return TriState.valueOf(!webpTranscoder.isWebpNativelySupported(imageFormat));
            case UNKNOWN:
                return TriState.UNSET;
            default:
                return TriState.NO;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doTranscode(EncodedImage encodedImage, PooledByteBufferOutputStream outputStream) throws Exception {
        InputStream imageInputStream = encodedImage.getInputStream();
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(imageInputStream);
        switch (imageFormat) {
            case WEBP_SIMPLE:
            case WEBP_EXTENDED:
                WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToJpeg(imageInputStream, outputStream, 80);
                return;
            case WEBP_LOSSLESS:
            case WEBP_EXTENDED_WITH_ALPHA:
                WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToPng(imageInputStream, outputStream);
                return;
            default:
                throw new IllegalArgumentException("Wrong image format");
        }
    }
}
