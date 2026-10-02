package org.eclipse.paho.client.mqttv3;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttDeliveryToken extends MqttToken implements IMqttDeliveryToken {
    public MqttDeliveryToken() {
    }

    public MqttDeliveryToken(String logContext) {
        super(logContext);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
    public MqttMessage getMessage() throws MqttException {
        return this.internalTok.getMessage();
    }

    protected void setMessage(MqttMessage msg) {
        this.internalTok.setMessage(msg);
    }
}
