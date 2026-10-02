package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttToken implements IMqttToken {
    public Token internalTok;

    public MqttToken() {
        this.internalTok = null;
    }

    public MqttToken(String logContext) {
        this.internalTok = null;
        this.internalTok = new Token(logContext);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttException getException() {
        return this.internalTok.getException();
    }

    public boolean isComplete() {
        return this.internalTok.isComplete();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setActionCallback(IMqttActionListener listener) {
        this.internalTok.setActionCallback(listener);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttActionListener getActionCallback() {
        return this.internalTok.getActionCallback();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttAsyncClient getClient() {
        return this.internalTok.getClient();
    }

    public void setUserContext(Object userContext) {
        this.internalTok.setUserContext(userContext);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttWireMessage getResponse() {
        return this.internalTok.getResponse();
    }
}
