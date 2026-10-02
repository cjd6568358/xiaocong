package com.facebook.react;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.JavaScriptModuleRegistry;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.common.ApplicationHolder;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.cxxbridge.Arguments;
import com.facebook.react.cxxbridge.CatalystInstanceImpl;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.cxxbridge.JSCJavaScriptExecutor;
import com.facebook.react.cxxbridge.JavaScriptExecutor;
import com.facebook.react.cxxbridge.NativeModuleRegistry;
import com.facebook.react.cxxbridge.UiThreadUtil;
import com.facebook.react.devsupport.DevServerHelper;
import com.facebook.react.devsupport.DevSupportManager;
import com.facebook.react.devsupport.DevSupportManagerFactory;
import com.facebook.react.devsupport.ReactInstanceDevCommandsHandler;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.debug.DeveloperSettings;
import com.facebook.react.uimanager.AppRegistry;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.soloader.SoLoader;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactInstanceManager {
    private static final String TAG = ReactInstanceManager.class.getSimpleName();
    private final Context mApplicationContext;
    private final NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
    private final JSBundleLoader mBundleLoader;
    private Activity mCurrentActivity;
    private volatile ReactContext mCurrentReactContext;
    private DefaultHardwareBackBtnHandler mDefaultBackButtonImpl;
    private final DevSupportManager mDevSupportManager;
    private final JSCConfig mJSCConfig;
    private final String mJSMainModuleName;
    private final boolean mLazyNativeModulesEnabled;
    private final boolean mLazyViewManagersEnabled;
    private LifecycleState mLifecycleState;
    private final MemoryPressureRouter mMemoryPressureRouter;
    private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    private final List<ReactPackage> mPackages;
    private ReactContextInitParams mPendingReactContextInitParams;
    private ReactContextInitAsyncTask mReactContextInitAsyncTask;
    private final UIImplementationProvider mUIImplementationProvider;
    private final boolean mUseDeveloperSupport;
    private final List<ReactRootView> mAttachedRootViews = new ArrayList();
    private final Collection<ReactInstanceEventListener> mReactInstanceEventListeners = Collections.synchronizedSet(new HashSet());
    private volatile boolean mHasStartedCreatingInitialContext = false;
    private final ReactInstanceDevCommandsHandler mDevInterface = new ReactInstanceDevCommandsHandler() { // from class: com.facebook.react.ReactInstanceManager.1
    };
    private final DefaultHardwareBackBtnHandler mBackBtnHandler = new DefaultHardwareBackBtnHandler() { // from class: com.facebook.react.ReactInstanceManager.2
        @Override // com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
        public void invokeDefaultOnBackPressed() {
            ReactInstanceManager.this.invokeDefaultOnBackPressed();
        }
    };

    public interface ReactInstanceEventListener {
        void onReactContextInitialized(ReactContext reactContext);
    }

    private class ReactContextInitParams {
        private final JSBundleLoader mJsBundleLoader;
        private final JavaScriptExecutor.Factory mJsExecutorFactory;

        public ReactContextInitParams(JavaScriptExecutor.Factory jsExecutorFactory, JSBundleLoader jsBundleLoader) {
            this.mJsExecutorFactory = (JavaScriptExecutor.Factory) Assertions.assertNotNull(jsExecutorFactory);
            this.mJsBundleLoader = (JSBundleLoader) Assertions.assertNotNull(jsBundleLoader);
        }

        public JavaScriptExecutor.Factory getJsExecutorFactory() {
            return this.mJsExecutorFactory;
        }

        public JSBundleLoader getJsBundleLoader() {
            return this.mJsBundleLoader;
        }
    }

    private final class ReactContextInitAsyncTask extends AsyncTask<ReactContextInitParams, Void, Result<ReactApplicationContext>> {
        private ReactContextInitAsyncTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            if (ReactInstanceManager.this.mCurrentReactContext != null) {
                ReactInstanceManager.this.tearDownReactContext(ReactInstanceManager.this.mCurrentReactContext);
                ReactInstanceManager.this.mCurrentReactContext = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Result<ReactApplicationContext> doInBackground(ReactContextInitParams... params) {
            boolean z = false;
            Process.setThreadPriority(0);
            if (params != null && params.length > 0 && params[0] != null) {
                z = true;
            }
            Assertions.assertCondition(z);
            try {
                JavaScriptExecutor jsExecutor = params[0].getJsExecutorFactory().create();
                return Result.of(ReactInstanceManager.this.createReactContext(jsExecutor, params[0].getJsBundleLoader()));
            } catch (Exception e) {
                return Result.of(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<ReactApplicationContext> result) {
            try {
                ReactInstanceManager.this.setupReactContext(result.get());
            } catch (Exception e) {
                ReactInstanceManager.this.mDevSupportManager.handleException(e);
            } finally {
                ReactInstanceManager.this.mReactContextInitAsyncTask = null;
            }
            if (ReactInstanceManager.this.mPendingReactContextInitParams != null) {
                ReactInstanceManager.this.recreateReactContextInBackground(ReactInstanceManager.this.mPendingReactContextInitParams.getJsExecutorFactory(), ReactInstanceManager.this.mPendingReactContextInitParams.getJsBundleLoader());
                ReactInstanceManager.this.mPendingReactContextInitParams = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(Result<ReactApplicationContext> reactApplicationContextResult) {
            try {
                ReactInstanceManager.this.mMemoryPressureRouter.destroy(reactApplicationContextResult.get());
            } catch (Exception e) {
                FLog.w("React", "Caught exception after cancelling react context init", e);
            } finally {
                ReactInstanceManager.this.mReactContextInitAsyncTask = null;
            }
        }
    }

    private static class Result<T> {
        private final Exception mException;
        private final T mResult;

        public static <T, U extends T> Result<T> of(U result) {
            return new Result<>(result);
        }

        public static <T> Result<T> of(Exception exception) {
            return new Result<>(exception);
        }

        private Result(T result) {
            this.mException = null;
            this.mResult = result;
        }

        private Result(Exception exception) {
            this.mException = exception;
            this.mResult = null;
        }

        public T get() throws Exception {
            if (this.mException != null) {
                throw this.mException;
            }
            Assertions.assertNotNull(this.mResult);
            return this.mResult;
        }
    }

    public static ReactInstanceManagerBuilder builder() {
        return new ReactInstanceManagerBuilder();
    }

    ReactInstanceManager(Context applicationContext, Activity currentActivity, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler, JSBundleLoader bundleLoader, String jsMainModuleName, List<ReactPackage> packages, boolean useDeveloperSupport, NotThreadSafeBridgeIdleDebugListener bridgeIdleDebugListener, LifecycleState initialLifecycleState, UIImplementationProvider uiImplementationProvider, NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler, JSCConfig jscConfig, RedBoxHandler redBoxHandler, boolean lazyNativeModulesEnabled, boolean lazyViewManagersEnabled) {
        initializeSoLoaderIfNecessary(applicationContext);
        ApplicationHolder.setApplication((Application) applicationContext.getApplicationContext());
        DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(applicationContext);
        this.mApplicationContext = applicationContext;
        this.mCurrentActivity = currentActivity;
        this.mDefaultBackButtonImpl = defaultHardwareBackBtnHandler;
        this.mBundleLoader = bundleLoader;
        this.mJSMainModuleName = jsMainModuleName;
        this.mPackages = packages;
        this.mUseDeveloperSupport = useDeveloperSupport;
        this.mDevSupportManager = DevSupportManagerFactory.create(applicationContext, this.mDevInterface, this.mJSMainModuleName, useDeveloperSupport, redBoxHandler);
        this.mBridgeIdleDebugListener = bridgeIdleDebugListener;
        this.mLifecycleState = initialLifecycleState;
        this.mUIImplementationProvider = uiImplementationProvider;
        this.mMemoryPressureRouter = new MemoryPressureRouter(applicationContext);
        this.mNativeModuleCallExceptionHandler = nativeModuleCallExceptionHandler;
        this.mJSCConfig = jscConfig;
        this.mLazyNativeModulesEnabled = lazyNativeModulesEnabled;
        this.mLazyViewManagersEnabled = lazyViewManagersEnabled;
    }

    public DevSupportManager getDevSupportManager() {
        return this.mDevSupportManager;
    }

    private static void initializeSoLoaderIfNecessary(Context applicationContext) {
        SoLoader.init(applicationContext, false);
    }

    public void createReactContextInBackground() {
        Assertions.assertCondition(!this.mHasStartedCreatingInitialContext, "createReactContextInBackground should only be called when creating the react application for the first time. When reloading JS, e.g. from a new file, explicitlyuse recreateReactContextInBackground");
        this.mHasStartedCreatingInitialContext = true;
        recreateReactContextInBackgroundInner();
    }

    private void recreateReactContextInBackgroundInner() {
        UiThreadUtil.assertOnUiThread();
        if (this.mUseDeveloperSupport && this.mJSMainModuleName != null) {
            final DeveloperSettings devSettings = this.mDevSupportManager.getDevSettings();
            if (this.mDevSupportManager.hasUpToDateJSBundleInCache() && !devSettings.isRemoteJSDebugEnabled()) {
                onJSBundleLoadedFromServer();
                return;
            } else if (this.mBundleLoader == null) {
                this.mDevSupportManager.handleReloadJS();
                return;
            } else {
                this.mDevSupportManager.isPackagerRunning(new DevServerHelper.PackagerStatusCallback() { // from class: com.facebook.react.ReactInstanceManager.3
                });
                return;
            }
        }
        recreateReactContextInBackgroundFromBundleLoader();
    }

    private void recreateReactContextInBackgroundFromBundleLoader() {
        recreateReactContextInBackground(new JSCJavaScriptExecutor.Factory(this.mJSCConfig.getConfigMap()), this.mBundleLoader);
    }

    public boolean hasStartedCreatingInitialContext() {
        return this.mHasStartedCreatingInitialContext;
    }

    public void onBackPressed() {
        UiThreadUtil.assertOnUiThread();
        ReactContext reactContext = this.mCurrentReactContext;
        if (this.mCurrentReactContext == null) {
            FLog.w("React", "Instance detached from instance manager");
            invokeDefaultOnBackPressed();
        } else {
            DeviceEventManagerModule deviceEventManagerModule = (DeviceEventManagerModule) ((ReactContext) Assertions.assertNotNull(reactContext)).getNativeModule(DeviceEventManagerModule.class);
            deviceEventManagerModule.emitHardwareBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeDefaultOnBackPressed() {
        UiThreadUtil.assertOnUiThread();
        if (this.mDefaultBackButtonImpl != null) {
            this.mDefaultBackButtonImpl.invokeDefaultOnBackPressed();
        }
    }

    public void onNewIntent(Intent intent) {
        if (this.mCurrentReactContext == null) {
            FLog.w("React", "Instance detached from instance manager");
            return;
        }
        String action = intent.getAction();
        Uri uri = intent.getData();
        if ("android.intent.action.VIEW".equals(action) && uri != null) {
            DeviceEventManagerModule deviceEventManagerModule = (DeviceEventManagerModule) ((ReactContext) Assertions.assertNotNull(this.mCurrentReactContext)).getNativeModule(DeviceEventManagerModule.class);
            deviceEventManagerModule.emitNewIntentReceived(uri);
        }
        this.mCurrentReactContext.onNewIntent(this.mCurrentActivity, intent);
    }

    public void onHostPause() {
        UiThreadUtil.assertOnUiThread();
        this.mDefaultBackButtonImpl = null;
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(false);
        }
        moveToBeforeResumeLifecycleState();
    }

    public void onHostPause(Activity activity) {
        Assertions.assertNotNull(this.mCurrentActivity);
        Assertions.assertCondition(activity == this.mCurrentActivity, "Pausing an activity that is not the current activity, this is incorrect! Current activity: " + this.mCurrentActivity.getClass().getSimpleName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + "Paused activity: " + activity.getClass().getSimpleName());
        onHostPause();
    }

    public void onHostResume(Activity activity, DefaultHardwareBackBtnHandler defaultBackButtonImpl) {
        UiThreadUtil.assertOnUiThread();
        this.mDefaultBackButtonImpl = defaultBackButtonImpl;
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(true);
        }
        this.mCurrentActivity = activity;
        moveToResumedLifecycleState(false);
    }

    public void onHostDestroy() {
        UiThreadUtil.assertOnUiThread();
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(false);
        }
        moveToBeforeCreateLifecycleState();
        this.mCurrentActivity = null;
    }

    public void onHostDestroy(Activity activity) {
        if (activity == this.mCurrentActivity) {
            onHostDestroy();
        }
    }

    private void moveToResumedLifecycleState(boolean force) {
        if (this.mCurrentReactContext != null && (force || this.mLifecycleState == LifecycleState.BEFORE_RESUME || this.mLifecycleState == LifecycleState.BEFORE_CREATE)) {
            this.mCurrentReactContext.onHostResume(this.mCurrentActivity);
        }
        this.mLifecycleState = LifecycleState.RESUMED;
    }

    private void moveToBeforeResumeLifecycleState() {
        if (this.mCurrentReactContext != null) {
            if (this.mLifecycleState == LifecycleState.BEFORE_CREATE) {
                this.mCurrentReactContext.onHostResume(this.mCurrentActivity);
                this.mCurrentReactContext.onHostPause();
            } else if (this.mLifecycleState == LifecycleState.RESUMED) {
                this.mCurrentReactContext.onHostPause();
            }
        }
        this.mLifecycleState = LifecycleState.BEFORE_RESUME;
    }

    private void moveToBeforeCreateLifecycleState() {
        if (this.mCurrentReactContext != null) {
            if (this.mLifecycleState == LifecycleState.RESUMED) {
                this.mCurrentReactContext.onHostPause();
                this.mLifecycleState = LifecycleState.BEFORE_RESUME;
            }
            if (this.mLifecycleState == LifecycleState.BEFORE_RESUME) {
                this.mCurrentReactContext.onHostDestroy();
            }
        }
        this.mLifecycleState = LifecycleState.BEFORE_CREATE;
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (this.mCurrentReactContext != null) {
            this.mCurrentReactContext.onActivityResult(activity, requestCode, resultCode, data);
        }
    }

    public void showDevOptionsDialog() {
        UiThreadUtil.assertOnUiThread();
        this.mDevSupportManager.showDevOptionsDialog();
    }

    public void attachMeasuredRootView(ReactRootView rootView) {
        UiThreadUtil.assertOnUiThread();
        this.mAttachedRootViews.add(rootView);
        if (this.mReactContextInitAsyncTask == null && this.mCurrentReactContext != null) {
            attachMeasuredRootViewToInstance(rootView, this.mCurrentReactContext.getCatalystInstance());
        }
    }

    public void detachRootView(ReactRootView rootView) {
        UiThreadUtil.assertOnUiThread();
        if (this.mAttachedRootViews.remove(rootView) && this.mCurrentReactContext != null && this.mCurrentReactContext.hasActiveCatalystInstance()) {
            detachViewFromInstance(rootView, this.mCurrentReactContext.getCatalystInstance());
        }
    }

    public List<ViewManager> createAllViewManagers(ReactApplicationContext catalystApplicationContext) {
        ReactMarker.logMarker("CREATE_VIEW_MANAGERS_START");
        Systrace.beginSection(0L, "createAllViewManagers");
        try {
            List<ViewManager> allViewManagers = new ArrayList<>();
            for (ReactPackage reactPackage : this.mPackages) {
                allViewManagers.addAll(reactPackage.createViewManagers(catalystApplicationContext));
            }
            Systrace.endSection(0L);
            ReactMarker.logMarker("CREATE_VIEW_MANAGERS_END");
            return allViewManagers;
        } catch (Throwable th) {
            Systrace.endSection(0L);
            ReactMarker.logMarker("CREATE_VIEW_MANAGERS_END");
            throw th;
        }
    }

    public ReactContext getCurrentReactContext() {
        return this.mCurrentReactContext;
    }

    private void onJSBundleLoadedFromServer() {
        recreateReactContextInBackground(new JSCJavaScriptExecutor.Factory(this.mJSCConfig.getConfigMap()), JSBundleLoader.createCachedBundleFromNetworkLoader(this.mDevSupportManager.getSourceUrl(), this.mDevSupportManager.getDownloadedJSBundleFile()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recreateReactContextInBackground(JavaScriptExecutor.Factory jsExecutorFactory, JSBundleLoader jsBundleLoader) {
        UiThreadUtil.assertOnUiThread();
        ReactContextInitParams initParams = new ReactContextInitParams(jsExecutorFactory, jsBundleLoader);
        if (this.mReactContextInitAsyncTask == null) {
            this.mReactContextInitAsyncTask = new ReactContextInitAsyncTask();
            this.mReactContextInitAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, initParams);
        } else {
            this.mPendingReactContextInitParams = initParams;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupReactContext(ReactApplicationContext reactContext) {
        ReactMarker.logMarker("SETUP_REACT_CONTEXT_START");
        Systrace.beginSection(0L, "setupReactContext");
        UiThreadUtil.assertOnUiThread();
        Assertions.assertCondition(this.mCurrentReactContext == null);
        this.mCurrentReactContext = (ReactContext) Assertions.assertNotNull(reactContext);
        CatalystInstance catalystInstance = (CatalystInstance) Assertions.assertNotNull(reactContext.getCatalystInstance());
        catalystInstance.initialize();
        this.mDevSupportManager.onNewReactContextCreated(reactContext);
        this.mMemoryPressureRouter.addMemoryPressureListener(catalystInstance);
        moveReactContextToCurrentLifecycleState();
        for (ReactRootView rootView : this.mAttachedRootViews) {
            attachMeasuredRootViewToInstance(rootView, catalystInstance);
        }
        ReactInstanceEventListener[] listeners = (ReactInstanceEventListener[]) this.mReactInstanceEventListeners.toArray(new ReactInstanceEventListener[this.mReactInstanceEventListeners.size()]);
        for (ReactInstanceEventListener listener : listeners) {
            listener.onReactContextInitialized(reactContext);
        }
        Systrace.endSection(0L);
        ReactMarker.logMarker("SETUP_REACT_CONTEXT_END");
    }

    private void attachMeasuredRootViewToInstance(ReactRootView rootView, CatalystInstance catalystInstance) {
        Systrace.beginSection(0L, "attachMeasuredRootViewToInstance");
        UiThreadUtil.assertOnUiThread();
        rootView.removeAllViews();
        rootView.setId(-1);
        UIManagerModule uiManagerModule = (UIManagerModule) catalystInstance.getNativeModule(UIManagerModule.class);
        int rootTag = uiManagerModule.addMeasuredRootView(rootView);
        rootView.setRootViewTag(rootTag);
        Bundle launchOptions = rootView.getLaunchOptions();
        WritableMap initialProps = Arguments.makeNativeMap(launchOptions);
        String jsAppModuleName = rootView.getJSModuleName();
        WritableNativeMap appParams = new WritableNativeMap();
        appParams.putDouble("rootTag", rootTag);
        appParams.putMap("initialProps", initialProps);
        ((AppRegistry) catalystInstance.getJSModule(AppRegistry.class)).runApplication(jsAppModuleName, appParams);
        rootView.onAttachedToReactInstance();
        Systrace.endSection(0L);
    }

    private void detachViewFromInstance(ReactRootView rootView, CatalystInstance catalystInstance) {
        UiThreadUtil.assertOnUiThread();
        ((AppRegistry) catalystInstance.getJSModule(AppRegistry.class)).unmountApplicationComponentAtRootTag(rootView.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tearDownReactContext(ReactContext reactContext) {
        UiThreadUtil.assertOnUiThread();
        if (this.mLifecycleState == LifecycleState.RESUMED) {
            reactContext.onHostPause();
        }
        for (ReactRootView rootView : this.mAttachedRootViews) {
            detachViewFromInstance(rootView, reactContext.getCatalystInstance());
        }
        reactContext.destroy();
        this.mDevSupportManager.onReactInstanceDestroyed(reactContext);
        this.mMemoryPressureRouter.removeMemoryPressureListener(reactContext.getCatalystInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ReactApplicationContext createReactContext(JavaScriptExecutor jsExecutor, JSBundleLoader jsBundleLoader) {
        FLog.i("React", "Creating react context.");
        ReactMarker.logMarker("CREATE_REACT_CONTEXT_START");
        ReactApplicationContext reactContext = new ReactApplicationContext(this.mApplicationContext);
        NativeModuleRegistryBuilder nativeModuleRegistryBuilder = new NativeModuleRegistryBuilder(reactContext, this.mLazyNativeModulesEnabled);
        JavaScriptModuleRegistry.Builder jsModulesBuilder = new JavaScriptModuleRegistry.Builder();
        if (this.mUseDeveloperSupport) {
            reactContext.setNativeModuleCallExceptionHandler(this.mDevSupportManager);
        }
        ReactMarker.logMarker("PROCESS_PACKAGES_START");
        Systrace.beginSection(0L, "createAndProcessCoreModulesPackage");
        try {
            CoreModulesPackage coreModulesPackage = new CoreModulesPackage(this, this.mBackBtnHandler, this.mUIImplementationProvider, this.mLazyViewManagersEnabled);
            processPackage(coreModulesPackage, nativeModuleRegistryBuilder, jsModulesBuilder);
            Systrace.endSection(0L);
            for (ReactPackage reactPackage : this.mPackages) {
                Systrace.beginSection(0L, "createAndProcessCustomReactPackage");
                try {
                    processPackage(reactPackage, nativeModuleRegistryBuilder, jsModulesBuilder);
                    Systrace.endSection(0L);
                } catch (Throwable th) {
                    Systrace.endSection(0L);
                    throw th;
                }
            }
            ReactMarker.logMarker("PROCESS_PACKAGES_END");
            ReactMarker.logMarker("BUILD_NATIVE_MODULE_REGISTRY_START");
            Systrace.beginSection(0L, "buildNativeModuleRegistry");
            try {
                NativeModuleRegistry nativeModuleRegistry = nativeModuleRegistryBuilder.build();
                Systrace.endSection(0L);
                ReactMarker.logMarker("BUILD_NATIVE_MODULE_REGISTRY_END");
                NativeModuleCallExceptionHandler exceptionHandler = this.mNativeModuleCallExceptionHandler != null ? this.mNativeModuleCallExceptionHandler : this.mDevSupportManager;
                CatalystInstanceImpl.Builder catalystInstanceBuilder = new CatalystInstanceImpl.Builder().setReactQueueConfigurationSpec(ReactQueueConfigurationSpec.createDefault()).setJSExecutor(jsExecutor).setRegistry(nativeModuleRegistry).setJSModuleRegistry(jsModulesBuilder.build()).setJSBundleLoader(jsBundleLoader).setNativeModuleCallExceptionHandler(exceptionHandler);
                ReactMarker.logMarker("CREATE_CATALYST_INSTANCE_START");
                Systrace.beginSection(0L, "createCatalystInstance");
                try {
                    CatalystInstance catalystInstance = catalystInstanceBuilder.build();
                    Systrace.endSection(0L);
                    ReactMarker.logMarker("CREATE_CATALYST_INSTANCE_END");
                    if (this.mBridgeIdleDebugListener != null) {
                        catalystInstance.addBridgeIdleDebugListener(this.mBridgeIdleDebugListener);
                    }
                    reactContext.initializeWithInstance(catalystInstance);
                    catalystInstance.runJSBundle();
                    return reactContext;
                } catch (Throwable th2) {
                    Systrace.endSection(0L);
                    ReactMarker.logMarker("CREATE_CATALYST_INSTANCE_END");
                    throw th2;
                }
            } catch (Throwable th3) {
                Systrace.endSection(0L);
                ReactMarker.logMarker("BUILD_NATIVE_MODULE_REGISTRY_END");
                throw th3;
            }
        } catch (Throwable th4) {
            Systrace.endSection(0L);
            throw th4;
        }
    }

    private void processPackage(ReactPackage reactPackage, NativeModuleRegistryBuilder nativeModuleRegistryBuilder, JavaScriptModuleRegistry.Builder jsModulesBuilder) {
        SystraceMessage.beginSection(0L, "processPackage").arg("className", reactPackage.getClass().getSimpleName()).flush();
        if (reactPackage instanceof ReactPackageLogger) {
            ((ReactPackageLogger) reactPackage).startProcessPackage();
        }
        nativeModuleRegistryBuilder.processPackage(reactPackage);
        for (Class<? extends JavaScriptModule> jsModuleClass : reactPackage.createJSModules()) {
            jsModulesBuilder.add(jsModuleClass);
        }
        if (reactPackage instanceof ReactPackageLogger) {
            ((ReactPackageLogger) reactPackage).endProcessPackage();
        }
        Systrace.endSection(0L);
    }

    private void moveReactContextToCurrentLifecycleState() {
        if (this.mLifecycleState == LifecycleState.RESUMED) {
            moveToResumedLifecycleState(true);
        }
    }
}
