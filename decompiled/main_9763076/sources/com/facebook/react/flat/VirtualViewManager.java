package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class VirtualViewManager<C extends FlatShadowNode> extends ViewManager<View, C> {
    VirtualViewManager() {
    }

    @Override // com.facebook.react.uimanager.ViewManager
    protected View createViewInstance(ThemedReactContext reactContext) {
        throw new RuntimeException(getName() + " doesn't map to a View");
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(View root, Object extraData) {
    }
}
