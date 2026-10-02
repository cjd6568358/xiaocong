package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import com.tencent.android.tpush.common.Constants;
import java.util.Queue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class GenericRequest<A, T, Z, R> implements Request, ResourceCallback, SizeReadyCallback {
    private static final Queue<GenericRequest<?, ?, ?, ?>> REQUEST_POOL = Util.createQueue(0);
    private GlideAnimationFactory<R> animationFactory;
    private Context context;
    private DiskCacheStrategy diskCacheStrategy;
    private Engine engine;
    private Drawable errorDrawable;
    private int errorResourceId;
    private Drawable fallbackDrawable;
    private int fallbackResourceId;
    private boolean isMemoryCacheable;
    private LoadProvider<A, T, Z, R> loadProvider;
    private Engine.LoadStatus loadStatus;
    private boolean loadedFromMemoryCache;
    private A model;
    private int overrideHeight;
    private int overrideWidth;
    private Drawable placeholderDrawable;
    private int placeholderResourceId;
    private Priority priority;
    private RequestCoordinator requestCoordinator;
    private RequestListener<? super A, R> requestListener;
    private Resource<?> resource;
    private Key signature;
    private float sizeMultiplier;
    private long startTime;
    private Status status;
    private final String tag = String.valueOf(hashCode());
    private Target<R> target;
    private Class<R> transcodeClass;
    private Transformation<Z> transformation;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CANCELLED,
        CLEARED,
        PAUSED
    }

    public static <A, T, Z, R> GenericRequest<A, T, Z, R> obtain(LoadProvider<A, T, Z, R> loadProvider, A model, Key signature, Context context, Priority priority, Target<R> target, float sizeMultiplier, Drawable placeholderDrawable, int placeholderResourceId, Drawable errorDrawable, int errorResourceId, Drawable fallbackDrawable, int fallbackResourceId, RequestListener<? super A, R> requestListener, RequestCoordinator requestCoordinator, Engine engine, Transformation<Z> transformation, Class<R> transcodeClass, boolean isMemoryCacheable, GlideAnimationFactory<R> animationFactory, int overrideWidth, int overrideHeight, DiskCacheStrategy diskCacheStrategy) {
        GenericRequest<A, T, Z, R> request = (GenericRequest) REQUEST_POOL.poll();
        if (request == null) {
            request = new GenericRequest<>();
        }
        request.init(loadProvider, model, signature, context, priority, target, sizeMultiplier, placeholderDrawable, placeholderResourceId, errorDrawable, errorResourceId, fallbackDrawable, fallbackResourceId, requestListener, requestCoordinator, engine, transformation, transcodeClass, isMemoryCacheable, animationFactory, overrideWidth, overrideHeight, diskCacheStrategy);
        return request;
    }

    private GenericRequest() {
    }

    @Override // com.bumptech.glide.request.Request
    public void recycle() {
        this.loadProvider = null;
        this.model = null;
        this.context = null;
        this.target = null;
        this.placeholderDrawable = null;
        this.errorDrawable = null;
        this.fallbackDrawable = null;
        this.requestListener = null;
        this.requestCoordinator = null;
        this.transformation = null;
        this.animationFactory = null;
        this.loadedFromMemoryCache = false;
        this.loadStatus = null;
        REQUEST_POOL.offer(this);
    }

    private void init(LoadProvider<A, T, Z, R> loadProvider, A model, Key signature, Context context, Priority priority, Target<R> target, float sizeMultiplier, Drawable placeholderDrawable, int placeholderResourceId, Drawable errorDrawable, int errorResourceId, Drawable fallbackDrawable, int fallbackResourceId, RequestListener<? super A, R> requestListener, RequestCoordinator requestCoordinator, Engine engine, Transformation<Z> transformation, Class<R> transcodeClass, boolean isMemoryCacheable, GlideAnimationFactory<R> animationFactory, int overrideWidth, int overrideHeight, DiskCacheStrategy diskCacheStrategy) {
        this.loadProvider = loadProvider;
        this.model = model;
        this.signature = signature;
        this.fallbackDrawable = fallbackDrawable;
        this.fallbackResourceId = fallbackResourceId;
        this.context = context.getApplicationContext();
        this.priority = priority;
        this.target = target;
        this.sizeMultiplier = sizeMultiplier;
        this.placeholderDrawable = placeholderDrawable;
        this.placeholderResourceId = placeholderResourceId;
        this.errorDrawable = errorDrawable;
        this.errorResourceId = errorResourceId;
        this.requestListener = requestListener;
        this.requestCoordinator = requestCoordinator;
        this.engine = engine;
        this.transformation = transformation;
        this.transcodeClass = transcodeClass;
        this.isMemoryCacheable = isMemoryCacheable;
        this.animationFactory = animationFactory;
        this.overrideWidth = overrideWidth;
        this.overrideHeight = overrideHeight;
        this.diskCacheStrategy = diskCacheStrategy;
        this.status = Status.PENDING;
        if (model != null) {
            check("ModelLoader", loadProvider.getModelLoader(), "try .using(ModelLoader)");
            check("Transcoder", loadProvider.getTranscoder(), "try .as*(Class).transcode(ResourceTranscoder)");
            check("Transformation", transformation, "try .transform(UnitTransformation.get())");
            if (diskCacheStrategy.cacheSource()) {
                check("SourceEncoder", loadProvider.getSourceEncoder(), "try .sourceEncoder(Encoder) or .diskCacheStrategy(NONE/RESULT)");
            } else {
                check("SourceDecoder", loadProvider.getSourceDecoder(), "try .decoder/.imageDecoder/.videoDecoder(ResourceDecoder) or .diskCacheStrategy(ALL/SOURCE)");
            }
            if (diskCacheStrategy.cacheSource() || diskCacheStrategy.cacheResult()) {
                check("CacheDecoder", loadProvider.getCacheDecoder(), "try .cacheDecoder(ResouceDecoder) or .diskCacheStrategy(NONE)");
            }
            if (diskCacheStrategy.cacheResult()) {
                check("Encoder", loadProvider.getEncoder(), "try .encode(ResourceEncoder) or .diskCacheStrategy(NONE/SOURCE)");
            }
        }
    }

    private static void check(String name, Object object, String suggestion) {
        if (object == null) {
            StringBuilder message = new StringBuilder(name);
            message.append(" must not be null");
            if (suggestion != null) {
                message.append(", ");
                message.append(suggestion);
            }
            throw new NullPointerException(message.toString());
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        this.startTime = LogTime.getLogTime();
        if (this.model == null) {
            onException(null);
            return;
        }
        this.status = Status.WAITING_FOR_SIZE;
        if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight)) {
            onSizeReady(this.overrideWidth, this.overrideHeight);
        } else {
            this.target.getSize(this);
        }
        if (!isComplete() && !isFailed() && canNotifyStatusChanged()) {
            this.target.onLoadStarted(getPlaceholderDrawable());
        }
        if (Log.isLoggable("GenericRequest", 2)) {
            logV("finished run method in " + LogTime.getElapsedMillis(this.startTime));
        }
    }

    void cancel() {
        this.status = Status.CANCELLED;
        if (this.loadStatus != null) {
            this.loadStatus.cancel();
            this.loadStatus = null;
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        Util.assertMainThread();
        if (this.status != Status.CLEARED) {
            cancel();
            if (this.resource != null) {
                releaseResource(this.resource);
            }
            if (canNotifyStatusChanged()) {
                this.target.onLoadCleared(getPlaceholderDrawable());
            }
            this.status = Status.CLEARED;
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        clear();
        this.status = Status.PAUSED;
    }

    private void releaseResource(Resource resource) {
        this.engine.release(resource);
        this.resource = null;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        return this.status == Status.RUNNING || this.status == Status.WAITING_FOR_SIZE;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isResourceSet() {
        return isComplete();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCancelled() {
        return this.status == Status.CANCELLED || this.status == Status.CLEARED;
    }

    public boolean isFailed() {
        return this.status == Status.FAILED;
    }

    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null && this.fallbackResourceId > 0) {
            this.fallbackDrawable = this.context.getResources().getDrawable(this.fallbackResourceId);
        }
        return this.fallbackDrawable;
    }

    private void setErrorPlaceholder(Exception e) {
        if (canNotifyStatusChanged()) {
            Drawable error = this.model == null ? getFallbackDrawable() : null;
            if (error == null) {
                error = getErrorDrawable();
            }
            if (error == null) {
                error = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(e, error);
        }
    }

    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null && this.errorResourceId > 0) {
            this.errorDrawable = this.context.getResources().getDrawable(this.errorResourceId);
        }
        return this.errorDrawable;
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null && this.placeholderResourceId > 0) {
            this.placeholderDrawable = this.context.getResources().getDrawable(this.placeholderResourceId);
        }
        return this.placeholderDrawable;
    }

    @Override // com.bumptech.glide.request.target.SizeReadyCallback
    public void onSizeReady(int width, int height) {
        if (Log.isLoggable("GenericRequest", 2)) {
            logV("Got onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
        }
        if (this.status == Status.WAITING_FOR_SIZE) {
            this.status = Status.RUNNING;
            int width2 = Math.round(this.sizeMultiplier * width);
            int height2 = Math.round(this.sizeMultiplier * height);
            ModelLoader<A, T> modelLoader = this.loadProvider.getModelLoader();
            DataFetcher<T> dataFetcher = modelLoader.getResourceFetcher(this.model, width2, height2);
            if (dataFetcher == null) {
                onException(new Exception("Failed to load model: '" + this.model + "'"));
                return;
            }
            ResourceTranscoder<Z, R> transcoder = this.loadProvider.getTranscoder();
            if (Log.isLoggable("GenericRequest", 2)) {
                logV("finished setup for calling load in " + LogTime.getElapsedMillis(this.startTime));
            }
            this.loadedFromMemoryCache = true;
            this.loadStatus = this.engine.load(this.signature, width2, height2, dataFetcher, this.loadProvider, this.transformation, transcoder, this.priority, this.isMemoryCacheable, this.diskCacheStrategy, this);
            this.loadedFromMemoryCache = this.resource != null;
            if (Log.isLoggable("GenericRequest", 2)) {
                logV("finished onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
            }
        }
    }

    private boolean canSetResource() {
        return this.requestCoordinator == null || this.requestCoordinator.canSetImage(this);
    }

    private boolean canNotifyStatusChanged() {
        return this.requestCoordinator == null || this.requestCoordinator.canNotifyStatusChanged(this);
    }

    private boolean isFirstReadyResource() {
        return this.requestCoordinator == null || !this.requestCoordinator.isAnyResourceSet();
    }

    private void notifyLoadSuccess() {
        if (this.requestCoordinator != null) {
            this.requestCoordinator.onRequestSuccess(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.request.ResourceCallback
    public void onResourceReady(Resource<?> resource) {
        if (resource == null) {
            onException(new Exception("Expected to receive a Resource<R> with an object of " + this.transcodeClass + " inside, but instead got null."));
            return;
        }
        Object received = resource.get();
        if (received == null || !this.transcodeClass.isAssignableFrom(received.getClass())) {
            releaseResource(resource);
            onException(new Exception("Expected to receive an object of " + this.transcodeClass + " but instead got " + (received != null ? received.getClass() : Constants.MAIN_VERSION_TAG) + "{" + received + "} inside Resource{" + resource + "}." + (received != null ? Constants.MAIN_VERSION_TAG : " To indicate failure return a null Resource object, rather than a Resource object containing null data.")));
        } else if (!canSetResource()) {
            releaseResource(resource);
            this.status = Status.COMPLETE;
        } else {
            onResourceReady(resource, received);
        }
    }

    private void onResourceReady(Resource<?> resource, R r) {
        boolean zIsFirstReadyResource = isFirstReadyResource();
        this.status = Status.COMPLETE;
        this.resource = resource;
        if (this.requestListener == null || !this.requestListener.onResourceReady(r, this.model, this.target, this.loadedFromMemoryCache, zIsFirstReadyResource)) {
            this.target.onResourceReady(r, this.animationFactory.build(this.loadedFromMemoryCache, zIsFirstReadyResource));
        }
        notifyLoadSuccess();
        if (Log.isLoggable("GenericRequest", 2)) {
            logV("Resource ready in " + LogTime.getElapsedMillis(this.startTime) + " size: " + (((double) resource.getSize()) * 9.5367431640625E-7d) + " fromCache: " + this.loadedFromMemoryCache);
        }
    }

    @Override // com.bumptech.glide.request.ResourceCallback
    public void onException(Exception exc) {
        if (Log.isLoggable("GenericRequest", 3)) {
            Log.d("GenericRequest", "load failed", exc);
        }
        this.status = Status.FAILED;
        if (this.requestListener == null || !this.requestListener.onException(exc, this.model, this.target, isFirstReadyResource())) {
            setErrorPlaceholder(exc);
        }
    }

    private void logV(String message) {
        Log.v("GenericRequest", message + " this: " + this.tag);
    }
}
