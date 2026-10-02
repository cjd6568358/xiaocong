package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttPingResp extends MqttAck {
    public MqttPingResp(byte info, byte[] variableHeader) {
        super((byte) 13);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return false;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String getKey() {
        return "Ping";
    }
}
