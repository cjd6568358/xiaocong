package org.eclipse.paho.android.service;

import android.os.Binder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class MqttServiceBinder extends Binder {
    private String activityToken;
    private MqttService mqttService;

    MqttServiceBinder(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    public MqttService getService() {
        return this.mqttService;
    }

    void setActivityToken(String activityToken) {
        this.activityToken = activityToken;
    }
}
