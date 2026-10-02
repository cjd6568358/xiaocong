package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.views.text.ReactRawTextManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTRawTextManager extends VirtualViewManager<RCTRawText> {
    @Override // com.facebook.react.flat.VirtualViewManager, com.facebook.react.uimanager.ViewManager
    public /* bridge */ /* synthetic */ void updateExtraData(View view, Object obj) {
        super.updateExtraData(view, obj);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return ReactRawTextManager.REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public RCTRawText createShadowNodeInstance() {
        return new RCTRawText();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<RCTRawText> getShadowNodeClass() {
        return RCTRawText.class;
    }
}
