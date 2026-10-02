package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BufferedMessage {
    private MqttWireMessage message;
    private MqttToken token;

    public BufferedMessage(MqttWireMessage message, MqttToken token) {
        this.message = message;
        this.token = token;
    }

    public MqttWireMessage getMessage() {
        return this.message;
    }

    public MqttToken getToken() {
        return this.token;
    }
}
