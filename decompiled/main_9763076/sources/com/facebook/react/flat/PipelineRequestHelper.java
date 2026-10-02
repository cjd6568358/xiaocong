package com.facebook.react.flat;

import android.graphics.Bitmap;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class PipelineRequestHelper implements DataSubscriber<CloseableReference<CloseableImage>> {
    private int mAttachCounter;
    private BitmapUpdateListener mBitmapUpdateListener;
    private DataSource<CloseableReference<CloseableImage>> mDataSource;
    private CloseableReference<CloseableImage> mImageRef;
    private final ImageRequest mImageRequest;

    PipelineRequestHelper(ImageRequest imageRequest) {
        this.mImageRequest = imageRequest;
    }

    void attach(BitmapUpdateListener listener) {
        this.mBitmapUpdateListener = listener;
        this.mAttachCounter++;
        if (this.mAttachCounter != 1) {
            Bitmap bitmap = getBitmap();
            if (bitmap != null) {
                listener.onSecondaryAttach(bitmap);
                return;
            }
            return;
        }
        listener.onImageLoadEvent(4);
        Assertions.assertCondition(this.mDataSource == null);
        Assertions.assertCondition(this.mImageRef == null);
        ImagePipeline imagePipeline = ImagePipelineFactory.getInstance().getImagePipeline();
        this.mDataSource = imagePipeline.fetchDecodedImage(this.mImageRequest, RCTImageView.getCallerContext());
        this.mDataSource.subscribe(this, UiThreadImmediateExecutorService.getInstance());
    }

    void detach() {
        this.mAttachCounter--;
        if (this.mAttachCounter == 0) {
            if (this.mDataSource != null) {
                this.mDataSource.close();
                this.mDataSource = null;
            }
            if (this.mImageRef != null) {
                this.mImageRef.close();
                this.mImageRef = null;
            }
            this.mBitmapUpdateListener = null;
        }
    }

    Bitmap getBitmap() {
        if (this.mImageRef == null) {
            return null;
        }
        CloseableImage closeableImage = this.mImageRef.get();
        if (!(closeableImage instanceof CloseableBitmap)) {
            this.mImageRef.close();
            this.mImageRef = null;
            return null;
        }
        return ((CloseableBitmap) closeableImage).getUnderlyingBitmap();
    }

    boolean isDetached() {
        return this.mAttachCounter == 0;
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onNewResult(DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (dataSource.isFinished()) {
            try {
                if (this.mDataSource == dataSource) {
                    this.mDataSource = null;
                    CloseableReference<CloseableImage> imageReference = dataSource.getResult();
                    if (imageReference != null) {
                        CloseableImage image = imageReference.get();
                        if (image instanceof CloseableBitmap) {
                            this.mImageRef = imageReference;
                            Bitmap bitmap = getBitmap();
                            if (bitmap != null) {
                                BitmapUpdateListener listener = (BitmapUpdateListener) Assertions.assumeNotNull(this.mBitmapUpdateListener);
                                listener.onBitmapReady(bitmap);
                                listener.onImageLoadEvent(2);
                                listener.onImageLoadEvent(3);
                            }
                        } else {
                            imageReference.close();
                        }
                    }
                }
            } finally {
                dataSource.close();
            }
        }
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onFailure(DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (this.mDataSource == dataSource) {
            ((BitmapUpdateListener) Assertions.assumeNotNull(this.mBitmapUpdateListener)).onImageLoadEvent(1);
            ((BitmapUpdateListener) Assertions.assumeNotNull(this.mBitmapUpdateListener)).onImageLoadEvent(3);
            this.mDataSource = null;
        }
        dataSource.close();
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (this.mDataSource == dataSource) {
            this.mDataSource = null;
        }
        dataSource.close();
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
    }
}
