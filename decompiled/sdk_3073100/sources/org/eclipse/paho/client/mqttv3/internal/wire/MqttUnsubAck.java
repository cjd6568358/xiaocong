package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttUnsubAck extends MqttAck {
    public MqttUnsubAck(byte info, byte[] data) throws IOException {
        super((byte) 11);
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        this.msgId = dis.readUnsignedShort();
        dis.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }
}
