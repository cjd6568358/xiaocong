package org.eclipse.paho.client.mqttv3.internal.wire;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class MqttAck extends MqttWireMessage {
    public MqttAck(byte type) {
        super(type);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte getMessageInfo() {
        return (byte) 0;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        return new StringBuffer(String.valueOf(super.toString())).append(" msgId ").append(this.msgId).toString();
    }
}
