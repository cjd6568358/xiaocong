package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.views.text.ReactVirtualTextViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTVirtualTextManager extends VirtualViewManager<RCTVirtualText> {
    @Override // com.facebook.react.flat.VirtualViewManager, com.facebook.react.uimanager.ViewManager
    public /* bridge */ /* synthetic */ void updateExtraData(View view, Object obj) {
        super.updateExtraData(view, obj);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return ReactVirtualTextViewManager.REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public RCTVirtualText createShadowNodeInstance() {
        return new RCTVirtualText();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<RCTVirtualText> getShadowNodeClass() {
        return RCTVirtualText.class;
    }
}
