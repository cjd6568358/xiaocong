package com.facebook.react.bridge;

import javax.inject.Provider;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ModuleSpec {
    private final Provider<? extends NativeModule> mProvider;
    private final Class<? extends NativeModule> mType;
    private static final Class[] EMPTY_SIGNATURE = new Class[0];
    private static final Class[] CONTEXT_SIGNATURE = {ReactApplicationContext.class};

    public ModuleSpec(Class<? extends NativeModule> type, Provider<? extends NativeModule> provider) {
        this.mType = type;
        this.mProvider = provider;
    }

    public Class<? extends NativeModule> getType() {
        return this.mType;
    }

    public Provider<? extends NativeModule> getProvider() {
        return this.mProvider;
    }
}
