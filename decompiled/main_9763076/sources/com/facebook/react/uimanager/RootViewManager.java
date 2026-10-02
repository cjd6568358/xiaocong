package com.facebook.react.uimanager;

import android.view.ViewGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RootViewManager extends ViewGroupManager<ViewGroup> {
    public static final String REACT_CLASS = "RootView";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ViewGroup createViewInstance(ThemedReactContext reactContext) {
        return new SizeMonitoringFrameLayout(reactContext);
    }
}
