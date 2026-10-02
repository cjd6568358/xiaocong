package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import org.eclipse.paho.client.mqttv3.MqttException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class LocalNetworkModule implements NetworkModule {
    static Class class$0;
    private String brokerName;
    private Object localAdapter;
    private Class localListener;

    public LocalNetworkModule(String brokerName) {
        this.brokerName = brokerName;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        if (!ExceptionHelper.isClassAvailable("com.ibm.mqttdirect.modules.local.bindings.localListener")) {
            throw ExceptionHelper.createMqttException(32103);
        }
        try {
            this.localListener = Class.forName("com.ibm.mqttdirect.modules.local.bindings.localListener");
            Class cls = this.localListener;
            Class<?>[] clsArr = new Class[1];
            Class<?> cls2 = class$0;
            if (cls2 == null) {
                try {
                    cls2 = Class.forName("java.lang.String");
                    class$0 = cls2;
                } catch (ClassNotFoundException e) {
                    throw new NoClassDefFoundError(e.getMessage());
                }
            }
            clsArr[0] = cls2;
            Method connect_m = cls.getMethod("connect", clsArr);
            this.localAdapter = connect_m.invoke(null, this.brokerName);
        } catch (Exception e2) {
        }
        if (this.localAdapter == null) {
            throw ExceptionHelper.createMqttException(32103);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        try {
            Method m = this.localListener.getMethod("getClientInputStream", new Class[0]);
            InputStream stream = (InputStream) m.invoke(this.localAdapter, new Object[0]);
            return stream;
        } catch (Exception e) {
            return null;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        try {
            Method m = this.localListener.getMethod("getClientOutputStream", new Class[0]);
            OutputStream stream = (OutputStream) m.invoke(this.localAdapter, new Object[0]);
            return stream;
        } catch (Exception e) {
            return null;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() throws IOException {
        if (this.localAdapter != null) {
            try {
                Method m = this.localListener.getMethod("close", new Class[0]);
                m.invoke(this.localAdapter, new Object[0]);
            } catch (Exception e) {
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return new StringBuffer("local://").append(this.brokerName).toString();
    }
}
