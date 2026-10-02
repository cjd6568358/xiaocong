package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultNativeModuleCallExceptionHandler implements NativeModuleCallExceptionHandler {
    @Override // com.facebook.react.bridge.NativeModuleCallExceptionHandler
    public void handleException(Exception e) throws Exception {
        if (e instanceof RuntimeException) {
            throw ((RuntimeException) e);
        }
        throw new RuntimeException(e);
    }
}
