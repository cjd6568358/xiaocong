package com.facebook.react;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.cxxbridge.LegacyModuleInfo;
import com.facebook.react.cxxbridge.ModuleHolder;
import com.facebook.react.cxxbridge.NativeModuleRegistry;
import com.facebook.react.module.model.ReactModuleInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeModuleRegistryBuilder {
    private final boolean mLazyNativeModulesEnabled;
    private final ReactApplicationContext mReactApplicationContext;
    private final Map<Class<? extends NativeModule>, ModuleHolder> mModules = new HashMap();
    private final Map<String, Class<? extends NativeModule>> namesToType = new HashMap();

    public NativeModuleRegistryBuilder(ReactApplicationContext reactApplicationContext, boolean lazyNativeModulesEnabled) {
        this.mReactApplicationContext = reactApplicationContext;
        this.mLazyNativeModulesEnabled = lazyNativeModulesEnabled;
    }

    public void processPackage(ReactPackage reactPackage) {
        ModuleHolder moduleHolder;
        if (this.mLazyNativeModulesEnabled) {
            if (!(reactPackage instanceof LazyReactPackage)) {
                throw new IllegalStateException("Lazy native modules requires all ReactPackage to inherit from LazyReactPackage");
            }
            LazyReactPackage lazyReactPackage = (LazyReactPackage) reactPackage;
            List<ModuleSpec> moduleSpecs = lazyReactPackage.getNativeModules(this.mReactApplicationContext);
            Map<Class, ReactModuleInfo> reactModuleInfoMap = lazyReactPackage.getReactModuleInfoProvider().getReactModuleInfos();
            for (ModuleSpec moduleSpec : moduleSpecs) {
                Class<? extends NativeModule> type = moduleSpec.getType();
                ReactModuleInfo reactModuleInfo = reactModuleInfoMap.get(type);
                if (reactModuleInfo == null) {
                    if (BaseJavaModule.class.isAssignableFrom(type)) {
                        throw new IllegalStateException("Native Java module " + type.getSimpleName() + " should be annotated with @ReactModule and added to a @ReactModuleList.");
                    }
                    NativeModule nativeModule = (NativeModule) moduleSpec.getProvider().get();
                    LegacyModuleInfo legacyModuleInfo = new LegacyModuleInfo(type, nativeModule);
                    moduleHolder = new ModuleHolder(legacyModuleInfo, nativeModule);
                } else {
                    moduleHolder = new ModuleHolder(reactModuleInfo, moduleSpec.getProvider());
                }
                String name = moduleHolder.getInfo().name();
                if (this.namesToType.containsKey(name)) {
                    Class<? extends NativeModule> existingNativeModule = this.namesToType.get(name);
                    if (!moduleHolder.getInfo().canOverrideExistingModule()) {
                        throw new IllegalStateException("Native module " + type.getSimpleName() + " tried to override " + existingNativeModule.getSimpleName() + " for module name " + name + ". If this was your intention, set canOverrideExistingModule=true");
                    }
                    this.mModules.remove(existingNativeModule);
                }
                this.namesToType.put(name, type);
                this.mModules.put(type, moduleHolder);
            }
            return;
        }
        FLog.d("React", reactPackage.getClass().getSimpleName() + " is not a LazyReactPackage, falling back to old version.");
        Iterator<NativeModule> it = reactPackage.createNativeModules(this.mReactApplicationContext).iterator();
        while (it.hasNext()) {
            addNativeModule(it.next());
        }
    }

    public void addNativeModule(NativeModule nativeModule) {
        String name = nativeModule.getName();
        Class<?> cls = nativeModule.getClass();
        if (this.namesToType.containsKey(name)) {
            Class<? extends NativeModule> cls2 = this.namesToType.get(name);
            if (!nativeModule.canOverrideExistingModule()) {
                throw new IllegalStateException("Native module " + cls.getSimpleName() + " tried to override " + cls2.getSimpleName() + " for module name " + name + ". If this was your intention, set canOverrideExistingModule=true");
            }
            this.mModules.remove(cls2);
        }
        this.namesToType.put(name, (Class<? extends NativeModule>) cls);
        this.mModules.put((Class<? extends NativeModule>) cls, new ModuleHolder(new LegacyModuleInfo(cls, nativeModule), nativeModule));
    }

    public NativeModuleRegistry build() {
        ArrayList<OnBatchCompleteListener> batchCompleteListenerModules = new ArrayList<>();
        for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
            Class<? extends NativeModule> type = entry.getKey();
            if (OnBatchCompleteListener.class.isAssignableFrom(type)) {
                final ModuleHolder moduleHolder = entry.getValue();
                batchCompleteListenerModules.add(new OnBatchCompleteListener() { // from class: com.facebook.react.NativeModuleRegistryBuilder.1
                    @Override // com.facebook.react.bridge.OnBatchCompleteListener
                    public void onBatchComplete() {
                        OnBatchCompleteListener listener = (OnBatchCompleteListener) moduleHolder.getModule();
                        listener.onBatchComplete();
                    }
                });
            }
        }
        return new NativeModuleRegistry(this.mModules, batchCompleteListenerModules);
    }
}
