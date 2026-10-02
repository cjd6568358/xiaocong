package com.facebook.react.modules.fresco;

import android.content.Context;
import com.facebook.common.logging.FLog;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.soloader.SoLoader;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FrescoModule extends ReactContextBaseJavaModule {
    private static boolean sHasBeenInitialized = false;
    private ImagePipelineConfig mConfig;

    public FrescoModule(ReactApplicationContext reactContext) {
        this(reactContext, null);
    }

    public FrescoModule(ReactApplicationContext reactContext, ImagePipelineConfig config) {
        super(reactContext);
        this.mConfig = config;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        super.initialize();
        if (!hasBeenInitialized()) {
            SoLoaderShim.setHandler(new FrescoHandler());
            if (this.mConfig == null) {
                this.mConfig = getDefaultConfig(getReactApplicationContext());
            }
            Context context = getReactApplicationContext().getApplicationContext();
            Fresco.initialize(context, this.mConfig);
            sHasBeenInitialized = true;
        } else if (this.mConfig != null) {
            FLog.w("React", "Fresco has already been initialized with a different config. The new Fresco configuration will be ignored!");
        }
        this.mConfig = null;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "FrescoModule";
    }

    public void clearSensitiveData() {
        Fresco.getImagePipeline().clearCaches();
    }

    public static boolean hasBeenInitialized() {
        return sHasBeenInitialized;
    }

    private static ImagePipelineConfig getDefaultConfig(Context context) {
        return getDefaultConfigBuilder(context).build();
    }

    public static ImagePipelineConfig.Builder getDefaultConfigBuilder(Context context) {
        HashSet<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new SystraceRequestListener());
        return OkHttpImagePipelineConfigFactory.newBuilder(context.getApplicationContext(), OkHttpClientProvider.getOkHttpClient()).setDownsampleEnabled(false).setRequestListeners(requestListeners);
    }

    private static class FrescoHandler implements SoLoaderShim.Handler {
        private FrescoHandler() {
        }

        @Override // com.facebook.common.soloader.SoLoaderShim.Handler
        public void loadLibrary(String libraryName) {
            SoLoader.loadLibrary(libraryName);
        }
    }
}
