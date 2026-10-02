package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface NativeModule {

    public interface NativeMethod {
        String getType();

        void invoke(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray readableNativeArray);
    }

    boolean canOverrideExistingModule();

    String getName();

    void initialize();

    void onCatalystInstanceDestroy();
}
