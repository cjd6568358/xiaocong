package com.bumptech.glide.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class DefaultConnectivityMonitor implements ConnectivityMonitor {
    private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver() { // from class: com.bumptech.glide.manager.DefaultConnectivityMonitor.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean wasConnected = DefaultConnectivityMonitor.this.isConnected;
            DefaultConnectivityMonitor.this.isConnected = DefaultConnectivityMonitor.this.isConnected(context);
            if (wasConnected != DefaultConnectivityMonitor.this.isConnected) {
                DefaultConnectivityMonitor.this.listener.onConnectivityChanged(DefaultConnectivityMonitor.this.isConnected);
            }
        }
    };
    private final Context context;
    private boolean isConnected;
    private boolean isRegistered;
    private final ConnectivityMonitor.ConnectivityListener listener;

    public DefaultConnectivityMonitor(Context context, ConnectivityMonitor.ConnectivityListener listener) {
        this.context = context.getApplicationContext();
        this.listener = listener;
    }

    private void register() {
        if (!this.isRegistered) {
            this.isConnected = isConnected(this.context);
            this.context.registerReceiver(this.connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.isRegistered = true;
        }
    }

    private void unregister() {
        if (this.isRegistered) {
            this.context.unregisterReceiver(this.connectivityReceiver);
            this.isRegistered = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        register();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        unregister();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }
}
