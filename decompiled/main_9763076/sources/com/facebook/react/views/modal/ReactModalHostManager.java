package com.facebook.react.views.modal;

import android.content.DialogInterface;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactModalHostManager extends ViewGroupManager<ReactModalHostView> {
    protected static final String REACT_CLASS = "RCTModalHostView";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactModalHostView createViewInstance(ThemedReactContext reactContext) {
        return new ReactModalHostView(reactContext);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public LayoutShadowNode createShadowNodeInstance() {
        return new ModalHostShadowNode();
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public Class<? extends LayoutShadowNode> getShadowNodeClass() {
        return ModalHostShadowNode.class;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void onDropViewInstance(ReactModalHostView view) {
        super.onDropViewInstance(view);
        view.onDropInstance();
    }

    @ReactProp(name = "animationType")
    public void setAnimationType(ReactModalHostView view, String animationType) {
        view.setAnimationType(animationType);
    }

    @ReactProp(name = "transparent")
    public void setTransparent(ReactModalHostView view, boolean transparent) {
        view.setTransparent(transparent);
    }

    @ReactProp(name = "hardwareAccelerated")
    public void setHardwareAccelerated(ReactModalHostView view, boolean hardwareAccelerated) {
        view.setHardwareAccelerated(hardwareAccelerated);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, final ReactModalHostView view) {
        final EventDispatcher dispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        view.setOnRequestCloseListener(new ReactModalHostView.OnRequestCloseListener() { // from class: com.facebook.react.views.modal.ReactModalHostManager.1
            @Override // com.facebook.react.views.modal.ReactModalHostView.OnRequestCloseListener
            public void onRequestClose(DialogInterface dialog) {
                dispatcher.dispatchEvent(new RequestCloseEvent(view.getId()));
            }
        });
        view.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.facebook.react.views.modal.ReactModalHostManager.2
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialog) {
                dispatcher.dispatchEvent(new ShowEvent(view.getId()));
            }
        });
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder().put("topRequestClose", MapBuilder.of("registrationName", "onRequestClose")).put("topShow", MapBuilder.of("registrationName", "onShow")).build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void onAfterUpdateTransaction(ReactModalHostView view) {
        super.onAfterUpdateTransaction(view);
        view.showOrUpdate();
    }
}
