package com.facebook.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ReactActivity extends Activity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {
    private final ReactActivityDelegate mDelegate = createReactActivityDelegate();

    protected ReactActivity() {
    }

    protected String getMainComponentName() {
        return null;
    }

    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName());
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDelegate.onCreate(savedInstanceState);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mDelegate.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mDelegate.onResume();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mDelegate.onDestroy();
    }

    @Override // android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override // com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        if (!this.mDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override // com.facebook.react.modules.core.PermissionAwareActivity
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        this.mDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
