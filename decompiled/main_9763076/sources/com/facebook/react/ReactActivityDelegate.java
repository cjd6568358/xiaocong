package com.facebook.react;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Callback;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionListener;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactActivityDelegate {
    private final int REQUEST_OVERLAY_PERMISSION_CODE;
    private final Activity mActivity;
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
    private final FragmentActivity mFragmentActivity;
    private final String mMainComponentName;
    private PermissionListener mPermissionListener;
    private Callback mPermissionsCallback;
    private ReactRootView mReactRootView;

    public ReactActivityDelegate(Activity activity, String mainComponentName) {
        this.REQUEST_OVERLAY_PERMISSION_CODE = 1111;
        this.mActivity = activity;
        this.mMainComponentName = mainComponentName;
        this.mFragmentActivity = null;
    }

    public ReactActivityDelegate(FragmentActivity fragmentActivity, String mainComponentName) {
        this.REQUEST_OVERLAY_PERMISSION_CODE = 1111;
        this.mFragmentActivity = fragmentActivity;
        this.mMainComponentName = mainComponentName;
        this.mActivity = null;
    }

    protected Bundle getLaunchOptions() {
        return null;
    }

    protected ReactRootView createRootView() {
        return new ReactRootView(getContext());
    }

    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getPlainActivity().getApplication()).getReactNativeHost();
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean needsOverlayPermission = false;
        if (getReactNativeHost().getUseDeveloperSupport() && Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(getContext())) {
            needsOverlayPermission = true;
            Intent serviceIntent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getContext().getPackageName()));
            FLog.w("React", "Overlay permissions needs to be granted in order for react native apps to run in dev mode");
            Toast.makeText(getContext(), "Overlay permissions needs to be granted in order for react native apps to run in dev mode", 1).show();
            ((Activity) getContext()).startActivityForResult(serviceIntent, 1111);
        }
        if (this.mMainComponentName != null && !needsOverlayPermission) {
            loadApp(this.mMainComponentName);
        }
        this.mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    }

    protected void loadApp(String appKey) {
        if (this.mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }
        this.mReactRootView = createRootView();
        this.mReactRootView.startReactApplication(getReactNativeHost().getReactInstanceManager(), appKey, getLaunchOptions());
        getPlainActivity().setContentView(this.mReactRootView);
    }

    protected void onPause() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostPause(getPlainActivity());
        }
    }

    protected void onResume() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostResume(getPlainActivity(), (DefaultHardwareBackBtnHandler) getPlainActivity());
        }
        if (this.mPermissionsCallback != null) {
            this.mPermissionsCallback.invoke(new Object[0]);
            this.mPermissionsCallback = null;
        }
    }

    protected void onDestroy() {
        if (this.mReactRootView != null) {
            this.mReactRootView.unmountReactApplication();
            this.mReactRootView = null;
        }
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostDestroy(getPlainActivity());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onActivityResult(getPlainActivity(), requestCode, resultCode, data);
            return;
        }
        if (requestCode == 1111 && Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(getContext())) {
            if (this.mMainComponentName != null) {
                loadApp(this.mMainComponentName);
            }
            Toast.makeText(getContext(), "Overlay permissions have been granted.", 1).show();
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport()) {
            if (keyCode == 82) {
                getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
                return true;
            }
            boolean didDoubleTapR = ((DoubleTapReloadRecognizer) Assertions.assertNotNull(this.mDoubleTapReloadRecognizer)).didDoubleTapR(keyCode, getPlainActivity().getCurrentFocus());
            if (didDoubleTapR) {
                getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
                return true;
            }
        }
        return false;
    }

    public boolean onBackPressed() {
        if (!getReactNativeHost().hasInstance()) {
            return false;
        }
        getReactNativeHost().getReactInstanceManager().onBackPressed();
        return true;
    }

    public boolean onNewIntent(Intent intent) {
        if (!getReactNativeHost().hasInstance()) {
            return false;
        }
        getReactNativeHost().getReactInstanceManager().onNewIntent(intent);
        return true;
    }

    @TargetApi(23)
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        this.mPermissionListener = listener;
        getPlainActivity().requestPermissions(permissions, requestCode);
    }

    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        this.mPermissionsCallback = new Callback() { // from class: com.facebook.react.ReactActivityDelegate.1
            @Override // com.facebook.react.bridge.Callback
            public void invoke(Object... args) {
                if (ReactActivityDelegate.this.mPermissionListener != null && ReactActivityDelegate.this.mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                    ReactActivityDelegate.this.mPermissionListener = null;
                }
            }
        };
    }

    private Context getContext() {
        return this.mActivity != null ? this.mActivity : (Context) Assertions.assertNotNull(this.mFragmentActivity);
    }

    private Activity getPlainActivity() {
        return (Activity) getContext();
    }
}
