package com.facebook.react.flat;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.views.modal.ReactModalHostManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RCTModalHostManager extends ReactModalHostManager {
    @Override // com.facebook.react.views.modal.ReactModalHostManager, com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public LayoutShadowNode createShadowNodeInstance() {
        return new FlatReactModalShadowNode();
    }

    @Override // com.facebook.react.views.modal.ReactModalHostManager, com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public Class<? extends LayoutShadowNode> getShadowNodeClass() {
        return FlatReactModalShadowNode.class;
    }
}
