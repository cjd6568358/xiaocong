package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageRequest {
    private final boolean mAutoRotateEnabled;
    private final ImageDecodeOptions mImageDecodeOptions;
    private final ImageType mImageType;
    private final boolean mIsDiskCacheEnabled;
    private final boolean mLocalThumbnailPreviewsEnabled;
    private final RequestLevel mLowestPermittedRequestLevel;
    private final Postprocessor mPostprocessor;
    private final boolean mProgressiveRenderingEnabled;
    private final Priority mRequestPriority;
    ResizeOptions mResizeOptions;
    private File mSourceFile;
    private final Uri mSourceUri;

    public enum ImageType {
        SMALL,
        DEFAULT
    }

    public static ImageRequest fromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        return ImageRequestBuilder.newBuilderWithSource(uri).build();
    }

    protected ImageRequest(ImageRequestBuilder builder) {
        this.mResizeOptions = null;
        this.mImageType = builder.getImageType();
        this.mSourceUri = builder.getSourceUri();
        this.mProgressiveRenderingEnabled = builder.isProgressiveRenderingEnabled();
        this.mLocalThumbnailPreviewsEnabled = builder.isLocalThumbnailPreviewsEnabled();
        this.mImageDecodeOptions = builder.getImageDecodeOptions();
        this.mResizeOptions = builder.getResizeOptions();
        this.mAutoRotateEnabled = builder.isAutoRotateEnabled();
        this.mRequestPriority = builder.getRequestPriority();
        this.mLowestPermittedRequestLevel = builder.getLowestPermittedRequestLevel();
        this.mIsDiskCacheEnabled = builder.isDiskCacheEnabled();
        this.mPostprocessor = builder.getPostprocessor();
    }

    public ImageType getImageType() {
        return this.mImageType;
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public int getPreferredWidth() {
        if (this.mResizeOptions != null) {
            return this.mResizeOptions.width;
        }
        return 2048;
    }

    public int getPreferredHeight() {
        if (this.mResizeOptions != null) {
            return this.mResizeOptions.height;
        }
        return 2048;
    }

    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public boolean getAutoRotateEnabled() {
        return this.mAutoRotateEnabled;
    }

    public boolean getProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    public boolean getLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public Priority getPriority() {
        return this.mRequestPriority;
    }

    public RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public boolean isDiskCacheEnabled() {
        return this.mIsDiskCacheEnabled;
    }

    public synchronized File getSourceFile() {
        if (this.mSourceFile == null) {
            this.mSourceFile = new File(this.mSourceUri.getPath());
        }
        return this.mSourceFile;
    }

    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ImageRequest)) {
            return false;
        }
        ImageRequest request = (ImageRequest) o;
        return Objects.equal(this.mSourceUri, request.mSourceUri) && Objects.equal(this.mImageType, request.mImageType) && Objects.equal(this.mSourceFile, request.mSourceFile);
    }

    public int hashCode() {
        return Objects.hashCode(this.mImageType, this.mSourceUri, this.mSourceFile);
    }

    public enum RequestLevel {
        FULL_FETCH(1),
        DISK_CACHE(2),
        ENCODED_MEMORY_CACHE(3),
        BITMAP_MEMORY_CACHE(4);

        private int mValue;

        RequestLevel(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return this.mValue;
        }

        public static RequestLevel getMax(RequestLevel requestLevel1, RequestLevel requestLevel2) {
            return requestLevel1.getValue() > requestLevel2.getValue() ? requestLevel1 : requestLevel2;
        }
    }
}
