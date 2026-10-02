package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttReceivedMessage extends MqttMessage {
    public void setMessageId(int msgId) {
        super.setId(msgId);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttMessage
    public void setDuplicate(boolean value) {
        super.setDuplicate(value);
    }
}
