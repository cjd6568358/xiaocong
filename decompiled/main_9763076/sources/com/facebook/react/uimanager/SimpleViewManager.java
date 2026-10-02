package com.facebook.react.uimanager;

import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class SimpleViewManager<T extends View> extends BaseViewManager<T, LayoutShadowNode> {
    @Override // com.facebook.react.uimanager.ViewManager
    public LayoutShadowNode createShadowNodeInstance() {
        return new LayoutShadowNode();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<LayoutShadowNode> getShadowNodeClass() {
        return LayoutShadowNode.class;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(T root, Object extraData) {
    }
}
