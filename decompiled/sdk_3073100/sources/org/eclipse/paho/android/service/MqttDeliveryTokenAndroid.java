package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class MqttDeliveryTokenAndroid extends MqttTokenAndroid implements IMqttDeliveryToken {
    private MqttMessage message;

    MqttDeliveryTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener, MqttMessage message) {
        super(client, userContext, listener);
        this.message = message;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
    public MqttMessage getMessage() throws MqttException {
        return this.message;
    }
}
