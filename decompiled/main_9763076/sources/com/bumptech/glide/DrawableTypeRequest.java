package com.bumptech.glide;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.model.ImageVideoModelLoader;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.provider.FixedLoadProvider;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableTypeRequest<ModelType> extends DrawableRequestBuilder<ModelType> {
    private final ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader;
    private final RequestManager.OptionsApplier optionsApplier;
    private final ModelLoader<ModelType, InputStream> streamModelLoader;

    private static <A, Z, R> FixedLoadProvider<A, ImageVideoWrapper, Z, R> buildProvider(Glide glide, ModelLoader<A, InputStream> streamModelLoader, ModelLoader<A, ParcelFileDescriptor> fileDescriptorModelLoader, Class<Z> resourceClass, Class<R> transcodedClass, ResourceTranscoder<Z, R> transcoder) {
        if (streamModelLoader == null && fileDescriptorModelLoader == null) {
            return null;
        }
        if (transcoder == null) {
            transcoder = glide.buildTranscoder(resourceClass, transcodedClass);
        }
        DataLoadProvider<ImageVideoWrapper, Z> dataLoadProvider = glide.buildDataProvider(ImageVideoWrapper.class, resourceClass);
        ImageVideoModelLoader<A> modelLoader = new ImageVideoModelLoader<>(streamModelLoader, fileDescriptorModelLoader);
        return new FixedLoadProvider<>(modelLoader, transcoder, dataLoadProvider);
    }

    DrawableTypeRequest(Class<ModelType> modelClass, ModelLoader<ModelType, InputStream> streamModelLoader, ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader, Context context, Glide glide, RequestTracker requestTracker, Lifecycle lifecycle, RequestManager.OptionsApplier optionsApplier) {
        super(context, modelClass, buildProvider(glide, streamModelLoader, fileDescriptorModelLoader, GifBitmapWrapper.class, GlideDrawable.class, null), glide, requestTracker, lifecycle);
        this.streamModelLoader = streamModelLoader;
        this.fileDescriptorModelLoader = fileDescriptorModelLoader;
        this.optionsApplier = optionsApplier;
    }

    public BitmapTypeRequest<ModelType> asBitmap() {
        return (BitmapTypeRequest) this.optionsApplier.apply(new BitmapTypeRequest(this, this.streamModelLoader, this.fileDescriptorModelLoader, this.optionsApplier));
    }
}
