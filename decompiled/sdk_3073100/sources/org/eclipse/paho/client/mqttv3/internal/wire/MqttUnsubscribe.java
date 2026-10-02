package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttUnsubscribe extends MqttWireMessage {
    private int count;
    private String[] names;

    public MqttUnsubscribe(byte info, byte[] data) throws IOException {
        super((byte) 10);
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        this.msgId = dis.readUnsignedShort();
        this.count = 0;
        this.names = new String[10];
        boolean end = false;
        while (!end) {
            try {
                this.names[this.count] = decodeUTF8(dis);
            } catch (Exception e) {
                end = true;
            }
        }
        dis.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(" names:[");
        for (int i = 0; i < this.count; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(new StringBuffer("\"").append(this.names[i]).append("\"").toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte getMessageInfo() {
        return (byte) ((this.duplicate ? 8 : 0) | 2);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] getVariableHeader() throws MqttException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(this.msgId);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException(ex);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public byte[] getPayload() throws MqttException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (int i = 0; i < this.names.length; i++) {
                encodeUTF8(dos, this.names[i]);
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException(ex);
        }
    }
}
