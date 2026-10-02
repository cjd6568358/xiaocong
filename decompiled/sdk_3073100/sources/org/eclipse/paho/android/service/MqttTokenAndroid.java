package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class MqttTokenAndroid implements IMqttToken {
    private MqttAndroidClient client;
    private IMqttToken delegate;
    private volatile boolean isComplete;
    private volatile MqttException lastException;
    private IMqttActionListener listener;
    private MqttException pendingException;
    private String[] topics;
    private Object userContext;
    private Object waitObject;

    MqttTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener) {
        this(client, userContext, listener, null);
    }

    MqttTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener, String[] topics) {
        this.waitObject = new Object();
        this.client = client;
        this.userContext = userContext;
        this.listener = listener;
        this.topics = topics;
    }

    void notifyComplete() {
        synchronized (this.waitObject) {
            this.isComplete = true;
            this.waitObject.notifyAll();
            if (this.listener != null) {
                this.listener.onSuccess(this);
            }
        }
    }

    void notifyFailure(Throwable exception) {
        synchronized (this.waitObject) {
            this.isComplete = true;
            if (exception instanceof MqttException) {
                this.pendingException = (MqttException) exception;
            } else {
                this.pendingException = new MqttException(exception);
            }
            this.waitObject.notifyAll();
            if (exception instanceof MqttException) {
                this.lastException = (MqttException) exception;
            }
            if (this.listener != null) {
                this.listener.onFailure(this, exception);
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttException getException() {
        return this.lastException;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttAsyncClient getClient() {
        return this.client;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setActionCallback(IMqttActionListener listener) {
        this.listener = listener;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttActionListener getActionCallback() {
        return this.listener;
    }

    void setDelegate(IMqttToken delegate) {
        this.delegate = delegate;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttWireMessage getResponse() {
        return this.delegate.getResponse();
    }
}
