package com.facebook.react.bridge;

import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactBridge {
    static {
        staticInit();
    }

    public static void staticInit() {
        SoLoader.loadLibrary("reactnativejni");
        SoLoader.loadLibrary("reactnativejnifb");
    }
}
