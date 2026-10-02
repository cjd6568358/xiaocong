package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class CallbackImpl implements Callback {
    private final int mCallbackId;
    private final CatalystInstance mCatalystInstance;
    private final ExecutorToken mExecutorToken;
    private boolean mInvoked = false;

    public CallbackImpl(CatalystInstance bridge, ExecutorToken executorToken, int callbackId) {
        this.mCatalystInstance = bridge;
        this.mExecutorToken = executorToken;
        this.mCallbackId = callbackId;
    }

    @Override // com.facebook.react.bridge.Callback
    public void invoke(Object... args) {
        if (this.mInvoked) {
            throw new RuntimeException("Illegal callback invocation from native module. This callback type only permits a single invocation from native code.");
        }
        this.mCatalystInstance.invokeCallback(this.mExecutorToken, this.mCallbackId, Arguments.fromJavaArgs(args));
        this.mInvoked = true;
    }
}
