package com.facebook.react.cxxbridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.common.futures.SimpleSettableFuture;
import com.facebook.react.module.model.Info;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import java.util.concurrent.ExecutionException;
import javax.inject.Provider;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ModuleHolder {
    private final Info mInfo;
    private boolean mInitializeNeeded;
    private NativeModule mModule;
    private Provider<? extends NativeModule> mProvider;

    public ModuleHolder(ReactModuleInfo info, Provider<? extends NativeModule> provider) {
        this.mInfo = info;
        this.mProvider = provider;
        if (this.mInfo.needsEagerInit()) {
            this.mModule = doCreate();
        }
    }

    public ModuleHolder(LegacyModuleInfo info, NativeModule nativeModule) {
        this.mInfo = info;
        this.mModule = nativeModule;
    }

    public synchronized void initialize() {
        if (this.mModule != null) {
            doInitialize(this.mModule);
        } else {
            this.mInitializeNeeded = true;
        }
    }

    public synchronized void destroy() {
        if (this.mModule != null) {
            this.mModule.onCatalystInstanceDestroy();
        }
    }

    public Info getInfo() {
        return this.mInfo;
    }

    public synchronized NativeModule getModule() {
        if (this.mModule == null) {
            this.mModule = doCreate();
        }
        return this.mModule;
    }

    private NativeModule doCreate() {
        NativeModule module = create();
        this.mProvider = null;
        return module;
    }

    private NativeModule create() {
        boolean isEagerModule = this.mInfo instanceof LegacyModuleInfo;
        String name = isEagerModule ? ((LegacyModuleInfo) this.mInfo).mType.getSimpleName() : this.mInfo.name();
        if (!isEagerModule) {
            ReactMarker.logMarker("CREATE_MODULE_START");
        }
        SystraceMessage.beginSection(0L, "createModule").arg(RNMessageModule.NAME, name).flush();
        NativeModule module = (NativeModule) ((Provider) Assertions.assertNotNull(this.mProvider)).get();
        if (this.mInitializeNeeded) {
            doInitialize(module);
            this.mInitializeNeeded = false;
        }
        Systrace.endSection(0L);
        if (!isEagerModule) {
            ReactMarker.logMarker("CREATE_MODULE_END");
        }
        return module;
    }

    private void doInitialize(NativeModule module) {
        SystraceMessage.Builder section = SystraceMessage.beginSection(0L, "initialize");
        if (module instanceof CxxModuleWrapper) {
            section.arg("className", module.getClass().getSimpleName());
        } else {
            section.arg(RNMessageModule.NAME, this.mInfo.name());
        }
        section.flush();
        callInitializeOnUiThread(module);
        Systrace.endSection(0L);
    }

    private static void callInitializeOnUiThread(final NativeModule module) {
        if (UiThreadUtil.isOnUiThread()) {
            module.initialize();
            return;
        }
        final SimpleSettableFuture<Void> future = new SimpleSettableFuture<>();
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.cxxbridge.ModuleHolder.1
            @Override // java.lang.Runnable
            public void run() {
                Systrace.beginSection(0L, "initializeOnUiThread");
                try {
                    module.initialize();
                    future.set(null);
                } catch (Exception e) {
                    future.setException(e);
                }
                Systrace.endSection(0L);
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
