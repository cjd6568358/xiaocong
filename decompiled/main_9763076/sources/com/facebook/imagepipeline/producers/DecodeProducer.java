package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    private final ByteArrayPool mByteArrayPool;
    private final boolean mDownsampleEnabled;
    private final boolean mDownsampleEnabledForNetwork;
    private final Executor mExecutor;
    private final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    public DecodeProducer(ByteArrayPool byteArrayPool, Executor executor, ImageDecoder imageDecoder, ProgressiveJpegConfig progressiveJpegConfig, boolean downsampleEnabled, boolean downsampleEnabledForNetwork, Producer<EncodedImage> inputProducer) {
        this.mByteArrayPool = (ByteArrayPool) Preconditions.checkNotNull(byteArrayPool);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mImageDecoder = (ImageDecoder) Preconditions.checkNotNull(imageDecoder);
        this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        this.mDownsampleEnabled = downsampleEnabled;
        this.mDownsampleEnabledForNetwork = downsampleEnabledForNetwork;
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        ProgressiveDecoder progressiveDecoder;
        ImageRequest imageRequest = producerContext.getImageRequest();
        if (!UriUtil.isNetworkUri(imageRequest.getSourceUri())) {
            progressiveDecoder = new LocalImagesProgressiveDecoder(consumer, producerContext);
        } else {
            ProgressiveJpegParser jpegParser = new ProgressiveJpegParser(this.mByteArrayPool);
            progressiveDecoder = new NetworkImagesProgressiveDecoder(consumer, producerContext, jpegParser, this.mProgressiveJpegConfig);
        }
        this.mInputProducer.produceResults(progressiveDecoder, producerContext);
    }

    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private final ImageDecodeOptions mImageDecodeOptions;
        private boolean mIsFinished;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;
        private final ProducerListener mProducerListener;

        protected abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        protected abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getListener();
            this.mImageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mIsFinished = false;
            JobScheduler.JobRunnable job = new JobScheduler.JobRunnable() { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.1
                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, boolean isLast) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled) {
                            ImageRequest request = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(request.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(request, encodedImage));
                            }
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, isLast);
                    }
                }
            };
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, job, this.mImageDecodeOptions.minDecodeIntervalMs);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.2
                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }
            });
        }

        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (isLast && !EncodedImage.isValid(newResult)) {
                handleError(new NullPointerException("Encoded image is not valid."));
            } else if (updateDecodeJob(newResult, isLast)) {
                if (isLast || this.mProducerContext.isIntermediateResultExpected()) {
                    this.mJobScheduler.scheduleJob();
                }
            }
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        protected void onProgressUpdateImpl(float progress) {
            super.onProgressUpdateImpl(0.99f * progress);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onFailureImpl(Throwable t) {
            handleError(t);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onCancellationImpl() {
            handleCancellation();
        }

        protected boolean updateDecodeJob(EncodedImage ref, boolean isLast) {
            return this.mJobScheduler.updateJob(ref, isLast);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doDecode(EncodedImage encodedImage, boolean isLast) {
            if (!isFinished() && EncodedImage.isValid(encodedImage)) {
                try {
                    long queueTime = this.mJobScheduler.getQueuedTime();
                    int length = isLast ? encodedImage.getSize() : getIntermediateImageEndOffset(encodedImage);
                    QualityInfo quality = isLast ? ImmutableQualityInfo.FULL_QUALITY : getQualityInfo();
                    this.mProducerListener.onProducerStart(this.mProducerContext.getId(), "DecodeProducer");
                    try {
                        CloseableImage image = DecodeProducer.this.mImageDecoder.decodeImage(encodedImage, length, quality, this.mImageDecodeOptions);
                        Map<String, String> extraMap = getExtraMap(image, queueTime, quality, isLast);
                        this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), "DecodeProducer", extraMap);
                        handleResult(image, isLast);
                        EncodedImage.closeSafely(encodedImage);
                    } catch (Exception e) {
                        Map<String, String> extraMap2 = getExtraMap(null, queueTime, quality, isLast);
                        this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), "DecodeProducer", e, extraMap2);
                        handleError(e);
                        EncodedImage.closeSafely(encodedImage);
                    }
                } catch (Throwable th) {
                    EncodedImage.closeSafely(encodedImage);
                    throw th;
                }
            }
        }

        private Map<String, String> getExtraMap(CloseableImage image, long queueTime, QualityInfo quality, boolean isFinal) {
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String queueStr = String.valueOf(queueTime);
            String qualityStr = String.valueOf(quality.isOfGoodEnoughQuality());
            String finalStr = String.valueOf(isFinal);
            String imageTypeStr = String.valueOf(this.mProducerContext.getImageRequest().getImageType());
            if (image instanceof CloseableStaticBitmap) {
                Bitmap bitmap = ((CloseableStaticBitmap) image).getUnderlyingBitmap();
                String sizeStr = bitmap.getWidth() + "x" + bitmap.getHeight();
                return ImmutableMap.of("bitmapSize", sizeStr, "queueTime", queueStr, "hasGoodQuality", qualityStr, "isFinal", finalStr, "imageType", imageTypeStr);
            }
            return ImmutableMap.of("queueTime", queueStr, "hasGoodQuality", qualityStr, "isFinal", finalStr, "imageType", imageTypeStr);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x0007 A[Catch: all -> 0x001c, DONT_GENERATE, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0009, B:9:0x0015, B:6:0x0007), top: B:15:0x0003 }] */
        private void maybeFinish(boolean shouldFinish) {
            synchronized (this) {
                if (shouldFinish) {
                    if (!this.mIsFinished) {
                        getConsumer().onProgressUpdate(1.0f);
                        this.mIsFinished = true;
                        this.mJobScheduler.clearJob();
                    }
                }
                throw th;
            }
        }

        private void handleResult(CloseableImage decodedImage, boolean isFinal) {
            CloseableReference<CloseableImage> decodedImageRef = CloseableReference.of(decodedImage);
            try {
                maybeFinish(isFinal);
                getConsumer().onNewResult(decodedImageRef, isFinal);
            } finally {
                CloseableReference.closeSafely(decodedImageRef);
            }
        }

        private void handleError(Throwable t) {
            maybeFinish(true);
            getConsumer().onFailure(t);
        }

        private void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(EncodedImage encodedImage, boolean isLast) {
            return !isLast ? false : super.updateDecodeJob(encodedImage, isLast);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.of(0, false, false);
        }
    }

    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig) {
            super(consumer, producerContext);
            this.mProgressiveJpegParser = (ProgressiveJpegParser) Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
            this.mLastScheduledScanNumber = 0;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(EncodedImage encodedImage, boolean isLast) {
            boolean ret;
            int scanNum;
            ret = super.updateDecodeJob(encodedImage, isLast);
            if (!isLast && EncodedImage.isValid(encodedImage)) {
                if (this.mProgressiveJpegParser.parseMoreData(encodedImage) && (scanNum = this.mProgressiveJpegParser.getBestScanNumber()) > this.mLastScheduledScanNumber && scanNum >= this.mProgressiveJpegConfig.getNextScanNumberToDecode(this.mLastScheduledScanNumber)) {
                    this.mLastScheduledScanNumber = scanNum;
                } else {
                    ret = false;
                }
            }
            return ret;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }
}
