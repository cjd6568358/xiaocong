package com.facebook.react.views.swiperefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SwipeRefreshLayoutManager extends ViewGroupManager<ReactSwipeRefreshLayout> {
    protected static final String REACT_CLASS = "AndroidSwipeRefreshLayout";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactSwipeRefreshLayout createViewInstance(ThemedReactContext reactContext) {
        return new ReactSwipeRefreshLayout(reactContext);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(defaultBoolean = true, name = "enabled")
    public void setEnabled(ReactSwipeRefreshLayout view, boolean enabled) {
        view.setEnabled(enabled);
    }

    @ReactProp(customType = "ColorArray", name = "colors")
    public void setColors(ReactSwipeRefreshLayout view, ReadableArray colors) {
        if (colors != null) {
            int[] colorValues = new int[colors.size()];
            for (int i = 0; i < colors.size(); i++) {
                colorValues[i] = colors.getInt(i);
            }
            view.setColorSchemeColors(colorValues);
            return;
        }
        view.setColorSchemeColors(new int[0]);
    }

    @ReactProp(customType = "Color", defaultInt = 0, name = "progressBackgroundColor")
    public void setProgressBackgroundColor(ReactSwipeRefreshLayout view, int color) {
        view.setProgressBackgroundColorSchemeColor(color);
    }

    @ReactProp(defaultInt = 1, name = "size")
    public void setSize(ReactSwipeRefreshLayout view, int size) {
        view.setSize(size);
    }

    @ReactProp(name = "refreshing")
    public void setRefreshing(ReactSwipeRefreshLayout view, boolean refreshing) {
        view.setRefreshing(refreshing);
    }

    @ReactProp(defaultFloat = 0.0f, name = "progressViewOffset")
    public void setProgressViewOffset(ReactSwipeRefreshLayout view, float offset) {
        view.setProgressViewOffset(offset);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(final ThemedReactContext reactContext, final ReactSwipeRefreshLayout view) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.facebook.react.views.swiperefresh.SwipeRefreshLayoutManager.1
            @Override // android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new RefreshEvent(view.getId()));
            }
        });
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("SIZE", MapBuilder.of("DEFAULT", 1, "LARGE", 0));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder().put("topRefresh", MapBuilder.of("registrationName", "onRefresh")).build();
    }
}
