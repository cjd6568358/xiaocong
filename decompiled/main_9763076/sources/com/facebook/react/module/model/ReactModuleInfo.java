package com.facebook.react.module.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactModuleInfo implements Info {
    private final boolean mCanOverrideExistingModule;
    private final String mName;
    private final boolean mNeedsEagerInit;

    @Override // com.facebook.react.module.model.Info
    public String name() {
        return this.mName;
    }

    @Override // com.facebook.react.module.model.Info
    public boolean canOverrideExistingModule() {
        return this.mCanOverrideExistingModule;
    }

    @Override // com.facebook.react.module.model.Info
    public boolean needsEagerInit() {
        return this.mNeedsEagerInit;
    }
}
