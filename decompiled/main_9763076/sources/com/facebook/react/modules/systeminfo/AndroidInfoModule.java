package com.facebook.react.modules.systeminfo;

import android.os.Build;
import com.facebook.react.bridge.BaseJavaModule;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AndroidInfoModule extends BaseJavaModule {
    private static final String IS_TESTING = "IS_TESTING";

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "AndroidConstants";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        HashMap<String, Object> constants = new HashMap<>();
        constants.put("Version", Integer.valueOf(Build.VERSION.SDK_INT));
        constants.put("ServerHost", AndroidInfoHelpers.getServerHost());
        constants.put("isTesting", Boolean.valueOf("true".equals(System.getProperty(IS_TESTING))));
        return constants;
    }
}
