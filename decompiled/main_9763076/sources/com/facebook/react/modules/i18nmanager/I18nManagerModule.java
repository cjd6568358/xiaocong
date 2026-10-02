package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.MapBuilder;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class I18nManagerModule extends ReactContextBaseJavaModule {
    private final I18nUtil sharedI18nUtilInstance;

    public I18nManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.sharedI18nUtilInstance = I18nUtil.getInstance();
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "I18nManager";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        Context context = getReactApplicationContext().getBaseContext();
        Locale locale = context.getResources().getConfiguration().locale;
        Map<String, Object> constants = MapBuilder.newHashMap();
        constants.put("isRTL", Boolean.valueOf(this.sharedI18nUtilInstance.isRTL(getReactApplicationContext())));
        constants.put("localeIdentifier", locale.toString());
        return constants;
    }

    @ReactMethod
    public void allowRTL(boolean value) {
        this.sharedI18nUtilInstance.allowRTL(getReactApplicationContext(), value);
    }

    @ReactMethod
    public void forceRTL(boolean value) {
        this.sharedI18nUtilInstance.forceRTL(getReactApplicationContext(), value);
    }
}
