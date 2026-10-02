package com.facebook.react;

import android.app.Activity;
import android.app.Application;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.uimanager.UIImplementationProvider;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactInstanceManagerBuilder {
    protected Application mApplication;
    protected NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
    protected Activity mCurrentActivity;
    protected DefaultHardwareBackBtnHandler mDefaultHardwareBackBtnHandler;
    protected LifecycleState mInitialLifecycleState;
    protected String mJSBundleAssetUrl;
    protected JSBundleLoader mJSBundleLoader;
    protected String mJSMainModuleName;
    protected boolean mLazyNativeModulesEnabled;
    protected boolean mLazyViewManagersEnabled;
    protected NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    protected RedBoxHandler mRedBoxHandler;
    protected UIImplementationProvider mUIImplementationProvider;
    protected boolean mUseDeveloperSupport;
    protected final List<ReactPackage> mPackages = new ArrayList();
    protected JSCConfig mJSCConfig = JSCConfig.EMPTY;

    ReactInstanceManagerBuilder() {
    }

    public ReactInstanceManagerBuilder setUIImplementationProvider(UIImplementationProvider uiImplementationProvider) {
        this.mUIImplementationProvider = uiImplementationProvider;
        return this;
    }

    public ReactInstanceManagerBuilder setBundleAssetName(String bundleAssetName) {
        this.mJSBundleAssetUrl = bundleAssetName == null ? null : "assets://" + bundleAssetName;
        this.mJSBundleLoader = null;
        return this;
    }

    public ReactInstanceManagerBuilder setJSBundleFile(String jsBundleFile) {
        if (!jsBundleFile.startsWith("assets://")) {
            return setJSBundleLoader(JSBundleLoader.createFileLoader(jsBundleFile));
        }
        this.mJSBundleAssetUrl = jsBundleFile;
        this.mJSBundleLoader = null;
        return this;
    }

    public ReactInstanceManagerBuilder setJSBundleLoader(JSBundleLoader jsBundleLoader) {
        this.mJSBundleLoader = jsBundleLoader;
        this.mJSBundleAssetUrl = null;
        return this;
    }

    public ReactInstanceManagerBuilder setJSMainModuleName(String jsMainModuleName) {
        this.mJSMainModuleName = jsMainModuleName;
        return this;
    }

    public ReactInstanceManagerBuilder addPackage(ReactPackage reactPackage) {
        this.mPackages.add(reactPackage);
        return this;
    }

    public ReactInstanceManagerBuilder setApplication(Application application) {
        this.mApplication = application;
        return this;
    }

    public ReactInstanceManagerBuilder setUseDeveloperSupport(boolean useDeveloperSupport) {
        this.mUseDeveloperSupport = useDeveloperSupport;
        return this;
    }

    public ReactInstanceManagerBuilder setInitialLifecycleState(LifecycleState initialLifecycleState) {
        this.mInitialLifecycleState = initialLifecycleState;
        return this;
    }

    public ReactInstanceManagerBuilder setRedBoxHandler(RedBoxHandler redBoxHandler) {
        this.mRedBoxHandler = redBoxHandler;
        return this;
    }

    public ReactInstanceManager build() {
        Assertions.assertNotNull(this.mApplication, "Application property has not been set with this builder");
        Assertions.assertCondition((!this.mUseDeveloperSupport && this.mJSBundleAssetUrl == null && this.mJSBundleLoader == null) ? false : true, "JS Bundle File or Asset URL has to be provided when dev support is disabled");
        Assertions.assertCondition((this.mJSMainModuleName == null && this.mJSBundleAssetUrl == null && this.mJSBundleLoader == null) ? false : true, "Either MainModuleName or JS Bundle File needs to be provided");
        if (this.mUIImplementationProvider == null) {
            this.mUIImplementationProvider = new UIImplementationProvider();
        }
        return new ReactInstanceManager(this.mApplication, this.mCurrentActivity, this.mDefaultHardwareBackBtnHandler, (this.mJSBundleLoader != null || this.mJSBundleAssetUrl == null) ? this.mJSBundleLoader : JSBundleLoader.createAssetLoader(this.mApplication, this.mJSBundleAssetUrl), this.mJSMainModuleName, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState) Assertions.assertNotNull(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mJSCConfig, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled);
    }
}
