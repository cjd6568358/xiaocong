package com.facebook.react.modules.permissions;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.SparseArray;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PermissionsModule extends ReactContextBaseJavaModule implements PermissionListener {
    private static final String ERROR_INVALID_ACTIVITY = "E_INVALID_ACTIVITY";
    private final String DENIED;
    private final String GRANTED;
    private final String NEVER_ASK_AGAIN;
    private final SparseArray<Callback> mCallbacks;
    private int mRequestCode;

    public PermissionsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mRequestCode = 0;
        this.GRANTED = "granted";
        this.DENIED = "denied";
        this.NEVER_ASK_AGAIN = "never_ask_again";
        this.mCallbacks = new SparseArray<>();
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "PermissionsAndroid";
    }

    @ReactMethod
    public void checkPermission(String permission, Promise promise) {
        Context context = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT < 23) {
            promise.resolve(Boolean.valueOf(context.checkPermission(permission, Process.myPid(), Process.myUid()) == 0));
        } else {
            promise.resolve(Boolean.valueOf(context.checkSelfPermission(permission) == 0));
        }
    }

    @ReactMethod
    public void shouldShowRequestPermissionRationale(String permission, Promise promise) {
        if (Build.VERSION.SDK_INT < 23) {
            promise.resolve(false);
            return;
        }
        try {
            promise.resolve(Boolean.valueOf(getPermissionAwareActivity().shouldShowRequestPermissionRationale(permission)));
        } catch (IllegalStateException e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @ReactMethod
    public void requestPermission(final String permission, final Promise promise) {
        Context context = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == 0) {
                promise.resolve("granted");
                return;
            }
            try {
                PermissionAwareActivity activity = getPermissionAwareActivity();
                this.mCallbacks.put(this.mRequestCode, new Callback() { // from class: com.facebook.react.modules.permissions.PermissionsModule.1
                    @Override // com.facebook.react.bridge.Callback
                    public void invoke(Object... args) {
                        int[] results = (int[]) args[0];
                        if (results[0] == 0) {
                            promise.resolve("granted");
                            return;
                        }
                        PermissionAwareActivity activity2 = (PermissionAwareActivity) args[1];
                        if (activity2.shouldShowRequestPermissionRationale(permission)) {
                            promise.resolve("denied");
                        } else {
                            promise.resolve("never_ask_again");
                        }
                    }
                });
                activity.requestPermissions(new String[]{permission}, this.mRequestCode, this);
                this.mRequestCode++;
                return;
            } catch (IllegalStateException e) {
                promise.reject(ERROR_INVALID_ACTIVITY, e);
                return;
            }
        }
        promise.resolve(Boolean.valueOf(context.checkPermission(permission, Process.myPid(), Process.myUid()) == 0));
    }

    @ReactMethod
    public void requestMultiplePermissions(ReadableArray permissions, final Promise promise) {
        final WritableMap grantedPermissions = new WritableNativeMap();
        final ArrayList<String> permissionsToCheck = new ArrayList<>();
        int checkedPermissionsCount = 0;
        Context context = getReactApplicationContext().getBaseContext();
        for (int i = 0; i < permissions.size(); i++) {
            String perm = permissions.getString(i);
            if (Build.VERSION.SDK_INT < 23) {
                grantedPermissions.putString(perm, context.checkPermission(perm, Process.myPid(), Process.myUid()) == 0 ? "granted" : "denied");
                checkedPermissionsCount++;
            } else if (context.checkSelfPermission(perm) == 0) {
                grantedPermissions.putString(perm, "granted");
                checkedPermissionsCount++;
            } else {
                permissionsToCheck.add(perm);
            }
        }
        if (permissions.size() == checkedPermissionsCount) {
            promise.resolve(grantedPermissions);
            return;
        }
        try {
            PermissionAwareActivity activity = getPermissionAwareActivity();
            this.mCallbacks.put(this.mRequestCode, new Callback() { // from class: com.facebook.react.modules.permissions.PermissionsModule.2
                @Override // com.facebook.react.bridge.Callback
                public void invoke(Object... args) {
                    int[] results = (int[]) args[0];
                    PermissionAwareActivity activity2 = (PermissionAwareActivity) args[1];
                    for (int j = 0; j < permissionsToCheck.size(); j++) {
                        String permission = (String) permissionsToCheck.get(j);
                        if (results[j] == 0) {
                            grantedPermissions.putString(permission, "granted");
                        } else if (activity2.shouldShowRequestPermissionRationale(permission)) {
                            grantedPermissions.putString(permission, "denied");
                        } else {
                            grantedPermissions.putString(permission, "never_ask_again");
                        }
                    }
                    promise.resolve(grantedPermissions);
                }
            });
            activity.requestPermissions((String[]) permissionsToCheck.toArray(new String[0]), this.mRequestCode, this);
            this.mRequestCode++;
        } catch (IllegalStateException e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @Override // com.facebook.react.modules.core.PermissionListener
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.mCallbacks.get(requestCode).invoke(grantResults, getPermissionAwareActivity());
        this.mCallbacks.remove(requestCode);
        return this.mCallbacks.size() == 0;
    }

    private PermissionAwareActivity getPermissionAwareActivity() {
        ComponentCallbacks2 currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            throw new IllegalStateException("Tried to use permissions API while not attached to an Activity.");
        }
        if (!(currentActivity instanceof PermissionAwareActivity)) {
            throw new IllegalStateException("Tried to use permissions API but the host Activity doesn't implement PermissionAwareActivity.");
        }
        return (PermissionAwareActivity) currentActivity;
    }
}
