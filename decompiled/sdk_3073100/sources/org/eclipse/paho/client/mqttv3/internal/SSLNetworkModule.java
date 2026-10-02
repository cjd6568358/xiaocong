package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SSLNetworkModule extends TCPNetworkModule {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private String[] enabledCiphers;
    private int handshakeTimeoutSecs;
    private String host;
    private int port;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public SSLNetworkModule(SSLSocketFactory factory, String host, int port, String resourceContext) {
        super(factory, host, port, resourceContext);
        this.host = host;
        this.port = port;
        log.setResourceName(resourceContext);
    }

    public void setEnabledCiphers(String[] enabledCiphers) {
        this.enabledCiphers = enabledCiphers;
        if (this.socket != null && enabledCiphers != null) {
            if (log.isLoggable(5)) {
                String ciphers = "";
                for (int i = 0; i < enabledCiphers.length; i++) {
                    if (i > 0) {
                        ciphers = new StringBuffer(String.valueOf(ciphers)).append(",").toString();
                    }
                    ciphers = new StringBuffer(String.valueOf(ciphers)).append(enabledCiphers[i]).toString();
                }
                log.fine(CLASS_NAME, "setEnabledCiphers", "260", new Object[]{ciphers});
            }
            ((SSLSocket) this.socket).setEnabledCipherSuites(enabledCiphers);
        }
    }

    public void setSSLhandshakeTimeout(int timeout) {
        super.setConnectTimeout(timeout);
        this.handshakeTimeoutSecs = timeout;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        super.start();
        setEnabledCiphers(this.enabledCiphers);
        int soTimeout = this.socket.getSoTimeout();
        this.socket.setSoTimeout(this.handshakeTimeoutSecs * 1000);
        ((SSLSocket) this.socket).startHandshake();
        this.socket.setSoTimeout(soTimeout);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return new StringBuffer("ssl://").append(this.host).append(":").append(this.port).toString();
    }
}
