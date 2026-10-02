package com.facebook.react.cxxbridge;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.module.model.Info;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LegacyModuleInfo implements Info {
    public final NativeModule mNativeModule;
    public final Class<?> mType;

    public LegacyModuleInfo(Class<?> type, NativeModule nativeModule) {
        this.mType = type;
        this.mNativeModule = nativeModule;
    }

    @Override // com.facebook.react.module.model.Info
    public String name() {
        return this.mNativeModule.getName();
    }

    @Override // com.facebook.react.module.model.Info
    public boolean canOverrideExistingModule() {
        return this.mNativeModule.canOverrideExistingModule();
    }

    @Override // com.facebook.react.module.model.Info
    public boolean needsEagerInit() {
        return true;
    }
}
