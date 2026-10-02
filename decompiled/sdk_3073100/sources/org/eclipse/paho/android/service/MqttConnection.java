package org.eclipse.paho.android.service;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class MqttConnection implements MqttCallbackExtended {
    private String clientHandle;
    private String clientId;
    private MqttConnectOptions connectOptions;
    private MqttClientPersistence persistence;
    private String serverURI;
    private MqttService service;
    private String wakeLockTag;
    private String reconnectActivityToken = null;
    private MqttAsyncClient myClient = null;
    private AlarmPingSender alarmPingSender = null;
    private volatile boolean disconnected = true;
    private boolean cleanSession = true;
    private volatile boolean isConnecting = false;
    private Map<IMqttDeliveryToken, String> savedTopics = new HashMap();
    private Map<IMqttDeliveryToken, MqttMessage> savedSentMessages = new HashMap();
    private Map<IMqttDeliveryToken, String> savedActivityTokens = new HashMap();
    private Map<IMqttDeliveryToken, String> savedInvocationContexts = new HashMap();
    private PowerManager.WakeLock wakelock = null;
    private DisconnectedBufferOptions bufferOpts = null;

    public String getServerURI() {
        return this.serverURI;
    }

    public String getClientId() {
        return this.clientId;
    }

    MqttConnection(MqttService service, String serverURI, String clientId, MqttClientPersistence persistence, String clientHandle) {
        this.persistence = null;
        this.service = null;
        this.wakeLockTag = null;
        this.serverURI = serverURI;
        this.service = service;
        this.clientId = clientId;
        this.persistence = persistence;
        this.clientHandle = clientHandle;
        this.wakeLockTag = getClass().getCanonicalName() + " " + clientId + " on host " + serverURI;
    }

    public void connect(MqttConnectOptions options, String invocationContext, String activityToken) {
        this.connectOptions = options;
        this.reconnectActivityToken = activityToken;
        if (options != null) {
            this.cleanSession = options.isCleanSession();
        }
        if (this.connectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        this.service.traceDebug("MqttConnection", "Connecting {" + this.serverURI + "} as {" + this.clientId + "}");
        final Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.activityToken", activityToken);
        resultBundle.putString("MqttService.invocationContext", invocationContext);
        resultBundle.putString("MqttService.callbackAction", "connect");
        try {
            if (this.persistence == null) {
                File myDir = this.service.getExternalFilesDir("MqttConnection");
                if (myDir == null && (myDir = this.service.getDir("MqttConnection", 0)) == null) {
                    resultBundle.putString("MqttService.errorMessage", "Error! No external and internal storage available");
                    resultBundle.putSerializable("MqttService.exception", new MqttPersistenceException());
                    this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
                    return;
                }
                this.persistence = new MqttDefaultFilePersistence(myDir.getAbsolutePath());
            }
            IMqttActionListener listener = new MqttConnectionListener(resultBundle) { // from class: org.eclipse.paho.android.service.MqttConnection.1
                @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttConnection.this.doAfterConnectSuccess(resultBundle);
                    MqttConnection.this.service.traceDebug("MqttConnection", "connect success!");
                }

                @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    resultBundle.putString("MqttService.errorMessage", exception.getLocalizedMessage());
                    resultBundle.putSerializable("MqttService.exception", exception);
                    MqttConnection.this.service.traceError("MqttConnection", "connect fail, call connect to reconnect.reason:" + exception.getMessage());
                    MqttConnection.this.doAfterConnectFail(resultBundle);
                }
            };
            if (this.myClient != null) {
                if (this.isConnecting) {
                    this.service.traceDebug("MqttConnection", "myClient != null and the client is connecting. Connect return directly.");
                    this.service.traceDebug("MqttConnection", "Connect return:isConnecting:" + this.isConnecting + ".disconnected:" + this.disconnected);
                    return;
                } else if (!this.disconnected) {
                    this.service.traceDebug("MqttConnection", "myClient != null and the client is connected and notify!");
                    doAfterConnectSuccess(resultBundle);
                    return;
                } else {
                    this.service.traceDebug("MqttConnection", "myClient != null and the client is not connected");
                    this.service.traceDebug("MqttConnection", "Do Real connect!");
                    setConnectingState(true);
                    this.myClient.connect(this.connectOptions, invocationContext, listener);
                    return;
                }
            }
            this.alarmPingSender = new AlarmPingSender(this.service);
            this.myClient = new MqttAsyncClient(this.serverURI, this.clientId, this.persistence, this.alarmPingSender);
            this.myClient.setCallback(this);
            this.service.traceDebug("MqttConnection", "Do Real connect!");
            setConnectingState(true);
            this.myClient.connect(this.connectOptions, invocationContext, listener);
        } catch (Exception e) {
            this.service.traceError("MqttConnection", "Exception occurred attempting to connect: " + e.getMessage());
            setConnectingState(false);
            handleException(resultBundle, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAfterConnectSuccess(Bundle resultBundle) {
        acquireWakeLock();
        this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
        deliverBacklog();
        setConnectingState(false);
        this.disconnected = false;
        releaseWakeLock();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
    public void connectComplete(boolean reconnect, String serverURI) {
        Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.callbackAction", "connectExtended");
        resultBundle.putBoolean("MqttService.reconnect", reconnect);
        resultBundle.putString("MqttService.serverURI", serverURI);
        this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAfterConnectFail(Bundle resultBundle) {
        acquireWakeLock();
        this.disconnected = true;
        setConnectingState(false);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
        releaseWakeLock();
    }

    private void handleException(Bundle resultBundle, Exception e) {
        resultBundle.putString("MqttService.errorMessage", e.getLocalizedMessage());
        resultBundle.putSerializable("MqttService.exception", e);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
    }

    private void deliverBacklog() {
        Iterator<MessageStore.StoredMessage> backlog = this.service.messageStore.getAllArrivedMessages(this.clientHandle);
        while (backlog.hasNext()) {
            MessageStore.StoredMessage msgArrived = backlog.next();
            Bundle resultBundle = messageToBundle(msgArrived.getMessageId(), msgArrived.getTopic(), msgArrived.getMessage());
            resultBundle.putString("MqttService.callbackAction", "messageArrived");
            this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
        }
    }

    private Bundle messageToBundle(String messageId, String topic, MqttMessage message) {
        Bundle result = new Bundle();
        result.putString("MqttService.messageId", messageId);
        result.putString("MqttService.destinationName", topic);
        result.putParcelable("MqttService.PARCEL", new ParcelableMqttMessage(message));
        return result;
    }

    void disconnect(String invocationContext, String activityToken) {
        this.service.traceDebug("MqttConnection", "disconnect()");
        this.disconnected = true;
        Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.activityToken", activityToken);
        resultBundle.putString("MqttService.invocationContext", invocationContext);
        resultBundle.putString("MqttService.callbackAction", "disconnect");
        if (this.myClient != null && this.myClient.isConnected()) {
            IMqttActionListener listener = new MqttConnectionListener(resultBundle);
            try {
                this.myClient.disconnect(invocationContext, listener);
            } catch (Exception e) {
                handleException(resultBundle, e);
            }
        } else {
            resultBundle.putString("MqttService.errorMessage", "not connected");
            this.service.traceError("disconnect", "not connected");
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
        }
        if (this.connectOptions != null && this.connectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    public boolean isConnected() {
        return this.myClient != null && this.myClient.isConnected();
    }

    public IMqttDeliveryToken publish(String topic, MqttMessage message, String invocationContext, String activityToken) {
        Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.callbackAction", "send");
        resultBundle.putString("MqttService.activityToken", activityToken);
        resultBundle.putString("MqttService.invocationContext", invocationContext);
        IMqttDeliveryToken sendToken = null;
        if (this.myClient != null && this.myClient.isConnected()) {
            IMqttActionListener listener = new MqttConnectionListener(resultBundle);
            try {
                sendToken = this.myClient.publish(topic, message, invocationContext, listener);
                storeSendDetails(topic, message, sendToken, invocationContext, activityToken);
                return sendToken;
            } catch (Exception e) {
                handleException(resultBundle, e);
                return sendToken;
            }
        }
        if (this.myClient != null && this.bufferOpts != null && this.bufferOpts.isBufferEnabled()) {
            IMqttActionListener listener2 = new MqttConnectionListener(resultBundle);
            try {
                sendToken = this.myClient.publish(topic, message, invocationContext, listener2);
                storeSendDetails(topic, message, sendToken, invocationContext, activityToken);
                return sendToken;
            } catch (Exception e2) {
                handleException(resultBundle, e2);
                return sendToken;
            }
        }
        Log.i("MqttConnection", "Client is not connected, so not sending message");
        resultBundle.putString("MqttService.errorMessage", "not connected");
        this.service.traceError("send", "not connected");
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
        return null;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void connectionLost(Throwable why) {
        this.service.traceDebug("MqttConnection", "connectionLost(" + why.getMessage() + ")");
        this.disconnected = true;
        try {
            if (!this.connectOptions.isAutomaticReconnect()) {
                this.myClient.disconnect(null, new IMqttActionListener() { // from class: org.eclipse.paho.android.service.MqttConnection.2
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken asyncActionToken) {
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    }
                });
            } else {
                this.alarmPingSender.schedule(100L);
            }
        } catch (Exception e) {
        }
        Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.callbackAction", "onConnectionLost");
        if (why != null) {
            resultBundle.putString("MqttService.errorMessage", why.getMessage());
            if (why instanceof MqttException) {
                resultBundle.putSerializable("MqttService.exception", why);
            }
            resultBundle.putString("MqttService.exceptionStack", Log.getStackTraceString(why));
        }
        this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
        releaseWakeLock();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void deliveryComplete(IMqttDeliveryToken messageToken) {
        this.service.traceDebug("MqttConnection", "deliveryComplete(" + messageToken + ")");
        MqttMessage message = this.savedSentMessages.remove(messageToken);
        if (message != null) {
            String topic = this.savedTopics.remove(messageToken);
            String activityToken = this.savedActivityTokens.remove(messageToken);
            String invocationContext = this.savedInvocationContexts.remove(messageToken);
            Bundle resultBundle = messageToBundle(null, topic, message);
            if (activityToken != null) {
                resultBundle.putString("MqttService.callbackAction", "send");
                resultBundle.putString("MqttService.activityToken", activityToken);
                resultBundle.putString("MqttService.invocationContext", invocationContext);
                this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
            }
            resultBundle.putString("MqttService.callbackAction", "messageDelivered");
            this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        this.service.traceDebug("MqttConnection", "messageArrived(" + topic + ",{" + message.toString() + "})");
        String messageId = this.service.messageStore.storeArrived(this.clientHandle, topic, message);
        Bundle resultBundle = messageToBundle(messageId, topic, message);
        resultBundle.putString("MqttService.callbackAction", "messageArrived");
        resultBundle.putString("MqttService.messageId", messageId);
        this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
    }

    private void storeSendDetails(String topic, MqttMessage msg, IMqttDeliveryToken messageToken, String invocationContext, String activityToken) {
        this.savedTopics.put(messageToken, topic);
        this.savedSentMessages.put(messageToken, msg);
        this.savedActivityTokens.put(messageToken, activityToken);
        this.savedInvocationContexts.put(messageToken, invocationContext);
    }

    private void acquireWakeLock() {
        if (this.wakelock == null) {
            PowerManager pm = (PowerManager) this.service.getSystemService("power");
            this.wakelock = pm.newWakeLock(1, this.wakeLockTag);
        }
        this.wakelock.acquire();
    }

    private void releaseWakeLock() {
        if (this.wakelock != null && this.wakelock.isHeld()) {
            this.wakelock.release();
        }
    }

    private class MqttConnectionListener implements IMqttActionListener {
        private final Bundle resultBundle;

        private MqttConnectionListener(Bundle resultBundle) {
            this.resultBundle = resultBundle;
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onSuccess(IMqttToken asyncActionToken) {
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.OK, this.resultBundle);
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            this.resultBundle.putString("MqttService.errorMessage", exception.getLocalizedMessage());
            this.resultBundle.putSerializable("MqttService.exception", exception);
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, this.resultBundle);
        }
    }

    void offline() {
        if (!this.disconnected && !this.cleanSession) {
            Exception e = new Exception("Android offline");
            connectionLost(e);
        }
    }

    synchronized void reconnect() {
        try {
            if (this.myClient == null) {
                this.service.traceError("MqttConnection", "Reconnect myClient = null. Will not do reconnect");
            } else if (this.isConnecting) {
                this.service.traceDebug("MqttConnection", "The client is connecting. Reconnect return directly.");
            } else if (!this.service.isOnline()) {
                this.service.traceDebug("MqttConnection", "The network is not reachable. Will not do reconnect");
            } else if (this.connectOptions.isAutomaticReconnect()) {
                Log.i("MqttConnection", "Requesting Automatic reconnect using New Java AC");
                Bundle resultBundle = new Bundle();
                resultBundle.putString("MqttService.activityToken", this.reconnectActivityToken);
                resultBundle.putString("MqttService.invocationContext", null);
                resultBundle.putString("MqttService.callbackAction", "connect");
                try {
                    this.myClient.reconnect();
                } catch (MqttException ex) {
                    Log.e("MqttConnection", "Exception occurred attempting to reconnect: " + ex.getMessage());
                    setConnectingState(false);
                    handleException(resultBundle, ex);
                }
            } else if (this.disconnected && !this.cleanSession) {
                this.service.traceDebug("MqttConnection", "Do Real Reconnect!");
                final Bundle resultBundle2 = new Bundle();
                resultBundle2.putString("MqttService.activityToken", this.reconnectActivityToken);
                resultBundle2.putString("MqttService.invocationContext", null);
                resultBundle2.putString("MqttService.callbackAction", "connect");
                try {
                    IMqttActionListener listener = new MqttConnectionListener(resultBundle2) { // from class: org.eclipse.paho.android.service.MqttConnection.3
                        @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                        public void onSuccess(IMqttToken asyncActionToken) {
                            MqttConnection.this.service.traceDebug("MqttConnection", "Reconnect Success!");
                            MqttConnection.this.service.traceDebug("MqttConnection", "DeliverBacklog when reconnect.");
                            MqttConnection.this.doAfterConnectSuccess(resultBundle2);
                        }

                        @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            resultBundle2.putString("MqttService.errorMessage", exception.getLocalizedMessage());
                            resultBundle2.putSerializable("MqttService.exception", exception);
                            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, resultBundle2);
                            MqttConnection.this.doAfterConnectFail(resultBundle2);
                        }
                    };
                    this.myClient.connect(this.connectOptions, null, listener);
                    setConnectingState(true);
                } catch (MqttException e) {
                    this.service.traceError("MqttConnection", "Cannot reconnect to remote server." + e.getMessage());
                    setConnectingState(false);
                    handleException(resultBundle2, e);
                } catch (Exception e2) {
                    this.service.traceError("MqttConnection", "Cannot reconnect to remote server." + e2.getMessage());
                    setConnectingState(false);
                    MqttException newEx = new MqttException(6, e2.getCause());
                    handleException(resultBundle2, newEx);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private synchronized void setConnectingState(boolean isConnecting) {
        this.isConnecting = isConnecting;
    }

    public void setBufferOpts(DisconnectedBufferOptions bufferOpts) {
        this.bufferOpts = bufferOpts;
        this.myClient.setBufferOpts(bufferOpts);
    }
}
