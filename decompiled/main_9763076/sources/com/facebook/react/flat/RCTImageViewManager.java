package com.facebook.react.flat;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTImageViewManager extends FlatViewManager {
    private final Object mCallerContext;
    private AbstractDraweeControllerBuilder mDraweeControllerBuilder;

    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void removeAllViews(FlatViewGroup flatViewGroup) {
        super.removeAllViews(flatViewGroup);
    }

    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void setBackgroundColor(FlatViewGroup flatViewGroup, int i) {
        super.setBackgroundColor(flatViewGroup, i);
    }

    public RCTImageViewManager() {
        this(null, null);
    }

    public RCTImageViewManager(AbstractDraweeControllerBuilder draweeControllerBuilder, Object callerContext) {
        this.mDraweeControllerBuilder = draweeControllerBuilder;
        this.mCallerContext = callerContext;
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RCTImageView";
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public RCTImageView createShadowNodeInstance() {
        return new RCTImageView(new DrawImageWithDrawee());
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public Class<RCTImageView> getShadowNodeClass() {
        return RCTImageView.class;
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        if (this.mDraweeControllerBuilder == null) {
            this.mDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        }
        return this.mDraweeControllerBuilder;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }
}
