package com.facebook.react;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ReactPackage {
    List<Class<? extends JavaScriptModule>> createJSModules();

    List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext);

    List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext);
}
