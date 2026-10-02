package org.eclipse.paho.android.service;

import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
interface MessageStore {

    public interface StoredMessage {
        MqttMessage getMessage();

        String getMessageId();

        String getTopic();
    }

    void clearArrivedMessages(String str);

    void close();

    boolean discardArrived(String str, String str2);

    Iterator<StoredMessage> getAllArrivedMessages(String str);

    String storeArrived(String str, String str2, MqttMessage mqttMessage);
}
