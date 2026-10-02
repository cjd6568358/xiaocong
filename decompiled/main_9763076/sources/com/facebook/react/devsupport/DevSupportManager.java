package com.facebook.react.devsupport;

import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.modules.debug.DeveloperSettings;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DevSupportManager extends NativeModuleCallExceptionHandler {
    DeveloperSettings getDevSettings();

    boolean getDevSupportEnabled();

    String getDownloadedJSBundleFile();

    String getSourceUrl();

    void handleReloadJS();

    boolean hasUpToDateJSBundleInCache();

    void hideRedboxDialog();

    void isPackagerRunning(DevServerHelper.PackagerStatusCallback packagerStatusCallback);

    void onNewReactContextCreated(ReactContext reactContext);

    void onReactInstanceDestroyed(ReactContext reactContext);

    void setDevSupportEnabled(boolean z);

    void showDevOptionsDialog();

    void showNewJSError(String str, ReadableArray readableArray, int i);

    void updateJSError(String str, ReadableArray readableArray, int i);
}
