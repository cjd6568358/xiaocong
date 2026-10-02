package com.bumptech.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.BitmapPreFiller;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorFileLoader;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorResourceLoader;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorUriLoader;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.load.model.stream.StreamByteArrayLoader;
import com.bumptech.glide.load.model.stream.StreamFileLoader;
import com.bumptech.glide.load.model.stream.StreamResourceLoader;
import com.bumptech.glide.load.model.stream.StreamStringLoader;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.model.stream.StreamUrlLoader;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDataLoadProvider;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.bitmap.ImageVideoDataLoadProvider;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDataLoadProvider;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.file.StreamFileDataLoadProvider;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableLoadProvider;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapperTransformation;
import com.bumptech.glide.load.resource.gifbitmap.ImageVideoGifDrawableLoadProvider;
import com.bumptech.glide.load.resource.transcode.GifBitmapWrapperDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.GlideBitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.load.resource.transcode.TranscoderRegistry;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.ManifestParser;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.provider.DataLoadProviderRegistry;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Glide {
    private static volatile Glide glide;
    private final CenterCrop bitmapCenterCrop;
    private final FitCenter bitmapFitCenter;
    private final BitmapPool bitmapPool;
    private final BitmapPreFiller bitmapPreFiller;
    private final DecodeFormat decodeFormat;
    private final GifBitmapWrapperTransformation drawableCenterCrop;
    private final GifBitmapWrapperTransformation drawableFitCenter;
    private final Engine engine;
    private final GenericLoaderFactory loaderFactory;
    private final MemoryCache memoryCache;
    private final ImageViewTargetFactory imageViewTargetFactory = new ImageViewTargetFactory();
    private final TranscoderRegistry transcoderRegistry = new TranscoderRegistry();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final DataLoadProviderRegistry dataLoadProviderRegistry = new DataLoadProviderRegistry();

    public static Glide get(Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    Context applicationContext = context.getApplicationContext();
                    List<GlideModule> modules = new ManifestParser(applicationContext).parse();
                    GlideBuilder builder = new GlideBuilder(applicationContext);
                    for (GlideModule module : modules) {
                        module.applyOptions(applicationContext, builder);
                    }
                    glide = builder.createGlide();
                    for (GlideModule module2 : modules) {
                        module2.registerComponents(applicationContext, glide);
                    }
                }
            }
        }
        return glide;
    }

    Glide(Engine engine, MemoryCache memoryCache, BitmapPool bitmapPool, Context context, DecodeFormat decodeFormat) {
        this.engine = engine;
        this.bitmapPool = bitmapPool;
        this.memoryCache = memoryCache;
        this.decodeFormat = decodeFormat;
        this.loaderFactory = new GenericLoaderFactory(context);
        this.bitmapPreFiller = new BitmapPreFiller(memoryCache, bitmapPool, decodeFormat);
        StreamBitmapDataLoadProvider streamBitmapLoadProvider = new StreamBitmapDataLoadProvider(bitmapPool, decodeFormat);
        this.dataLoadProviderRegistry.register(InputStream.class, Bitmap.class, streamBitmapLoadProvider);
        FileDescriptorBitmapDataLoadProvider fileDescriptorLoadProvider = new FileDescriptorBitmapDataLoadProvider(bitmapPool, decodeFormat);
        this.dataLoadProviderRegistry.register(ParcelFileDescriptor.class, Bitmap.class, fileDescriptorLoadProvider);
        ImageVideoDataLoadProvider imageVideoDataLoadProvider = new ImageVideoDataLoadProvider(streamBitmapLoadProvider, fileDescriptorLoadProvider);
        this.dataLoadProviderRegistry.register(ImageVideoWrapper.class, Bitmap.class, imageVideoDataLoadProvider);
        GifDrawableLoadProvider gifDrawableLoadProvider = new GifDrawableLoadProvider(context, bitmapPool);
        this.dataLoadProviderRegistry.register(InputStream.class, GifDrawable.class, gifDrawableLoadProvider);
        this.dataLoadProviderRegistry.register(ImageVideoWrapper.class, GifBitmapWrapper.class, new ImageVideoGifDrawableLoadProvider(imageVideoDataLoadProvider, gifDrawableLoadProvider, bitmapPool));
        this.dataLoadProviderRegistry.register(InputStream.class, File.class, new StreamFileDataLoadProvider());
        register(File.class, ParcelFileDescriptor.class, new FileDescriptorFileLoader.Factory());
        register(File.class, InputStream.class, new StreamFileLoader.Factory());
        register(Integer.TYPE, ParcelFileDescriptor.class, new FileDescriptorResourceLoader.Factory());
        register(Integer.TYPE, InputStream.class, new StreamResourceLoader.Factory());
        register(Integer.class, ParcelFileDescriptor.class, new FileDescriptorResourceLoader.Factory());
        register(Integer.class, InputStream.class, new StreamResourceLoader.Factory());
        register(String.class, ParcelFileDescriptor.class, new FileDescriptorStringLoader.Factory());
        register(String.class, InputStream.class, new StreamStringLoader.Factory());
        register(Uri.class, ParcelFileDescriptor.class, new FileDescriptorUriLoader.Factory());
        register(Uri.class, InputStream.class, new StreamUriLoader.Factory());
        register(URL.class, InputStream.class, new StreamUrlLoader.Factory());
        register(GlideUrl.class, InputStream.class, new HttpUrlGlideUrlLoader.Factory());
        register(byte[].class, InputStream.class, new StreamByteArrayLoader.Factory());
        this.transcoderRegistry.register(Bitmap.class, GlideBitmapDrawable.class, new GlideBitmapDrawableTranscoder(context.getResources(), bitmapPool));
        this.transcoderRegistry.register(GifBitmapWrapper.class, GlideDrawable.class, new GifBitmapWrapperDrawableTranscoder(new GlideBitmapDrawableTranscoder(context.getResources(), bitmapPool)));
        this.bitmapCenterCrop = new CenterCrop(bitmapPool);
        this.drawableCenterCrop = new GifBitmapWrapperTransformation(bitmapPool, this.bitmapCenterCrop);
        this.bitmapFitCenter = new FitCenter(bitmapPool);
        this.drawableFitCenter = new GifBitmapWrapperTransformation(bitmapPool, this.bitmapFitCenter);
    }

    public BitmapPool getBitmapPool() {
        return this.bitmapPool;
    }

    <Z, R> ResourceTranscoder<Z, R> buildTranscoder(Class<Z> decodedClass, Class<R> transcodedClass) {
        return this.transcoderRegistry.get(decodedClass, transcodedClass);
    }

    <T, Z> DataLoadProvider<T, Z> buildDataProvider(Class<T> dataClass, Class<Z> decodedClass) {
        return this.dataLoadProviderRegistry.get(dataClass, decodedClass);
    }

    <R> Target<R> buildImageViewTarget(ImageView imageView, Class<R> transcodedClass) {
        return this.imageViewTargetFactory.buildTarget(imageView, transcodedClass);
    }

    Engine getEngine() {
        return this.engine;
    }

    CenterCrop getBitmapCenterCrop() {
        return this.bitmapCenterCrop;
    }

    FitCenter getBitmapFitCenter() {
        return this.bitmapFitCenter;
    }

    GifBitmapWrapperTransformation getDrawableCenterCrop() {
        return this.drawableCenterCrop;
    }

    GifBitmapWrapperTransformation getDrawableFitCenter() {
        return this.drawableFitCenter;
    }

    DecodeFormat getDecodeFormat() {
        return this.decodeFormat;
    }

    private GenericLoaderFactory getLoaderFactory() {
        return this.loaderFactory;
    }

    public void clearMemory() {
        Util.assertMainThread();
        this.memoryCache.clearMemory();
        this.bitmapPool.clearMemory();
    }

    public void trimMemory(int level) {
        Util.assertMainThread();
        this.memoryCache.trimMemory(level);
        this.bitmapPool.trimMemory(level);
    }

    public static void clear(Target<?> target) {
        Util.assertMainThread();
        Request request = target.getRequest();
        if (request != null) {
            request.clear();
            target.setRequest(null);
        }
    }

    public <T, Y> void register(Class<T> modelClass, Class<Y> resourceClass, ModelLoaderFactory<T, Y> factory) {
        ModelLoaderFactory<T, Y> removed = this.loaderFactory.register(modelClass, resourceClass, factory);
        if (removed != null) {
            removed.teardown();
        }
    }

    public static <T, Y> ModelLoader<T, Y> buildModelLoader(Class<T> modelClass, Class<Y> resourceClass, Context context) {
        if (modelClass != null) {
            return get(context).getLoaderFactory().buildModelLoader(modelClass, resourceClass);
        }
        if (Log.isLoggable("Glide", 3)) {
            Log.d("Glide", "Unable to load null model, setting placeholder only");
        }
        return null;
    }

    public static <T> ModelLoader<T, InputStream> buildStreamModelLoader(Class<T> modelClass, Context context) {
        return buildModelLoader(modelClass, InputStream.class, context);
    }

    public static <T> ModelLoader<T, ParcelFileDescriptor> buildFileDescriptorModelLoader(Class<T> modelClass, Context context) {
        return buildModelLoader(modelClass, ParcelFileDescriptor.class, context);
    }

    public static RequestManager with(Context context) {
        RequestManagerRetriever retriever = RequestManagerRetriever.get();
        return retriever.get(context);
    }

    public static RequestManager with(Activity activity) {
        RequestManagerRetriever retriever = RequestManagerRetriever.get();
        return retriever.get(activity);
    }

    public static RequestManager with(FragmentActivity activity) {
        RequestManagerRetriever retriever = RequestManagerRetriever.get();
        return retriever.get(activity);
    }

    public static RequestManager with(Fragment fragment) {
        RequestManagerRetriever retriever = RequestManagerRetriever.get();
        return retriever.get(fragment);
    }
}
