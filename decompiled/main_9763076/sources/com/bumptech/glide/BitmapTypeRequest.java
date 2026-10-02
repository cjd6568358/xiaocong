package com.bumptech.glide;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.model.ImageVideoModelLoader;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.provider.FixedLoadProvider;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapTypeRequest<ModelType> extends BitmapRequestBuilder<ModelType, Bitmap> {
    private final ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader;
    private final Glide glide;
    private final RequestManager.OptionsApplier optionsApplier;
    private final ModelLoader<ModelType, InputStream> streamModelLoader;

    private static <A, R> FixedLoadProvider<A, ImageVideoWrapper, Bitmap, R> buildProvider(Glide glide, ModelLoader<A, InputStream> streamModelLoader, ModelLoader<A, ParcelFileDescriptor> fileDescriptorModelLoader, Class<R> transcodedClass, ResourceTranscoder<Bitmap, R> transcoder) {
        if (streamModelLoader == null && fileDescriptorModelLoader == null) {
            return null;
        }
        if (transcoder == null) {
            transcoder = glide.buildTranscoder(Bitmap.class, transcodedClass);
        }
        DataLoadProvider<ImageVideoWrapper, Bitmap> loadProvider = glide.buildDataProvider(ImageVideoWrapper.class, Bitmap.class);
        ImageVideoModelLoader<A> modelLoader = new ImageVideoModelLoader<>(streamModelLoader, fileDescriptorModelLoader);
        return new FixedLoadProvider<>(modelLoader, transcoder, loadProvider);
    }

    BitmapTypeRequest(GenericRequestBuilder<ModelType, ?, ?, ?> other, ModelLoader<ModelType, InputStream> streamModelLoader, ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader, RequestManager.OptionsApplier optionsApplier) {
        super(buildProvider(other.glide, streamModelLoader, fileDescriptorModelLoader, Bitmap.class, null), Bitmap.class, other);
        this.streamModelLoader = streamModelLoader;
        this.fileDescriptorModelLoader = fileDescriptorModelLoader;
        this.glide = other.glide;
        this.optionsApplier = optionsApplier;
    }
}
