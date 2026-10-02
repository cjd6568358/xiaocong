package org.eclipse.paho.android.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@SuppressLint({"Registered"})
public class MqttService extends Service implements MqttTraceHandler {
    private BackgroundDataPreferenceReceiver backgroundDataPreferenceMonitor;
    MessageStore messageStore;
    private MqttServiceBinder mqttServiceBinder;
    private NetworkConnectionIntentReceiver networkConnectionMonitor;
    private String traceCallbackId;
    private boolean traceEnabled = false;
    private volatile boolean backgroundDataEnabled = true;
    private Map<String, MqttConnection> connections = new ConcurrentHashMap();

    void callbackToActivity(String clientHandle, Status status, Bundle dataBundle) {
        Intent callbackIntent = new Intent("MqttService.callbackToActivity.v0");
        if (clientHandle != null) {
            callbackIntent.putExtra("MqttService.clientHandle", clientHandle);
        }
        callbackIntent.putExtra("MqttService.callbackStatus", status);
        if (dataBundle != null) {
            callbackIntent.putExtras(dataBundle);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(callbackIntent);
    }

    public String getClient(String serverURI, String clientId, String contextId, MqttClientPersistence persistence) {
        String clientHandle = serverURI + ":" + clientId + ":" + contextId;
        if (!this.connections.containsKey(clientHandle)) {
            MqttConnection client = new MqttConnection(this, serverURI, clientId, persistence, clientHandle);
            this.connections.put(clientHandle, client);
        }
        return clientHandle;
    }

    public void connect(String clientHandle, MqttConnectOptions connectOptions, String invocationContext, String activityToken) throws MqttException {
        MqttConnection client = getConnection(clientHandle);
        client.connect(connectOptions, null, activityToken);
    }

    void reconnect() {
        traceDebug("MqttService", "Reconnect to server, client size=" + this.connections.size());
        for (MqttConnection client : this.connections.values()) {
            traceDebug("Reconnect Client:", client.getClientId() + '/' + client.getServerURI());
            if (isOnline()) {
                client.reconnect();
            }
        }
    }

    public void disconnect(String clientHandle, String invocationContext, String activityToken) {
        MqttConnection client = getConnection(clientHandle);
        client.disconnect(invocationContext, activityToken);
        this.connections.remove(clientHandle);
        stopSelf();
    }

    public boolean isConnected(String clientHandle) {
        MqttConnection client = getConnection(clientHandle);
        return client.isConnected();
    }

    public IMqttDeliveryToken publish(String clientHandle, String topic, MqttMessage message, String invocationContext, String activityToken) throws MqttException {
        MqttConnection client = getConnection(clientHandle);
        return client.publish(topic, message, invocationContext, activityToken);
    }

    private MqttConnection getConnection(String clientHandle) {
        MqttConnection client = this.connections.get(clientHandle);
        if (client == null) {
            throw new IllegalArgumentException("Invalid ClientHandle");
        }
        return client;
    }

    public Status acknowledgeMessageArrival(String clientHandle, String id) {
        return this.messageStore.discardArrived(clientHandle, id) ? Status.OK : Status.ERROR;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mqttServiceBinder = new MqttServiceBinder(this);
        this.messageStore = new DatabaseMessageStore(this, this);
    }

    @Override // android.app.Service
    public void onDestroy() {
        for (MqttConnection client : this.connections.values()) {
            client.disconnect(null, null);
        }
        if (this.mqttServiceBinder != null) {
            this.mqttServiceBinder = null;
        }
        unregisterBroadcastReceivers();
        if (this.messageStore != null) {
            this.messageStore.close();
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        String activityToken = intent.getStringExtra("MqttService.activityToken");
        this.mqttServiceBinder.setActivityToken(activityToken);
        return this.mqttServiceBinder;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerBroadcastReceivers();
        return 1;
    }

    public void setTraceCallbackId(String traceCallbackId) {
        this.traceCallbackId = traceCallbackId;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceDebug(String tag, String message) {
        traceCallback("debug", tag, message);
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceError(String tag, String message) {
        traceCallback("error", tag, message);
    }

    private void traceCallback(String severity, String tag, String message) {
        if (this.traceCallbackId != null && this.traceEnabled) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("MqttService.callbackAction", "trace");
            dataBundle.putString("MqttService.traceSeverity", severity);
            dataBundle.putString("MqttService.traceTag", tag);
            dataBundle.putString("MqttService.errorMessage", message);
            callbackToActivity(this.traceCallbackId, Status.ERROR, dataBundle);
        }
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceException(String tag, String message, Exception e) {
        if (this.traceCallbackId != null) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("MqttService.callbackAction", "trace");
            dataBundle.putString("MqttService.traceSeverity", "exception");
            dataBundle.putString("MqttService.errorMessage", message);
            dataBundle.putSerializable("MqttService.exception", e);
            dataBundle.putString("MqttService.traceTag", tag);
            callbackToActivity(this.traceCallbackId, Status.ERROR, dataBundle);
        }
    }

    private void registerBroadcastReceivers() {
        if (this.networkConnectionMonitor == null) {
            this.networkConnectionMonitor = new NetworkConnectionIntentReceiver();
            registerReceiver(this.networkConnectionMonitor, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        if (Build.VERSION.SDK_INT < 14) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService("connectivity");
            this.backgroundDataEnabled = cm.getBackgroundDataSetting();
            if (this.backgroundDataPreferenceMonitor == null) {
                this.backgroundDataPreferenceMonitor = new BackgroundDataPreferenceReceiver();
                registerReceiver(this.backgroundDataPreferenceMonitor, new IntentFilter("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"));
            }
        }
    }

    private void unregisterBroadcastReceivers() {
        if (this.networkConnectionMonitor != null) {
            unregisterReceiver(this.networkConnectionMonitor);
            this.networkConnectionMonitor = null;
        }
        if (Build.VERSION.SDK_INT < 14 && this.backgroundDataPreferenceMonitor != null) {
            unregisterReceiver(this.backgroundDataPreferenceMonitor);
        }
    }

    private class NetworkConnectionIntentReceiver extends BroadcastReceiver {
        private NetworkConnectionIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            MqttService.this.traceDebug("MqttService", "Internal network status receive.");
            PowerManager pm = (PowerManager) MqttService.this.getSystemService("power");
            PowerManager.WakeLock wl = pm.newWakeLock(1, "MQTT");
            wl.acquire();
            MqttService.this.traceDebug("MqttService", "Reconnect for Network recovery.");
            if (!MqttService.this.isOnline()) {
                MqttService.this.notifyClientsOffline();
            } else {
                MqttService.this.traceDebug("MqttService", "Online,reconnect.");
                MqttService.this.reconnect();
            }
            wl.release();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService("connectivity");
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected() && this.backgroundDataEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyClientsOffline() {
        for (MqttConnection connection : this.connections.values()) {
            connection.offline();
        }
    }

    private class BackgroundDataPreferenceReceiver extends BroadcastReceiver {
        private BackgroundDataPreferenceReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) MqttService.this.getSystemService("connectivity");
            MqttService.this.traceDebug("MqttService", "Reconnect since BroadcastReceiver.");
            if (cm.getBackgroundDataSetting()) {
                if (!MqttService.this.backgroundDataEnabled) {
                    MqttService.this.backgroundDataEnabled = true;
                    MqttService.this.reconnect();
                    return;
                }
                return;
            }
            MqttService.this.backgroundDataEnabled = false;
            MqttService.this.notifyClientsOffline();
        }
    }

    public void setBufferOpts(String clientHandle, DisconnectedBufferOptions bufferOpts) {
        MqttConnection client = getConnection(clientHandle);
        client.setBufferOpts(bufferOpts);
    }
}
