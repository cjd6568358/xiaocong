package org.eclipse.paho.client.mqttv3;

import java.util.Enumeration;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface MqttClientPersistence {
    void clear() throws MqttPersistenceException;

    void close() throws MqttPersistenceException;

    boolean containsKey(String str) throws MqttPersistenceException;

    MqttPersistable get(String str) throws MqttPersistenceException;

    Enumeration keys() throws MqttPersistenceException;

    void open(String str, String str2) throws MqttPersistenceException;

    void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException;

    void remove(String str) throws MqttPersistenceException;
}
