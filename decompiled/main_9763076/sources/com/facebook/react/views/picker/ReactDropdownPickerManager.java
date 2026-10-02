package com.facebook.react.views.picker;

import com.facebook.react.uimanager.ThemedReactContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactDropdownPickerManager extends ReactPickerManager {
    protected static final String REACT_CLASS = "AndroidDropdownPicker";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactPicker createViewInstance(ThemedReactContext reactContext) {
        return new ReactPicker(reactContext, 1);
    }
}
