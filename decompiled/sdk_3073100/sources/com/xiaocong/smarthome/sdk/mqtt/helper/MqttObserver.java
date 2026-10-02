package com.xiaocong.smarthome.sdk.mqtt.helper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface MqttObserver {
    void deviceControllerReceiveMsg(String str, String str2);

    void deviceControllerStatusChanged(int i);
}
