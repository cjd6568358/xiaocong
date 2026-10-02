package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.ReactShadowNode;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ViewManager<T extends View, C extends ReactShadowNode> extends BaseJavaModule {
    public abstract C createShadowNodeInstance();

    protected abstract T createViewInstance(ThemedReactContext themedReactContext);

    @Override // com.facebook.react.bridge.NativeModule
    public abstract String getName();

    public abstract Class<? extends C> getShadowNodeClass();

    public abstract void updateExtraData(T t, Object obj);

    public final void updateProperties(T viewToUpdate, ReactStylesDiffMap props) {
        ViewManagerPropertyUpdater.updateProps(this, viewToUpdate, props);
        onAfterUpdateTransaction(viewToUpdate);
    }

    public final T createView(ThemedReactContext themedReactContext, JSResponderHandler jSResponderHandler) {
        T t = (T) createViewInstance(themedReactContext);
        addEventEmitters(themedReactContext, t);
        if (t instanceof ReactInterceptingViewGroup) {
            ((ReactInterceptingViewGroup) t).setOnInterceptTouchEventListener(jSResponderHandler);
        }
        return t;
    }

    public void onDropViewInstance(T view) {
    }

    protected void addEventEmitters(ThemedReactContext reactContext, T view) {
    }

    protected void onAfterUpdateTransaction(T view) {
    }

    public void receiveCommand(T root, int commandId, ReadableArray args) {
    }

    public Map<String, Integer> getCommandsMap() {
        return null;
    }

    public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
        return null;
    }

    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return null;
    }

    public Map<String, Object> getExportedViewConstants() {
        return null;
    }

    public Map<String, String> getNativeProps() {
        return ViewManagerPropertyUpdater.getNativeProps(getClass(), getShadowNodeClass());
    }
}
