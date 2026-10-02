package com.facebook.react.devsupport;

import com.facebook.react.bridge.DefaultNativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.modules.debug.DeveloperSettings;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DisabledDevSupportManager implements DevSupportManager {
    private final DefaultNativeModuleCallExceptionHandler mDefaultNativeModuleCallExceptionHandler = new DefaultNativeModuleCallExceptionHandler();

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void showNewJSError(String message, ReadableArray details, int errorCookie) {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void updateJSError(String message, ReadableArray details, int errorCookie) {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void hideRedboxDialog() {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void showDevOptionsDialog() {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void setDevSupportEnabled(boolean isDevSupportEnabled) {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public boolean getDevSupportEnabled() {
        return false;
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public DeveloperSettings getDevSettings() {
        return null;
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void onNewReactContextCreated(ReactContext reactContext) {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void onReactInstanceDestroyed(ReactContext reactContext) {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public String getSourceUrl() {
        return null;
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public String getDownloadedJSBundleFile() {
        return null;
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public boolean hasUpToDateJSBundleInCache() {
        return false;
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void handleReloadJS() {
    }

    @Override // com.facebook.react.devsupport.DevSupportManager
    public void isPackagerRunning(DevServerHelper.PackagerStatusCallback callback) {
    }

    @Override // com.facebook.react.bridge.NativeModuleCallExceptionHandler
    public void handleException(Exception e) throws Exception {
        this.mDefaultNativeModuleCallExceptionHandler.handleException(e);
    }
}
