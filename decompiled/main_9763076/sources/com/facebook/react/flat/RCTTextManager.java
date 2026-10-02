package com.facebook.react.flat;

import com.facebook.react.views.text.ReactTextViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTTextManager extends FlatViewManager {
    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void removeAllViews(FlatViewGroup flatViewGroup) {
        super.removeAllViews(flatViewGroup);
    }

    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void setBackgroundColor(FlatViewGroup flatViewGroup, int i) {
        super.setBackgroundColor(flatViewGroup, i);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return ReactTextViewManager.REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public RCTText createShadowNodeInstance() {
        return new RCTText();
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public Class<RCTText> getShadowNodeClass() {
        return RCTText.class;
    }
}
