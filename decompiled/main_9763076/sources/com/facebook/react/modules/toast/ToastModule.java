package com.facebook.react.modules.toast;

import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ToastModule extends ReactContextBaseJavaModule {
    private static final String DURATION_LONG_KEY = "LONG";
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String GRAVITY_BOTTOM_KEY = "BOTTOM";
    private static final String GRAVITY_CENTER = "CENTER";
    private static final String GRAVITY_TOP_KEY = "TOP";

    public ToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "ToastAndroid";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = MapBuilder.newHashMap();
        constants.put(DURATION_SHORT_KEY, 0);
        constants.put(DURATION_LONG_KEY, 1);
        constants.put(GRAVITY_TOP_KEY, 49);
        constants.put(GRAVITY_BOTTOM_KEY, 81);
        constants.put(GRAVITY_CENTER, 17);
        return constants;
    }

    @ReactMethod
    public void show(final String message, final int duration) {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.toast.ToastModule.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(ToastModule.this.getReactApplicationContext(), message, duration).show();
            }
        });
    }

    @ReactMethod
    public void showWithGravity(final String message, final int duration, final int gravity) {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.toast.ToastModule.2
            @Override // java.lang.Runnable
            public void run() {
                Toast toast = Toast.makeText(ToastModule.this.getReactApplicationContext(), message, duration);
                toast.setGravity(gravity, 0, 0);
                toast.show();
            }
        });
    }
}
