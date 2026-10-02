package com.facebook.react.modules.clipboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClipboardModule extends ReactContextBaseJavaModule {
    public ClipboardModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "Clipboard";
    }

    private ClipboardManager getClipboardService() {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        getReactApplicationContext();
        return (ClipboardManager) reactApplicationContext.getSystemService("clipboard");
    }

    @ReactMethod
    public void getString(Promise promise) {
        try {
            ClipboardManager clipboard = getClipboardService();
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() >= 1) {
                ClipData.Item firstItem = clipboard.getPrimaryClip().getItemAt(0);
                promise.resolve(Constants.MAIN_VERSION_TAG + ((Object) firstItem.getText()));
            } else {
                promise.resolve(Constants.MAIN_VERSION_TAG);
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    @SuppressLint({"DeprecatedMethod"})
    public void setString(String text) {
        getReactApplicationContext();
        if (Build.VERSION.SDK_INT >= 11) {
            ClipData clipdata = ClipData.newPlainText(null, text);
            ClipboardManager clipboard = getClipboardService();
            clipboard.setPrimaryClip(clipdata);
        } else {
            ClipboardManager clipboard2 = getClipboardService();
            clipboard2.setText(text);
        }
    }
}
