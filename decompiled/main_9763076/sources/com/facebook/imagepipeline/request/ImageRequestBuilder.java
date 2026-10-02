package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageRequestBuilder {
    private Uri mSourceUri = null;
    private ImageRequest.RequestLevel mLowestPermittedRequestLevel = ImageRequest.RequestLevel.FULL_FETCH;
    private boolean mAutoRotateEnabled = false;
    private ResizeOptions mResizeOptions = null;
    private ImageDecodeOptions mImageDecodeOptions = ImageDecodeOptions.defaults();
    private ImageRequest.ImageType mImageType = ImageRequest.ImageType.DEFAULT;
    private boolean mProgressiveRenderingEnabled = false;
    private boolean mLocalThumbnailPreviewsEnabled = false;
    private Priority mRequestPriority = Priority.HIGH;
    private Postprocessor mPostprocessor = null;
    private boolean mDiskCacheEnabled = true;

    public static ImageRequestBuilder newBuilderWithSource(Uri uri) {
        return new ImageRequestBuilder().setSource(uri);
    }

    private ImageRequestBuilder() {
    }

    public ImageRequestBuilder setSource(Uri uri) {
        Preconditions.checkNotNull(uri);
        this.mSourceUri = uri;
        return this;
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public ImageRequestBuilder setAutoRotateEnabled(boolean enabled) {
        this.mAutoRotateEnabled = enabled;
        return this;
    }

    public boolean isAutoRotateEnabled() {
        return this.mAutoRotateEnabled;
    }

    public ImageRequestBuilder setResizeOptions(ResizeOptions resizeOptions) {
        this.mResizeOptions = resizeOptions;
        return this;
    }

    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public ImageRequest.ImageType getImageType() {
        return this.mImageType;
    }

    public ImageRequestBuilder setProgressiveRenderingEnabled(boolean enabled) {
        this.mProgressiveRenderingEnabled = enabled;
        return this;
    }

    public boolean isProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    public boolean isLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public boolean isDiskCacheEnabled() {
        return this.mDiskCacheEnabled && UriUtil.isNetworkUri(this.mSourceUri);
    }

    public Priority getRequestPriority() {
        return this.mRequestPriority;
    }

    public ImageRequestBuilder setPostprocessor(Postprocessor postprocessor) {
        this.mPostprocessor = postprocessor;
        return this;
    }

    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    public ImageRequest build() {
        validate();
        return new ImageRequest(this);
    }

    public static class BuilderException extends RuntimeException {
        public BuilderException(String message) {
            super("Invalid request builder: " + message);
        }
    }

    protected void validate() {
        if (this.mSourceUri == null) {
            throw new BuilderException("Source must be set!");
        }
        if (UriUtil.isLocalResourceUri(this.mSourceUri)) {
            if (!this.mSourceUri.isAbsolute()) {
                throw new BuilderException("Resource URI path must be absolute.");
            }
            if (this.mSourceUri.getPath().isEmpty()) {
                throw new BuilderException("Resource URI must not be empty");
            }
            try {
                Integer.parseInt(this.mSourceUri.getPath().substring(1));
            } catch (NumberFormatException e) {
                throw new BuilderException("Resource URI path must be a resource id.");
            }
        }
        if (UriUtil.isLocalAssetUri(this.mSourceUri) && !this.mSourceUri.isAbsolute()) {
            throw new BuilderException("Asset URI path must be absolute.");
        }
    }
}
