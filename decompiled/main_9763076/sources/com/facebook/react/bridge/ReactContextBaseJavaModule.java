package com.facebook.react.bridge;

import android.app.Activity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ReactContextBaseJavaModule extends BaseJavaModule {
    private final ReactApplicationContext mReactApplicationContext;

    public ReactContextBaseJavaModule(ReactApplicationContext reactContext) {
        this.mReactApplicationContext = reactContext;
    }

    protected final ReactApplicationContext getReactApplicationContext() {
        return this.mReactApplicationContext;
    }

    protected final Activity getCurrentActivity() {
        return this.mReactApplicationContext.getCurrentActivity();
    }
}
