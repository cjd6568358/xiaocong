package com.facebook.react.modules.debug;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactContext;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SourceCodeModule extends BaseJavaModule {
    public static final String NAME = "SourceCode";
    private final ReactContext mReactContext;

    public SourceCodeModule(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        HashMap<String, Object> constants = new HashMap<>();
        String sourceURL = (String) Assertions.assertNotNull(this.mReactContext.getCatalystInstance().getSourceURL(), "No source URL loaded, have you initialised the instance?");
        constants.put("scriptURL", sourceURL);
        return constants;
    }
}
