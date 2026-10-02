package com.facebook.react.modules.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.android.tpush.common.Constants;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IntentModule extends ReactContextBaseJavaModule {
    public IntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "IntentAndroid";
    }

    @ReactMethod
    public void getInitialURL(Promise promise) {
        try {
            Activity currentActivity = getCurrentActivity();
            String initialURL = null;
            if (currentActivity != null) {
                Intent intent = currentActivity.getIntent();
                String action = intent.getAction();
                Uri uri = intent.getData();
                if ("android.intent.action.VIEW".equals(action) && uri != null) {
                    initialURL = uri.toString();
                }
            }
            promise.resolve(initialURL);
        } catch (Exception e) {
            promise.reject(new JSApplicationIllegalArgumentException("Could not get the initial URL : " + e.getMessage()));
        }
    }

    @ReactMethod
    public void openURL(String url, Promise promise) {
        if (url == null || url.isEmpty()) {
            promise.reject(new JSApplicationIllegalArgumentException("Invalid URL: " + url));
            return;
        }
        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            String selfPackageName = getReactApplicationContext().getPackageName();
            ComponentName componentName = intent.resolveActivity(getReactApplicationContext().getPackageManager());
            String otherPackageName = componentName != null ? componentName.getPackageName() : Constants.MAIN_VERSION_TAG;
            if (currentActivity == null || !selfPackageName.equals(otherPackageName)) {
                intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            }
            if (currentActivity != null) {
                currentActivity.startActivity(intent);
            } else {
                getReactApplicationContext().startActivity(intent);
            }
            promise.resolve(true);
        } catch (Exception e) {
            promise.reject(new JSApplicationIllegalArgumentException("Could not open URL '" + url + "': " + e.getMessage()));
        }
    }

    @ReactMethod
    public void canOpenURL(String url, Promise promise) {
        if (url == null || url.isEmpty()) {
            promise.reject(new JSApplicationIllegalArgumentException("Invalid URL: " + url));
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            boolean canOpen = intent.resolveActivity(getReactApplicationContext().getPackageManager()) != null;
            promise.resolve(Boolean.valueOf(canOpen));
        } catch (Exception e) {
            promise.reject(new JSApplicationIllegalArgumentException("Could not check if URL '" + url + "' can be opened: " + e.getMessage()));
        }
    }
}
