package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferOutputStream;
import com.facebook.imagepipeline.nativecode.JpegTranscoder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.tencent.android.tpush.common.Constants;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ResizeAndRotateProducer implements Producer<EncodedImage> {
    private final Executor mExecutor;
    private final Producer<EncodedImage> mInputProducer;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public ResizeAndRotateProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Producer<EncodedImage> inputProducer) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mPooledByteBufferFactory = (PooledByteBufferFactory) Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        this.mInputProducer.produceResults(new TransformingConsumer(consumer, context), context);
    }

    private class TransformingConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private boolean mIsCancelled;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;

        public TransformingConsumer(final Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer);
            this.mIsCancelled = false;
            this.mProducerContext = producerContext;
            JobScheduler.JobRunnable job = new JobScheduler.JobRunnable() { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.1
                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, boolean isLast) throws Throwable {
                    TransformingConsumer.this.doTransform(encodedImage, isLast);
                }
            };
            this.mJobScheduler = new JobScheduler(ResizeAndRotateProducer.this.mExecutor, job, 100);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.2
                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (TransformingConsumer.this.mProducerContext.isIntermediateResultExpected()) {
                        TransformingConsumer.this.mJobScheduler.scheduleJob();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    TransformingConsumer.this.mJobScheduler.clearJob();
                    TransformingConsumer.this.mIsCancelled = true;
                    consumer.onCancellation();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (!this.mIsCancelled) {
                if (newResult != null) {
                    TriState shouldTransform = ResizeAndRotateProducer.shouldTransform(this.mProducerContext.getImageRequest(), newResult);
                    if (isLast || shouldTransform != TriState.UNSET) {
                        if (shouldTransform != TriState.YES) {
                            getConsumer().onNewResult(newResult, isLast);
                            return;
                        } else {
                            if (this.mJobScheduler.updateJob(newResult, isLast)) {
                                if (isLast || this.mProducerContext.isIntermediateResultExpected()) {
                                    this.mJobScheduler.scheduleJob();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (isLast) {
                    getConsumer().onNewResult(null, true);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doTransform(EncodedImage encodedImage, boolean isLast) throws Throwable {
            this.mProducerContext.getListener().onProducerStart(this.mProducerContext.getId(), "ResizeAndRotateProducer");
            ImageRequest imageRequest = this.mProducerContext.getImageRequest();
            PooledByteBufferOutputStream outputStream = ResizeAndRotateProducer.this.mPooledByteBufferFactory.newOutputStream();
            Map<String, String> extraMap = null;
            InputStream is = null;
            try {
                try {
                    int numerator = ResizeAndRotateProducer.getScaleNumerator(imageRequest, encodedImage);
                    extraMap = getExtraMap(encodedImage, imageRequest, numerator);
                    is = encodedImage.getInputStream();
                    JpegTranscoder.transcodeJpeg(is, outputStream, ResizeAndRotateProducer.getRotationAngle(imageRequest, encodedImage), numerator, 85);
                    CloseableReference<PooledByteBuffer> ref = CloseableReference.of(outputStream.toByteBuffer());
                    try {
                        EncodedImage ret = new EncodedImage(ref);
                        try {
                            ret.setImageFormat(ImageFormat.JPEG);
                            try {
                                ret.parseMetaData();
                                this.mProducerContext.getListener().onProducerFinishWithSuccess(this.mProducerContext.getId(), "ResizeAndRotateProducer", extraMap);
                                getConsumer().onNewResult(ret, isLast);
                                EncodedImage.closeSafely(ret);
                                try {
                                    CloseableReference.closeSafely(ref);
                                    Closeables.closeQuietly(is);
                                    outputStream.close();
                                } catch (Exception e) {
                                    e = e;
                                    this.mProducerContext.getListener().onProducerFinishWithFailure(this.mProducerContext.getId(), "ResizeAndRotateProducer", e, extraMap);
                                    getConsumer().onFailure(e);
                                    Closeables.closeQuietly(is);
                                    outputStream.close();
                                } catch (Throwable th) {
                                    th = th;
                                    Closeables.closeQuietly(is);
                                    outputStream.close();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                EncodedImage.closeSafely(ret);
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            CloseableReference.closeSafely(ref);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        }

        private Map<String, String> getExtraMap(EncodedImage encodedImage, ImageRequest imageRequest, int numerator) {
            String requestedSize;
            if (!this.mProducerContext.getListener().requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String originalSize = encodedImage.getWidth() + "x" + encodedImage.getHeight();
            if (imageRequest.getResizeOptions() != null) {
                requestedSize = imageRequest.getResizeOptions().width + "x" + imageRequest.getResizeOptions().height;
            } else {
                requestedSize = "Unspecified";
            }
            String fraction = numerator > 0 ? numerator + "/8" : Constants.MAIN_VERSION_TAG;
            return ImmutableMap.of("Original size", originalSize, "Requested size", requestedSize, "Fraction", fraction, "queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TriState shouldTransform(ImageRequest request, EncodedImage encodedImage) {
        if (encodedImage == null || encodedImage.getImageFormat() == ImageFormat.UNKNOWN) {
            return TriState.UNSET;
        }
        if (encodedImage.getImageFormat() != ImageFormat.JPEG) {
            return TriState.NO;
        }
        return TriState.valueOf(getRotationAngle(request, encodedImage) != 0 || shouldResize(getScaleNumerator(request, encodedImage)));
    }

    static float determineResizeRatio(ResizeOptions resizeOptions, int width, int height) {
        if (resizeOptions == null) {
            return 1.0f;
        }
        float widthRatio = resizeOptions.width / width;
        float heightRatio = resizeOptions.height / height;
        float ratio = Math.max(widthRatio, heightRatio);
        if (width * ratio > 2048.0f) {
            ratio = 2048.0f / width;
        }
        if (height * ratio > 2048.0f) {
            return 2048.0f / height;
        }
        return ratio;
    }

    static int roundNumerator(float maxRatio) {
        return (int) (0.6666667f + (8.0f * maxRatio));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getScaleNumerator(ImageRequest imageRequest, EncodedImage encodedImage) {
        ResizeOptions resizeOptions = imageRequest.getResizeOptions();
        if (resizeOptions == null) {
            return 8;
        }
        int rotationAngle = getRotationAngle(imageRequest, encodedImage);
        boolean swapDimensions = rotationAngle == 90 || rotationAngle == 270;
        int widthAfterRotation = swapDimensions ? encodedImage.getHeight() : encodedImage.getWidth();
        int heightAfterRotation = swapDimensions ? encodedImage.getWidth() : encodedImage.getHeight();
        float ratio = determineResizeRatio(resizeOptions, widthAfterRotation, heightAfterRotation);
        int numerator = roundNumerator(ratio);
        if (numerator > 8) {
            return 8;
        }
        if (numerator < 1) {
            return 1;
        }
        return numerator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRotationAngle(ImageRequest imageRequest, EncodedImage encodedImage) {
        if (!imageRequest.getAutoRotateEnabled()) {
            return 0;
        }
        int rotationAngle = encodedImage.getRotationAngle();
        Preconditions.checkArgument(rotationAngle == 0 || rotationAngle == 90 || rotationAngle == 180 || rotationAngle == 270);
        return rotationAngle;
    }

    private static boolean shouldResize(int numerator) {
        return numerator < 8;
    }
}
