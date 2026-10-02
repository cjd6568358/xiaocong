package com.facebook.react.views.text;

import com.facebook.react.uimanager.ThemedReactContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactRawTextManager extends ReactTextViewManager {
    public static final String REACT_CLASS = "RCTRawText";

    @Override // com.facebook.react.views.text.ReactTextViewManager, com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.views.text.ReactTextViewManager, com.facebook.react.uimanager.ViewManager
    public ReactTextView createViewInstance(ThemedReactContext context) {
        throw new IllegalStateException("RKRawText doesn't map into a native view");
    }

    @Override // com.facebook.react.views.text.ReactTextViewManager, com.facebook.react.uimanager.ViewManager
    public void updateExtraData(ReactTextView view, Object extraData) {
    }

    @Override // com.facebook.react.views.text.ReactTextViewManager, com.facebook.react.uimanager.ViewManager
    public ReactTextShadowNode createShadowNodeInstance() {
        return new ReactVirtualTextShadowNode();
    }
}
