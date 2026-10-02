package com.facebook.react.views.drawer;

import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.drawer.events.DrawerClosedEvent;
import com.facebook.react.views.drawer.events.DrawerOpenedEvent;
import com.facebook.react.views.drawer.events.DrawerSlideEvent;
import com.facebook.react.views.drawer.events.DrawerStateChangedEvent;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactDrawerLayoutManager extends ViewGroupManager<ReactDrawerLayout> {
    public static final int CLOSE_DRAWER = 2;
    public static final int OPEN_DRAWER = 1;
    protected static final String REACT_CLASS = "AndroidDrawerLayout";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, ReactDrawerLayout view) {
        view.setDrawerListener(new DrawerEventEmitter(view, ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactDrawerLayout createViewInstance(ThemedReactContext context) {
        return new ReactDrawerLayout(context);
    }

    @ReactProp(defaultInt = 8388611, name = "drawerPosition")
    public void setDrawerPosition(ReactDrawerLayout view, int drawerPosition) {
        if (8388611 == drawerPosition || 8388613 == drawerPosition) {
            view.setDrawerPosition(drawerPosition);
            return;
        }
        throw new JSApplicationIllegalArgumentException("Unknown drawerPosition " + drawerPosition);
    }

    @ReactProp(defaultFloat = Float.NaN, name = "drawerWidth")
    public void getDrawerWidth(ReactDrawerLayout view, float width) {
        int widthInPx = Float.isNaN(width) ? -1 : Math.round(PixelUtil.toPixelFromDIP(width));
        view.setDrawerWidth(widthInPx);
    }

    @ReactProp(name = "drawerLockMode")
    public void setDrawerLockMode(ReactDrawerLayout view, String drawerLockMode) {
        if (drawerLockMode == null || "unlocked".equals(drawerLockMode)) {
            view.setDrawerLockMode(0);
        } else if ("locked-closed".equals(drawerLockMode)) {
            view.setDrawerLockMode(1);
        } else {
            if ("locked-open".equals(drawerLockMode)) {
                view.setDrawerLockMode(2);
                return;
            }
            throw new JSApplicationIllegalArgumentException("Unknown drawerLockMode " + drawerLockMode);
        }
    }

    @Override // com.facebook.react.uimanager.BaseViewManager
    public void setElevation(ReactDrawerLayout view, float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                Method method = ReactDrawerLayout.class.getMethod("setDrawerElevation", Float.TYPE);
                method.invoke(view, Float.valueOf(PixelUtil.toPixelFromDIP(elevation)));
            } catch (Exception ex) {
                FLog.w("React", "setDrawerElevation is not available in this version of the support lib.", ex);
            }
        }
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("openDrawer", 1, "closeDrawer", 2);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(ReactDrawerLayout root, int commandId, ReadableArray args) {
        switch (commandId) {
            case 1:
                root.openDrawer();
                break;
            case 2:
                root.closeDrawer();
                break;
        }
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map getExportedViewConstants() {
        return MapBuilder.of("DrawerPosition", MapBuilder.of("Left", 8388611, "Right", 8388613));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of("topDrawerSlide", MapBuilder.of("registrationName", "onDrawerSlide"), "topDrawerOpened", MapBuilder.of("registrationName", "onDrawerOpen"), "topDrawerClosed", MapBuilder.of("registrationName", "onDrawerClose"), "topDrawerStateChanged", MapBuilder.of("registrationName", "onDrawerStateChanged"));
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void addView(ReactDrawerLayout parent, View child, int index) {
        if (getChildCount(parent) >= 2) {
            throw new JSApplicationIllegalArgumentException("The Drawer cannot have more than two children");
        }
        if (index != 0 && index != 1) {
            throw new JSApplicationIllegalArgumentException("The only valid indices for drawer's child are 0 or 1. Got " + index + " instead.");
        }
        parent.addView(child, index);
        parent.setDrawerProperties();
    }

    public static class DrawerEventEmitter implements DrawerLayout.DrawerListener {
        private final DrawerLayout mDrawerLayout;
        private final EventDispatcher mEventDispatcher;

        public DrawerEventEmitter(DrawerLayout drawerLayout, EventDispatcher eventDispatcher) {
            this.mDrawerLayout = drawerLayout;
            this.mEventDispatcher = eventDispatcher;
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerSlide(View view, float v) {
            this.mEventDispatcher.dispatchEvent(new DrawerSlideEvent(this.mDrawerLayout.getId(), v));
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerOpened(View view) {
            this.mEventDispatcher.dispatchEvent(new DrawerOpenedEvent(this.mDrawerLayout.getId()));
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerClosed(View view) {
            this.mEventDispatcher.dispatchEvent(new DrawerClosedEvent(this.mDrawerLayout.getId()));
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        public void onDrawerStateChanged(int i) {
            this.mEventDispatcher.dispatchEvent(new DrawerStateChangedEvent(this.mDrawerLayout.getId(), i));
        }
    }
}
