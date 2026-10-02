package com.xiaocong.smarthome.sdk.mqtt.helper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCMqttOberserverManager {
    private static XCMqttOberserverManager instance;
    private MqttConcreteObservable observable = new MqttConcreteObservable();

    private XCMqttOberserverManager() {
    }

    public static XCMqttOberserverManager getDefault() {
        if (instance == null) {
            synchronized (XCMqttOberserverManager.class) {
                if (instance == null) {
                    instance = new XCMqttOberserverManager();
                }
            }
        }
        return instance;
    }

    public void register(MqttObserver observer) {
        this.observable.addObserver(observer);
    }

    public void unregister(MqttObserver observer) {
        this.observable.removeObserver(observer);
    }

    public void post(String topic, String msg) {
        this.observable.notifyObservers(topic, msg);
    }

    public void status(int status) {
        this.observable.statusNotifyObservers(status);
    }
}
