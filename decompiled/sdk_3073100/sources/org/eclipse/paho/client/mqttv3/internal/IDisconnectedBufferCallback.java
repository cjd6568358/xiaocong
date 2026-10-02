package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IDisconnectedBufferCallback {
    void publishBufferedMessage(BufferedMessage bufferedMessage) throws MqttException;
}
