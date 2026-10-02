package com.facebook.react.modules.netinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NetInfoModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String CONNECTION_TYPE_NONE = "NONE";
    private static final String CONNECTION_TYPE_UNKNOWN = "UNKNOWN";
    private static final String ERROR_MISSING_PERMISSION = "E_MISSING_PERMISSION";
    private static final String MISSING_PERMISSION_MESSAGE = "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />";
    private String mConnectivity;
    private final ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
    private final ConnectivityManager mConnectivityManager;
    private boolean mNoNetworkPermission;

    public NetInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mNoNetworkPermission = false;
        this.mConnectivity = Constants.MAIN_VERSION_TAG;
        this.mConnectivityManager = (ConnectivityManager) reactContext.getSystemService("connectivity");
        this.mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        registerReceiver();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        unregisterReceiver();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "NetInfo";
    }

    @ReactMethod
    public void getCurrentConnectivity(Promise promise) {
        if (this.mNoNetworkPermission) {
            promise.reject(ERROR_MISSING_PERMISSION, MISSING_PERMISSION_MESSAGE, null);
        } else {
            promise.resolve(createConnectivityEventMap());
        }
    }

    @ReactMethod
    public void isConnectionMetered(Promise promise) {
        if (this.mNoNetworkPermission) {
            promise.reject(ERROR_MISSING_PERMISSION, MISSING_PERMISSION_MESSAGE, null);
        } else {
            promise.resolve(Boolean.valueOf(ConnectivityManagerCompat.isActiveNetworkMetered(this.mConnectivityManager)));
        }
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getReactApplicationContext().registerReceiver(this.mConnectivityBroadcastReceiver, filter);
        this.mConnectivityBroadcastReceiver.setRegistered(true);
    }

    private void unregisterReceiver() {
        if (this.mConnectivityBroadcastReceiver.isRegistered()) {
            getReactApplicationContext().unregisterReceiver(this.mConnectivityBroadcastReceiver);
            this.mConnectivityBroadcastReceiver.setRegistered(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAndSendConnectionType() {
        String currentConnectivity = getCurrentConnectionType();
        if (!currentConnectivity.equalsIgnoreCase(this.mConnectivity)) {
            this.mConnectivity = currentConnectivity;
            sendConnectivityChangedEvent();
        }
    }

    private String getCurrentConnectionType() {
        try {
            NetworkInfo networkInfo = this.mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return CONNECTION_TYPE_NONE;
            }
            if (ConnectivityManager.isNetworkTypeValid(networkInfo.getType())) {
                return networkInfo.getTypeName().toUpperCase();
            }
            return CONNECTION_TYPE_UNKNOWN;
        } catch (SecurityException e) {
            this.mNoNetworkPermission = true;
            return CONNECTION_TYPE_UNKNOWN;
        }
    }

    private void sendConnectivityChangedEvent() {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("networkStatusDidChange", createConnectivityEventMap());
    }

    private WritableMap createConnectivityEventMap() {
        WritableMap event = new WritableNativeMap();
        event.putString("network_info", this.mConnectivity);
        return event;
    }

    private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        private ConnectivityBroadcastReceiver() {
            this.isRegistered = false;
        }

        public void setRegistered(boolean registered) {
            this.isRegistered = registered;
        }

        public boolean isRegistered() {
            return this.isRegistered;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetInfoModule.this.updateAndSendConnectionType();
            }
        }
    }
}
