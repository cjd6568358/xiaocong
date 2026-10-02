package com.facebook.react;

import com.facebook.react.bridge.WritableNativeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface JSCConfig {
    public static final JSCConfig EMPTY = new JSCConfig() { // from class: com.facebook.react.JSCConfig.1
        @Override // com.facebook.react.JSCConfig
        public WritableNativeMap getConfigMap() {
            return new WritableNativeMap();
        }
    };

    WritableNativeMap getConfigMap();
}
