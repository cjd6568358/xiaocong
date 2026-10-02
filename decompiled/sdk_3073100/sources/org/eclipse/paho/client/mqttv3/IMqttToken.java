package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IMqttToken {
    IMqttActionListener getActionCallback();

    IMqttAsyncClient getClient();

    MqttException getException();

    MqttWireMessage getResponse();

    void setActionCallback(IMqttActionListener iMqttActionListener);
}
