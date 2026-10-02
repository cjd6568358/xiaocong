package com.facebook.react.modules.image;

import android.net.Uri;
import android.util.SparseArray;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageLoaderModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String ERROR_GET_SIZE_FAILURE = "E_GET_SIZE_FAILURE";
    private static final String ERROR_INVALID_URI = "E_INVALID_URI";
    private static final String ERROR_PREFETCH_FAILURE = "E_PREFETCH_FAILURE";
    private final Object mCallerContext;
    private final Object mEnqueuedRequestMonitor;
    private final SparseArray<DataSource<Void>> mEnqueuedRequests;

    public ImageLoaderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mEnqueuedRequestMonitor = new Object();
        this.mEnqueuedRequests = new SparseArray<>();
        this.mCallerContext = this;
    }

    public ImageLoaderModule(ReactApplicationContext reactContext, Object callerContext) {
        super(reactContext);
        this.mEnqueuedRequestMonitor = new Object();
        this.mEnqueuedRequests = new SparseArray<>();
        this.mCallerContext = callerContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "ImageLoader";
    }

    @ReactMethod
    public void getSize(String uriString, final Promise promise) {
        if (uriString == null || uriString.isEmpty()) {
            promise.reject(ERROR_INVALID_URI, "Cannot get the size of an image for an empty URI");
            return;
        }
        Uri uri = Uri.parse(uriString);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(request, this.mCallerContext);
        DataSubscriber<CloseableReference<CloseableImage>> dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableImage>>() { // from class: com.facebook.react.modules.image.ImageLoaderModule.1
            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource2) {
                if (dataSource2.isFinished()) {
                    CloseableReference<CloseableImage> ref = dataSource2.getResult();
                    try {
                        if (ref != null) {
                            CloseableImage image = ref.get();
                            WritableMap sizes = Arguments.createMap();
                            sizes.putInt(IMediaFormat.KEY_WIDTH, image.getWidth());
                            sizes.putInt(IMediaFormat.KEY_HEIGHT, image.getHeight());
                            promise.resolve(sizes);
                            return;
                        }
                        promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE);
                    } catch (Exception e) {
                        promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, e);
                    } finally {
                        CloseableReference.closeSafely(ref);
                    }
                }
            }

            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource2) {
                promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, dataSource2.getFailureCause());
            }
        };
        dataSource.subscribe(dataSubscriber, CallerThreadExecutor.getInstance());
    }

    @ReactMethod
    public void prefetchImage(String uriString, final int requestId, final Promise promise) {
        if (uriString == null || uriString.isEmpty()) {
            promise.reject(ERROR_INVALID_URI, "Cannot prefetch an image for an empty URI");
            return;
        }
        Uri uri = Uri.parse(uriString);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
        DataSource<Void> prefetchSource = Fresco.getImagePipeline().prefetchToDiskCache(request, this.mCallerContext);
        DataSubscriber<Void> prefetchSubscriber = new BaseDataSubscriber<Void>() { // from class: com.facebook.react.modules.image.ImageLoaderModule.2
            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onNewResultImpl(DataSource<Void> dataSource) {
                if (dataSource.isFinished()) {
                    try {
                        ImageLoaderModule.this.removeRequest(requestId);
                        promise.resolve(true);
                    } finally {
                        dataSource.close();
                    }
                }
            }

            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onFailureImpl(DataSource<Void> dataSource) {
                try {
                    ImageLoaderModule.this.removeRequest(requestId);
                    promise.reject(ImageLoaderModule.ERROR_PREFETCH_FAILURE, dataSource.getFailureCause());
                } finally {
                    dataSource.close();
                }
            }
        };
        registerRequest(requestId, prefetchSource);
        prefetchSource.subscribe(prefetchSubscriber, CallerThreadExecutor.getInstance());
    }

    @ReactMethod
    public void abortRequest(int requestId) {
        DataSource<Void> request = removeRequest(requestId);
        if (request != null) {
            request.close();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.image.ImageLoaderModule$3] */
    @ReactMethod
    public void queryCache(final ReadableArray uris, final Promise promise) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.image.ImageLoaderModule.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                WritableMap result = Arguments.createMap();
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                for (int i = 0; i < uris.size(); i++) {
                    String uriString = uris.getString(i);
                    Uri uri = Uri.parse(uriString);
                    if (imagePipeline.isInBitmapMemoryCache(uri)) {
                        result.putString(uriString, "memory");
                    } else if (imagePipeline.isInDiskCacheSync(uri)) {
                        result.putString(uriString, "disk");
                    }
                }
                promise.resolve(result);
            }
        }.executeOnExecutor(GuardedAsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private void registerRequest(int requestId, DataSource<Void> request) {
        synchronized (this.mEnqueuedRequestMonitor) {
            this.mEnqueuedRequests.put(requestId, request);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DataSource<Void> removeRequest(int requestId) {
        DataSource<Void> request;
        synchronized (this.mEnqueuedRequestMonitor) {
            request = this.mEnqueuedRequests.get(requestId);
            this.mEnqueuedRequests.remove(requestId);
        }
        return request;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        synchronized (this.mEnqueuedRequestMonitor) {
            int size = this.mEnqueuedRequests.size();
            for (int i = 0; i < size; i++) {
                DataSource<Void> enqueuedRequest = this.mEnqueuedRequests.valueAt(i);
                if (enqueuedRequest != null) {
                    enqueuedRequest.close();
                }
            }
            this.mEnqueuedRequests.clear();
        }
    }
}
