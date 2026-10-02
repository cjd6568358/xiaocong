package com.facebook.react.cxxbridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.NativeModule;
import com.facebook.soloader.SoLoader;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class CxxModuleWrapper implements NativeModule {

    @DoNotStrip
    private HybridData mHybridData;

    private native HybridData initHybrid(String str, String str2);

    @Override // com.facebook.react.bridge.NativeModule
    public native String getName();

    static {
        SoLoader.loadLibrary("reactnativejnifb");
    }

    public CxxModuleWrapper(String library, String factory) {
        SoLoader.loadLibrary(library);
        this.mHybridData = initHybrid(SoLoader.unpackLibraryAndDependencies(library).getAbsolutePath(), factory);
    }

    public Map<String, NativeModule.NativeMethod> getMethods() {
        throw new UnsupportedOperationException();
    }

    @Override // com.facebook.react.bridge.NativeModule
    public void initialize() {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public boolean canOverrideExistingModule() {
        return false;
    }

    public boolean supportsWebWorkers() {
        return false;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        this.mHybridData.resetNative();
    }

    protected CxxModuleWrapper(HybridData hd) {
        this.mHybridData = hd;
    }
}
