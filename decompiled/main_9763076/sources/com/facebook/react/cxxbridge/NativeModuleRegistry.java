package com.facebook.react.cxxbridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.systrace.Systrace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeModuleRegistry {
    private final ArrayList<OnBatchCompleteListener> mBatchCompleteListenerModules;
    private final Map<Class<? extends NativeModule>, ModuleHolder> mModules;

    public NativeModuleRegistry(Map<Class<? extends NativeModule>, ModuleHolder> modules, ArrayList<OnBatchCompleteListener> batchCompleteListenerModules) {
        this.mModules = modules;
        this.mBatchCompleteListenerModules = batchCompleteListenerModules;
    }

    Collection<JavaModuleWrapper> getJavaModules(CatalystInstanceImpl catalystInstanceImpl) {
        ArrayList<JavaModuleWrapper> javaModules = new ArrayList<>();
        for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
            Class<?> type = entry.getKey();
            if (!CxxModuleWrapper.class.isAssignableFrom(type)) {
                javaModules.add(new JavaModuleWrapper(catalystInstanceImpl, entry.getValue()));
            }
        }
        return javaModules;
    }

    Collection<CxxModuleWrapper> getCxxModules() {
        ArrayList<CxxModuleWrapper> cxxModules = new ArrayList<>();
        for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
            Class<?> type = entry.getKey();
            if (CxxModuleWrapper.class.isAssignableFrom(type)) {
                cxxModules.add((CxxModuleWrapper) entry.getValue().getModule());
            }
        }
        return cxxModules;
    }

    void notifyCatalystInstanceDestroy() {
        UiThreadUtil.assertOnUiThread();
        Systrace.beginSection(0L, "NativeModuleRegistry_notifyCatalystInstanceDestroy");
        try {
            for (ModuleHolder module : this.mModules.values()) {
                module.destroy();
            }
            Systrace.endSection(0L);
        } catch (Throwable th) {
            Systrace.endSection(0L);
            throw th;
        }
    }

    void notifyCatalystInstanceInitialized() {
        UiThreadUtil.assertOnUiThread();
        ReactMarker.logMarker("NativeModule_start");
        Systrace.beginSection(0L, "NativeModuleRegistry_notifyCatalystInstanceInitialized");
        try {
            for (ModuleHolder module : this.mModules.values()) {
                module.initialize();
            }
            Systrace.endSection(0L);
            ReactMarker.logMarker("NativeModule_end");
        } catch (Throwable th) {
            Systrace.endSection(0L);
            ReactMarker.logMarker("NativeModule_end");
            throw th;
        }
    }

    public void onBatchComplete() {
        for (int i = 0; i < this.mBatchCompleteListenerModules.size(); i++) {
            this.mBatchCompleteListenerModules.get(i).onBatchComplete();
        }
    }

    public <T extends NativeModule> boolean hasModule(Class<T> moduleInterface) {
        return this.mModules.containsKey(moduleInterface);
    }

    public <T extends NativeModule> T getModule(Class<T> cls) {
        return (T) ((ModuleHolder) Assertions.assertNotNull(this.mModules.get(cls))).getModule();
    }

    public List<NativeModule> getAllModules() {
        List<NativeModule> modules = new ArrayList<>();
        for (ModuleHolder module : this.mModules.values()) {
            modules.add(module.getModule());
        }
        return modules;
    }
}
