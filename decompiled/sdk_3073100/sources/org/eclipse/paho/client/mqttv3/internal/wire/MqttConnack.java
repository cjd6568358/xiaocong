package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttConnack extends MqttAck {
    private int returnCode;
    private boolean sessionPresent;

    public MqttConnack(byte info, byte[] variableHeader) throws IOException {
        super((byte) 2);
        ByteArrayInputStream bais = new ByteArrayInputStream(variableHeader);
        DataInputStream dis = new DataInputStream(bais);
        this.sessionPresent = (dis.readUnsignedByte() & 1) == 1;
        this.returnCode = dis.readUnsignedByte();
        dis.close();
    }

    public int getReturnCode() {
        return this.returnCode;
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
        return "Con";
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttAck, org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        return new StringBuffer(String.valueOf(super.toString())).append(" session present:").append(this.sessionPresent).append(" return code: ").append(this.returnCode).toString();
    }
}
