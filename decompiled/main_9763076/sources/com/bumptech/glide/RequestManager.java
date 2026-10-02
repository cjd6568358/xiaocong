package com.bumptech.glide;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.util.Util;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RequestManager implements LifecycleListener {
    private final Context context;
    private final Glide glide;
    private final Lifecycle lifecycle;
    private DefaultOptions options;
    private final OptionsApplier optionsApplier;
    private final RequestTracker requestTracker;
    private final RequestManagerTreeNode treeNode;

    public interface DefaultOptions {
        <T> void apply(GenericRequestBuilder<T, ?, ?, ?> genericRequestBuilder);
    }

    public RequestManager(Context context, Lifecycle lifecycle, RequestManagerTreeNode treeNode) {
        this(context, lifecycle, treeNode, new RequestTracker(), new ConnectivityMonitorFactory());
    }

    RequestManager(Context context, final Lifecycle lifecycle, RequestManagerTreeNode treeNode, RequestTracker requestTracker, ConnectivityMonitorFactory factory) {
        this.context = context.getApplicationContext();
        this.lifecycle = lifecycle;
        this.treeNode = treeNode;
        this.requestTracker = requestTracker;
        this.glide = Glide.get(context);
        this.optionsApplier = new OptionsApplier();
        ConnectivityMonitor connectivityMonitor = factory.build(context, new RequestManagerConnectivityListener(requestTracker));
        if (Util.isOnBackgroundThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.bumptech.glide.RequestManager.1
                @Override // java.lang.Runnable
                public void run() {
                    lifecycle.addListener(RequestManager.this);
                }
            });
        } else {
            lifecycle.addListener(this);
        }
        lifecycle.addListener(connectivityMonitor);
    }

    public void onTrimMemory(int level) {
        this.glide.trimMemory(level);
    }

    public void onLowMemory() {
        this.glide.clearMemory();
    }

    public void pauseRequests() {
        Util.assertMainThread();
        this.requestTracker.pauseRequests();
    }

    public void resumeRequests() {
        Util.assertMainThread();
        this.requestTracker.resumeRequests();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        resumeRequests();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        pauseRequests();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
        this.requestTracker.clearRequests();
    }

    public <A, T> GenericModelRequest<A, T> using(ModelLoader<A, T> modelLoader, Class<T> dataClass) {
        return new GenericModelRequest<>(modelLoader, dataClass);
    }

    public DrawableTypeRequest<String> load(String string) {
        return (DrawableTypeRequest) fromString().load(string);
    }

    public DrawableTypeRequest<String> fromString() {
        return loadGeneric(String.class);
    }

    private <T> DrawableTypeRequest<T> loadGeneric(Class<T> modelClass) {
        ModelLoader<T, InputStream> streamModelLoader = Glide.buildStreamModelLoader(modelClass, this.context);
        ModelLoader<T, ParcelFileDescriptor> fileDescriptorModelLoader = Glide.buildFileDescriptorModelLoader(modelClass, this.context);
        if (modelClass != null && streamModelLoader == null && fileDescriptorModelLoader == null) {
            throw new IllegalArgumentException("Unknown type " + modelClass + ". You must provide a Model of a type for which there is a registered ModelLoader, if you are using a custom model, you must first call Glide#register with a ModelLoaderFactory for your custom model class");
        }
        return (DrawableTypeRequest) this.optionsApplier.apply(new DrawableTypeRequest(modelClass, streamModelLoader, fileDescriptorModelLoader, this.context, this.glide, this.requestTracker, this.lifecycle, this.optionsApplier));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Class<T> getSafeClass(T t) {
        if (t != null) {
            return (Class<T>) t.getClass();
        }
        return null;
    }

    public final class GenericModelRequest<A, T> {
        private final Class<T> dataClass;
        private final ModelLoader<A, T> modelLoader;

        GenericModelRequest(ModelLoader<A, T> modelLoader, Class<T> dataClass) {
            this.modelLoader = modelLoader;
            this.dataClass = dataClass;
        }

        public GenericModelRequest<A, T>.GenericTypeRequest load(A model) {
            return new GenericTypeRequest(model);
        }

        public final class GenericTypeRequest {
            private final A model;
            private final Class<A> modelClass;
            private final boolean providedModel = true;

            GenericTypeRequest(A model) {
                this.model = model;
                this.modelClass = RequestManager.getSafeClass(model);
            }

            public <Z> GenericTranscodeRequest<A, T, Z> as(Class<Z> resourceClass) {
                GenericTranscodeRequest<A, T, Z> result = (GenericTranscodeRequest) RequestManager.this.optionsApplier.apply(new GenericTranscodeRequest(RequestManager.this.context, RequestManager.this.glide, this.modelClass, GenericModelRequest.this.modelLoader, GenericModelRequest.this.dataClass, resourceClass, RequestManager.this.requestTracker, RequestManager.this.lifecycle, RequestManager.this.optionsApplier));
                if (this.providedModel) {
                    result.load(this.model);
                }
                return result;
            }
        }
    }

    class OptionsApplier {
        OptionsApplier() {
        }

        public <A, X extends GenericRequestBuilder<A, ?, ?, ?>> X apply(X builder) {
            if (RequestManager.this.options != null) {
                RequestManager.this.options.apply(builder);
            }
            return builder;
        }
    }

    private static class RequestManagerConnectivityListener implements ConnectivityMonitor.ConnectivityListener {
        private final RequestTracker requestTracker;

        public RequestManagerConnectivityListener(RequestTracker requestTracker) {
            this.requestTracker = requestTracker;
        }

        @Override // com.bumptech.glide.manager.ConnectivityMonitor.ConnectivityListener
        public void onConnectivityChanged(boolean isConnected) {
            if (isConnected) {
                this.requestTracker.restartRequests();
            }
        }
    }
}
