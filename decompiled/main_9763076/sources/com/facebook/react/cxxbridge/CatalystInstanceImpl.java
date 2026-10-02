package com.facebook.react.cxxbridge;

import android.content.res.AssetManager;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ExecutorToken;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.JavaScriptModuleRegistry;
import com.facebook.react.bridge.MemoryPressure;
import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.bridge.queue.ReactQueueConfigurationImpl;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.soloader.SoLoader;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.TraceListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class CatalystInstanceImpl implements CatalystInstance {
    static final String REACT_NATIVE_LIB = "reactnativejnifb";
    private static final AtomicInteger sNextInstanceIdForTrace;
    private volatile boolean mAcceptCalls;
    private final CopyOnWriteArrayList<NotThreadSafeBridgeIdleDebugListener> mBridgeIdleListeners;
    private volatile boolean mDestroyed;
    private final HybridData mHybridData;
    private boolean mInitialized;
    private boolean mJSBundleHasLoaded;
    private final JSBundleLoader mJSBundleLoader;
    private final ArrayList<PendingJSCall> mJSCallsPendingInit;
    private final Object mJSCallsPendingInitLock;
    private final JavaScriptModuleRegistry mJSModuleRegistry;
    private final NativeModuleRegistry mJavaRegistry;
    private final String mJsPendingCallsTitleForTrace;
    private ExecutorToken mMainExecutorToken;
    private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    private final AtomicInteger mPendingJSCalls;
    private final ReactQueueConfigurationImpl mReactQueueConfiguration;
    private String mSourceURL;
    private final TraceListener mTraceListener;

    private native void callJSCallback(ExecutorToken executorToken, int i, NativeArray nativeArray);

    private native void callJSFunction(ExecutorToken executorToken, String str, String str2, NativeArray nativeArray);

    private native ExecutorToken getMainExecutorToken();

    private native void handleMemoryPressureCritical();

    private native void handleMemoryPressureModerate();

    private native void handleMemoryPressureUiHidden();

    private static native HybridData initHybrid();

    private native void initializeBridge(ReactCallback reactCallback, JavaScriptExecutor javaScriptExecutor, MessageQueueThread messageQueueThread, MessageQueueThread messageQueueThread2, Collection<JavaModuleWrapper> collection, Collection<CxxModuleWrapper> collection2);

    private native void jniLoadScriptFromAssets(AssetManager assetManager, String str);

    private native void jniLoadScriptFromFile(String str, String str2);

    private native void jniLoadScriptFromOptimizedBundle(String str, String str2, int i);

    private native void jniSetSourceURL(String str);

    public native long getJavaScriptContext();

    public native void setGlobalVariable(String str, String str2);

    public native void startProfiler(String str);

    public native void stopProfiler(String str, String str2);

    public native boolean supportsProfiling();

    static {
        SoLoader.loadLibrary(REACT_NATIVE_LIB);
        sNextInstanceIdForTrace = new AtomicInteger(1);
    }

    private static class PendingJSCall {
        public NativeArray mArguments;
        public ExecutorToken mExecutorToken;
        public String mMethod;
        public String mModule;

        public PendingJSCall(ExecutorToken executorToken, String module, String method, NativeArray arguments) {
            this.mExecutorToken = executorToken;
            this.mModule = module;
            this.mMethod = method;
            this.mArguments = arguments;
        }
    }

    private CatalystInstanceImpl(ReactQueueConfigurationSpec ReactQueueConfigurationSpec, JavaScriptExecutor jsExecutor, NativeModuleRegistry registry, JavaScriptModuleRegistry jsModuleRegistry, JSBundleLoader jsBundleLoader, NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler) {
        this.mPendingJSCalls = new AtomicInteger(0);
        this.mJsPendingCallsTitleForTrace = "pending_js_calls_instance" + sNextInstanceIdForTrace.getAndIncrement();
        this.mDestroyed = false;
        this.mJSCallsPendingInit = new ArrayList<>();
        this.mJSCallsPendingInitLock = new Object();
        this.mInitialized = false;
        this.mAcceptCalls = false;
        FLog.d("React", "Initializing React Xplat Bridge.");
        this.mHybridData = initHybrid();
        this.mReactQueueConfiguration = ReactQueueConfigurationImpl.create(ReactQueueConfigurationSpec, new NativeExceptionHandler());
        this.mBridgeIdleListeners = new CopyOnWriteArrayList<>();
        this.mJavaRegistry = registry;
        this.mJSModuleRegistry = jsModuleRegistry;
        this.mJSBundleLoader = jsBundleLoader;
        this.mNativeModuleCallExceptionHandler = nativeModuleCallExceptionHandler;
        this.mTraceListener = new JSProfilerTraceListener(this);
        initializeBridge(new BridgeCallback(this), jsExecutor, this.mReactQueueConfiguration.getJSQueueThread(), this.mReactQueueConfiguration.getNativeModulesQueueThread(), this.mJavaRegistry.getJavaModules(this), this.mJavaRegistry.getCxxModules());
        this.mMainExecutorToken = getMainExecutorToken();
    }

    private static class BridgeCallback implements ReactCallback {
        private final WeakReference<CatalystInstanceImpl> mOuter;

        public BridgeCallback(CatalystInstanceImpl outer) {
            this.mOuter = new WeakReference<>(outer);
        }

        @Override // com.facebook.react.cxxbridge.ReactCallback
        public void onBatchComplete() {
            CatalystInstanceImpl impl = this.mOuter.get();
            if (impl != null) {
                impl.mJavaRegistry.onBatchComplete();
            }
        }

        @Override // com.facebook.react.cxxbridge.ReactCallback
        public void incrementPendingJSCalls() {
            CatalystInstanceImpl impl = this.mOuter.get();
            if (impl != null) {
                impl.incrementPendingJSCalls();
            }
        }

        @Override // com.facebook.react.cxxbridge.ReactCallback
        public void decrementPendingJSCalls() {
            CatalystInstanceImpl impl = this.mOuter.get();
            if (impl != null) {
                impl.decrementPendingJSCalls();
            }
        }

        @Override // com.facebook.react.cxxbridge.ReactCallback
        public void onNativeException(Exception e) {
            CatalystInstanceImpl impl = this.mOuter.get();
            if (impl != null) {
                impl.onNativeException(e);
            }
        }
    }

    void setSourceURLs(String deviceURL, String remoteURL) {
        this.mSourceURL = deviceURL;
        jniSetSourceURL(remoteURL);
    }

    void loadScriptFromAssets(AssetManager assetManager, String assetURL) {
        this.mSourceURL = assetURL;
        jniLoadScriptFromAssets(assetManager, assetURL);
    }

    void loadScriptFromFile(String fileName, String sourceURL) {
        this.mSourceURL = sourceURL;
        jniLoadScriptFromFile(fileName, sourceURL);
    }

    void loadScriptFromOptimizedBundle(String path, String sourceURL, int flags) {
        this.mSourceURL = sourceURL;
        jniLoadScriptFromOptimizedBundle(path, sourceURL, flags);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void runJSBundle() {
        Assertions.assertCondition(!this.mJSBundleHasLoaded, "JS bundle was already loaded!");
        this.mJSBundleHasLoaded = true;
        this.mJSBundleLoader.loadScript(this);
        synchronized (this.mJSCallsPendingInitLock) {
            this.mAcceptCalls = true;
            for (PendingJSCall call : this.mJSCallsPendingInit) {
                callJSFunction(call.mExecutorToken, call.mModule, call.mMethod, call.mArguments);
            }
            this.mJSCallsPendingInit.clear();
        }
        Systrace.registerListener(this.mTraceListener);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public String getSourceURL() {
        return this.mSourceURL;
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void callFunction(ExecutorToken executorToken, String module, String method, NativeArray arguments) {
        if (this.mDestroyed) {
            FLog.w("React", "Calling JS function after bridge has been destroyed.");
            return;
        }
        if (!this.mAcceptCalls) {
            synchronized (this.mJSCallsPendingInitLock) {
                if (!this.mAcceptCalls) {
                    this.mJSCallsPendingInit.add(new PendingJSCall(executorToken, module, method, arguments));
                    return;
                }
            }
        }
        callJSFunction(executorToken, module, method, arguments);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void invokeCallback(ExecutorToken executorToken, int callbackID, NativeArray arguments) {
        if (this.mDestroyed) {
            FLog.w("React", "Invoking JS callback after bridge has been destroyed.");
        } else {
            callJSCallback(executorToken, callbackID, arguments);
        }
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void destroy() {
        UiThreadUtil.assertOnUiThread();
        if (!this.mDestroyed) {
            this.mDestroyed = true;
            this.mHybridData.resetNative();
            this.mJavaRegistry.notifyCatalystInstanceDestroy();
            boolean wasIdle = this.mPendingJSCalls.getAndSet(0) == 0;
            if (!wasIdle && !this.mBridgeIdleListeners.isEmpty()) {
                for (NotThreadSafeBridgeIdleDebugListener listener : this.mBridgeIdleListeners) {
                    listener.onTransitionToBridgeIdle();
                }
            }
            Systrace.unregisterListener(this.mTraceListener);
        }
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void initialize() {
        UiThreadUtil.assertOnUiThread();
        Assertions.assertCondition(!this.mInitialized, "This catalyst instance has already been initialized");
        Assertions.assertCondition(this.mAcceptCalls, "RunJSBundle hasn't completed.");
        this.mInitialized = true;
        this.mJavaRegistry.notifyCatalystInstanceInitialized();
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public ReactQueueConfiguration getReactQueueConfiguration() {
        return this.mReactQueueConfiguration;
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public <T extends JavaScriptModule> T getJSModule(Class<T> cls) {
        return (T) getJSModule(this.mMainExecutorToken, cls);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public <T extends JavaScriptModule> T getJSModule(ExecutorToken executorToken, Class<T> cls) {
        return (T) ((JavaScriptModuleRegistry) Assertions.assertNotNull(this.mJSModuleRegistry)).getJavaScriptModule(this, executorToken, cls);
    }

    public <T extends NativeModule> boolean hasNativeModule(Class<T> nativeModuleInterface) {
        return this.mJavaRegistry.hasModule(nativeModuleInterface);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public <T extends NativeModule> T getNativeModule(Class<T> cls) {
        return (T) this.mJavaRegistry.getModule(cls);
    }

    public Collection<NativeModule> getNativeModules() {
        return this.mJavaRegistry.getAllModules();
    }

    @Override // com.facebook.react.bridge.MemoryPressureListener
    public void handleMemoryPressure(MemoryPressure level) {
        if (!this.mDestroyed) {
            switch (level) {
                case UI_HIDDEN:
                    handleMemoryPressureUiHidden();
                    break;
                case MODERATE:
                    handleMemoryPressureModerate();
                    break;
                case CRITICAL:
                    handleMemoryPressureCritical();
                    break;
            }
        }
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener listener) {
        this.mBridgeIdleListeners.add(listener);
    }

    @Override // com.facebook.react.bridge.CatalystInstance
    public void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener listener) {
        this.mBridgeIdleListeners.remove(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void incrementPendingJSCalls() {
        int oldPendingCalls = this.mPendingJSCalls.getAndIncrement();
        boolean wasIdle = oldPendingCalls == 0;
        Systrace.traceCounter(0L, this.mJsPendingCallsTitleForTrace, oldPendingCalls + 1);
        if (wasIdle && !this.mBridgeIdleListeners.isEmpty()) {
            for (NotThreadSafeBridgeIdleDebugListener listener : this.mBridgeIdleListeners) {
                listener.onTransitionToBridgeBusy();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decrementPendingJSCalls() {
        int newPendingCalls = this.mPendingJSCalls.decrementAndGet();
        boolean isNowIdle = newPendingCalls == 0;
        Systrace.traceCounter(0L, this.mJsPendingCallsTitleForTrace, newPendingCalls);
        if (isNowIdle && !this.mBridgeIdleListeners.isEmpty()) {
            for (NotThreadSafeBridgeIdleDebugListener listener : this.mBridgeIdleListeners) {
                listener.onTransitionToBridgeIdle();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNativeException(Exception e) {
        this.mNativeModuleCallExceptionHandler.handleException(e);
        this.mReactQueueConfiguration.getUIQueueThread().runOnQueue(new Runnable() { // from class: com.facebook.react.cxxbridge.CatalystInstanceImpl.1
            @Override // java.lang.Runnable
            public void run() {
                CatalystInstanceImpl.this.destroy();
            }
        });
    }

    private class NativeExceptionHandler implements QueueThreadExceptionHandler {
        private NativeExceptionHandler() {
        }

        @Override // com.facebook.react.bridge.queue.QueueThreadExceptionHandler
        public void handleException(Exception e) {
            CatalystInstanceImpl.this.onNativeException(e);
        }
    }

    private static class JSProfilerTraceListener implements TraceListener {
        private final WeakReference<CatalystInstanceImpl> mOuter;

        public JSProfilerTraceListener(CatalystInstanceImpl outer) {
            this.mOuter = new WeakReference<>(outer);
        }
    }

    public static class Builder {
        private JSBundleLoader mJSBundleLoader;
        private JavaScriptExecutor mJSExecutor;
        private JavaScriptModuleRegistry mJSModuleRegistry;
        private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
        private ReactQueueConfigurationSpec mReactQueueConfigurationSpec;
        private NativeModuleRegistry mRegistry;

        public Builder setReactQueueConfigurationSpec(ReactQueueConfigurationSpec ReactQueueConfigurationSpec) {
            this.mReactQueueConfigurationSpec = ReactQueueConfigurationSpec;
            return this;
        }

        public Builder setRegistry(NativeModuleRegistry registry) {
            this.mRegistry = registry;
            return this;
        }

        public Builder setJSModuleRegistry(JavaScriptModuleRegistry jsModuleRegistry) {
            this.mJSModuleRegistry = jsModuleRegistry;
            return this;
        }

        public Builder setJSBundleLoader(JSBundleLoader jsBundleLoader) {
            this.mJSBundleLoader = jsBundleLoader;
            return this;
        }

        public Builder setJSExecutor(JavaScriptExecutor jsExecutor) {
            this.mJSExecutor = jsExecutor;
            return this;
        }

        public Builder setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler handler) {
            this.mNativeModuleCallExceptionHandler = handler;
            return this;
        }

        public CatalystInstanceImpl build() {
            return new CatalystInstanceImpl((ReactQueueConfigurationSpec) Assertions.assertNotNull(this.mReactQueueConfigurationSpec), (JavaScriptExecutor) Assertions.assertNotNull(this.mJSExecutor), (NativeModuleRegistry) Assertions.assertNotNull(this.mRegistry), (JavaScriptModuleRegistry) Assertions.assertNotNull(this.mJSModuleRegistry), (JSBundleLoader) Assertions.assertNotNull(this.mJSBundleLoader), (NativeModuleCallExceptionHandler) Assertions.assertNotNull(this.mNativeModuleCallExceptionHandler));
        }
    }
}
