package com.facebook.react.cxxbridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class CallbackImpl implements Callback {

    @DoNotStrip
    private final HybridData mHybridData;

    private native void nativeInvoke(NativeArray nativeArray);

    @DoNotStrip
    private CallbackImpl(HybridData hybridData) {
        this.mHybridData = hybridData;
    }

    @Override // com.facebook.react.bridge.Callback
    public void invoke(Object... args) {
        nativeInvoke(com.facebook.react.bridge.Arguments.fromJavaArgs(args));
    }
}
