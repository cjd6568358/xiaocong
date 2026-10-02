package com.facebook.react.uimanager;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThemedReactContext extends ReactContext {
    private final ReactApplicationContext mReactApplicationContext;

    public ThemedReactContext(ReactApplicationContext reactApplicationContext, Context base) {
        super(base);
        initializeWithInstance(reactApplicationContext.getCatalystInstance());
        this.mReactApplicationContext = reactApplicationContext;
    }

    @Override // com.facebook.react.bridge.ReactContext
    public void addLifecycleEventListener(LifecycleEventListener listener) {
        this.mReactApplicationContext.addLifecycleEventListener(listener);
    }

    @Override // com.facebook.react.bridge.ReactContext
    public void removeLifecycleEventListener(LifecycleEventListener listener) {
        this.mReactApplicationContext.removeLifecycleEventListener(listener);
    }

    @Override // com.facebook.react.bridge.ReactContext
    public Activity getCurrentActivity() {
        return this.mReactApplicationContext.getCurrentActivity();
    }
}
