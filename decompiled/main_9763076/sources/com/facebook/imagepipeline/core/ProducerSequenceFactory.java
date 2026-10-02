package com.facebook.imagepipeline.core;

import android.net.Uri;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.media.MediaUtils;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.DecodeProducer;
import com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer;
import com.facebook.imagepipeline.producers.LocalAssetFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriFetchProducer;
import com.facebook.imagepipeline.producers.LocalFileFetchProducer;
import com.facebook.imagepipeline.producers.LocalResourceFetchProducer;
import com.facebook.imagepipeline.producers.LocalVideoThumbnailProducer;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.PostprocessedBitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.PostprocessorProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.producers.ThrottlingProducer;
import com.facebook.imagepipeline.producers.ThumbnailBranchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProducerSequenceFactory {
    Producer<EncodedImage> mBackgroundNetworkFetchToEncodedMemorySequence;
    private Producer<EncodedImage> mCommonNetworkFetchToEncodedMemorySequence;
    Producer<CloseableReference<CloseableImage>> mDataFetchSequence;
    private final boolean mDownsampleEnabled;
    Producer<CloseableReference<CloseableImage>> mLocalAssetFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalContentUriFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalImageFileFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalResourceFetchSequence;
    Producer<CloseableReference<CloseableImage>> mLocalVideoFileFetchSequence;
    Producer<CloseableReference<CloseableImage>> mNetworkFetchSequence;
    Producer<Void> mNetworkFetchToEncodedMemoryPrefetchSequence;
    private final NetworkFetcher mNetworkFetcher;
    private final ProducerFactory mProducerFactory;
    private final boolean mResizeAndRotateEnabledForNetwork;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;
    private final boolean mWebpSupportEnabled;
    Map<Producer<CloseableReference<CloseableImage>>, Producer<CloseableReference<CloseableImage>>> mPostprocessorSequences = new HashMap();
    Map<Producer<CloseableReference<CloseableImage>>, Producer<Void>> mCloseableImagePrefetchSequences = new HashMap();

    public ProducerSequenceFactory(ProducerFactory producerFactory, NetworkFetcher networkFetcher, boolean resizeAndRotateEnabledForNetwork, boolean downsampleEnabled, boolean webpSupportEnabled, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        this.mProducerFactory = producerFactory;
        this.mNetworkFetcher = networkFetcher;
        this.mResizeAndRotateEnabledForNetwork = resizeAndRotateEnabledForNetwork;
        this.mDownsampleEnabled = downsampleEnabled;
        this.mWebpSupportEnabled = webpSupportEnabled;
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
    }

    public Producer<Void> getEncodedImagePrefetchProducerSequence(ImageRequest imageRequest) {
        validateEncodedImageRequest(imageRequest);
        return getNetworkFetchToEncodedMemoryPrefetchSequence();
    }

    private static void validateEncodedImageRequest(ImageRequest imageRequest) {
        Preconditions.checkNotNull(imageRequest);
        Preconditions.checkArgument(UriUtil.isNetworkUri(imageRequest.getSourceUri()));
        Preconditions.checkArgument(imageRequest.getLowestPermittedRequestLevel().getValue() <= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue());
    }

    public Producer<CloseableReference<CloseableImage>> getDecodedImageProducerSequence(ImageRequest imageRequest) {
        Producer<CloseableReference<CloseableImage>> pipelineSequence = getBasicDecodedImageSequence(imageRequest);
        if (imageRequest.getPostprocessor() != null) {
            return getPostprocessorSequence(pipelineSequence);
        }
        return pipelineSequence;
    }

    private Producer<CloseableReference<CloseableImage>> getBasicDecodedImageSequence(ImageRequest imageRequest) {
        Preconditions.checkNotNull(imageRequest);
        Uri uri = imageRequest.getSourceUri();
        Preconditions.checkNotNull(uri, "Uri is null.");
        if (UriUtil.isNetworkUri(uri)) {
            return getNetworkFetchSequence();
        }
        if (UriUtil.isLocalFileUri(uri)) {
            if (MediaUtils.isVideo(MediaUtils.extractMime(uri.getPath()))) {
                return getLocalVideoFileFetchSequence();
            }
            return getLocalImageFileFetchSequence();
        }
        if (UriUtil.isLocalContentUri(uri)) {
            return getLocalContentUriFetchSequence();
        }
        if (UriUtil.isLocalAssetUri(uri)) {
            return getLocalAssetFetchSequence();
        }
        if (UriUtil.isLocalResourceUri(uri)) {
            return getLocalResourceFetchSequence();
        }
        if (UriUtil.isDataUri(uri)) {
            return getDataFetchSequence();
        }
        String uriString = uri.toString();
        if (uriString.length() > 30) {
            uriString = uriString.substring(0, 30) + "...";
        }
        throw new RuntimeException("Unsupported uri scheme! Uri is: " + uriString);
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getNetworkFetchSequence() {
        if (this.mNetworkFetchSequence == null) {
            this.mNetworkFetchSequence = newBitmapCacheGetToDecodeSequence(getCommonNetworkFetchToEncodedMemorySequence());
        }
        return this.mNetworkFetchSequence;
    }

    private synchronized Producer<EncodedImage> getBackgroundNetworkFetchToEncodedMemorySequence() {
        if (this.mBackgroundNetworkFetchToEncodedMemorySequence == null) {
            this.mBackgroundNetworkFetchToEncodedMemorySequence = this.mProducerFactory.newBackgroundThreadHandoffProducer(getCommonNetworkFetchToEncodedMemorySequence(), this.mThreadHandoffProducerQueue);
        }
        return this.mBackgroundNetworkFetchToEncodedMemorySequence;
    }

    private synchronized Producer<Void> getNetworkFetchToEncodedMemoryPrefetchSequence() {
        if (this.mNetworkFetchToEncodedMemoryPrefetchSequence == null) {
            ProducerFactory producerFactory = this.mProducerFactory;
            this.mNetworkFetchToEncodedMemoryPrefetchSequence = ProducerFactory.newSwallowResultProducer(getBackgroundNetworkFetchToEncodedMemorySequence());
        }
        return this.mNetworkFetchToEncodedMemoryPrefetchSequence;
    }

    private synchronized Producer<EncodedImage> getCommonNetworkFetchToEncodedMemorySequence() {
        if (this.mCommonNetworkFetchToEncodedMemorySequence == null) {
            Producer<EncodedImage> inputProducer = newEncodedCacheMultiplexToTranscodeSequence(this.mProducerFactory.newNetworkFetchProducer(this.mNetworkFetcher));
            this.mCommonNetworkFetchToEncodedMemorySequence = ProducerFactory.newAddImageTransformMetaDataProducer(inputProducer);
            if (this.mResizeAndRotateEnabledForNetwork && !this.mDownsampleEnabled) {
                this.mCommonNetworkFetchToEncodedMemorySequence = this.mProducerFactory.newResizeAndRotateProducer(this.mCommonNetworkFetchToEncodedMemorySequence);
            }
        }
        return this.mCommonNetworkFetchToEncodedMemorySequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getLocalImageFileFetchSequence() {
        if (this.mLocalImageFileFetchSequence == null) {
            LocalFileFetchProducer localFileFetchProducer = this.mProducerFactory.newLocalFileFetchProducer();
            this.mLocalImageFileFetchSequence = newBitmapCacheGetToLocalTransformSequence(localFileFetchProducer);
        }
        return this.mLocalImageFileFetchSequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getLocalVideoFileFetchSequence() {
        if (this.mLocalVideoFileFetchSequence == null) {
            LocalVideoThumbnailProducer localVideoThumbnailProducer = this.mProducerFactory.newLocalVideoThumbnailProducer();
            this.mLocalVideoFileFetchSequence = newBitmapCacheGetToBitmapCacheSequence(localVideoThumbnailProducer);
        }
        return this.mLocalVideoFileFetchSequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getLocalContentUriFetchSequence() {
        if (this.mLocalContentUriFetchSequence == null) {
            LocalContentUriFetchProducer localContentUriFetchProducer = this.mProducerFactory.newLocalContentUriFetchProducer();
            ThumbnailProducer<EncodedImage>[] thumbnailProducers = {this.mProducerFactory.newLocalContentUriThumbnailFetchProducer(), this.mProducerFactory.newLocalExifThumbnailProducer()};
            this.mLocalContentUriFetchSequence = newBitmapCacheGetToLocalTransformSequence(localContentUriFetchProducer, thumbnailProducers);
        }
        return this.mLocalContentUriFetchSequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getLocalResourceFetchSequence() {
        if (this.mLocalResourceFetchSequence == null) {
            LocalResourceFetchProducer localResourceFetchProducer = this.mProducerFactory.newLocalResourceFetchProducer();
            this.mLocalResourceFetchSequence = newBitmapCacheGetToLocalTransformSequence(localResourceFetchProducer);
        }
        return this.mLocalResourceFetchSequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getLocalAssetFetchSequence() {
        if (this.mLocalAssetFetchSequence == null) {
            LocalAssetFetchProducer localAssetFetchProducer = this.mProducerFactory.newLocalAssetFetchProducer();
            this.mLocalAssetFetchSequence = newBitmapCacheGetToLocalTransformSequence(localAssetFetchProducer);
        }
        return this.mLocalAssetFetchSequence;
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getDataFetchSequence() {
        if (this.mDataFetchSequence == null) {
            Producer<EncodedImage> inputProducer = this.mProducerFactory.newDataFetchProducer();
            if (Build.VERSION.SDK_INT < 18 && !this.mWebpSupportEnabled) {
                inputProducer = this.mProducerFactory.newWebpTranscodeProducer(inputProducer);
            }
            ProducerFactory producerFactory = this.mProducerFactory;
            Producer<EncodedImage> inputProducer2 = ProducerFactory.newAddImageTransformMetaDataProducer(inputProducer);
            if (!this.mDownsampleEnabled) {
                inputProducer2 = this.mProducerFactory.newResizeAndRotateProducer(inputProducer2);
            }
            this.mDataFetchSequence = newBitmapCacheGetToDecodeSequence(inputProducer2);
        }
        return this.mDataFetchSequence;
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> inputProducer) {
        ThumbnailProducer<EncodedImage>[] defaultThumbnailProducers = {this.mProducerFactory.newLocalExifThumbnailProducer()};
        return newBitmapCacheGetToLocalTransformSequence(inputProducer, defaultThumbnailProducers);
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> inputProducer, ThumbnailProducer<EncodedImage>[] thumbnailProducers) {
        Producer<EncodedImage> inputProducerAfterDecode = newLocalTransformationsSequence(newEncodedCacheMultiplexToTranscodeSequence(inputProducer), thumbnailProducers);
        return newBitmapCacheGetToDecodeSequence(inputProducerAfterDecode);
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToDecodeSequence(Producer<EncodedImage> inputProducer) {
        DecodeProducer decodeProducer = this.mProducerFactory.newDecodeProducer(inputProducer);
        return newBitmapCacheGetToBitmapCacheSequence(decodeProducer);
    }

    private Producer<EncodedImage> newEncodedCacheMultiplexToTranscodeSequence(Producer<EncodedImage> inputProducer) {
        if (Build.VERSION.SDK_INT < 18 && !this.mWebpSupportEnabled) {
            inputProducer = this.mProducerFactory.newWebpTranscodeProducer(inputProducer);
        }
        EncodedMemoryCacheProducer encodedMemoryCacheProducer = this.mProducerFactory.newEncodedMemoryCacheProducer(this.mProducerFactory.newDiskCacheProducer(inputProducer));
        return this.mProducerFactory.newEncodedCacheKeyMultiplexProducer(encodedMemoryCacheProducer);
    }

    private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToBitmapCacheSequence(Producer<CloseableReference<CloseableImage>> inputProducer) {
        BitmapMemoryCacheProducer bitmapMemoryCacheProducer = this.mProducerFactory.newBitmapMemoryCacheProducer(inputProducer);
        BitmapMemoryCacheKeyMultiplexProducer bitmapKeyMultiplexProducer = this.mProducerFactory.newBitmapMemoryCacheKeyMultiplexProducer(bitmapMemoryCacheProducer);
        ThreadHandoffProducer<CloseableReference<CloseableImage>> threadHandoffProducer = this.mProducerFactory.newBackgroundThreadHandoffProducer(bitmapKeyMultiplexProducer, this.mThreadHandoffProducerQueue);
        return this.mProducerFactory.newBitmapMemoryCacheGetProducer(threadHandoffProducer);
    }

    private Producer<EncodedImage> newLocalTransformationsSequence(Producer<EncodedImage> inputProducer, ThumbnailProducer<EncodedImage>[] thumbnailProducers) {
        Producer<EncodedImage> localImageProducer = ProducerFactory.newAddImageTransformMetaDataProducer(inputProducer);
        if (!this.mDownsampleEnabled) {
            localImageProducer = this.mProducerFactory.newResizeAndRotateProducer(localImageProducer);
        }
        ThrottlingProducer<EncodedImage> localImageThrottlingProducer = this.mProducerFactory.newThrottlingProducer(5, localImageProducer);
        ProducerFactory producerFactory = this.mProducerFactory;
        return ProducerFactory.newBranchOnSeparateImagesProducer(newLocalThumbnailProducer(thumbnailProducers), localImageThrottlingProducer);
    }

    private Producer<EncodedImage> newLocalThumbnailProducer(ThumbnailProducer<EncodedImage>[] thumbnailProducers) {
        ThumbnailBranchProducer thumbnailBranchProducer = this.mProducerFactory.newThumbnailBranchProducer(thumbnailProducers);
        return this.mDownsampleEnabled ? thumbnailBranchProducer : this.mProducerFactory.newResizeAndRotateProducer(thumbnailBranchProducer);
    }

    private synchronized Producer<CloseableReference<CloseableImage>> getPostprocessorSequence(Producer<CloseableReference<CloseableImage>> inputProducer) {
        if (!this.mPostprocessorSequences.containsKey(inputProducer)) {
            PostprocessorProducer postprocessorProducer = this.mProducerFactory.newPostprocessorProducer(inputProducer);
            PostprocessedBitmapMemoryCacheProducer postprocessedBitmapMemoryCacheProducer = this.mProducerFactory.newPostprocessorBitmapMemoryCacheProducer(postprocessorProducer);
            this.mPostprocessorSequences.put(inputProducer, postprocessedBitmapMemoryCacheProducer);
        }
        return this.mPostprocessorSequences.get(inputProducer);
    }
}
