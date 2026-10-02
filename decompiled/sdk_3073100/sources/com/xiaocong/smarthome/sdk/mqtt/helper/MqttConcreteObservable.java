package com.xiaocong.smarthome.sdk.mqtt.helper;

import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttConcreteObservable {
    private int mStatus = -1;
    private ArrayList<MqttObserver> observers;

    public void addObserver(MqttObserver observer) {
        if (this.observers == null) {
            this.observers = new ArrayList<>();
        }
        if (!this.observers.contains(this.observers)) {
            this.observers.add(observer);
        }
    }

    public void removeObserver(MqttObserver observer) {
        if (this.observers != null && this.observers.size() > 0) {
            this.observers.remove(observer);
        }
    }

    public void notifyObservers(String topic, String msg) {
        if (this.observers != null && this.observers.size() > 0) {
            for (MqttObserver observer : this.observers) {
                observer.deviceControllerReceiveMsg(topic, msg);
            }
        }
    }

    public void statusNotifyObservers(int status) {
        if (this.observers != null && this.observers.size() > 0 && this.mStatus != status) {
            this.mStatus = status;
            for (MqttObserver observer : this.observers) {
                observer.deviceControllerStatusChanged(status);
            }
        }
    }
}
