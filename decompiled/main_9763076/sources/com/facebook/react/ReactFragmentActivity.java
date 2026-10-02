package com.facebook.react;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ReactFragmentActivity extends FragmentActivity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {
    private final ReactActivityDelegate mDelegate = createReactActivityDelegate();

    protected ReactFragmentActivity() {
    }

    protected String getMainComponentName() {
        return null;
    }

    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate((FragmentActivity) this, getMainComponentName());
    }

    @Override // android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDelegate.onCreate(savedInstanceState);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mDelegate.onPause();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mDelegate.onResume();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mDelegate.onDestroy();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        if (!this.mDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
