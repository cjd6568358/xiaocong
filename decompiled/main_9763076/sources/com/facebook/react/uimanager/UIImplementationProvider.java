package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UIImplementationProvider {
    public UIImplementation createUIImplementation(ReactApplicationContext reactContext, List<ViewManager> viewManagers, EventDispatcher eventDispatcher) {
        return new UIImplementation(reactContext, viewManagers, eventDispatcher);
    }
}
