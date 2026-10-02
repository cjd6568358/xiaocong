package com.facebook.react.views.art;

import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNodeAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ARTSurfaceViewManager extends BaseViewManager<ARTSurfaceView, ARTSurfaceViewShadowNode> {
    private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() { // from class: com.facebook.react.views.art.ARTSurfaceViewManager.1
        @Override // com.facebook.yoga.YogaMeasureFunction
        public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
            throw new IllegalStateException("SurfaceView should have explicit width and height set");
        }
    };
    protected static final String REACT_CLASS = "ARTSurfaceView";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ARTSurfaceViewShadowNode createShadowNodeInstance() {
        ARTSurfaceViewShadowNode node = new ARTSurfaceViewShadowNode();
        node.setMeasureFunction(MEASURE_FUNCTION);
        return node;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<ARTSurfaceViewShadowNode> getShadowNodeClass() {
        return ARTSurfaceViewShadowNode.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ARTSurfaceView createViewInstance(ThemedReactContext reactContext) {
        return new ARTSurfaceView(reactContext);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(ARTSurfaceView root, Object extraData) {
        root.setSurfaceTextureListener((ARTSurfaceViewShadowNode) extraData);
    }
}
