package com.facebook.react;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.Systrace;
import com.facebook.react.devsupport.HMRClient;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ExceptionsManagerModule;
import com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
import com.facebook.react.modules.core.JSTimersExecution;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;
import com.facebook.react.modules.core.Timing;
import com.facebook.react.modules.debug.AnimationsDebugModule;
import com.facebook.react.modules.debug.SourceCodeModule;
import com.facebook.react.modules.systeminfo.AndroidInfoModule;
import com.facebook.react.uimanager.AppRegistry;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Provider;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class CoreModulesPackage extends LazyReactPackage implements ReactPackageLogger {
    private final DefaultHardwareBackBtnHandler mHardwareBackBtnHandler;
    private final boolean mLazyViewManagersEnabled;
    private final ReactInstanceManager mReactInstanceManager;
    private final UIImplementationProvider mUIImplementationProvider;

    CoreModulesPackage(ReactInstanceManager reactInstanceManager, DefaultHardwareBackBtnHandler hardwareBackBtnHandler, UIImplementationProvider uiImplementationProvider, boolean lazyViewManagersEnabled) {
        this.mReactInstanceManager = reactInstanceManager;
        this.mHardwareBackBtnHandler = hardwareBackBtnHandler;
        this.mUIImplementationProvider = uiImplementationProvider;
        this.mLazyViewManagersEnabled = lazyViewManagersEnabled;
    }

    @Override // com.facebook.react.LazyReactPackage
    public List<ModuleSpec> getNativeModules(final ReactApplicationContext reactContext) {
        List<ModuleSpec> moduleSpecList = new ArrayList<>();
        moduleSpecList.add(new ModuleSpec(AndroidInfoModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.1
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m61get() {
                return new AndroidInfoModule();
            }
        }));
        moduleSpecList.add(new ModuleSpec(AnimationsDebugModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.2
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m62get() {
                return new AnimationsDebugModule(reactContext, CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager().getDevSettings());
            }
        }));
        moduleSpecList.add(new ModuleSpec(DeviceEventManagerModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.3
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m63get() {
                return new DeviceEventManagerModule(reactContext, CoreModulesPackage.this.mHardwareBackBtnHandler);
            }
        }));
        moduleSpecList.add(new ModuleSpec(ExceptionsManagerModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.4
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m64get() {
                return new ExceptionsManagerModule(CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
            }
        }));
        moduleSpecList.add(new ModuleSpec(HeadlessJsTaskSupportModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.5
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m65get() {
                return new HeadlessJsTaskSupportModule(reactContext);
            }
        }));
        moduleSpecList.add(new ModuleSpec(SourceCodeModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.6
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m66get() {
                return new SourceCodeModule(reactContext);
            }
        }));
        moduleSpecList.add(new ModuleSpec(Timing.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.7
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m67get() {
                return new Timing(reactContext, CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
            }
        }));
        moduleSpecList.add(new ModuleSpec(UIManagerModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.CoreModulesPackage.8
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m68get() {
                return CoreModulesPackage.this.createUIManager(reactContext);
            }
        }));
        return moduleSpecList;
    }

    @Override // com.facebook.react.ReactPackage
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        List<Class<? extends JavaScriptModule>> jsModules = new ArrayList<>(Arrays.asList(DeviceEventManagerModule.RCTDeviceEventEmitter.class, JSTimersExecution.class, RCTEventEmitter.class, RCTNativeAppEventEmitter.class, AppRegistry.class, Systrace.class, HMRClient.class));
        return jsModules;
    }

    @Override // com.facebook.react.LazyReactPackage
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        ReactMarker.logMarker("CORE_REACT_PACKAGE_GET_REACT_MODULE_INFO_PROVIDER_START");
        ReactModuleInfoProvider reactModuleInfoProvider = LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
        ReactMarker.logMarker("CORE_REACT_PACKAGE_GET_REACT_MODULE_INFO_PROVIDER_END");
        return reactModuleInfoProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UIManagerModule createUIManager(ReactApplicationContext reactContext) {
        ReactMarker.logMarker("CREATE_UI_MANAGER_MODULE_START");
        com.facebook.systrace.Systrace.beginSection(0L, "createUIManagerModule");
        try {
            List<ViewManager> viewManagersList = this.mReactInstanceManager.createAllViewManagers(reactContext);
            return new UIManagerModule(reactContext, viewManagersList, this.mUIImplementationProvider, this.mLazyViewManagersEnabled);
        } finally {
            com.facebook.systrace.Systrace.endSection(0L);
            ReactMarker.logMarker("CREATE_UI_MANAGER_MODULE_END");
        }
    }

    @Override // com.facebook.react.ReactPackageLogger
    public void startProcessPackage() {
        ReactMarker.logMarker("PROCESS_CORE_REACT_PACKAGE_START");
    }

    @Override // com.facebook.react.ReactPackageLogger
    public void endProcessPackage() {
        ReactMarker.logMarker("PROCESS_CORE_REACT_PACKAGE_END");
    }
}
