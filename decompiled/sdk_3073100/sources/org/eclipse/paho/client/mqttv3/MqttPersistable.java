package org.eclipse.paho.client.mqttv3;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface MqttPersistable {
    byte[] getHeaderBytes() throws MqttPersistenceException;

    int getHeaderLength() throws MqttPersistenceException;

    int getHeaderOffset() throws MqttPersistenceException;

    byte[] getPayloadBytes() throws MqttPersistenceException;

    int getPayloadLength() throws MqttPersistenceException;

    int getPayloadOffset() throws MqttPersistenceException;
}
