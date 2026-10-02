package org.eclipse.paho.client.mqttv3;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface MqttCallback {
    void connectionLost(Throwable th);

    void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken);

    void messageArrived(String str, MqttMessage mqttMessage) throws Exception;
}
