package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.net.Uri;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PipelineDraweeControllerBuilder extends AbstractDraweeControllerBuilder<PipelineDraweeControllerBuilder, ImageRequest, CloseableReference<CloseableImage>, ImageInfo> {
    private final ImagePipeline mImagePipeline;
    private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;

    public PipelineDraweeControllerBuilder(Context context, PipelineDraweeControllerFactory pipelineDraweeControllerFactory, ImagePipeline imagePipeline, Set<ControllerListener> boundControllerListeners) {
        super(context, boundControllerListeners);
        this.mImagePipeline = imagePipeline;
        this.mPipelineDraweeControllerFactory = pipelineDraweeControllerFactory;
    }

    @Override // com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder
    public PipelineDraweeControllerBuilder setUri(Uri uri) {
        return (PipelineDraweeControllerBuilder) super.setImageRequest(ImageRequest.fromUri(uri));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeControllerBuilder
    public PipelineDraweeController obtainController() {
        DraweeController oldController = getOldController();
        if (oldController instanceof PipelineDraweeController) {
            PipelineDraweeController controller = (PipelineDraweeController) oldController;
            controller.initialize(obtainDataSourceSupplier(), generateUniqueControllerId(), getCallerContext());
            return controller;
        }
        return this.mPipelineDraweeControllerFactory.newController(obtainDataSourceSupplier(), generateUniqueControllerId(), getCallerContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeControllerBuilder
    public DataSource<CloseableReference<CloseableImage>> getDataSourceForRequest(ImageRequest imageRequest, Object callerContext, boolean bitmapCacheOnly) {
        return bitmapCacheOnly ? this.mImagePipeline.fetchImageFromBitmapCache(imageRequest, callerContext) : this.mImagePipeline.fetchDecodedImage(imageRequest, callerContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeControllerBuilder
    public PipelineDraweeControllerBuilder getThis() {
        return this;
    }
}
