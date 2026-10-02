package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface NetworkModule {
    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    String getServerURI();

    void start() throws MqttException, IOException;

    void stop() throws IOException;
}
