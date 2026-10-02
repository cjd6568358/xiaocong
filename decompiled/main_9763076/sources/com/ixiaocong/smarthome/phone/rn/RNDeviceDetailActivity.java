package com.ixiaocong.smarthome.phone.rn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNDeviceDetailActivity extends ReactActivity {
    Handler mHandler = new Handler() { // from class: com.ixiaocong.smarthome.phone.rn.RNDeviceDetailActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intent intent = new Intent("ACTION_UPDATE_STATUS");
            intent.putExtra(RNMessageModule.NAME, "ACTION_UPDATE_STATUS");
            LocalBroadcastManager.getInstance(RNDeviceDetailActivity.this).sendBroadcast(intent);
        }
    };

    @Override // com.facebook.react.ReactActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // com.facebook.react.ReactActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // com.facebook.react.ReactActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Gain permission failed", 0).show();
            } else {
                Toast.makeText(this, "Gain permission succeed", 0).show();
            }
        }
    }

    @Override // com.facebook.react.ReactActivity
    protected String getMainComponentName() {
        return "381765";
    }

    @Override // com.facebook.react.ReactActivity
    protected ReactActivityDelegate createReactActivityDelegate() {
        ReactActivityDelegate reactActivityDelegate = new ReactActivityDelegate(this, getMainComponentName()) { // from class: com.ixiaocong.smarthome.phone.rn.RNDeviceDetailActivity.2
            @Override // com.facebook.react.ReactActivityDelegate
            protected Bundle getLaunchOptions() {
                Bundle bundle = RNDeviceDetailActivity.this.getIntent().getExtras();
                return bundle;
            }
        };
        return reactActivityDelegate;
    }
}
