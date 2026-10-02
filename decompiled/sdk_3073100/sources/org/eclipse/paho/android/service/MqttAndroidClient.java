package org.eclipse.paho.android.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttAndroidClient extends BroadcastReceiver implements IMqttAsyncClient {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private volatile boolean bindedService;
    private MqttCallback callback;
    private String clientHandle;
    private final String clientId;
    private MqttConnectOptions connectOptions;
    private IMqttToken connectToken;
    private final Ack messageAck;
    private MqttService mqttService;
    private Context myContext;
    private MqttClientPersistence persistence;
    private volatile boolean receiverRegistered;
    private final String serverURI;
    private final MyServiceConnection serviceConnection;
    private final SparseArray<IMqttToken> tokenMap;
    private int tokenNumber;
    private MqttTraceHandler traceCallback;
    private boolean traceEnabled;

    public enum Ack {
        AUTO_ACK,
        MANUAL_ACK
    }

    private final class MyServiceConnection implements ServiceConnection {
        private MyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MqttAndroidClient.this.mqttService = ((MqttServiceBinder) binder).getService();
            MqttAndroidClient.this.bindedService = true;
            MqttAndroidClient.this.doConnect();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            MqttAndroidClient.this.mqttService = null;
        }
    }

    public MqttAndroidClient(Context ctx, String serverURI, String clientId, MqttClientPersistence persistence) {
        this(ctx, serverURI, clientId, persistence, Ack.AUTO_ACK);
    }

    public MqttAndroidClient(Context context, String serverURI, String clientId, MqttClientPersistence persistence, Ack ackType) {
        this.serviceConnection = new MyServiceConnection();
        this.tokenMap = new SparseArray<>();
        this.tokenNumber = 0;
        this.persistence = null;
        this.traceEnabled = false;
        this.receiverRegistered = false;
        this.bindedService = false;
        this.myContext = context;
        this.serverURI = serverURI;
        this.clientId = clientId;
        this.persistence = persistence;
        this.messageAck = ackType;
    }

    public boolean isConnected() {
        return (this.clientHandle == null || this.mqttService == null || !this.mqttService.isConnected(this.clientHandle)) ? false : true;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getClientId() {
        return this.clientId;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getServerURI() {
        return this.serverURI;
    }

    public IMqttToken connect(MqttConnectOptions options) throws MqttException {
        return connect(options, null, null);
    }

    public IMqttToken connect(MqttConnectOptions options, Object userContext, IMqttActionListener callback) throws MqttException {
        IMqttActionListener listener;
        IMqttToken token = new MqttTokenAndroid(this, userContext, callback);
        this.connectOptions = options;
        this.connectToken = token;
        if (this.mqttService == null) {
            Intent serviceStartIntent = new Intent();
            serviceStartIntent.setClassName(this.myContext, "org.eclipse.paho.android.service.MqttService");
            ComponentName service = this.myContext.startService(serviceStartIntent);
            if (service == null && (listener = token.getActionCallback()) != null) {
                listener.onFailure(token, new RuntimeException("cannot start service org.eclipse.paho.android.service.MqttService"));
            }
            this.myContext.bindService(serviceStartIntent, this.serviceConnection, 1);
            if (!this.receiverRegistered) {
                registerReceiver(this);
            }
        } else {
            pool.execute(new Runnable() { // from class: org.eclipse.paho.android.service.MqttAndroidClient.1
                @Override // java.lang.Runnable
                public void run() {
                    MqttAndroidClient.this.doConnect();
                    if (!MqttAndroidClient.this.receiverRegistered) {
                        MqttAndroidClient.this.registerReceiver(MqttAndroidClient.this);
                    }
                }
            });
        }
        return token;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerReceiver(BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("MqttService.callbackToActivity.v0");
        LocalBroadcastManager.getInstance(this.myContext).registerReceiver(receiver, filter);
        this.receiverRegistered = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doConnect() {
        if (this.clientHandle == null) {
            this.clientHandle = this.mqttService.getClient(this.serverURI, this.clientId, this.myContext.getApplicationInfo().packageName, this.persistence);
        }
        this.mqttService.setTraceEnabled(this.traceEnabled);
        this.mqttService.setTraceCallbackId(this.clientHandle);
        String activityToken = storeToken(this.connectToken);
        try {
            this.mqttService.connect(this.clientHandle, this.connectOptions, null, activityToken);
        } catch (MqttException e) {
            IMqttActionListener listener = this.connectToken.getActionCallback();
            if (listener != null) {
                listener.onFailure(this.connectToken, e);
            }
        }
    }

    public IMqttToken disconnect() throws MqttException {
        IMqttToken token = new MqttTokenAndroid(this, null, null);
        String activityToken = storeToken(token);
        this.mqttService.disconnect(this.clientHandle, null, activityToken);
        return token;
    }

    public IMqttDeliveryToken publish(String topic, MqttMessage message) throws MqttException {
        return publish(topic, message, null, null);
    }

    public IMqttDeliveryToken publish(String topic, MqttMessage message, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttDeliveryTokenAndroid token = new MqttDeliveryTokenAndroid(this, userContext, callback, message);
        String activityToken = storeToken(token);
        IMqttDeliveryToken internalToken = this.mqttService.publish(this.clientHandle, topic, message, null, activityToken);
        token.setDelegate(internalToken);
        return token;
    }

    public void setCallback(MqttCallback callback) {
        this.callback = callback;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        String handleFromIntent = data.getString("MqttService.clientHandle");
        if (handleFromIntent != null && handleFromIntent.equals(this.clientHandle)) {
            String action = data.getString("MqttService.callbackAction");
            if ("connect".equals(action)) {
                connectAction(data);
                return;
            }
            if ("connectExtended".equals(action)) {
                connectExtendedAction(data);
                return;
            }
            if ("messageArrived".equals(action)) {
                messageArrivedAction(data);
                return;
            }
            if ("subscribe".equals(action)) {
                subscribeAction(data);
                return;
            }
            if ("unsubscribe".equals(action)) {
                unSubscribeAction(data);
                return;
            }
            if ("send".equals(action)) {
                sendAction(data);
                return;
            }
            if ("messageDelivered".equals(action)) {
                messageDeliveredAction(data);
                return;
            }
            if ("onConnectionLost".equals(action)) {
                connectionLostAction(data);
                return;
            }
            if ("disconnect".equals(action)) {
                disconnected(data);
            } else if ("trace".equals(action)) {
                traceAction(data);
            } else {
                this.mqttService.traceError("MqttService", "Callback action doesn't exist.");
            }
        }
    }

    private void connectAction(Bundle data) {
        IMqttToken token = this.connectToken;
        removeMqttToken(data);
        simpleAction(token, data);
    }

    private void disconnected(Bundle data) {
        this.clientHandle = null;
        IMqttToken token = removeMqttToken(data);
        if (token != null) {
            ((MqttTokenAndroid) token).notifyComplete();
        }
        if (this.callback != null) {
            this.callback.connectionLost(null);
        }
    }

    private void connectionLostAction(Bundle data) {
        if (this.callback != null) {
            Exception reason = (Exception) data.getSerializable("MqttService.exception");
            this.callback.connectionLost(reason);
        }
    }

    private void connectExtendedAction(Bundle data) {
        if (this.callback instanceof MqttCallbackExtended) {
            boolean reconnect = data.getBoolean("MqttService.reconnect", false);
            String serverURI = data.getString("MqttService.serverURI");
            ((MqttCallbackExtended) this.callback).connectComplete(reconnect, serverURI);
        }
    }

    private void simpleAction(IMqttToken token, Bundle data) {
        if (token != null) {
            Status status = (Status) data.getSerializable("MqttService.callbackStatus");
            if (status == Status.OK) {
                ((MqttTokenAndroid) token).notifyComplete();
                return;
            } else {
                Exception exceptionThrown = (Exception) data.getSerializable("MqttService.exception");
                ((MqttTokenAndroid) token).notifyFailure(exceptionThrown);
                return;
            }
        }
        this.mqttService.traceError("MqttService", "simpleAction : token is null");
    }

    private void sendAction(Bundle data) {
        IMqttToken token = getMqttToken(data);
        simpleAction(token, data);
    }

    private void subscribeAction(Bundle data) {
        IMqttToken token = removeMqttToken(data);
        simpleAction(token, data);
    }

    private void unSubscribeAction(Bundle data) {
        IMqttToken token = removeMqttToken(data);
        simpleAction(token, data);
    }

    private void messageDeliveredAction(Bundle data) {
        IMqttToken token = removeMqttToken(data);
        if (token != null && this.callback != null) {
            Status status = (Status) data.getSerializable("MqttService.callbackStatus");
            if (status == Status.OK && (token instanceof IMqttDeliveryToken)) {
                this.callback.deliveryComplete((IMqttDeliveryToken) token);
            }
        }
    }

    private void messageArrivedAction(Bundle data) {
        if (this.callback != null) {
            String messageId = data.getString("MqttService.messageId");
            String destinationName = data.getString("MqttService.destinationName");
            ParcelableMqttMessage message = (ParcelableMqttMessage) data.getParcelable("MqttService.PARCEL");
            try {
                if (this.messageAck == Ack.AUTO_ACK) {
                    this.callback.messageArrived(destinationName, message);
                    this.mqttService.acknowledgeMessageArrival(this.clientHandle, messageId);
                } else {
                    message.messageId = messageId;
                    this.callback.messageArrived(destinationName, message);
                }
            } catch (Exception e) {
            }
        }
    }

    private void traceAction(Bundle data) {
        if (this.traceCallback != null) {
            String severity = data.getString("MqttService.traceSeverity");
            String message = data.getString("MqttService.errorMessage");
            String tag = data.getString("MqttService.traceTag");
            if ("debug".equals(severity)) {
                this.traceCallback.traceDebug(tag, message);
            } else if ("error".equals(severity)) {
                this.traceCallback.traceError(tag, message);
            } else {
                Exception e = (Exception) data.getSerializable("MqttService.exception");
                this.traceCallback.traceException(tag, message, e);
            }
        }
    }

    private synchronized String storeToken(IMqttToken token) {
        int i;
        this.tokenMap.put(this.tokenNumber, token);
        i = this.tokenNumber;
        this.tokenNumber = i + 1;
        return Integer.toString(i);
    }

    private synchronized IMqttToken removeMqttToken(Bundle data) {
        IMqttToken token;
        String activityToken = data.getString("MqttService.activityToken");
        if (activityToken != null) {
            int tokenNumber = Integer.parseInt(activityToken);
            token = this.tokenMap.get(tokenNumber);
            this.tokenMap.delete(tokenNumber);
        } else {
            token = null;
        }
        return token;
    }

    private synchronized IMqttToken getMqttToken(Bundle data) {
        String activityToken;
        activityToken = data.getString("MqttService.activityToken");
        return this.tokenMap.get(Integer.parseInt(activityToken));
    }

    public void setBufferOpts(DisconnectedBufferOptions bufferOpts) {
        this.mqttService.setBufferOpts(this.clientHandle, bufferOpts);
    }

    public void registerResources(Context context) {
        if (context != null) {
            this.myContext = context;
            if (!this.receiverRegistered) {
                registerReceiver(this);
            }
        }
    }
}
