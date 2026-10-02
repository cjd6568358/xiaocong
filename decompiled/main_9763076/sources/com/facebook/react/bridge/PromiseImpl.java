package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PromiseImpl implements Promise {
    private Callback mReject;
    private Callback mResolve;

    public PromiseImpl(Callback resolve, Callback reject) {
        this.mResolve = resolve;
        this.mReject = reject;
    }

    @Override // com.facebook.react.bridge.Promise
    public void resolve(Object value) {
        if (this.mResolve != null) {
            this.mResolve.invoke(value);
        }
    }

    @Override // com.facebook.react.bridge.Promise
    public void reject(String code, String message) {
        reject(code, message, null);
    }

    @Override // com.facebook.react.bridge.Promise
    @Deprecated
    public void reject(String message) {
        reject("EUNSPECIFIED", message, null);
    }

    @Override // com.facebook.react.bridge.Promise
    public void reject(String code, Throwable e) {
        reject(code, e.getMessage(), e);
    }

    @Override // com.facebook.react.bridge.Promise
    public void reject(Throwable e) {
        reject("EUNSPECIFIED", e.getMessage(), e);
    }

    @Override // com.facebook.react.bridge.Promise
    public void reject(String code, String message, Throwable e) {
        if (this.mReject != null) {
            if (code == null) {
                code = "EUNSPECIFIED";
            }
            WritableNativeMap errorInfo = new WritableNativeMap();
            errorInfo.putString("code", code);
            errorInfo.putString("message", message);
            this.mReject.invoke(errorInfo);
        }
    }
}
