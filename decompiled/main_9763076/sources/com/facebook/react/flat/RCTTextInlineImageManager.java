package com.facebook.react.flat;

import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTTextInlineImageManager extends VirtualViewManager<RCTTextInlineImage> {
    @Override // com.facebook.react.flat.VirtualViewManager, com.facebook.react.uimanager.ViewManager
    public /* bridge */ /* synthetic */ void updateExtraData(View view, Object obj) {
        super.updateExtraData(view, obj);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RCTTextInlineImage";
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public RCTTextInlineImage createShadowNodeInstance() {
        return new RCTTextInlineImage();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<RCTTextInlineImage> getShadowNodeClass() {
        return RCTTextInlineImage.class;
    }
}
