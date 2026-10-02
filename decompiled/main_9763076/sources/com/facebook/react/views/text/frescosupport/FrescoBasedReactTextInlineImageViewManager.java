package com.facebook.react.views.text.frescosupport;

import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FrescoBasedReactTextInlineImageViewManager extends ViewManager<View, FrescoBasedReactTextInlineImageShadowNode> {
    protected static final String REACT_CLASS = "RCTTextInlineImage";
    private final Object mCallerContext;
    private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;

    public FrescoBasedReactTextInlineImageViewManager() {
        this(null, null);
    }

    public FrescoBasedReactTextInlineImageViewManager(AbstractDraweeControllerBuilder draweeControllerBuilder, Object callerContext) {
        this.mDraweeControllerBuilder = draweeControllerBuilder;
        this.mCallerContext = callerContext;
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public View createViewInstance(ThemedReactContext context) {
        throw new IllegalStateException("RCTTextInlineImage doesn't map into a native view");
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public FrescoBasedReactTextInlineImageShadowNode createShadowNodeInstance() {
        return new FrescoBasedReactTextInlineImageShadowNode(this.mDraweeControllerBuilder != null ? this.mDraweeControllerBuilder : Fresco.newDraweeControllerBuilder(), this.mCallerContext);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<FrescoBasedReactTextInlineImageShadowNode> getShadowNodeClass() {
        return FrescoBasedReactTextInlineImageShadowNode.class;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(View root, Object extraData) {
    }
}
