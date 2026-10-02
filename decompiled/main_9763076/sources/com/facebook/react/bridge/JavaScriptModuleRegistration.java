package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaScriptModuleRegistration {
    private final Class<? extends JavaScriptModule> mModuleInterface;
    private String mName;

    public JavaScriptModuleRegistration(Class<? extends JavaScriptModule> moduleInterface) {
        this.mModuleInterface = moduleInterface;
    }

    public Class<? extends JavaScriptModule> getModuleInterface() {
        return this.mModuleInterface;
    }

    public String getName() {
        if (this.mName == null) {
            String name = this.mModuleInterface.getSimpleName();
            int dollarSignIndex = name.lastIndexOf(36);
            if (dollarSignIndex != -1) {
                name = name.substring(dollarSignIndex + 1);
            }
            this.mName = name;
        }
        return this.mName;
    }
}
