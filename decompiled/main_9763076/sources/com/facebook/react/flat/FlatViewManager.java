package com.facebook.react.flat;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class FlatViewManager extends ViewGroupManager<FlatViewGroup> {
    FlatViewManager() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public FlatViewGroup createViewInstance(ThemedReactContext reactContext) {
        return new FlatViewGroup(reactContext);
    }

    @Override // com.facebook.react.uimanager.BaseViewManager
    public void setBackgroundColor(FlatViewGroup view, int backgroundColor) {
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeAllViews(FlatViewGroup parent) {
        parent.removeAllViewsInLayout();
    }
}
